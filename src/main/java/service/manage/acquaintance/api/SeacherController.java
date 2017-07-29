package service.manage.acquaintance.api;

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

@RestController
@RequestMapping(path="/api/v1/searcher")
@Api(value="searcher", description="画像からユーザ検索を行うAPI")
public class SeacherController {

	@PostMapping
	@ApiOperation(value = "画像から登録ユーザの検索を行う。",response = SearchResult.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "既知ユーザの検索に成功"),
            @ApiResponse(code = 404, message = "検索ユーザが存在しない")
    }
    )
	public SearchResult search(@RequestBody MultipartFile image){
		SearchResult searchResult = null;
		return searchResult;
	}
	
}
