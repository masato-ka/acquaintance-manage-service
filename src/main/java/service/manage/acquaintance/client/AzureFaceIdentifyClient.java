package service.manage.acquaintance.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.expression.spel.ast.TypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import service.manage.acquaintance.client.interceptor.AuthHeaderRequestInterceptor;
import service.manage.acquaintance.client.model.FaceDetectResult;
import service.manage.acquaintance.client.model.Identify;
import service.manage.acquaintance.client.model.Person;
import service.manage.acquaintance.client.model.PersonGroup;
import service.manage.acquaintance.client.model.ResultIdentify;
import service.manage.acquaintance.client.model.TrainResult;
import service.manage.acquaintance.domain.service.exception.NoHumanFaceException;

@Slf4j
@Component
public class AzureFaceIdentifyClient {
	
	private final RestTemplate restTemplate;
	
	@Value("${azure.face.api.ServerUrl}")
	private String rootUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/identify")
	private String identifyFaceUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/detect")
	private String faceDetectUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups")
	private String personGroupUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroup}")
	private String personGroupWithIdUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/train")
	private String personGroupTrainUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons")
	private String personUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons/{personId}")
	private String personWithIdUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces")
	private String personFaceUri;
	@Value("${azure.face.api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces/{persistedFaceId}")
	private String personFaceWithIdUri;
	
	public AzureFaceIdentifyClient(RestTemplateBuilder restTemplateBuilder,
									AuthHeaderRequestInterceptor authHeaderRequestInterceptor){
		restTemplate = restTemplateBuilder
				.interceptors(authHeaderRequestInterceptor)
				.rootUri(rootUri)
				.build();
	}
	
	public void createPersonGroup(String groupId, String groupName, String userData){
		Map<String,String> payload = new HashMap<>();
		payload.put("name", groupName);
		payload.put("userData", userData);
		restTemplate.put(personGroupWithIdUri, payload, groupId);
	}
	
	public List<PersonGroup> getPersonGroup() {
		/*
		URI uri = UriComponentsBuilder.fromUriString(personGroupUri).build().toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.get(uri)
					.build();
*/
		List<PersonGroup> result = Arrays.asList(
							restTemplate.getForObject(personGroupUri, PersonGroup[].class)
						);
		return result;
	}
	
	public void deletePersonGroups(String groupId) {
		restTemplate.delete(personGroupWithIdUri, groupId);
	}
	
	public Map<String,String> createPerson(String groupId, String personName, String userData) {
		URI targetUri = UriComponentsBuilder
				.fromUriString(personUri)
				.buildAndExpand(groupId)
				.toUri();
	
		Map<String, String> payload = new HashMap<>();
		payload.put("name", personName);
		payload.put("userData", userData);
		
		RequestEntity<Map<String,String>> requestEntity = RequestEntity
				.post(targetUri)
				.body(payload);
	
		ResponseEntity<Map<String,String>> responseEntity = 
				restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String,String>>(){});
		return 	responseEntity.getBody();
	}
	
	public Person getPerson(String groupId, String personId) {
		Person result = restTemplate.getForObject(personWithIdUri, Person.class, groupId,personId);
		return result;
	}
	
	public List<Person> getPersons(String groupId) {
		List<Person> result = Arrays.asList(
					restTemplate.getForObject(personUri, Person[].class, groupId)
					);
		return result;
	}
	
	public void deletePerson(String groupId, String personId) {
		restTemplate.delete(personWithIdUri, groupId, personId);
	}

	
	public Map<String,String> addPersonFaceImage(String groupId, String personId, byte[] image){
		URI targetUri = UriComponentsBuilder
					.fromUriString(personFaceUri)
					.buildAndExpand(groupId, personId)
					.toUri();
		
		RequestEntity<byte[]> requestEntity = RequestEntity
					.post(targetUri)
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(image);
		
		ResponseEntity<Map<String,String>> responseEntity = 
				restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String,String>>(){});
		return 		responseEntity.getBody();
	}
	
	public void deletePersonFaceImage(String groupId, String personId, String persistedFaceId){
		restTemplate.delete(personFaceWithIdUri, groupId, personId, persistedFaceId);
	}
	
	public void train(String groupId){
		restTemplate.postForObject(personGroupTrainUri, null, Void.class, groupId);
	}
	
	public TrainResult getTraingStatus(String groupId){
		TrainResult trainResult = restTemplate.getForObject(personGroupTrainUri, TrainResult.class,groupId);
		return trainResult;
	}
	
	public List<ResultIdentify> identifyFace(String groupId, int maxNumofConRet, double threthold, byte[] image){
		
		List<String> faceIds = faceDetect(image);
		
		if(faceIds.isEmpty()){
			throw new NoHumanFaceException("No contain human face in the image.");
		}
		
		Identify payload = new Identify();
		payload.setFaceIds(faceIds);
		payload.setPersonGroupId(groupId);
		payload.setMaxNumOfCandidatesReturned(maxNumofConRet);
		payload.setConfidenceThreshold(threthold);
		log.info(payload.toString());
		URI targetUri = UriComponentsBuilder
					.fromUriString(identifyFaceUri).build()
					.toUri();
		
		RequestEntity<Identify> requestEntity = RequestEntity
					.post(targetUri)
					.body(payload);
		
		ResponseEntity<List<ResultIdentify>> result = restTemplate.exchange(requestEntity, 
					new ParameterizedTypeReference<List<ResultIdentify>>(){});
		
		return result.getBody();
	}
	
	public List<String> faceDetect(byte[] image){
		URI targetUri = UriComponentsBuilder
					.fromUriString(faceDetectUri).build()
					.toUri();
		
		RequestEntity<byte[]> requestEntity = RequestEntity
					.post(targetUri)
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(image);
		
		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity,String.class);
		
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode jsonNode;
		List<String> result = new ArrayList<>();
		try {
			jsonNode = objectMapper.readTree(responseEntity.getBody());
			if (jsonNode.isArray()) {
			    for (final JsonNode objNode : jsonNode) {
			        result.add(objNode.get("faceId").asText());
			    }
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return result;
		
	}

	
}
