package com.fortuneprogramming.inventoryservice.services;

import com.fortuneprogramming.inventoryservice.dtos.InventoryResponseDto;
import com.fortuneprogramming.inventoryservice.repositories.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @SneakyThrows
    @Transactional(readOnly = true)
    public List<InventoryResponseDto> isInStock(List<String> skuCode){
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
         return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                 .map(inventory -> InventoryResponseDto.builder()
                             .skuCode(inventory.getSkuCode())
                             .isInStock(inventory.getQuantity() > 0)
                             .build())
                 .toList();
    }
}
