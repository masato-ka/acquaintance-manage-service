package service.manage.acquaintance.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FaceImage {
//TODO リポジトリの実装と循環参照回避
	@Id
	@GeneratedValue
	private Integer id;
	private String persistedFaceId;
	@ManyToOne
	@JsonIgnore
	private Acquaintance acquaintance;
	
}
