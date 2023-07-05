package cprv.customer.info.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Transaction {

    private String id;
    private String custId;
    private String name;
    private double amount;
    
}
