package service.manage.acquaintance.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import service.manage.acquaintance.client.model.TrainResult;
import service.manage.acquaintance.domain.service.PersonSearchService;

@RestController
@RequestMapping("/api/v1/trainer")
@Api(value="searcher", description="学習を実行するAPI")
public class TrainerController {
	
	@Value("${azure.face.api.groupId:default}")
	private String groupId;
	
	private final PersonSearchService personSearchService;
	public TrainerController(PersonSearchService personSearchService){
		this.personSearchService = personSearchService;
	}
	
	@PostMapping()
	@ApiOperation(value = "学習器の学習を実行する。学習は非同期で実行されるため、/statusへのGETリクエストにて学習の状態を参照する。事前に、acquaintance APIを使いデータを投入する必要がある。")
	@ApiResponses(value={
			@ApiResponse(code=200, message="学習の実行を開始"),
			@ApiResponse(code=404, message="指定したgroupIdが存在しない。"),
			@ApiResponse(code=409, message="前回実行した学習を継続中")
	})
	public void train(){
		personSearchService.train(groupId);
	}
	
	@GetMapping(path="status")
	@ApiOperation(value = "学習状態を取得するためのAPI非同期で結果を返す。")
	public TrainResult getTrainStatus(){
		TrainResult result = null;
		
		return result;
	}

}
