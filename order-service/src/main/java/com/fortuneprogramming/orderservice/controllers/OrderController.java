package com.fortuneprogramming.orderservice.controllers;

import com.fortuneprogramming.orderservice.dtos.OrderRequestDto;
import com.fortuneprogramming.orderservice.services.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<String> placeOrder(@RequestBody OrderRequestDto orderRequestDto){
         return CompletableFuture.supplyAsync(() -> orderService.placeOrder(orderRequestDto));
    }
    //Fallback method to describe error that occurred due to inactive inventory service
    public CompletableFuture<String> fallbackMethod(OrderRequestDto orderRequestDto, RuntimeException runtimeException){
        return CompletableFuture.supplyAsync(() -> "Something went wrong! Please order after sometime");
    }
}
