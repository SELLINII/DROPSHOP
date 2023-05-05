package com.sofiene.dropshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.sofiene.dropshop.models.Product;
import com.sofiene.dropshop.models.User;
import com.sofiene.dropshop.services.Categoryservice;
import com.sofiene.dropshop.services.Productservice;
import com.sofiene.dropshop.services.Userservice;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class Productcontrollers {
	@Autowired
	private Productservice productService;
	@Autowired
	private Userservice userservice;
	@Autowired
	private Categoryservice categoryservice;
	
	
	@GetMapping("/products/{id}")
    public String newProduct(@ModelAttribute("product")Product  product, Model model, @PathVariable("id") Long categoryId,HttpSession s) {
		
		Long userId = (Long) s.getAttribute("user_id");
//		Long categoryId =(Long) s.getAttribute("category_id");
		// route guard
		if(userId == null) {
			return "redirect:/";
		}
		if (categoryId ==null) {
			return"redirect:/";
		}
		String userName = (String) s.getAttribute("userName");
		
		List<Product> products = productService.allProducts();
		model.addAttribute("product",product);
        return "product.jsp";
    }
	
	
	
	@GetMapping("/products/new")
	public String create(@ModelAttribute("product") Product product,Model model) {
		
		return "form.jsp";
	}
	
	@PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") Product product, BindingResult result,Model model,
    		HttpSession session,RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
        	List<Product> products=productService.allProducts();
    		model.addAttribute("products",products);
    		
            return "form.jsp";
        } else {
        	
        	Long userId = (Long) session.getAttribute("user_id");
        	// Find user by ID
        	User currentUser = userservice.findUserById(userId);
        	
        	product.setUser(currentUser);
        	// Create a book in the DB
            productService.createProduct(product);
            redirectAttributes.addFlashAttribute("message", "you succcefuly create a book");
            return "redirect:/products";
        }
    }
	 @GetMapping("/products/{id}")
		public String index(Model model,@PathVariable("id") Long id) {
			Product product =productService.findProduct(id);
			model.addAttribute("product", product);

			return "Showone.jsp";
		}
	 @GetMapping("/products/edit/{id}")
		public String updatePage(@PathVariable("id") Long productId, Model model) {
			Product product= productService.findProduct(productId);
			model.addAttribute("product", product);
			return "edit.jsp";
		}
	 @RequestMapping(value="/products/{id}", method=RequestMethod.PUT)
	    public String update(@Valid @ModelAttribute("product") Product product, BindingResult result) {
	        if (result.hasErrors()) {
	            return "edit.jsp";
	        } else {
	        	// Fetch the book object from DB
	        	Product orginalProduct = productService.findProduct(product.getId());
	        	product.setUser(orginalProduct.getUser());
	        	
	            productService.updateProduct(product);
	            return "redirect:/products";
	        }
	    }
	 @DeleteMapping("/products/{id}")
	    public String destroy(@PathVariable("id") Long id) {
	        productService.deleteProduct(id);
	        return "redirect:/products";
	    }
}
