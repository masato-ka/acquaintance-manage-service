package service.manage.acquaintance.client;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import service.manage.acquaintance.client.model.Person;
import service.manage.acquaintance.client.model.PersonGroup;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class AzureFaceIdentifyClientTest {

	@Autowired
	AzureFaceIdentifyClient client;
	
	@Test
	public void TestdeletePersonFroup(){
		client.deletePersonGroups("test");
	}
	
	@Test
	public void TestcreatePersonGroup() {
		client.createPersonGroup("test", "testName", "The group for test");
	}
	
	@Test
	public void TestGetPresonGroups() {
		List<PersonGroup> result = client.getPersonGroup();
	
		PersonGroup payload = result.get(0);
		assertEquals(payload.getPersonGroupId(), "test");
		assertEquals(payload.getName(), "testName");
		assertEquals(payload.getUserData(), "The group for test");
		
	}
	
	@Test
	public void TestCreatePersonAndGetPerson(){
		Map<String,String> result = client.createPerson("test", "Masato", "User for Test");
		assertTrue(result.containsKey("personId"));
		Person person = client.getPerson("test", result.get("personId"));
		assertEquals(person.getName(),"Masato");
		assertEquals(person.getUserData(), "User for Test");
		assertEquals(person.getPersonId(), result.get("personId"));
	}

	@Test
	public void TestCaseAddImage(){
		List<Person> persons = client.getPersons("test");
		List<String> personsId = new ArrayList<>();
		for(int i = 0; i < persons.size(); i++){
			personsId.add(persons.get(i).getPersonId());
		}
		
		File file = new File("path");
//		ByteArrayInputStream bis = new ByteArrayInputStream(file.`);
	//	client.addPersonFaceImage("test", personsId.get(0), image);
	}
	
	
}
