package com.fortuneprogramming.orderservice.services;

import com.fortuneprogramming.orderservice.dtos.InventoryResponseDto;
import com.fortuneprogramming.orderservice.dtos.OrderItemDto;
import com.fortuneprogramming.orderservice.dtos.OrderRequestDto;
import com.fortuneprogramming.orderservice.models.Order;
import com.fortuneprogramming.orderservice.models.OrderItem;
import com.fortuneprogramming.orderservice.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient.Builder webClient;

    public void placeOrder(OrderRequestDto orderRequestDto){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItem> orderItems = orderRequestDto.getOrderItemsDtoList()
                .stream()
                .map(this::mapDtoToObject)
                .toList();
        order.setOrderItemsList(orderItems);

        List<String> skuCodes = order.getOrderItemsList().stream()
                .map(OrderItem::getSkuCode)
                .toList();

        InventoryResponseDto[] inventoryResponseList = webClient.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponseDto[].class)
                .block();


        boolean productsInStock = Arrays.stream(inventoryResponseList)
                .allMatch(InventoryResponseDto::getIsInStock);

        if(productsInStock){
            orderRepository.save(order);
        }else
            throw new IllegalArgumentException("Product is Out Of Stock, Kindly check out other product");
    }
    private OrderItem mapDtoToObject(OrderItemDto orderItemDto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(orderItemDto.getId());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setPrice(orderItemDto.getPrice());
        orderItem.setSkuCode(orderItemDto.getSkuCode());
        return orderItem;
    }
}
