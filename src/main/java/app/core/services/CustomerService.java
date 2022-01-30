package app.core.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import app.core.entities.Product;
import app.core.exceptions.ProductNotFoundException;
import app.core.exceptions.StockException;

@Service
@Transactional
public class CustomerService extends ClientService {
	
	public Product purchaseProduct(int productID) throws StockException, ProductNotFoundException {
		Optional<Product> opt = productRepo.findById(productID);
		if (opt.isPresent()) {
			final Product product = opt.get();
			
			if (product.getAmount() < 1) {
				throw new StockException("Product with ID '" + productID + "' ran out of stock!");
			}
			
			product.setAmount(product.getAmount() - 1);
			return product;
			
		} else {
			throw new ProductNotFoundException("Product with ID '" + productID + "' does not exist!");
		}
	}
	
}
