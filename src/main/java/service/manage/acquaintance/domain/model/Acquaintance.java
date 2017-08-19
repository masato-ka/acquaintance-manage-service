package service.manage.acquaintance.domain.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
	@NotNull
	private String personName;
	@Min(0)
	@Max(200)
	private Integer age;
	@Pattern(regexp="^male$|^female$")
	private String sex;
	@OneToMany(mappedBy="acquaintance")
	private List<FaceImage> faceImage;

	@JsonIgnore
	private String azurePersonId;
	@JsonIgnore
	private String azureGroupId;

	
}
