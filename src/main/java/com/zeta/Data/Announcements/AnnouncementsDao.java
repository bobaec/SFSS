package com.zeta.Data.Announcements;

import com.zeta.Models.Announcement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Repository
public class AnnouncementsDao implements AnnouncementsData {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AnnouncementsDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean addAnnouncement(Announcement announcement) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = dateFormat.getCalendar().getTime();
        java.sql.Timestamp sqlDate = new java.sql.Timestamp(date.getTime());

        try {
            String sql =
                    "INSERT INTO Announcement (User, Title, Message, Date, Campus) VALUES (?, ?, ?, ?, ?)";

            jdbcTemplate.update(sql, announcement.getUsername(), announcement.getTitle(),
                    announcement.getMessage(), sqlDate, announcement.getCampus().toString());
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Announcement showAnnouncements(int ID) {
        Announcement announcement = null;
        try {
            String sql = "SELECT * FROM Announcement WHERE ID = ?";

            announcement = jdbcTemplate.queryForObject(sql, new Object[]{ID}, new AnnouncementsRowMapper());

            return announcement;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public List<Announcement> showAllAnnouncements() {
        List<Announcement> list;
        try {
            String sql = "SELECT * FROM Announcement";
            list = jdbcTemplate.query(sql, new AnnouncementsRowMapper());
        } catch (Exception e) {

            return null;
        }

        Collections.sort(list, new Comparator<Announcement>() {
            @Override
            public int compare(Announcement o1, Announcement o2) {
                return o2.getDate().compareTo(o1.getDate());
            }
        });

        return list;
    }
}