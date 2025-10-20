package org.hbrs.ia.code;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;

import java.util.ArrayList;
import java.util.List;

public class ManagePersonalController implements ManagePersonal{

    private MongoClient client;
    private MongoDatabase supermongo;
    private MongoCollection<Document> salesmen;


    public ManagePersonalController() {
        client = new MongoClient("localhost", 27017);
        supermongo = client.getDatabase("highperformance");
        salesmen = supermongo.getCollection("salesmen");
    }


    @Override
    public void createSalesMan(SalesMan record) {
        salesmen.insertOne(record.toDocument());
    }

    @Override
    public void addSocialPerformanceRecord(SocialPerformanceRecord record, SalesMan salesMan) {
        Document update = new Document("$push", 
            new Document("socialperformancerecord", record.toDocument())
        );
        
        salesmen.updateOne(
            new Document("sid", salesMan.getId()),
            update
        );
    }

    @Override
    public SalesMan readSalesMan(int sid) {
        Document newDocument = this.salesmen.find(new Document("sid" , sid)).first();
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
    public List<SocialPerformanceRecord> readSocialPerformanceRecord(SalesMan salesMan) {
        Document newDocument = salesmen.find(new Document("sid" , salesMan.getId())).first();
        assert newDocument != null;
        SalesMan salesman = new SalesMan(newDocument.get("firstname").toString() , newDocument.get("lastname").toString() , salesMan.getId());
        
        return List.of();
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
