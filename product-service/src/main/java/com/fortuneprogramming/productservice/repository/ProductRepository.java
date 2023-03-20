package com.fortuneprogramming.productservice.repository;

import com.fortuneprogramming.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
