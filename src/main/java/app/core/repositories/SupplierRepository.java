package app.core.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import app.core.entities.Supplier;

public interface SupplierRepository extends JpaRepository<Supplier, Integer> {
	
	Optional<Supplier> findByName(String name);
	
}
