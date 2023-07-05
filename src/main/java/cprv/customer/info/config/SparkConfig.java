package cprv.customer.info.config;

import org.apache.spark.sql.SparkSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SparkConfig {

    @Value("${app.name:cprv-customer}")
    private String appName;

    @Value("${master.uri:local}")
    private String masterUri;

    @Bean
    public SparkSession sparkSession() {
        SparkSession spark = SparkSession.builder()
            // .sparkContext(javaSparkContext().sc())
            .appName(appName)
            .master(masterUri)
            .getOrCreate();
        return spark;
    }
    
}
