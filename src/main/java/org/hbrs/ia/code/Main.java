package org.hbrs.ia.code;

import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        // Create instance of controller
        ManagePersonalController controller = new ManagePersonalController();

        // Create salesman and insert into database
        SalesMan salesMan = new SalesMan("John", "Doe", 1001);
        controller.createSalesMan(salesMan);
        System.out.println("Inserted salesman: " + salesMan.getFirstname() + " " + salesMan.getLastname());

        // Read same salesman from database
        SalesMan foundSalesMan = controller.readSalesMan(salesMan.getId());
        System.out.println("Found salesman: " + foundSalesMan.getFirstname() + " " + foundSalesMan.getLastname());

        // Update salesman information
        salesMan.setFirstname("Jonathan");
        controller.updateSalesMan(salesMan);
        System.out.println("Updated salesman: " + salesMan.getFirstname() + " " + salesMan.getLastname());

        // Add social performance record
        SocialPerformanceRecord record = new SocialPerformanceRecord(2025, 4, 3, 4, 3, 5, 4);
        salesMan.setSocialPerformanceRecords(new ArrayList<>());
        controller.addSocialPerformanceRecord(record, salesMan);
        System.out.println("Added social performance record for year: " + record.getYear());


        // Delete salesman from database
        controller.deleteSalesMan(salesMan);
        System.out.println("Deleted salesman: " + salesMan.getFirstname() + " " + salesMan.getLastname());

    }
}
