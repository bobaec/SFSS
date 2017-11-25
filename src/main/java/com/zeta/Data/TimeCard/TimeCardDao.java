package com.zeta.Data.TimeCard;

import com.zeta.Data.Task.TaskRowMapper;
import com.zeta.Models.Task;
import com.zeta.Models.TimeCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class TimeCardDao implements TimeCardData {

    private JdbcTemplate jdbcTemplate;
    private Connection con;

    @Autowired
    public TimeCardDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        try {
            this.con = dataSource.getConnection();
        } catch (SQLException e) {
            // TODO: implement proper error handling
            //If this error throws then this means that the database can't be connected to at all
            System.out.println(e.getErrorCode());
        }
    }

    @Override
    public boolean saveTimeCard(TimeCard timeCard) {
        // 0 is isSubmitted in integer form, needs to be false because timecard should not be submitted before this
        return updateRecords(timeCard, 0);
    }

    @Override
    public boolean submitTimeCard(TimeCard timeCard) {
        // 1 is isSumbitted in integer form, needs to be true because we submit the timecard via this method
        return updateRecords(timeCard, 1);
    }

    private boolean updateRecords(TimeCard timeCard, int isSubmitted) {
        // If timecard is already submitted it should not be allowed to be saved/submitted again
        if (timeCard.isTimeCardSubmitted())
            return false;

        if (timeCardRecordExist(timeCard))
            return updateTimeCard(timeCard);

        return addNewTimeCard(timeCard, isSubmitted);
    }

    // Returns true if user-shift combo record exists
    private boolean timeCardRecordExist(TimeCard timeCard) {
        String result;
        try {
            result = jdbcTemplate.queryForObject(
                    "select Campus from Shift where User = ? and ID = ?",
                    new Object[]{timeCard.getUsername(), timeCard.getShiftId()},
                    (resultSet, i) -> resultSet.getString("Campus"));

            // if record is found then string is obtained, otherwise null
            return result == null;

        } catch (Exception e) {
            return false;
        }
    }

    private boolean addNewTimeCard(TimeCard timeCard, int isSubmitted) {
        try {
            String shiftSQL = "update Shift set Campus = ?, Location = ?, Notes = ?, isTimeCardSubmitted = ? " +
                    "where User = ? and ID = ?";
            String userTaskSQL = "insert into UserTask (User, Shift, Task, Count) values (?, ?, ?, ?)";

            con.setAutoCommit(false);

            PreparedStatement updateShift = con.prepareStatement(shiftSQL);
            updateShift.setString(1, timeCard.getCampus().toString());
            updateShift.setString(2, timeCard.getLocation());
            updateShift.setString(3, timeCard.getNotes());
            updateShift.setInt(4, isSubmitted);
            updateShift.setString(5, timeCard.getUsername());
            updateShift.setLong(6, timeCard.getShiftId());

            updateShift.execute();

            insertTasksIntoUserTask(timeCard, userTaskSQL);

            con.commit();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean updateTimeCard(TimeCard timeCard) {
        try {

            String shiftSQL = "update Shift set Location = ?, Notes = ?, isTimeCardSubmitted = ? where User = ? and ID = ?";
            String userTaskSQL = "insert into UserTask (User, Shift, Task, Count) values (?, ?, ?, ?)";

            con.setAutoCommit(false);

            PreparedStatement updateShift = con.prepareStatement(shiftSQL);
            updateShift.setString(1, timeCard.getLocation());
            updateShift.setString(2, timeCard.getNotes());
            updateShift.setBoolean(3, timeCard.isTimeCardSubmitted());
            updateShift.setString(4, timeCard.getUsername());
            updateShift.setLong(5, timeCard.getShiftId());

            updateShift.execute();

            // Remove data for that specific user-shift combination
            clearUserTaskRecords(timeCard.getUsername(), timeCard.getShiftId());

            // Enter new records for that user-shift combination
            insertTasksIntoUserTask(timeCard, userTaskSQL);

            con.commit();

        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public TimeCard getTimeCard(String username, long shiftId) {
        TimeCard timeCard = null;
        try {
            String shiftSQL = "select Campus, Location, Notes, isTimeCardSubmitted from Shift where User = ? and ID = ?";
            String taskSQL = "select Name from Task where isDeactivated = 0 order by Name asc";
            String userTaskSQL = "select Task, Count from UserTask where User = ? and Shift = ? order by Task asc";

            // Create time card and set campus, location, notes
            timeCard = jdbcTemplate.queryForObject(shiftSQL, new Object[]{username, shiftId}, new TimeCardRowMapper());

            // Get list of all tasks
            List<Task> allTasks = jdbcTemplate.query(taskSQL, new TaskRowMapper());

            // Get list of all tasks user did in particular shift
            List<Task> userTasks = jdbcTemplate.query(
                    userTaskSQL, new Object[]{username, shiftId}, new UserTaskMapper());

            // Populates all task counts with counts from user tasks
            for (Task task : allTasks) {
                for (Task userTask : userTasks) {
                    if (task.getTaskName().equals(userTask.getTaskName())) {
                        task.setCount(userTask.getCount());
                    }
                }
            }

            timeCard.setUsername(username);
            timeCard.setShiftId(shiftId);
            timeCard.setTasks(allTasks);

        } catch (Exception e) {
            return null;
        }
        return timeCard;
    }

    private void clearUserTaskRecords(String username, long shiftId) throws SQLException {

        String sql = "delete from UserTask where User = ? and Shift = ?";

        PreparedStatement removeRecords = con.prepareStatement(sql);
        removeRecords.setString(1, username);
        removeRecords.setLong(2, shiftId);

        removeRecords.execute();
    }

    // For each task update count and set if count is not 0
    private void insertTasksIntoUserTask(TimeCard timeCard, String sql) throws SQLException {

        for (Task task : timeCard.getTasks()) {
            if (task.getCount() == 0) {
                continue;
            }

            PreparedStatement insertUserTask = con.prepareStatement(sql);
            insertUserTask.setString(1, timeCard.getUsername());
            insertUserTask.setLong(2, timeCard.getShiftId());
            insertUserTask.setString(3, task.getTaskName());
            insertUserTask.setInt(4, task.getCount());

            insertUserTask.execute();
        }
    }
}