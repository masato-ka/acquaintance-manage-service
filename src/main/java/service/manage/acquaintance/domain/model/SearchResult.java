package service.manage.acquaintance.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchResult {

	private double simirarity;
	private Acquaintance acquaintanceInfo;
	
}
