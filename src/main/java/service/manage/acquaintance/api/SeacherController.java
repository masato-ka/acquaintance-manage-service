package service.manage.acquaintance.api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import service.manage.acquaintance.domain.model.SearchResult;
import service.manage.acquaintance.domain.service.PersonSearchService;

@RestController
@RequestMapping(path="/api/v1/searcher")
@Api(value="searcher", description="画像からユーザ検索を行うAPI")
public class SeacherController {
	
	@Value("${azure.face.api.groupId:default}")
	private String groupId;
	private final PersonSearchService personSearchService;
	
	public SeacherController(PersonSearchService personSeachService){
		this.personSearchService = personSeachService;
	}
	
	@PostMapping
	@ApiOperation(value = "画像から登録ユーザの検索を行う。")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "既知ユーザの検索に成功"),
            @ApiResponse(code = 404, message = "顔画像が含まれない画像データ")
    }
    )
	public List<SearchResult> search(@RequestBody MultipartFile image){
		
		List<SearchResult> searchResult = null;
		try {
			searchResult = this.personSearchService.search(groupId, image.getBytes());
		} catch (IOException e) {
			// TODO searcherの処理
			e.printStackTrace();
		}
		
		return searchResult;
	}
	
}
