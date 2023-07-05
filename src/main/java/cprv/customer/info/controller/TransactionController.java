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

import cprv.customer.info.model.Transaction;
import cprv.customer.info.service.TransactionService;

@RestController
@RequestMapping(path="/transaction", produces=MediaType.APPLICATION_JSON_VALUE)
public class TransactionController {

    Gson gson = new Gson();

    @Autowired
    private TransactionService transSvc;

    @PostMapping(path="/post", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postTransactionDetails(@RequestBody String payload) {
        Transaction trans = gson.fromJson(payload, Transaction.class);
        List<String> transJsonList = transSvc.postTransactionDetails(trans);
        String resp = gson.toJson(transJsonList);
        return ResponseEntity.ok("POST Transaction Success!\n" + resp);
    }

    @GetMapping(path="/get/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getTransactionDetails(@PathVariable String id) {
        List<String> transQueryList = transSvc.getTransactionDetails(id);
        String resp = gson.toJson(transQueryList);
        return ResponseEntity.ok("GET Transaction Success!\n" + resp);
    }

    @PutMapping(path="/put/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putTransactionDetails(@RequestBody String payload, @PathVariable String id) {
        Transaction trans = gson.fromJson(payload, Transaction.class);
        List<String> transJsonList = transSvc.putTransactionDetails(trans, id);
        String resp = gson.toJson(transJsonList);
        return ResponseEntity.ok("PUT Transaction Success!\n" + resp);
    }

    @DeleteMapping(path="/delete/{id}", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> deleteTransactionDetails(@PathVariable String id) {
        List<String> transJsonList = transSvc.deleteTransactionDetails(id);
        String resp = gson.toJson(transJsonList);
        return ResponseEntity.ok("DELETE Transaction Success!\n" + resp);
    }
    
}
