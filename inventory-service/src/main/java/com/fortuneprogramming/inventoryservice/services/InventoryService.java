package com.fortuneprogramming.inventoryservice.services;

import com.fortuneprogramming.inventoryservice.dtos.InventoryResponseDto;
import com.fortuneprogramming.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponseDto> isInStock(List<String> skuCode){
         return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                 .map(inventory -> InventoryResponseDto.builder()
                             .skuCode(inventory.getSkuCode())
                             .isInStock(inventory.getQuantity() > 0)
                             .build())
                 .toList();
    }
}
