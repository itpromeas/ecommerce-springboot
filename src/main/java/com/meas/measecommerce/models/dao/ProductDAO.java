package com.meas.measecommerce.models.dao;

import com.meas.measecommerce.models.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product, Long> {
}
