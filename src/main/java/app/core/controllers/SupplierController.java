package app.core.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Product;
import app.core.entities.Supplier;
import app.core.exceptions.ProductNotFoundException;
import app.core.exceptions.SupplierNotFoundException;
import app.core.services.SupplierService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@CrossOrigin
@RequestMapping("/api/supplier")
public class SupplierController {
	
	@Autowired
	private SupplierService supplierServ;
	
	@PostMapping("/add")
	public Product addProduct(@RequestHeader int supplierID, @RequestParam String name, @RequestParam int amount) {
		try {
			return supplierServ.addProduct(name, amount, supplierID);
		} catch (SupplierNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@PutMapping("/update")
	public Product updateProduct(@RequestHeader int supplierID, @RequestBody Product product) {
		try {
			return supplierServ.updateProduct(product, supplierID);
		} catch (SupplierNotFoundException | ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@DeleteMapping("/delete")
	public int deleteProduct(@RequestHeader int supplierID, @RequestParam int productID) {
		try {
			return supplierServ.deleteProduct(productID, supplierID);
		} catch (SupplierNotFoundException | ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/get")
	public List<Product> getAllProducts(@RequestHeader int supplierID) {
		try {
			return supplierServ.getAllProducts(supplierID);
		} catch (SupplierNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/get/{productID}")
	public Product getOneProduct(@PathVariable int productID) {
		try {
			return supplierServ.getOneProduct(productID);
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/details")
	public Supplier getDetails(@RequestHeader int supplierID) {
		try {
			return supplierServ.getDetails(supplierID);
		} catch (SupplierNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
