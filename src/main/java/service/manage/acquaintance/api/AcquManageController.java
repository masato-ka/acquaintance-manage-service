package service.manage.acquaintance.api;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.model.SearchResult;

@RestController
@RequestMapping(path="/api/v1/acquaintancies")
@Api(value="acquaintancies", description="ユーザ情報の管理を行うAPI")
public class AcquManageController {
	
	@GetMapping
	@ApiOperation(value = "登録されているユーザ情報を全件取得",response = Iterable.class)
	public List<Acquaintance> getAllAcqu(){
		List<Acquaintance> result = null;
		return result;
	}
	
	@PostMapping
	@ApiOperation(value = "ユーザ情報を1件追加する。", response = Acquaintance.class)
	public Acquaintance saveAcqu(@RequestBody Acquaintance acquaintance){
		Acquaintance result = null;
		return result;
	}
	
	@GetMapping(path="/{id}")
	@ApiOperation(value = "指定したIDのAcquaintance情報を１件取得する。", response = Acquaintance.class)
	public Acquaintance getAcqua(@PathVariable Integer acquaitanceId){
		return new Acquaintance();
	}
	
	@PutMapping(path="/{id}")
	@ApiOperation(value = "指定したIDのAcquaintance情報を１件更新する。", response = Acquaintance.class)
	public Acquaintance updateAcqua(@PathVariable Integer acquaitanceId, @RequestBody Acquaintance acquaintance){
		return new Acquaintance();
	}
	
	@DeleteMapping(path="/{id}")
	@ApiOperation(value = "指定したIDのAcquaintance情報を削除する。また紐付いている画像データも削除する。", response = Acquaintance.class)
	public Acquaintance deleateAcqua(){
		return new Acquaintance();
	}
	
	@PostMapping(path="/{id}/image")
	@ApiOperation(value = "指定したIDへ画像データを追加する。", response = Acquaintance.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "画像データの追加に成功"),
            @ApiResponse(code = 409, message = "すでに画像が登録されているため追加できません。")
    }
    )
	public Acquaintance createImage(@PathVariable Integer acquaitanceId, @RequestBody MultipartFile multiPartFile){
		Acquaintance result = new Acquaintance();
		result.setSavedImage(true);
		return new Acquaintance();
	}

	@PutMapping(path="/{id}/image")
	@ApiOperation(value = "指定したIDの画像データを更新する。", response = Acquaintance.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "画像データの更新に成功しました。"),
            @ApiResponse(code = 404, message = "画像が登録されていないため更新に失敗しました。")
    }
	)
	public Acquaintance updateImage(@PathVariable Integer acquaintanceId, @RequestBody MultipartFile multiPartFile){
		return new Acquaintance();
	}
	
	@GetMapping(path="/{id}/image")
	@ApiOperation(value = "指定したIDの画像データを取得する。")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "画像データの取得に成功した場合。"),
            @ApiResponse(code = 404, message = "画像が存在しないため、取得に失敗しました。")
    }
	)
	public MultipartFile readImage(@PathVariable Integer acquaintanceId){
		return null;
	}
	
	
	
	
}
