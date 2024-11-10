package com.assignment.crm.controller;

import com.assignment.crm.dto.SalesDto;
import com.assignment.crm.model.Sales;
import com.assignment.crm.service.impl.SalesServiceImpl;
import com.assignment.crm.utils.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SalesControllerTest {
    @Mock
    private SalesServiceImpl salesService;

    @InjectMocks
    private SalesController salesController;

    private Sales sales;
    private SalesDto salesDto;

    @BeforeEach
    void setUp() {
        // Initialize Sales
        sales = new Sales();
        sales.setId(1L);
        sales.setStage("Lead");
        sales.setDealSize(1000.0);
        sales.setProbabilityOfClosing(0.75);
        sales.setCreatedAt(LocalDateTime.now());

        // Initialize SalesDto
        salesDto = new SalesDto();
        salesDto.setId(sales.getId());
        salesDto.setStage(sales.getStage());
        salesDto.setDealSize(sales.getDealSize());
        salesDto.setProbabilityOfClosing(sales.getProbabilityOfClosing());
        salesDto.setCreatedAt(sales.getCreatedAt());
    }

    @Test
    void testGetAllSales() {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(sales);
        when(salesService.getAllSales(1L)).thenReturn(salesList);

        ResponseEntity<List<SalesDto>> response = salesController.getAllSales(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(salesDto.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetSalesByIdSuccess() throws CustomException {
        when(salesService.getSalesById(1L)).thenReturn(sales);

        ResponseEntity<SalesDto> response = salesController.getSalesById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(salesDto.getId(), response.getBody().getId());
    }

    @Test
    void testGetSalesByIdFailure() {
        when(salesService.getSalesById(2L)).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> {
            salesController.getSalesById(2L);
        });

        assertEquals("Cannot find sales with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testSaveSales() {
        when(salesService.createSales(sales, 1L)).thenReturn(sales);

        ResponseEntity<SalesDto> response = salesController.saveSales(1L, sales);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(salesDto.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateSalesSuccess() throws CustomException {
        when(salesService.updateSales(sales)).thenReturn(true);

        ResponseEntity<String> response = salesController.updateSales(sales);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Sales updated successfully", response.getBody());
    }

    @Test
    void testUpdateSalesFailure() {
        when(salesService.updateSales(sales)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            salesController.updateSales(sales);
        });

        assertEquals("Cannot find sales with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testDeleteSalesSuccess() throws CustomException {
        when(salesService.deleteSales(2L)).thenReturn(true);

        ResponseEntity<String> response = salesController.deleteSales(2L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("sales deleted successfully", response.getBody());
    }

    @Test
    void testDeleteSalesFailure() throws CustomException{
        when(salesService.deleteSales(2L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            salesController.deleteSales(2L);
        });

        assertEquals("Cannot find sales with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testUpdateStageSuccess() throws CustomException {
        when(salesService.updateStage(1L, "Closed")).thenReturn(true);

        ResponseEntity<String> response = salesController.updateStage(1L, "Closed");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Stage updated successfully", response.getBody());
    }

    @Test
    void testUpdateStageFailure() {
        when(salesService.updateStage(2L, "Closed")).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            salesController.updateStage(2L, "Closed");
        });

        assertEquals("Cannot find sales with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testCloseDealSuccess() throws CustomException {
        when(salesService.closeDeal(1L)).thenReturn(true);

        ResponseEntity<String> response = salesController.closeDeal(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Deal closed successfully", response.getBody());
    }

    @Test
    void testCloseDealFailure() {
        when(salesService.closeDeal(2L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            salesController.closeDeal(2L);
        });

        assertEquals("Cannot find sales with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
