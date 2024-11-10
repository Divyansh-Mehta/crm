package com.assignment.crm.integration;


import com.assignment.crm.dto.InteractionLogDto;
import com.assignment.crm.model.Customer;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.model.Sales;
import com.assignment.crm.repository.InteractionLogRepository;
import com.assignment.crm.service.impl.SalesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class InteractionLogApisTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InteractionLogRepository interactionLogRepository;

    @MockBean
    private SalesServiceImpl salesService;

    private InteractionLog interactionLog;
    private InteractionLogDto interactionLogDto;

    @BeforeEach
    void setUp() {
        // Initialize InteractionLog
        interactionLog = new InteractionLog();
        interactionLog.setId(1L);
        interactionLog.setInteractionDate(LocalDate.now());
        interactionLog.setType("Email");
        interactionLog.setNotes("Waiting for response");

        // Initialize InteractionLog
        interactionLogDto = new InteractionLogDto();
        interactionLogDto.setId(interactionLog.getId());
        interactionLogDto.setInteractionDate(interactionLog.getInteractionDate());
        interactionLogDto.setType(interactionLog.getType());
        interactionLogDto.setNotes(interactionLog.getNotes());
    }

    @Test
    void testGetAllLogsForASales() throws Exception {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);

        when(interactionLogRepository.findAllBySalesId(1L)).thenReturn(logs);

        mockMvc.perform(get("/api/interaction-logs/sales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").value(interactionLogDto.getId()))
                .andExpect(jsonPath("$[0].type").value(interactionLogDto.getType()))
                .andExpect(jsonPath("$[0].notes").value(interactionLogDto.getNotes()));
    }

    @Test
    void testGetAllLogsForACustomer() throws Exception {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);
        when(interactionLogRepository.findAllByCustomerId(1L)).thenReturn(logs);

        mockMvc.perform(get("/api/interaction-logs/customer/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].type").value(interactionLogDto.getType()))
                .andExpect(jsonPath("$[0].notes").value(interactionLogDto.getNotes()));
    }

    @Test
    void testGetInteractionLogByIdSuccess() throws Exception {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        mockMvc.perform(get("/api/interaction-logs/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(interactionLogDto.getId()))
                .andExpect(jsonPath("$.type").value(interactionLogDto.getType()))
                .andExpect(jsonPath("$.notes").value(interactionLogDto.getNotes()));
    }

    @Test
    void testGetInteractionLogByIdFailure() throws Exception {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/interaction-logs/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testSaveInteractionLog() throws Exception {
        when(interactionLogRepository.save(any(InteractionLog.class))).thenReturn(interactionLog);
        Sales sales = new Sales();
        Customer customer = new Customer();
        customer.setId(1L);
        sales.setId(1L);
        sales.setCustomer(customer);
        when(salesService.getSalesById(1L)).thenReturn(sales);

        mockMvc.perform(post("/api/interaction-logs/sales/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"type\": \"Email\", \"notes\": \"Waiting for response\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(interactionLogDto.getId()))
                .andExpect(jsonPath("$.type").value(interactionLogDto.getType()))
                .andExpect(jsonPath("$.notes").value(interactionLogDto.getNotes()));
    }

    @Test
    void testUpdateInteractionLogSuccess() throws Exception {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        mockMvc.perform(put("/api/interaction-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 1, \"type\": \"Call\", \"notes\": \"Discussed project updates.\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Log updated successfully"));
    }

    @Test
    void testUpdateInteractionLogFailure() throws Exception {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/interaction-logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\": 2, \"type\": \"Call\", \"notes\": \"Discussed project updates.\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteInteractionLogSuccess() throws Exception {
        Sales sales = new Sales();
        Customer customer = new Customer();
        customer.setId(1L);
        sales.setId(1L);
        sales.setCustomer(customer);
        interactionLog.setSales(sales);
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        mockMvc.perform(delete("/api/interaction-logs/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Log deleted successfully"));
    }

    @Test
    void testDeleteInteractionLogFailure() throws Exception {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/interaction-logs/{id}", 2L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddNotesSuccess() throws Exception {
        when(interactionLogRepository.findById(1L)).thenReturn(Optional.of(interactionLog));

        mockMvc.perform(put("/api/interaction-logs/{id}/notes", 1L)
                        .param("notes", "New notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Notes added successfully"));
    }

    @Test
    void testAddNotesFailure() throws Exception {
        when(interactionLogRepository.findById(2L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/interaction-logs/{id}/notes", 2L)
                        .param("notes", "New notes")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
