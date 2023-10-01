package com.fetch.varunk.receiptprocessor.service.rules.command;

import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.RequestItem;
import com.fetch.varunk.receiptprocessor.util.Constants;
import org.apache.tomcat.util.bcel.Const;

import java.time.LocalTime;
import java.util.List;

public class Commands {

    public static double alphaNumericNameCommand(ProcessReceiptRequest processRequest){
        String name = processRequest.getRetailer().replaceAll(Constants.APHA_NUMERIC_PATTERN, "");
        return name.length();
    }

    public static double roundNumberCommand(ProcessReceiptRequest processRequest){
        double number = processRequest.getTotal();
        return Math.abs(number - Math.round(number)) == 0 ? Constants.ROUND_NUMBER_POINTS : 0;
    }

    public static double quarterMultipleCommand(ProcessReceiptRequest processRequest){
        double number =  processRequest.getTotal();
        double reminder = processRequest.getTotal() % Constants.MULTIPLE_NUMBER_MULTIPLIER;

        return reminder == 0 ? Constants.MULTIPLE_NUMBER_POINTS : 0;
    }

    public static double countItemRule(ProcessReceiptRequest processRequest) {
        List<RequestItem> requestItemList = processRequest.getItems();
        int items = requestItemList.size() / Constants.COUNT_ITEM_RULE_DIVIDE;
        return items * Constants.COUNT_ITEM_RULE_MULTIPLIER;
    }

    public static double itemDescriptionRule(ProcessReceiptRequest processRequest) {
        List<RequestItem> requestItemList = processRequest.getItems();
        double count = 0;
        for(RequestItem requestItem : requestItemList){
            if (requestItem.getShortDescription().trim().length() % Constants.ITEM_DESC_LENGTH_MOD == 0){
                count += Math.ceil(requestItem.getPrice() * Constants.ITEM_DESC_RULE_MULTIPLIER);
            }
        }

        return count;
    }

    public static double dayRule(ProcessReceiptRequest processRequest){
        int day = processRequest.getPurchaseDate().getDayOfMonth();
        if(day % 2 == 0){
            return 0;
        }
        return Constants.DAY_RULE_POINT;
    }

    public static double timeRule(ProcessReceiptRequest processRequest){
        LocalTime receiptPurchaseTime = processRequest.getPurchaseTime();
        LocalTime startTime = LocalTime.of(Constants.START_TIME_HOUR, 0); // 2:00 PM
        LocalTime endTime = LocalTime.of(Constants.END_TIME_HOUR, 0);
        if(startTime.compareTo(receiptPurchaseTime) <= 0 && endTime.compareTo(receiptPurchaseTime) >= 0){
            return Constants.TIME_RULE_POINTS;
        }
        return 0;
    }

    public static double invokeAllRules(ProcessReceiptRequest processRequest){
        double sol = alphaNumericNameCommand(processRequest) + roundNumberCommand(processRequest) + quarterMultipleCommand(processRequest) + timeRule(processRequest) + dayRule(processRequest) + itemDescriptionRule(processRequest) + countItemRule(processRequest);
        return sol;
    }
}
