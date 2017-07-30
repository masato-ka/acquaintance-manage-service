package service.manage.acquaintance.client.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {

	private String personId;
	private List<String> persistedFields;
	private String name;
	private String userData;
	
}
