package cprv.customer.info.service;

import java.util.List;
import java.util.UUID;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cprv.customer.info.model.Transaction;
import cprv.customer.info.repository.TransactionRepo;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepo transRepo;

    public List<String> postTransactionDetails(Transaction trans) {
        trans.setId(UUID.randomUUID().toString());
        Dataset<Row> transDf = transRepo.postTransactionDetails(trans);
        List<String> transJsonList = transDf.toJSON().collectAsList();
        return transJsonList;
    }

    public List<String> getTransactionDetails(String id) {
        Dataset<Row> transQueryDf = transRepo.getTransactionDetails(id);
        List<String> transQueryList = transQueryDf.toJSON().collectAsList();
        return transQueryList;
    }

    public List<String> putTransactionDetails(Transaction trans, String id) {
        trans.setId(id);
        Dataset<Row> transDf = transRepo.putTransactionDetails(trans, id);
        List<String> transJsonList = transDf.toJSON().collectAsList();
        return transJsonList;
    }

    public List<String> deleteTransactionDetails(String id) {
        Dataset<Row> transDf = transRepo.deleteTransactionDetails(id);
        List<String> transJsonList = transDf.toJSON().collectAsList();
        return transJsonList;
    }
    
}
