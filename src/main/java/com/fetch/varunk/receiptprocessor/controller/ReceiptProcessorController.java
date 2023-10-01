package com.fetch.varunk.receiptprocessor.controller;

import com.fetch.varunk.receiptprocessor.model.GetPointsResponse;
import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.ProcessReceiptResponse;
import com.fetch.varunk.receiptprocessor.model.Receipt;
import com.fetch.varunk.receiptprocessor.service.ReceiptProcessorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptProcessorController {

    @Autowired
    private ReceiptProcessorService receiptProcessorService;

    @PostMapping("/process")
    public ResponseEntity<ProcessReceiptResponse> process(@RequestBody ProcessReceiptRequest processRequest) {
        log.info("Process request received: " + processRequest);
        if(processRequest == null){
            return  new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        String id = receiptProcessorService.processReceipt(processRequest);
        return new ResponseEntity<>(new ProcessReceiptResponse(id), HttpStatus.CREATED);
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<GetPointsResponse> getReceiptPoints(@PathVariable String id) {
        log.info("Get Point request received for id: " + id);
        Receipt receipt = receiptProcessorService.getReceipt(id);
        if (receipt == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(new GetPointsResponse(receipt.getPoints()), HttpStatus.OK);
    }

}
