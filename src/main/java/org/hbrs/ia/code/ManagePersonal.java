package org.hbrs.ia.code;

import org.hbrs.ia.model.SalesMan;
import org.hbrs.ia.model.SocialPerformanceRecord;
import java.util.List;

/**
 * Code lines are commented for suppressing compile errors.
 * Are there any CRUD-operations missing?
 */
public interface ManagePersonal {
    public void createSalesMan( SalesMan record );

    public void addSocialPerformanceRecord(SocialPerformanceRecord record , SalesMan salesMan );
    // Remark: an SocialPerformanceRecord corresponds to part B of a bonus sheet

    public SalesMan readSalesMan( int sid );

    public List<SalesMan> readAllSalesMen();

    //public List<SocialPerformanceRecord> readSocialPerformanceRecords( SalesMan salesMan );
    // Remark: How do you integrate the year?

    public SocialPerformanceRecord readSocialPerformanceRecord( SalesMan salesMan, int year);

    public void updateSalesMan( SalesMan record );

    public void updateSocialPerformanceRecord( SocialPerformanceRecord record , SalesMan salesMan );

    public void deleteSalesMan( SalesMan record );

    public void deleteSocialPerformanceRecord( SocialPerformanceRecord record , SalesMan salesMan );

}
