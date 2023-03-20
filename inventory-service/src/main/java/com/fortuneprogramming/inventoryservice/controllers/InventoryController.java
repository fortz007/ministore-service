package com.fortuneprogramming.inventoryservice.controllers;

import com.fortuneprogramming.inventoryservice.dtos.InventoryResponseDto;
import com.fortuneprogramming.inventoryservice.services.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponseDto> isInStock(@RequestParam List<String> skuCode){
          return inventoryService.isInStock(skuCode);
    }
}
