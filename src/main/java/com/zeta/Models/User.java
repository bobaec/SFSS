package com.zeta.Models;

import javax.persistence.*;

/*
 * com.zeta.Models.User class
 */
@Entity
public class User {
    @Id
    @Column(nullable = false)
    private String sfuId;

    @Column(nullable = true)
    private long studentNumber;

    @Column(nullable = true)
    private String name;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private long phoneNumber;

    //TODO: Uncomment and implement this
    //private List<User> contacts = new ArrayList<User>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Role role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private Campus campus;

    @Column(nullable = true)
    private long accountCode;

    public User() { } //Required by JPA

    public User(String sfu_id, long studentNumber, String name, String email, long phoneNumber, Role role, Campus campus, long accountCode) {
        this.sfuId = sfu_id;
        this.studentNumber = studentNumber;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.campus = campus;
        this.accountCode = accountCode;
    }

    public String getSfuId() {
        return sfuId;
    }

    public void setSfuId(String sfuId) {
        this.sfuId = sfuId;
    }

    public long getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(long studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Campus getCampus() {
        return campus;
    }

    public void setCampus(Campus campus) {
        this.campus = campus;
    }

    public long getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(long accountCode) {
        this.accountCode = accountCode;
    }
}