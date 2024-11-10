package com.assignment.crm.controller;

import com.assignment.crm.dto.SalesDto;
import com.assignment.crm.model.Sales;
import com.assignment.crm.service.SalesService;
import com.assignment.crm.utils.CustomException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/sales")
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    /*
       Get all sales of a customer
    1. Use sales service to get list of all sales of a customer
    2. Convert list of sales to list of salesDto
    3. Return a response entity
     */
    @GetMapping("/customer/{id}")
    public ResponseEntity<List<SalesDto>> getAllSales(@PathVariable("id") Long customerId){
        List<Sales> salesList = salesService.getAllSales(customerId);
        List<SalesDto> salesDtos = new ArrayList<>();

        for (Sales sales: salesList){
            SalesDto salesDto = createSalesDtoFromSales(sales);
            salesDtos.add(salesDto);
        }

        return new ResponseEntity<>(salesDtos, HttpStatus.OK);
    }

    /*
       Get sales by id
    1. Use sales service to get required sales
    2. If sales is null throw CustomException
    3. If sales is not null convert it to salesDto
    4. Return response entity
     */
    @GetMapping("/{id}")
    public ResponseEntity<SalesDto> getSalesById(@PathVariable Long id) throws CustomException{
        Sales sales = salesService.getSalesById(id);
        if (sales == null){
            throw new CustomException("Cannot find sales with given id", HttpStatus.NOT_FOUND);
        }
        SalesDto salesDto = createSalesDtoFromSales(sales);
        return new ResponseEntity<>(salesDto, HttpStatus.OK);
    }

    /*
       Save a sales
    1. Use sales service to save the sales
    2. Convert the saved sales to salesDto
    3. Return response entity
     */
    @PostMapping("/customer/{id}")
    public ResponseEntity<SalesDto> saveSales(@PathVariable("id") Long customerId, @RequestBody Sales sales){
        Sales savedSales = salesService.createSales(sales, customerId);
        SalesDto salesDto = createSalesDtoFromSales(sales);
        return new ResponseEntity<>(salesDto, HttpStatus.CREATED);
    }

    /*
       Update sales
    1. Use sales service to update sales
    2. If sales is not updated throw customException
    3. If sales is updated return a response Entity
     */
    @PutMapping
    public ResponseEntity<String> updateSales(@RequestBody Sales sales) throws CustomException{
        boolean isUpdated = salesService.updateSales(sales);
        if (!isUpdated){
            throw new CustomException("Cannot find sales with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Sales updated successfully", HttpStatus.OK);
    }

    /*
       Delete sales
    1. Use sales service to delete a sales
    2. If sales is not deleted throw salesException
    3. If sales is deleted return response Entity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSales(@PathVariable Long id) throws CustomException{
        boolean isDeleted = salesService.deleteSales(id);
        if (!isDeleted){
            throw new CustomException("Cannot find sales with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("sales deleted successfully", HttpStatus.OK);
    }

    /*
       Update stage
    1. Use sales service to update stage
    2. If stage is not updated throw customException
    3. If stage is updated return response Entity
     */
    @PutMapping("/{id}/update-stage")
    public ResponseEntity<String> updateStage(@PathVariable Long id, @RequestParam String stage) throws CustomException{
        boolean isUpdated = salesService.updateStage(id, stage);
        if (!isUpdated){
            throw new CustomException("Cannot find sales with given id", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>("Stage updated successfully", HttpStatus.OK);
    }

    /*
       Close deal
    1. Use sales service to close deal
    2. If deal is not closed throw customException
    3. If deal is closed return response Entity
     */
    @PutMapping("/{id}/close-deal")
    public ResponseEntity<String> closeDeal(@PathVariable Long id) throws CustomException{
        boolean isClosed = salesService.closeDeal(id);
        if (!isClosed){
            throw new CustomException("Cannot find sales with given id", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("Deal closed successfully", HttpStatus.OK);
    }

    /*
    Helper function to convert sales to salesDto
    1. Create a salesDto object
    2. Set its fields from sales object
    3. Return the salesDto object
     */
    private SalesDto createSalesDtoFromSales(Sales sales){
        SalesDto salesDto = new SalesDto();
        salesDto.setId(sales.getId());
        salesDto.setStage(sales.getStage());
        salesDto.setDealSize(sales.getDealSize());
        salesDto.setProbabilityOfClosing(sales.getProbabilityOfClosing());
        salesDto.setClosingDate(sales.getClosingDate());
        salesDto.setCreatedAt(sales.getCreatedAt());
        return salesDto;
    }
}
