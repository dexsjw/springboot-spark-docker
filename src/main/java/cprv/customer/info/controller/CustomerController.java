package cprv.customer.info.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import cprv.customer.info.model.Customer;
import cprv.customer.info.service.CustomerService;

@RestController
@RequestMapping(path="/customer", produces=MediaType.APPLICATION_JSON_VALUE)
public class CustomerController {

    Gson gson = new Gson();

    @Autowired
    private CustomerService custSvc;

    @PostMapping(path="/post", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postCustomerDetails(@RequestBody String payload) {
        Customer cust = gson.fromJson(payload, Customer.class);
        List<String> custJsonList = custSvc.postCustomerDetails(cust);
        String resp = gson.toJson(custJsonList);
        return ResponseEntity.ok("POST Customer Success!\n" + resp);
    }

    @GetMapping(path="/get/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getCustomerDetails(@PathVariable String id) {
        List<String> custQueryList = custSvc.getCustomerDetails(id);
        String resp = gson.toJson(custQueryList);
        return ResponseEntity.ok("GET Customer Success!\n" + resp);
    }

    @PutMapping(path="/put/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putCustomerDetails(@RequestBody String payload, @PathVariable String id) {
        Customer cust = gson.fromJson(payload, Customer.class);
        List<String> custJsonList = custSvc.putCustomerDetails(cust, id);
        String resp = gson.toJson(custJsonList);
        return ResponseEntity.ok("PUT Customer Success!\n" + resp);
    }

    @DeleteMapping(path="/delete/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteCustomerDetails(@PathVariable String id) {
        List<String> custJsonList = custSvc.deleteCustomerDetails(id);
        String resp = gson.toJson(custJsonList);
        return ResponseEntity.ok("DELETE Customer Success!\n" + resp);
    }
    
}
