package service.manage.acquaintance.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;
import service.manage.acquaintance.client.AzureFaceIdentifyClient;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.repository.AcquaintanceRepository;
import service.manage.acquaintance.domain.service.exception.ApiLimitedException;
import service.manage.acquaintance.domain.service.exception.MalformedRequestException;
import service.manage.acquaintance.domain.service.exception.ResourceNotFoundException;
import service.manage.acquaintance.domain.service.exception.TrainingResourceLockException;
import service.manage.acquaintance.domain.service.exception.UserResourceLimiteException;

@Slf4j
@Service
public class AcquaintanceService {

	//TODO groupIDの取り扱い方。
	@Value("${azure.face.api.groupId:default}")
	private String groupId;
	
	private final AcquaintanceRepository acquaRepository; 
	private final AzureFaceIdentifyClient afiClient;
	
	
	public AcquaintanceService(AcquaintanceRepository acquaRepository,
							   AzureFaceIdentifyClient afiClient){
		
		this.afiClient = afiClient;
		this.acquaRepository = acquaRepository;
	}
	
	
	public Acquaintance save(Acquaintance acqua){
		
		//TODO Acquaintance オブジェクトの各プロパティ名の採番タイミングなどを検討する必要あり。
		acqua.setAzureGroupId(groupId);
		Map<String, String> personId = null;
		try{
			personId = afiClient
						.createPerson(acqua.getAzureGroupId(), acqua.getPersonName(), acqua.getPersonName());
			log.info(personId.toString());
		}catch(HttpClientErrorException e){
			if(HttpStatus.NOT_FOUND == e.getStatusCode()){
				afiClient.createPersonGroup(groupId, "test", "Hackason.");
				personId = afiClient
						.createPerson(acqua.getAzureGroupId(), acqua.getPersonName(), acqua.getPersonName());
				log.info(personId.toString());
			}else{
				//TODO personName未指定時のエラー処理(BAD Request
				throw e;
			}
		}
		acqua.setAzurePersonId(personId.get("personId"));
		Acquaintance result = acquaRepository.save(acqua);
		return result;
	}
	
	public Acquaintance update(Acquaintance acqua){
		//Update処理の場合、Azure側のユーザ情報は変更されない。(できない。)
		acquaRepository.save(acqua);
		return acqua;
	}
	
	public void delete(int id){
		//Azure側のIDを取得する。
		Acquaintance target = acquaRepository.findOne(id);
		if(target == null){
			throw new ResourceNotFoundException();
		}
		//TODO 紐づく画像データの削除を実行する。
		afiClient.deletePerson(target.getAzureGroupId(), target.getAzurePersonId());
		acquaRepository.delete(id);

	}
	
	public List<Acquaintance> getAcquaList(){
		return acquaRepository.findAll();
	}
	
	public Acquaintance getAcquaById(int id){
		Acquaintance result = acquaRepository.findOne(id);
		if(result==null){
			throw new ResourceNotFoundException(); 
		}
		return result;
	}
	
	
	
	public Acquaintance getOneWithAzurePersonId(String azurePsersonId){
		return acquaRepository.findOneByAzurePersonId(azurePsersonId);
	}
	
	public void createPersonGroup(String groupName, String userData){
		afiClient.createPersonGroup(groupId, groupName, userData);
	}
	public void deletePersonGroup(){
		//TODO groupId複数対応時には要修正
		afiClient.deletePersonGroups(groupId);
		acquaRepository.deleteAll();
	}
	
}
