package app.core.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import app.core.entities.Supplier;
import app.core.exceptions.SupplierAlreadyExistsException;
import app.core.exceptions.SupplierNotFoundException;
import app.core.services.ManagerService;

@RestController
@CrossOrigin
@RequestMapping("/api/manager")
public class ManagerController {

	private ManagerService managerServ;
	
	@PostMapping("/add")
	public Supplier addSupplier(@RequestParam String name) {
		try {
			return managerServ.addSupplier(name);
		} catch (SupplierAlreadyExistsException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
	}
	
	@DeleteMapping("/delete")
	public int deleteSupplier(@RequestParam int supplierID) {
		try {
			return managerServ.deleteSupplier(supplierID);
		} catch (SupplierNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
		}
	}
	
	@GetMapping("/get")
	public List<Supplier> getAllSuppliers() {
		return managerServ.getAllSuppliers();
	}
}
