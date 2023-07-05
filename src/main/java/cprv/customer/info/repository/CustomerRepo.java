package cprv.customer.info.repository;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.Column;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cprv.customer.info.model.Customer;

@Repository
public class CustomerRepo {
    
    @Autowired
    private SparkSession spark;
    private Dataset<Row> custDf;
    private String jsonFilePath = "src/main/resources/sample-customer.json";

    private Dataset<Row> generateDfFromJson() {
        Dataset<Row> custDf = spark.read()
            .option("multiLine",true)
            .json(jsonFilePath);
        return custDf;
    }

    private void isCustDfNull() {
        if (custDf == null) {
            custDf = generateDfFromJson();
        }
    }

    public Dataset<Row> postCustomerDetails(Customer cust) {
        isCustDfNull();
        List<Customer> custList = new ArrayList<>();
        custList.add(cust);
        Dataset<Row> df = spark.createDataFrame(custList, Customer.class);
        custDf = custDf.unionAll(df);
        custDf.show();
        return custDf;
    }

    public Dataset<Row> getCustomerDetails(String id) {
        isCustDfNull();
        Column idCol = new Column("id");
        Dataset<Row> custQueryDf = custDf.where(idCol.equalTo(id));
        custQueryDf.show();
        return custQueryDf;
    }

    public Dataset<Row> putCustomerDetails(Customer cust, String id) {
        isCustDfNull();
        Column idCol = new Column("id");
        if (!custDf.where(idCol.equalTo(id)).isEmpty()) {
            Dataset<Row> tempCustDf = custDf.where(idCol.notEqual(id));
            List<Customer> custList = new ArrayList<>();
            custList.add(cust);
            Dataset<Row> df = spark.createDataFrame(custList, Customer.class);
            custDf = tempCustDf.unionAll(df);
            tempCustDf.show();
            df.show();
            custDf.show();
        }
        return custDf;
    }

    public Dataset<Row> deleteCustomerDetails(String id) {
        isCustDfNull();
        Column idCol = new Column("id");
        custDf = custDf.where(idCol.notEqual(id));
        return custDf;
    }
    
}
