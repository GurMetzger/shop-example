package app.core.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import app.core.entities.Product;
import app.core.entities.Supplier;
import app.core.exceptions.ProductNotFoundException;
import app.core.exceptions.SupplierNotFoundException;

@Service
@Transactional
public class SupplierService extends ClientService {
	
	public Product addProduct(String name, int amount, int supplierID) throws SupplierNotFoundException {
		if (name == null) {
			throw new IllegalArgumentException("Name is null");
		}
		if (amount < 1) {
			throw new IllegalArgumentException("Amount must be larger than 1");
		}
		
		Optional<Supplier> opt = supplierRepo.findById(supplierID);
		if (opt.isPresent()) {
			final Product product = new Product(name, amount);
			product.setSupplier(opt.get());
			
			return productRepo.save(product);
		} else {
			throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' not found");
		}
	}
	
	public Product updateProduct(Product product, int supplierID) throws SupplierNotFoundException, ProductNotFoundException {
		if (product == null) {
			throw new IllegalArgumentException("Product is null");
		}
		
		Optional<Product> optProduct = productRepo.findById(product.getId());
		if (optProduct.isPresent()) {
			
			Optional<Supplier> optSupplier = supplierRepo.findById(supplierID);
			if (optSupplier.isPresent()) { 
				
				final Product productDb = optProduct.get();
				if (productDb.getSupplier().getId() != supplierID) {
					throw new IllegalArgumentException("Cannot update another supplier's products");
				}
				
				productDb.setAmount(productDb.getAmount() + product.getAmount());
				return productDb;
				
			} else {
				throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' doesn't exist");
			}
		} else {
			throw new ProductNotFoundException("Product with ID '" + product.getId() + "' doesn't exist");
		}
	}
	
	public int deleteProduct(int productID, int supplierID) throws SupplierNotFoundException, ProductNotFoundException {
		Optional<Product> optProduct = productRepo.findById(productID);
		if (optProduct.isPresent()) {
			
			Optional<Supplier> optSupplier = supplierRepo.findById(supplierID); 
			if (optSupplier.isPresent()) {
				
				if (optProduct.get().getSupplier().getId() != supplierID) {
					throw new IllegalArgumentException("Cannot delete another supplier's products");
				}
				
				productRepo.deleteById(productID);
				return productID;
			} else {
				throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' doesn't exist");
			}
		} else {
			throw new ProductNotFoundException("Product with ID '" + productID + "' doesn't exist");
		}
	}
	
	public List<Product> getAllProducts(int supplierID) throws SupplierNotFoundException {
		Optional<Supplier> opt = supplierRepo.findById(supplierID);
		if (opt.isPresent()) {
			Hibernate.initialize(opt.get().getProducts());
			return opt.get().getProducts().stream().collect(Collectors.toList());
		} else {
			throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' doesn't exist");
		}
	}
	
	public Product getOneProduct(int productID) throws ProductNotFoundException {
		Optional<Product> opt = productRepo.findById(productID);
		if (opt.isPresent()) {
			return opt.get();
		} else {
			throw new ProductNotFoundException("Product with ID '" + productID + "' doesn't exist");
		}
	}
	
	public Supplier getDetails(int supplierID) throws SupplierNotFoundException {
		Optional<Supplier> opt = supplierRepo.findById(supplierID);
		if (opt.isPresent()) {
			Hibernate.initialize(opt.get().getProducts());
			return opt.get();
		} else {
			throw new SupplierNotFoundException("Supplier with ID '" + supplierID + "' doesn't exist");
		}
	}
	
}
