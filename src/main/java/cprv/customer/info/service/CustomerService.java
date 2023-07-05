package cprv.customer.info.service;

import java.util.List;
import java.util.UUID;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cprv.customer.info.model.Customer;
import cprv.customer.info.repository.CustomerRepo;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo custRepo;

    public List<String> postCustomerDetails(Customer cust) {
        cust.setId(UUID.randomUUID().toString());
        Dataset<Row> custDf = custRepo.postCustomerDetails(cust);
        List<String> custJsonList = custDf.toJSON().collectAsList();
        return custJsonList;
    }

    public List<String> getCustomerDetails(String id) {
        Dataset<Row> custQueryDf = custRepo.getCustomerDetails(id);
        List<String> custQueryList = custQueryDf.toJSON().collectAsList();
        return custQueryList;
    }

    public List<String> putCustomerDetails(Customer cust, String id) {
        cust.setId(id);
        Dataset<Row> custDf = custRepo.putCustomerDetails(cust, id);
        List<String> custJsonList = custDf.toJSON().collectAsList();
        return custJsonList;
    }

    public List<String> deleteCustomerDetails(String id) {
        Dataset<Row> custDf = custRepo.deleteCustomerDetails(id);
        List<String> custJsonList = custDf.toJSON().collectAsList();
        return custJsonList;
    }
    
}
