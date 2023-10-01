package com.fetch.varunk.receiptprocessor.service;

import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.Receipt;
import com.fetch.varunk.receiptprocessor.service.rules.command.Commands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class ReceiptProcessorService {

    /*
    Simple In-memory structure  to handle this use case, easily subsitutable to a inmemory no sql db if required.
    */
    static Map<String, Receipt> inMemoryCache = new HashMap<>();

    public String processReceipt(ProcessReceiptRequest processRequest) {
        // Model receipt and items from request DTO
        Receipt requestReceipt = generateReceipt(processRequest);
        double points = Commands.invokeAllRules(processRequest);
        requestReceipt.setPoints(points);
        inMemoryCache.put(requestReceipt.getId(), requestReceipt);
        return requestReceipt.getId();
    }

    public Receipt getReceipt(String id) {
        return inMemoryCache.get(id);
    }
    private Receipt generateReceipt(ProcessReceiptRequest processRequest) {
        String id = UUID.randomUUID().toString();
        while(inMemoryCache.containsKey(id)){
            id = UUID.randomUUID().toString();
        }
        Receipt requestReceipt = Receipt.builder()
                .id(id)
                .points(0)
                .build();
        return requestReceipt;

    }

}
