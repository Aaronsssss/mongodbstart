package org.hbrs.ia.model;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class SalesMan {
    private String firstname;
    private String lastname;
    private Integer sid;
    private List<SocialPerformanceRecord> socialPerformanceRecords;

    public SalesMan(String firstname, String lastname, Integer sid) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.sid = sid;
        this.socialPerformanceRecords = new ArrayList<>();
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getId() {
        return sid;
    }

    public void setId(Integer sid) {
        this.sid = sid;
    }

    public List<SocialPerformanceRecord> getSocialPerformanceRecords() {
        return socialPerformanceRecords;
    }

    public void addSocialPerformanceRecord(SocialPerformanceRecord record) {
        this.socialPerformanceRecords.add(record);
    }

    public Document toDocument() {
        org.bson.Document document = new Document();
        document.append("firstname" , this.firstname );
        document.append("lastname" , this.lastname );
        document.append("sid" , this.sid);
        document.append("socialperformancerecord", new ArrayList<>());
        return document;
    }
}
