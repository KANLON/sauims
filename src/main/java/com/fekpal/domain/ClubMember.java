package com.fekpal.domain;

import java.sql.Timestamp;

public class ClubMember extends BasePOJO {

    private int id;

    private Person person;

    private Club club;

    private int memberDuty;

    private int userState;

    private Timestamp joinTime;

    private Timestamp leaveTime;

    private String clubDepartment;

    private int available;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public int getMemberDuty() {
        return memberDuty;
    }

    public void setMemberDuty(int memberDuty) {
        this.memberDuty = memberDuty;
    }

    public int getUserState() {
        return userState;
    }

    public void setUserState(int userState) {
        this.userState = userState;
    }

    public Timestamp getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Timestamp joinTime) {
        this.joinTime = joinTime;
    }

    public Timestamp getLeaveTime() {
        return leaveTime;
    }

    public void setLeaveTime(Timestamp leaveTime) {
        this.leaveTime = leaveTime;
    }

    public String getClubDepartment() {
        return clubDepartment;
    }

    public void setClubDepartment(String clubDepartment) {
        this.clubDepartment = clubDepartment;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }
}
