package com.fetch.varunk.receiptprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RequestItem {
    String shortDescription;
    Double price;

    @Override
    public String toString() {
        return "RequestItem [Description =" + shortDescription + ", price=" + price + "]";
    }
}
