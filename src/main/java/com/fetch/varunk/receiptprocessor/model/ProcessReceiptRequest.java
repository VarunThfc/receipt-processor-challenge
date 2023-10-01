package com.fetch.varunk.receiptprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ProcessReceiptRequest {

    String retailer;
    LocalDate purchaseDate;
    LocalTime purchaseTime;
    double total;
    List<RequestItem> items;

    @Override
    public String toString() {
        return "ProcessReceiptRequest{" +
                "retailer='" + retailer + '\'' +
                ", purchaseDate=" + purchaseDate +
                ", purchaseTime=" + purchaseTime +
                ", total=" + total +
                ", items=" + items +
                '}';
    }

}
