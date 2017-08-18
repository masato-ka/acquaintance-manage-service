package service.manage.acquaintance.api;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.model.FaceImage;
import service.manage.acquaintance.domain.service.AcquaintanceService;
import service.manage.acquaintance.domain.service.FaceImageService;

@Slf4j
@RestController
@RequestMapping(path="/api/v1/acquaintancies")
@Api(value="acquaintancies", description="ユーザ情報の管理を行うAPI")
public class AcquManageController {
	
	private final AcquaintanceService acquService;
	private final FaceImageService faceImageService;
	
	public AcquManageController(AcquaintanceService acquService, FaceImageService faceImageService){
		this.acquService = acquService;
		this.faceImageService = faceImageService;
	}
	
	@GetMapping
	@ApiOperation(value = "登録されているユーザ情報を全件取得",response = Iterable.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "データの取得に成功"),
            @ApiResponse(code = 404, message = "該当するグループが存在しない。")
	}
    )
	public List<Acquaintance> getAllAcqu(){
		List<Acquaintance> result = acquService.getAcquaList();
		//TODO グループが存在しない場合に200を送る。
		return result;
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ApiOperation(value = "ユーザ情報を1件作成する。", 
				notes="ユーザ作成時は'personName', 'age', 'sex'のみを利用",
				response = Acquaintance.class)
	@ApiResponses(value = {
            @ApiResponse(code = 201, message = "ユーザの成功に成功"),
            @ApiResponse(code = 400, message = "リクエスト情報が不正"),
            @ApiResponse(code = 404, message = "該当するグループが存在しない。")
	}
    )
	public Acquaintance saveAcqu(@RequestBody Acquaintance acquaintance){
		log.info(acquaintance.toString());
		return acquService.save(acquaintance);
	}
	
	@GetMapping(path="/{acquaitanceId}")
	@ApiOperation(value = "指定したIDのAcquaintance情報を１件取得する。", response = Acquaintance.class)
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "ユーザ情報取得成功"),
            @ApiResponse(code = 400, message = "リクエストパスが不正"),
            @ApiResponse(code = 404, message = "指定したIDが存在しない。")
	}
    )
	public Acquaintance getAcqua(@PathVariable Integer acquaitanceId){
		return acquService.getAcquaById(acquaitanceId);
	}
	
	@PutMapping(path="/{acquaintanceId}")
	@ApiOperation(value = "指定したIDのAcquaintance情報を１件更新する。")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "ユーザの更新に成功"),
            @ApiResponse(code = 400, message = "リクエスト情報が不正またはリクエストが不正"),
            @ApiResponse(code = 404, message = "該当するユーザ情報が存在しない。")
	}
    )
	public Acquaintance updateAcqua(@PathVariable Integer acquaintanceId, @RequestBody Acquaintance acquaintance){
		acquaintance.setPersonId(acquaintanceId);
		return acquService.update(acquaintance);
	}
	
	@DeleteMapping(path="/{acquaintanceId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "指定したIDのAcquaintance情報を削除する。また紐付いている画像データも削除する。")
	@ApiResponses(value = {
            @ApiResponse(code = 203, message = "ユーザ情報の削除に成功"),
            @ApiResponse(code = 500, message = "削除処理の途中で処理が失敗"),
            @ApiResponse(code = 404, message = "該当するユーザ情報が存在しない。")
	}
    )
	public void deleateAcqua(@PathVariable Integer acquaintanceId){
		acquService.delete(acquaintanceId);
	}
	
	@PutMapping(path="/{acquaintanceId}/images")
	@ApiOperation(value = "指定したIDへ画像データを追加する。")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "画像データの追加に成功"),
            @ApiResponse(code = 500, message= "画像の追加処理中にエラーが発生")
	}
    ) //TODO 引数をtrainImageからimageに変更すること。合わせて全体に名前を統一すること。 
	public FaceImage putImage(@PathVariable Integer acquaintanceId, @RequestBody MultipartFile trainImage) throws IOException{
		Acquaintance acquaintance = acquService.getAcquaById(acquaintanceId);
		FaceImage result = faceImageService.saveFaceImage(acquaintance, trainImage.getBytes());
		return result;
	}

	@DeleteMapping(path="/{acquaintanceId}/images/{imageId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	@ApiOperation(value = "指定した画像IDの画像を削除する。")
	@ApiResponses(value = {
            @ApiResponse(code = 203, message = "画像の削除に成功"),
            @ApiResponse(code = 400, message = "リクエストパスが不正"),
            @ApiResponse(code = 404, message = "指定したユーザに紐づく画像が存在しない。"),
            @ApiResponse(code = 500, message = "削除処理の途中で処理が失敗"),
	}
    )
	public void deleteImage(@PathVariable Integer acquaintanceId, @PathVariable Integer imageId){
		faceImageService.deleteFaceImage(acquaintanceId, imageId);
	}
	
	@GetMapping(path="/{acquaintanceId}/images")
	@ApiOperation(value = "指定したIDの画像情報を取得する。")
	@ApiResponses(value = {
            @ApiResponse(code = 200, message = "画像情報の取得成功"),
            @ApiResponse(code = 400, message = "リクエストパスが不正"),
            @ApiResponse(code = 404, message = "指定したユーザが存在しない。")
	}
	)
	public List<FaceImage> readImage(@PathVariable Integer acquaintanceId){
		Acquaintance acquaintance = this.acquService.getAcquaById(acquaintanceId);
		return acquaintance.getFaceImage();
	}
	
	@DeleteMapping(path="/groupId")
	@ApiOperation(value = "groupIDを削除し、Azure側の登録情報をすべて削除", notes="今の所デバック用APIです。")
	public void deleteGroupId(){
		acquService.deletePersonGroup();
	}	
	
}
