package com.assignment.crm.controller;

import com.assignment.crm.dto.InteractionLogDto;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.service.impl.InteractionLogServiceImpl;
import com.assignment.crm.utils.CustomException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class InteractionLogControllerTest {
    @Mock
    private InteractionLogServiceImpl interactionLogService;

    @InjectMocks
    private InteractionLogController interactionLogController;

    private InteractionLog interactionLog;
    private InteractionLogDto interactionLogDto;

    @BeforeEach
    void setUp() {
        // Initialize InteractionLog
        interactionLog = new InteractionLog();
        interactionLog.setId(1L);
        interactionLog.setInteractionDate(LocalDate.now());
        interactionLog.setType("Email");
        interactionLog.setNotes("Waiting for Follow up");

        // Initialize InteractionLogDto
        interactionLogDto = new InteractionLogDto();
        interactionLogDto.setId(interactionLog.getId());
        interactionLogDto.setInteractionDate(interactionLog.getInteractionDate());
        interactionLogDto.setType(interactionLog.getType());
        interactionLogDto.setNotes(interactionLog.getNotes());
    }

    @Test
    void testGetAllLogsForASales() {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);
        when(interactionLogService.getAllInteractionLogsOfASales(1L)).thenReturn(logs);

        ResponseEntity<List<InteractionLogDto>> response = interactionLogController.getAllLogsForASales(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(interactionLogDto.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetAllLogsForACustomer() {
        List<InteractionLog> logs = new ArrayList<>();
        logs.add(interactionLog);
        when(interactionLogService.getAllInteractionLogsOfASales(1L)).thenReturn(logs);

        ResponseEntity<List<InteractionLogDto>> response = interactionLogController.getAllLogsForACustomer(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals(interactionLogDto.getId(), response.getBody().get(0).getId());
    }

    @Test
    void testGetInteractionLogByIdSuccess() throws CustomException {
        when(interactionLogService.getInteractionLogById(1L)).thenReturn(interactionLog);

        ResponseEntity<InteractionLogDto> response = interactionLogController.getInteractionLogById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(interactionLogDto.getId(), response.getBody().getId());
    }

    @Test
    void testGetInteractionLogByIdFailure() throws CustomException{
        when(interactionLogService.getInteractionLogById(2L)).thenReturn(null);

        CustomException exception = assertThrows(CustomException.class, () -> {
            interactionLogController.getInteractionLogById(2L);
        });

        assertEquals("Cannot find log with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testSaveInteractionLog() {
        when(interactionLogService.saveInteractionLog(interactionLog, 1L)).thenReturn(interactionLog);

        ResponseEntity<InteractionLogDto> response = interactionLogController.saveInteractionLog(1L, interactionLog);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(interactionLogDto.getId(), response.getBody().getId());
    }

    @Test
    void testUpdateInteractionLogSuccess() throws CustomException {
        when(interactionLogService.updateInteractionLog(interactionLog)).thenReturn(true);

        ResponseEntity<String> response = interactionLogController.updateInteractionLog(interactionLog);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Log updated successfully", response.getBody());
    }

    @Test
    void testUpdateInteractionLogFailure() {
        when(interactionLogService.updateInteractionLog(interactionLog)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            interactionLogController.updateInteractionLog(interactionLog);
        });

        assertEquals("Cannot find log with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testDeleteInteractionLogSuccess() throws CustomException {
        when(interactionLogService.deleteInteractionLog(1L)).thenReturn(true);

        ResponseEntity<String> response = interactionLogController.deleteInteractionLog(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Log deleted successfully", response.getBody());
    }

    @Test
    void testDeleteInteractionLogFailure() {
        when(interactionLogService.deleteInteractionLog(2L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            interactionLogController.deleteInteractionLog(2L);
        });

        assertEquals("Cannot find log with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }

    @Test
    void testAddNotesSuccess() throws CustomException {
        when(interactionLogService.addNotes("New notes", 1L)).thenReturn(true);

        ResponseEntity<String> response = interactionLogController.addNotes(1L, "New notes");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Notes added successfully", response.getBody());
    }

    @Test
    void testAddNotesFailure() {
        when(interactionLogService.addNotes("New notes", 2L)).thenReturn(false);

        CustomException exception = assertThrows(CustomException.class, () -> {
            interactionLogController.addNotes(2L, "New notes");
        });

        assertEquals("Cannot find log with given id", exception.getMessage());
        assertEquals(HttpStatus.NOT_FOUND, exception.getHttpStatus());
    }
}
