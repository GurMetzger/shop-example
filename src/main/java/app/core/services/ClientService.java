package app.core.services;

import org.springframework.beans.factory.annotation.Autowired;

import app.core.repositories.ProductRepository;
import app.core.repositories.SupplierRepository;

public abstract class ClientService {
	
	@Autowired
	protected ProductRepository productRepo;
	@Autowired
	protected SupplierRepository supplierRepo;
	
}
