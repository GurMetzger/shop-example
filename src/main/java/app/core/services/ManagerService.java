package app.core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import app.core.entities.Supplier;
import app.core.exceptions.SupplierAlreadyExistsException;
import app.core.exceptions.SupplierNotFoundException;

@Service
@Transactional
public class ManagerService extends ClientService {
	
	public Supplier addSupplier(String name) throws SupplierAlreadyExistsException {
		Optional<Supplier> opt = supplierRepo.findByName(name);
		if (opt.isPresent()) {
			throw new SupplierAlreadyExistsException("Supplier with name '" + name + "' already exists");
		}
		
		return supplierRepo.save(new Supplier(name));
	}
	
	public int deleteSupplier(int supplierID) throws SupplierNotFoundException {
		Optional<Supplier> opt = supplierRepo.findById(supplierID);
		if (opt.isPresent()) {
			supplierRepo.deleteById(supplierID);
			return supplierID;
		} else {
			throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' doesn't exist");
		}
	}
	
	public List<Supplier> getAllSuppliers() {
		List<Supplier> suppliers = supplierRepo.findAll();
		
		return suppliers.stream()
				.peek(s -> Hibernate.initialize(s.getProducts()))
				.collect(Collectors.toList());
	}
	
}
