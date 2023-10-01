package com.fetch.varunk.receiptprocessor.service;

import com.fetch.varunk.receiptprocessor.model.ProcessReceiptRequest;
import com.fetch.varunk.receiptprocessor.model.Receipt;
import com.fetch.varunk.receiptprocessor.service.rules.command.Commands;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Commands.class})
public class ReceiptProcessorServiceTest {
    private ReceiptProcessorService receiptProcessorService = new ReceiptProcessorService();

    private Commands commands;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Commands.class);
    }
    @Test
    public void processReceiptTest(){
        PowerMockito.mockStatic(Commands.class);
        when(Commands.invokeAllRules(any())).thenReturn(0.0);
        String abc = receiptProcessorService.processReceipt(new ProcessReceiptRequest("abc", LocalDate.now(), LocalTime.now(), 0.0, null));
        assertTrue(abc != null);
    }

    @Test
    public void processGetReceiptTest(){
        PowerMockito.mockStatic(Commands.class);
        when(Commands.invokeAllRules(any())).thenReturn(0.0);
        when(Commands.invokeAllRules(any())).thenReturn(0.0);
        String abc = receiptProcessorService.processReceipt(new ProcessReceiptRequest("abc", LocalDate.now(), LocalTime.now(), 0.0, null));
        Receipt object = receiptProcessorService.getReceipt(abc);
        assertTrue(object != null && abc != null);
    }

}
