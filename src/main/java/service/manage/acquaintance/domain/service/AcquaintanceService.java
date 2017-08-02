package service.manage.acquaintance.domain.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import service.manage.acquaintance.client.AzureFaceIdentifyClient;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.repository.AcquaintanceRepository;

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
		}catch(HttpClientErrorException e){
			if(e.getMessage().equals("404 Not Found")){
				afiClient.createPersonGroup(groupId, "test", "Hackason.");
				personId = afiClient
						.createPerson(acqua.getAzureGroupId(), acqua.getPersonName(), acqua.getPersonName());
			}else{
				throw e;
			}
		
		}
			//TODO 上記処理失敗時に以降処理せずエラーにすること。
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
		//TODO 紐づく画像データの削除を実行する。
		
		afiClient.deletePerson(target.getAzureGroupId(), target.getAzurePersonId());
		acquaRepository.delete(id);

	}
	
	public List<Acquaintance> getAcquaList(){
		return acquaRepository.findAll();
	}
	
	public Acquaintance getAcquaById(int id){
		return acquaRepository.findOne(id);
	}
	
	
	
	public Acquaintance getOneWithAzurePersonId(String azurePsersonId){
		return acquaRepository.findOneByAzurePersonId(azurePsersonId);
	}
	
	public void deletePersonGroup(){
		afiClient.deletePersonGroups(groupId);
	}
	
}
