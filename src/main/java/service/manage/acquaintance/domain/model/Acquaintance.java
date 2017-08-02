package service.manage.acquaintance.domain.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Acquaintance {

	@Id
	@GeneratedValue
	private Integer personId;
	private String personName;
	private Integer age;
	private String sex;
	@OneToMany(mappedBy="acquaintance")
	private List<FaceImage> faceImage;

	@JsonIgnore
	private String azurePersonId;
	@JsonIgnore
	private String azureGroupId;

	
}
