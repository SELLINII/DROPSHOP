package com.sofiene.dropshop.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.sofiene.dropshop.models.Product;
import com.sofiene.dropshop.repository.Productinterface;

@Service
public class Productservice {private final Productinterface  productRepository;



public Productservice(Productinterface productRepository) {
       this.productRepository = productRepository;
   }
   
public List<Product> allProducts() {
       return productRepository.findAll();
   }
   
   public Product createProduct(Product b) {
       return productRepository.save(b);
   }
   
   // read one 
   public Product findProduct(Long id) {
       Optional<Product> optionalProduct= productRepository.findById(id);
       if(optionalProduct.isPresent()) {
           return optionalProduct.get();
       } else {
           return null;
       }
   }
   
   
  public Product updateProduct(Product product) {
		
		return productRepository.save(product);
	}
	
	public void deleteProduct(Long id) {
		productRepository.deleteById(id);
	}

}
