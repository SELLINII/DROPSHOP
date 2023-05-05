package com.sofiene.dropshop.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sofiene.dropshop.models.Product;



public interface Productinterface extends CrudRepository<Product, Long> {
	List<Product> findAll();
}
