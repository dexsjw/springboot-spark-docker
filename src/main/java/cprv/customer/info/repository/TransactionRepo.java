package cprv.customer.info.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cprv.customer.info.model.Transaction;

@Repository
public class TransactionRepo {
   
    @Autowired
    private SparkSession spark;
    private Dataset<Row> transDf;
    private String jsonFilePath = "src/main/resources/sample-transaction.json";

    private Dataset<Row> generateDfFromJson() {
        Dataset<Row> transDf = spark.read()
            .option("multiLine",true)
            .json(jsonFilePath);
        return transDf;
    }

    private void istransDfNull() {
        if (transDf == null) {
            transDf = generateDfFromJson();
        }
    }

    public Dataset<Row> postTransactionDetails(Transaction trans) {
        istransDfNull();
        List<Transaction> transList = new ArrayList<>();
        transList.add(trans);
        Dataset<Row> df = spark.createDataFrame(transList, Transaction.class);
        transDf = transDf.unionAll(df);
        transDf.show();
        return transDf;
    }

    public Dataset<Row> getTransactionDetails(String id) {
        istransDfNull();
        Column idCol = new Column("id");
        Dataset<Row> transQueryDf = transDf.where(idCol.equalTo(id));
        transQueryDf.show();
        return transQueryDf;
    }

    public Dataset<Row> putTransactionDetails(Transaction trans, String id) {
        istransDfNull();
        Column idCol = new Column("id");
        if (!transDf.where(idCol.equalTo(id)).isEmpty()) {
            Dataset<Row> tempTransDf = transDf.where(idCol.notEqual(id));
            List<Transaction> transList = new ArrayList<>();
            transList.add(trans);
            Dataset<Row> df = spark.createDataFrame(transList, Transaction.class);
            transDf = tempTransDf.unionAll(df);
            tempTransDf.show();
            df.show();
            transDf.show();
        }
        return transDf;
    }

    public Dataset<Row> deleteTransactionDetails(String id) {
        istransDfNull();
        Column idCol = new Column("id");
        transDf = transDf.where(idCol.notEqual(id));
        return transDf;
    }
    
}
