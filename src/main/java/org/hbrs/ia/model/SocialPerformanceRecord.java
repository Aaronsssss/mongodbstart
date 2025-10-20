package org.hbrs.ia.model;

import org.bson.Document;


public class SocialPerformanceRecord {
    private Integer year;
    private Integer leadershipCompetence;
    private Integer opennessToEmployee;
    private Integer socialBehavior;
    private Integer attitude;
    private Integer communicationSkills;
    private Integer integrityToCompany;

    SocialPerformanceRecord(Integer year, Integer leadershipCompetence, Integer opennessToEmployee, Integer socialBehavior, Integer attitude, Integer communicationSkills, Integer integrityToCompany) {
        this.year = year;
        this.leadershipCompetence = leadershipCompetence;
        this.opennessToEmployee = opennessToEmployee;
        this.socialBehavior = socialBehavior;
        this.attitude = attitude;
        this.communicationSkills = communicationSkills;
        this.integrityToCompany = integrityToCompany;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getLeadershipCompetence() {
        return leadershipCompetence;
    }

    public void setLeadershipCompetence(Integer leadershipCompetence) {
        this.leadershipCompetence = leadershipCompetence;
    }

    public Integer getOpennessToEmployee() {
        return opennessToEmployee;
    }

    public void setOpennessToEmployee(Integer opennessToEmployee) {
        this.opennessToEmployee = opennessToEmployee;
    }

    public Integer getSocialBehavior() {
        return socialBehavior;
    }

    public void setSocialBehavior(Integer socialBehavior) {
        this.socialBehavior = socialBehavior;
    }

    public Integer getAttitude() {
        return attitude;
    }

    public void setAttitude(Integer attitude) {
        this.attitude = attitude;
    }

    public Integer getCommunicationSkills() {
        return communicationSkills;
    }

    public void setCommunicationSkills(Integer communicationSkills) {
        this.communicationSkills = communicationSkills;
    }

    public Integer getIntegrityToCompany() {
        return integrityToCompany;
    }

    public void setIntegrityToCompany(Integer integrityToCompany) {
        this.integrityToCompany = integrityToCompany;
    }

    public Document toDocument() {
        Document document = new Document();
        document.append("year", this.year);
        document.append("leadershipCompetence", this.leadershipCompetence);
        document.append("opennessToEmployee", this.opennessToEmployee);
        document.append("socialBehavior", this.socialBehavior);
        document.append("attitude", this.attitude);
        document.append("communicationSkills", this.communicationSkills);
        document.append("integrityToCompany", this.integrityToCompany);
        return document;
    }
    

}
