package app.core.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Product;
import app.core.exceptions.ProductNotFoundException;
import app.core.exceptions.StockException;
import app.core.services.CustomerService;

@RestController
@CrossOrigin
@RequestMapping("/api/customer")
public class CustomerController {

	@Autowired
	private CustomerService customerServ;
	
	@PostMapping("/purchase")
	public Product purhcaseProduct(@RequestParam int productID) {
		try {
			return customerServ.purchaseProduct(productID);
		} catch (StockException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		} catch (ProductNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
}
