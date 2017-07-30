package service.manage.acquaintance.client.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultIdentify {

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class PersonIdentify{
		private String personId;
		private Double confidence;
	}
	
	private String faceId;
    private List<PersonIdentify> candidates;
}
