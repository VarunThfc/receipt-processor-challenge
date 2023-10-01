package com.fetch.varunk.receiptprocessor.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class Receipt {
    String id;
    double points;

    @Override
    public String toString() {
        return "Receipt [id=" + id + ", points=" + points + " ]";
    }
}
