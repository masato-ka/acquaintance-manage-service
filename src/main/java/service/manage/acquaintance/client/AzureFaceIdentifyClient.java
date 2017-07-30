package service.manage.acquaintance.client;

import java.net.URI;
import java.net.URISyntaxException;
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

import service.manage.acquaintance.client.model.Person;
import service.manage.acquaintance.client.model.PersonGroup;

@Component
public class AzureFaceIdentifyClient {

	private final RestTemplate restTemplate;
	private final String subscriptionKey = "";
	
	@Value("${api.ServerUrl}/face/v1.0/persongroups")
	private String personGroupUri;
	@Value("${api.ServerUrl}/face/v1.0/persongroups/{personGroup}")
	private String personGroupWithIdUri;
	@Value("${api.ServerUrl}/face/v1.0/persongroups/{personGroup}/persons")
	private String personUri;
	@Value("${api.ServerUrl}/face/v1.0/persongroups/{personGroup}/persons/{personId}")
	private String personWithIdUri;
	@Value("${api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces")
	private String personFaceUri;
	@Value("${api.ServerUrl}/face/v1.0/persongroups/{personGroupId}/persons/{personId}/persistedFaces/{persistedFaceId}")
	private String personFaceWithIdUri;
	
	public AzureFaceIdentifyClient(RestTemplateBuilder restTemplateBuilder){
		restTemplate = restTemplateBuilder.rootUri("https://westcentralus.api.cognitive.microsoft.com/face/v1.0/detect").build();
	}
	
	public PersonGroup createPersonGroup(String groupId, String groupName, String userData) throws URISyntaxException{
		
		URI targetUri = UriComponentsBuilder.fromUriString(personGroupWithIdUri).buildAndExpand(groupId).toUri();
		Map<String,String> payload = new HashMap<>();
		payload.put("name", groupName);
		payload.put("userData", userData);
		
		RequestEntity<Map<String,String>> requestEntity = RequestEntity
					.put(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.body(payload);
		
		ResponseEntity<PersonGroup> response = restTemplate.exchange(requestEntity, PersonGroup.class);
		return response.getBody();		
	}
	
	public List<PersonGroup> getPersonGroup() throws URISyntaxException{
		
		URI uri = new URI(personGroupUri);
		RequestEntity<Void> requestEntity = RequestEntity
					.get(uri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		ResponseEntity<List<PersonGroup>> responseEntity = restTemplate.exchange(requestEntity, 
										new ParameterizedTypeReference<List<PersonGroup>>(){});
		return responseEntity.getBody();
	}
	
	public void deletePersonGroups(String groupId) throws URISyntaxException{
		URI targetUri = UriComponentsBuilder.fromUriString(personGroupWithIdUri).buildAndExpand(groupId).toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.delete(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		restTemplate.exchange(requestEntity, Void.class);
	}
	
	public Person createPerson(String groupId, String personName, String userData) 
			throws URISyntaxException{
		URI targetUri = UriComponentsBuilder.fromUriString(personUri).buildAndExpand(groupId).toUri();
		Map<String, String> payload = new HashMap<>();
		payload.put("name", personName);
		payload.put("userData", userData);
		
		RequestEntity<Map<String,String>> requestEntity = RequestEntity
						.post(targetUri)
						.header("Ocp-Apim-Subscription-Key", subscriptionKey)
						.body(payload);
		ResponseEntity<Person> responseEntity = restTemplate.exchange(requestEntity, Person.class);
		return responseEntity.getBody();
	}
	
	public Person getPerson(String groupId, String personId) throws URISyntaxException{
		URI targetUri = UriComponentsBuilder.fromUriString(personWithIdUri).buildAndExpand(groupId,personId).toUri();
		RequestEntity<Void> requestEntity = RequestEntity
					.get(targetUri)
					.header("Ocp-Apim-Subscription-Key", subscriptionKey)
					.build();
		ResponseEntity<Person> responseEntity = restTemplate.exchange(requestEntity, Person.class);

		return responseEntity.getBody();
	}
	
	public List<Person> getPersons(String groupId) throws URISyntaxException{
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
	
	public void train(){
		
	}
	
	public void retrain(){
		
	}
	
	public void identifyFace(){
		
	}
	
}
