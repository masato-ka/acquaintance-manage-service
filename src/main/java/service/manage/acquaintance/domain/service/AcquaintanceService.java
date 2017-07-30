package service.manage.acquaintance.domain.service;

import org.springframework.stereotype.Service;

import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.repository.AcquaintanceRepository;

@Service
public class AcquaintanceService {

	private final AcquaintanceRepository acquaRepository; 
	
	public AcquaintanceService(AcquaintanceRepository acquaRepository){
		this.acquaRepository = acquaRepository;
	}
	
	public Acquaintance save(Acquaintance acqua){
		acquaRepository.save(acqua);
		return acqua;
	}
	
	public Acquaintance getOneWithAzurePersonId(String azurePsersonId){
		return acquaRepository.findOneByAzurePersonId(azurePsersonId);
	}
	
	
}
