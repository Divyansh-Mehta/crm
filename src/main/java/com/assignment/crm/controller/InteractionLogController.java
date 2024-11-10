package com.assignment.crm.controller;

import com.assignment.crm.dto.InteractionLogDto;
import com.assignment.crm.model.InteractionLog;
import com.assignment.crm.service.InteractionLogService;
import com.assignment.crm.utils.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/interaction-logs")
public class InteractionLogController {
    private final InteractionLogService interactionLogService;

    public InteractionLogController(InteractionLogService interactionLogService) {
        this.interactionLogService = interactionLogService;
    }

    /*
       Get all interaction logs for a sales
    1. Use interactionLog service to get all required logs
    2. Convert List of interactionLogs to List of interactionLogDtos
    3. Return a response Entity
     */
    @GetMapping("/sales/{id}")
    public ResponseEntity<List<InteractionLogDto>> getAllLogsForASales(@PathVariable("id") Long salesId) {
        List<InteractionLog> logs = interactionLogService.getAllInteractionLogsOfASales(salesId);
        List<InteractionLogDto> logDtos = new ArrayList<>();
        for (InteractionLog log: logs){
            InteractionLogDto logDto = createInteractionLogDtoFromInteractionLog(log);
            logDtos.add(logDto);
        }
        return new ResponseEntity<>(logDtos, HttpStatus.OK);
    }

    /*
       Get all interaction logs for a customer
    1. Use interactionLog service to get all required logs
    2. Convert List of interactionLogs to List of interactionLogDtos
    3. Return a response Entity
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<InteractionLogDto>> getAllLogsForACustomer(@PathVariable("id") Long customerId) {
        List<InteractionLog> logs = interactionLogService.getAllInteractionLogsOfACustomer(customerId);
        List<InteractionLogDto> logDtos = new ArrayList<>();
        for (InteractionLog log: logs){
            InteractionLogDto logDto = createInteractionLogDtoFromInteractionLog(log);
            logDtos.add(logDto);
        }
        return new ResponseEntity<>(logDtos, HttpStatus.OK);
    }

    /*
       Get interactionLog by id
    1. Use interactionLog service to get required interactionLog
    2. If log is null throw CustomException
    3. If log is not null convert it to logDto
    4. Return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<InteractionLogDto> getInteractionLogById(@PathVariable Long id) throws CustomException{
        InteractionLog log = interactionLogService.getInteractionLogById(id);
        if (log == null){
            throw new CustomException("Cannot find log with given id", HttpStatus.NOT_FOUND);
        }
        InteractionLogDto logDto = createInteractionLogDtoFromInteractionLog(log);
        return new ResponseEntity<>(logDto, HttpStatus.OK);
    }

    /*
       Save interactionLog
    1. Use interactionLog service to save the log
    2. Convert the saved log to logDto
    3. Return response entity
     */
    @PostMapping("/sales/{id}")
    public ResponseEntity<InteractionLogDto> saveInteractionLog(@PathVariable("id") Long salesId, @RequestBody InteractionLog log){
        InteractionLog savedLog = interactionLogService.saveInteractionLog(log, salesId);
        InteractionLogDto logDto = createInteractionLogDtoFromInteractionLog(savedLog);
        return new ResponseEntity<>(logDto, HttpStatus.CREATED);
    }

    /*
       Update interactionLog
    1. Use interactionLog service to update log
    2. If log is not updated throw customException
    3. If log is updated return a response Entity
     */
    @PutMapping
    public ResponseEntity<String> updateInteractionLog(@RequestBody InteractionLog log) throws  CustomException{
        boolean isUpdated = interactionLogService.updateInteractionLog(log);
        if (!isUpdated){
            throw new CustomException("Cannot find log with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Log updated successfully", HttpStatus.OK);
    }

    /*
       Delete interactionLog
    1. Use interactionLog service to delete a log
    2. If log is not deleted throw salesException
    3. If log is deleted return response Entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInteractionLog(@PathVariable Long id) throws CustomException{
        boolean isDeleted = interactionLogService.deleteInteractionLog(id);
        if (!isDeleted){
            throw new CustomException("Cannot find log with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Log deleted successfully", HttpStatus.OK);
    }

    /*
       Add notes
    1. Use interactionLog service to update notes
    2. If notes is not added throw customException
    3. If notes is added return response Entity
     */
    @PutMapping("/{id}/notes")
    public ResponseEntity<String> addNotes(@PathVariable Long id, @RequestParam String notes) throws CustomException {
        boolean isAdded = interactionLogService.addNotes(notes, id);
        if (!isAdded) {
            throw new CustomException("Cannot find log with given id", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Notes added successfully", HttpStatus.OK);
    }

    /*
    Helper function to convert interactionLog to interactionLogDto
    1. Create a interactionLogDto object
    2. Set its fields from interactionLog object
    3. Return the interactionLogDto object
     */
    private InteractionLogDto createInteractionLogDtoFromInteractionLog(InteractionLog log){
        InteractionLogDto logDto = new InteractionLogDto();
        logDto.setId(log.getId());
        logDto.setInteractionDate(log.getInteractionDate());
        logDto.setType(log.getType());
        logDto.setNotes(log.getNotes());
        return logDto;
    }
}