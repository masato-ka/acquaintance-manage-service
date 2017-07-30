package service.manage.acquaintance.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
	@JsonIgnore
	private String azurePersonId;
	private String personName;
	private Integer age;
	private String sex;
	private boolean savedImage;
	
}
