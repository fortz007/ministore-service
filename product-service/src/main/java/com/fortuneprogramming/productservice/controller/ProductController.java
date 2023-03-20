package com.fortuneprogramming.productservice.controller;


import com.fortuneprogramming.productservice.dto.ProductRequest;
import com.fortuneprogramming.productservice.dto.ProductResponse;
import com.fortuneprogramming.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
        return "Product Created:" + productRequest;
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts (){
        return productService.getAllProducts();
    }
}
