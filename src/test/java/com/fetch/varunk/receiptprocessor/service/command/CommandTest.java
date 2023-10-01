package com.fetch.varunk.receiptprocessor.service.command;

import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.RequestItem;
import com.fetch.varunk.receiptprocessor.service.rules.command.Commands;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    @Test
    public void alphaNumericNameCommandTest(){
        double result = (double) Commands.alphaNumericNameCommand(new ProcessReceiptRequest("abcd",null, null, 100, null));
        assertEquals(4, result);
    }

    @Test
    public void roundNumberCommandTest(){
        double result = (double) Commands.roundNumberCommand(new ProcessReceiptRequest("abcd",null, null, 100, null));
        assertEquals(50, result);
    }

    @Test
    public void quarterMultipleCommandTest(){
        double result = (double) Commands.quarterMultipleCommand(new ProcessReceiptRequest("abcd",null, null, 100, null));
        assertEquals(25, result);
    }

    @Test
    public void countItemCommandTest(){
        List<RequestItem> alist= new ArrayList<>();
        alist.add(new RequestItem("abcdef", 100.0));
        double result = (double) Commands.countItemRule(new ProcessReceiptRequest("abcd",null, null, 100, alist));
        assertEquals(0, result);
    }

    @Test
    public void itemDescriptionRuleCommandTest(){
        List<RequestItem> alist= new ArrayList<>();
        alist.add(new RequestItem("abcdef", 100.0));
        double result = (double) Commands.itemDescriptionRule(new ProcessReceiptRequest("abcd",null, null, 100, alist));
        assertEquals(20, result);
    }

    @Test
    public void dayRuleCommandTest(){
        double result = (double) Commands.dayRule(new ProcessReceiptRequest("abcd",LocalDate.now(), null, 100, null));
        assertEquals(LocalDate.now().getDayOfMonth()%2 == 0 ? 0 : 6 , result);
    }

    @Test
    public void timeRuleCommandTest(){
        double result = (double) Commands.timeRule(new ProcessReceiptRequest("abcd",LocalDate.now(), LocalTime.now(), 100, null));
        assertEquals(LocalTime.now().getHour() <= 16 && LocalTime.now().getHour() >= 14  ? 10 : 0 , result);
    }
}
