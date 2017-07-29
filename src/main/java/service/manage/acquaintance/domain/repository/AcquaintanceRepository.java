package service.manage.acquaintance.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import service.manage.acquaintance.domain.model.Acquaintance;

public interface AcquaintanceRepository extends JpaRepository<Acquaintance, Integer> {

}
