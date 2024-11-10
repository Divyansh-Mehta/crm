package com.assignment.crm.integration;

import com.assignment.crm.controller.SalesController;
import com.assignment.crm.dto.SalesDto;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.SalesRepository;
import com.assignment.crm.service.impl.SalesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class SalesApisTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SalesRepository salesRepository;

    private Sales sales;
    private SalesDto salesDto;

    @BeforeEach
    void setUp() {
        // Initialize Sales and SalesDto
        sales = new Sales();
        sales.setId(1L);
        sales.setStage("lead");
        sales.setDealSize(10000.0);
        sales.setProbabilityOfClosing(0.75);
        sales.setClosingDate(LocalDateTime.now());
        sales.setCreatedAt(LocalDateTime.now());

        salesDto = new SalesDto();
        salesDto.setId(sales.getId());
        salesDto.setStage(sales.getStage());
        salesDto.setDealSize(sales.getDealSize());
        salesDto.setProbabilityOfClosing(sales.getProbabilityOfClosing());
        salesDto.setClosingDate(sales.getClosingDate());
        salesDto.setCreatedAt(sales.getCreatedAt());
    }

    @Test
    void testGetAllSales() throws Exception {
        List<Sales> salesList = new ArrayList<>();
        salesList.add(sales);

        when(salesRepository.findAllByCustomerId(1L)).thenReturn(salesList);

        mockMvc.perform(get("/api/sales/customer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(salesDto.getId()))
                .andExpect(jsonPath("$[0].stage").value(salesDto.getStage()))
                .andExpect(jsonPath("$[0].dealSize").value(salesDto.getDealSize()))
                .andExpect(jsonPath("$[0].probabilityOfClosing").value(salesDto.getProbabilityOfClosing()));
    }

    @Test
    void testGetSalesByIdSuccess() throws Exception {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        mockMvc.perform(get("/api/sales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(salesDto.getId()))
                .andExpect(jsonPath("$.stage").value(salesDto.getStage()))
                .andExpect(jsonPath("$.dealSize").value(salesDto.getDealSize()))
                .andExpect(jsonPath("$.probabilityOfClosing").value(salesDto.getProbabilityOfClosing()));
    }

    @Test
    void testGetSalesByIdFailure() throws Exception {
        when(salesRepository.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveSales() throws Exception {
        when(salesRepository.save(any(Sales.class))).thenReturn(sales);

        mockMvc.perform(post("/api/sales/customer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"stage\": \"lead\", \"dealSize\": 10000, \"probabilityOfClosing\": 0.75}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.stage").value(salesDto.getStage()))
                .andExpect(jsonPath("$.dealSize").value(salesDto.getDealSize()))
                .andExpect(jsonPath("$.probabilityOfClosing").value(salesDto.getProbabilityOfClosing()));
    }

    @Test
    void testUpdateSalesSuccess() throws Exception {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        mockMvc.perform(put("/api/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"stage\": \"Negotiation\", \"dealSize\": 15000, \"probabilityOfClosing\": 0.85}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sales updated successfully"));
    }

    @Test
    void testUpdateSalesFailure() throws Exception {
        when(salesRepository.findById(2L)).thenReturn(Optional.empty());;

        mockMvc.perform(put("/api/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 2, \"stage\": \"Negotiation\", \"dealSize\": 15000, \"probabilityOfClosing\": 0.85}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteSalesSuccess() throws Exception {
        when(salesRepository.existsById(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/sales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("sales deleted successfully"));
    }

    @Test
    void testDeleteSalesFailure() throws Exception {
        when(salesRepository.existsById(2L)).thenReturn(false);

        mockMvc.perform(delete("/api/sales/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testUpdateStageSuccess() throws Exception {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        mockMvc.perform(put("/api/sales/{id}/update-stage", 1L)
                        .param("stage", "closed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Stage updated successfully"));
    }

    @Test
    void testUpdateStageFailure() throws Exception {
        when(salesRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/sales/{id}/update-stage", 2L)
                        .param("stage", "closed")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCloseDealSuccess() throws Exception {
        when(salesRepository.findById(1L)).thenReturn(Optional.of(sales));

        mockMvc.perform(put("/api/sales/{id}/close-deal", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Deal closed successfully"));
    }

    @Test
    void testCloseDealFailure() throws Exception {
        when(salesRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/sales/{id}/close-deal", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
