package org.hbrs.mongodb.test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hbrs.ia.code.ManagePersonalController;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ManagePersonalControllerTest {

    ManagePersonalController controller;
    SalesMan salesMan;
    SocialPerformanceRecord record;


    @BeforeEach
    void setUp() {
        controller = new ManagePersonalController();
        controller.setDatabase("HooverDBTest");
        salesMan = new SalesMan("John", "Doe", 1001);
        record = new SocialPerformanceRecord(2025, 4, 5, 3, 5, 4, 4);
    }

    @AfterEach
    void cleanUp() {
        controller.deleteSalesMan(salesMan);
    }

    @Test
    void insertSalesMan() {
        controller.createSalesMan(salesMan);
        SalesMan dbSalesMan = controller.readSalesMan(salesMan.getId());
        assertEquals(salesMan.getId(), dbSalesMan.getId());
    }

    @Test
    void inertSocialPerformanceRecord() {
        controller.createSalesMan(salesMan);
        controller.addSocialPerformanceRecord(record, salesMan);

        SocialPerformanceRecord dbSocialPerformanceRecord = controller.readSocialPerformanceRecord(salesMan, 2025);
        assertEquals(record.getYear(), dbSocialPerformanceRecord.getYear());
        assertEquals(record.getLeadershipCompetence(), dbSocialPerformanceRecord.getLeadershipCompetence());
        assertEquals(record.getOpennessToEmployee(), dbSocialPerformanceRecord.getOpennessToEmployee());
        assertEquals(record.getSocialBehavior(), dbSocialPerformanceRecord.getSocialBehavior());
        assertEquals(record.getAttitude(), dbSocialPerformanceRecord.getAttitude());
        assertEquals(record.getCommunicationSkills(), dbSocialPerformanceRecord.getCommunicationSkills());
        assertEquals(record.getIntegrityToCompany(), dbSocialPerformanceRecord.getIntegrityToCompany());
    }

    @Test
    void updateSalesMan() {
        controller.createSalesMan(salesMan);
        salesMan.setLastname("Smith");
        controller.updateSalesMan(salesMan);
    }
}