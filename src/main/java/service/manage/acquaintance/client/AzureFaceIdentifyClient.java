package service.manage.acquaintance.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import service.manage.acquaintance.client.model.FaceDetectResult;
import service.manage.acquaintance.client.model.Identify;
import service.manage.acquaintance.client.model.Person;
import service.manage.acquaintance.client.model.PersonGroup;
import service.manage.acquaintance.client.model.ResultIdentify;

@Component
public class AzureFaceIdentifyClient {

	private final RestTemplate restTemplate;
	@Value("${azure.face.api.subscription}")
	private String subscriptionKey;
	
	@Value("{$azure.face.api.ServerUrl}/face/v1.0/identify")
	private String identifyFaceUri;
	@Value("{$azure.face.api.ServerUrl}/face/v1.0/detect")
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
	
	public AzureFaceIdentifyClient(RestTemplateBuilder restTemplateBuilder){
		restTemplate = restTemplateBuilder.rootUri("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect").build();
	}
	
	public void createPersonGroup(String groupId, String groupName, String userData){
		
		URI targetUri = UriComponentsBuilder.fromUriString(personGroupWithIdUri).buildAndExpand(groupId).toUri();
		Map<String,String> payload = new HashMap<>();
		payload.put("name", groupName);
		payload.put("userData", userData);
		
		RequestEntity<Map<String,String>> requestEntity = RequestEntity
					.put(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.body(payload);
		
		ResponseEntity<Void> response = restTemplate.exchange(requestEntity, Void.class);	
	}
	
	public List<PersonGroup> getPersonGroup() {
		
		URI uri = UriComponentsBuilder.fromUriString(personGroupUri).build().toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.get(uri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		ResponseEntity<List<PersonGroup>> responseEntity = restTemplate.exchange(requestEntity, 
										new ParameterizedTypeReference<List<PersonGroup>>(){});
		return responseEntity.getBody();
	}
	
	public void deletePersonGroups(String groupId) {
		URI targetUri = UriComponentsBuilder.fromUriString(personGroupWithIdUri).buildAndExpand(groupId).toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.delete(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		restTemplate.exchange(requestEntity, Void.class);
	}
	
	public Map<String,String> createPerson(String groupId, String personName, String userData) {
		URI targetUri = UriComponentsBuilder.fromUriString(personUri).buildAndExpand(groupId).toUri();
		Map<String, String> payload = new HashMap<>();
		payload.put("name", personName);
		payload.put("userData", userData);
		
		RequestEntity<Map<String,String>> requestEntity = RequestEntity
						.post(targetUri)
						.header("Ocp-Apim-Subscription-Key", subscriptionKey)
						.body(payload);
		ResponseEntity<Map<String,String>> responseEntity = restTemplate.exchange(requestEntity,
					new ParameterizedTypeReference<Map<String,String>>(){});
		return responseEntity.getBody();
	}
	
	public Person getPerson(String groupId, String personId) {
		URI targetUri = UriComponentsBuilder.fromUriString(personWithIdUri).buildAndExpand(groupId,personId).toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.get(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		ResponseEntity<Person> responseEntity = restTemplate.exchange(requestEntity, Person.class);

		return responseEntity.getBody();
	}
	
	public List<Person> getPersons(String groupId) {
		URI targetUri = UriComponentsBuilder.fromUriString(personUri).buildAndExpand(groupId).toUri();
		
		RequestEntity<Void> requestEntity = RequestEntity
				.get(targetUri)
				.header("Ocp-Apim-Subscription-Key", subscriptionKey)
				.build();
		ResponseEntity<List<Person>> responseEntity = restTemplate.exchange(requestEntity, 
				new ParameterizedTypeReference<List<Person>>(){});

		return responseEntity.getBody();
		
	}
	
	public Map<String,String> addPersonFaceImage(String groupId, String personId, byte[] image){
		URI targetUri = UriComponentsBuilder
					.fromUriString(personFaceUri)
					.buildAndExpand(groupId, personId)
					.toUri();
		
		RequestEntity<byte[]> requestEntity = RequestEntity
					.post(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.contentType(MediaType.APPLICATION_OCTET_STREAM)
					.body(image);
		
		ResponseEntity<Map<String,String>> responseEntity = 
				restTemplate.exchange(requestEntity, new ParameterizedTypeReference<Map<String,String>>(){});
		return 		responseEntity.getBody();
	}
	
	public void deleatePersonFaceImage(String groupId, String personId, String persistedFaceId){
		URI targetUri = UriComponentsBuilder
					.fromUriString(personFaceWithIdUri)
					.buildAndExpand(groupId, personId, persistedFaceId)
					.toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.delete(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		restTemplate.exchange(requestEntity, Void.class);
	}
	
	public void train(String groupId){
		URI targetUri = UriComponentsBuilder
					.fromUriString(personGroupTrainUri)
					.buildAndExpand(groupId)
					.toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.post(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		restTemplate.exchange(requestEntity, Void.class);
	}	
	
	public List<ResultIdentify> identifyFace(String groupId, int maxNumofConRet, double threthold, byte[] image){
		
		List<String> faceIds = faceDetect(image);
		
		Identify payload = new Identify();
		payload.setFaceIds(faceIds);
		
		URI targetUri = UriComponentsBuilder
					.fromUriString(identifyFaceUri).build()
					.toUri();
		
		RequestEntity<Identify> requestEntity = RequestEntity
					.post(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
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
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
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
