package com.fetch.varunk.receiptprocessor.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fetch.varunk.receiptprocessor.ReceiptProcessorApplication;
import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.Receipt;
import com.fetch.varunk.receiptprocessor.model.RequestItem;
import com.fetch.varunk.receiptprocessor.service.ReceiptProcessorService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(ReceiptProcessorController.class)
public class ReceiptProcessorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReceiptProcessorService receiptProcessorService;

    private JSONObject receipt;

    @Test
    public void getPointsReturnValidPoints() throws Exception {
        int expectedPoints = 100;
        String mockUuid = UUID.randomUUID().toString();
        Receipt mockReceipt = new Receipt(mockUuid, expectedPoints);
        mockReceipt.setPoints(expectedPoints);

        when(receiptProcessorService.getReceipt(mockUuid)).thenReturn(mockReceipt);
        mockMvc.perform(get("/receipts/" + mockUuid + "/points"))
                .andExpect(status().isOk())
                .andExpect(content().json("{ points: " + expectedPoints + "}"));

        verify(receiptProcessorService, times(1)).getReceipt(mockUuid);
    }


    @Test
    public void getPointsIvalidPoint() throws Exception {
        String notFoundUuid = "this_id_is_not_found";

        when(receiptProcessorService.getReceipt(notFoundUuid)).thenReturn(null);
        mockMvc.perform(get("/receipts/" + notFoundUuid + "/points"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(receiptProcessorService, times(1)).getReceipt(notFoundUuid);
    }

    @Test
    public void processReturnsId() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();
        List<RequestItem> alist = new ArrayList<>();
        alist.add(new RequestItem("short - description",2.5));
        ProcessReceiptRequest request = ProcessReceiptRequest.builder()
                .retailer("retailer")
                .purchaseDate(LocalDate.now())
                .purchaseTime(LocalTime.now())
                .total(1.0)
                .items(alist)
                .build();
        objectMapper.registerModule(new JavaTimeModule());

        String json = objectMapper.writeValueAsString(request);

        String mockReceiptId = UUID.randomUUID().toString();
        when(receiptProcessorService.processReceipt(any(ProcessReceiptRequest.class))).thenReturn(mockReceiptId);

        mockMvc.perform(post("/receipts/process").contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string(containsString("id")))
                .andExpect(content().json("{ id: " + mockReceiptId + "}"));

        verify(receiptProcessorService, times(1)).processReceipt(any(ProcessReceiptRequest.class));
    }




}
