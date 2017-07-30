package service.manage.acquaintance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import service.manage.acquaintance.domain.model.Acquaintance;

public interface AcquaintanceRepository extends JpaRepository<Acquaintance, Integer> {
	
	Acquaintance findOneByAzurePersonId(String azurePersonId);
	
}
