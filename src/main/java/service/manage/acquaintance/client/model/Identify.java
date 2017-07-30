package service.manage.acquaintance.client.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Identify {

	 private String personGroupId;
	 private List<String> faceIds;
	 private int maxNumOfCandidatesReturned;
	 private double confidenceThreshold;
}
