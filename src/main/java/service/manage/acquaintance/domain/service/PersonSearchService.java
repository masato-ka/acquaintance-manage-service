package service.manage.acquaintance.domain.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import service.manage.acquaintance.client.AzureFaceIdentifyClient;
import service.manage.acquaintance.client.model.FaceDetectResult;
import service.manage.acquaintance.client.model.ResultIdentify;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.model.SearchResult;

@Service
public class PersonSearchService {

	private final AcquaintanceService acquService;
	private final AzureFaceIdentifyClient afiClient;
	
	@Value("${azure.face.api.max_number_con:3}")
	private Integer maxNumofConRet; 
	@Value("${azure.face.api.threthold:0.7}")
	private double threthold;
	
	public PersonSearchService(AcquaintanceService acquService, AzureFaceIdentifyClient afiClient){
		
		this.acquService = acquService;
		this.afiClient = afiClient;
		
	}
	
	
	public List<SearchResult> search(String groupId, byte[] image){
		
		List<SearchResult> result = new ArrayList<>();
		
		List<ResultIdentify> resultIdentifies = afiClient.identifyFace(groupId, maxNumofConRet, threthold, image);
		
		List<ResultIdentify.PersonIdentify> personIdentifies;		
		for(int index=0; index < resultIdentifies.size(); index++){
			
			personIdentifies = resultIdentifies.get(index).getCandidates();
			List<ResultIdentify.PersonIdentify> sorted = personIdentifies.stream()
				.sorted((pi1,pi2)->pi1.getConfidence().compareTo(pi2.getConfidence()))
				.collect(Collectors.toList());
			ResultIdentify.PersonIdentify sortedResult = sorted.get(0);
			
			Acquaintance acquaintance = acquService.getOneWithAzurePersonId(sortedResult.getPersonId());
			SearchResult searchResult = new SearchResult(sortedResult.getConfidence(), acquaintance);
			result.add(searchResult);
		}
	
		return result;
	}
	
	public void train(String groupId){
		afiClient.train(groupId);
	}
	
}
