package org.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ManagePersonalController implements ManagePersonal{

    private MongoClient client;
    private MongoDatabase supermongo;
    private MongoCollection<Document> salesmen;


    public ManagePersonalController() {
        client = new MongoClient("localhost", 27017);
        supermongo = client.getDatabase("highperformance");
        salesmen = supermongo.getCollection("salesmen");
    }

    /**
     * For Testing class
     * @param databaseName
     */
    public void setDatabase(String databaseName) {
        supermongo = client.getDatabase(databaseName);
        salesmen = supermongo.getCollection("salesmen");
    }


    @Override
    public void createSalesMan(SalesMan record) {
        salesmen.insertOne(record.toDocument());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        // Create the update operation: push the new SocialPerformanceRecord to the array
        Document update = new Document("$push",
                new Document("socialperformancerecord", record.toDocument())
        );

        // If the "socialperformancerecord" field does not exist, ensure it is created as an array
        Document setOnInsert = new Document("$setOnInsert",
                new Document("socialperformancerecord", new ArrayList<>())
        );

        // Perform an upsert operation to ensure the field exists
        salesmen.updateOne(
                new Document("sid", salesMan.getId()), // Find the document for the SalesMan
                new Document("$setOnInsert", setOnInsert) // Ensure the field exists as an array
        );

        // Add the record to the "socialperformancerecord" array
        salesmen.updateOne(
                new Document("sid", salesMan.getId()),
                update
        );

        // Update the in-memory salesMan object
        salesMan.addSocialPerformanceRecord(record);
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        Document newDocument = this.salesmen.find(new Document("sid" , sid)).first();
        this.salesmen.find(eq("sid", "123"));
        assert newDocument != null;
        return new SalesMan(newDocument.get("firstname").toString() , newDocument.get("lastname").toString() , sid);
    }

    @Override
    public List<SalesMan> readAllSalesMen() {
        return salesmen.find()
                .map(document -> new SalesMan(
                        document.getString("firstname"),
                        document.getString("lastname"),
                        document.getInteger("sid")
                ))
                .into(new ArrayList<>());
    }

    @Override
    public SocialPerformanceRecord readSocialPerformanceRecord(SalesMan salesMan, int year) {
        // Fetch the document for the specified salesman
        Document newDocument = salesmen.find(new Document("sid", salesMan.getId())).first();
        
        // Check if the document exists
        if (newDocument == null) {
            // Handle the case where the salesman's document does not exist
            System.err.println("No document found for salesman with ID: " + salesMan.getId());
            return null;
        }

        // Extract and cast the "socialperformancerecord" field if it exists
        List<Document> socialRecords = newDocument.getList("socialperformancerecord", Document.class);

        if (socialRecords == null) {
            System.err.println("No social performance records found for salesman with ID: " + salesMan.getId());
            return null;
        }

        // Iterate through the records to find the matching year
        for (Document record : socialRecords) {
            if (record.getInteger("year") == year) {
                // Convert the record document into a SocialPerformanceRecord instance
                return SocialPerformanceRecord.fromDocument(record);
            }
        }

        // If no record is found for the specified year
        System.err.println("No social performance record found for year: " + year + " for salesman with ID: " + salesMan.getId());
        return null;
    }

    @Override
    public void updateSalesMan(SalesMan record) {
        Document updateDoc = new Document("$set", new Document()
                .append("firstname", record.getFirstname())
                .append("lastname", record.getLastname())
                .append("sid", record.getId()));

        salesmen.updateOne(
                new Document("sid", record.getId()),
                updateDoc
        );
    }

    @Override
    public void updateSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        Document oldDoc = new Document("sid", salesMan.getId())
            .append("socialperformancerecord.year", record.getYear());

        Document updateDoc = new Document("$set",
            new Document("socialperformancerecord.$", record.toDocument()));
    
        salesmen.updateOne(oldDoc, updateDoc);
    }

    @Override
    public void deleteSalesMan(SalesMan record) {
        salesmen.deleteOne(new Document("sid", record.getId()));
    }

    @Override
    public void deleteSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        Document updateDoc = new Document("$pull",
                new Document("socialperformancerecord",
                new Document("year", record.getYear())
                )
        );

        salesmen.updateOne(
                new Document("sid", salesMan.getId()),
                updateDoc
        );
    }
}