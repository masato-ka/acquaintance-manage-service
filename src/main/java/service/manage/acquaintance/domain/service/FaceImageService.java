package service.manage.acquaintance.domain.service;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import service.manage.acquaintance.client.AzureFaceIdentifyClient;
import service.manage.acquaintance.domain.model.Acquaintance;
import service.manage.acquaintance.domain.model.FaceImage;
import service.manage.acquaintance.domain.repository.FaceImageRepository;
import service.manage.acquaintance.domain.service.exception.ResourceNotFoundException;

@Service
public class FaceImageService {

	//TODO groupIDの取り扱い方。
	@Value("${azure.face.api.groupId:default}")
	String groupId;
	
	private final FaceImageRepository faceImageRepository;
	private final AzureFaceIdentifyClient afiClient;
	
	public FaceImageService(AzureFaceIdentifyClient afiClient,
							FaceImageRepository faceImageRepository){
		this.faceImageRepository = faceImageRepository;
		this.afiClient=afiClient;
	}
	
	public FaceImage saveFaceImage(Acquaintance acquaintance, byte [] image){
		FaceImage faceImage = new FaceImage();
		
		Map<String, String> result = afiClient.addPersonFaceImage(groupId, 
																  acquaintance.getAzurePersonId(),
																  image);
		String persistedFaceId = result.get("persistedFaceId");
		faceImage.setAcquaintance(acquaintance);
		faceImage.setPersistedFaceId(persistedFaceId);
		
		faceImageRepository.save(faceImage);
		
		return faceImage;
	}
	
	public List<FaceImage> getFaceImagiByAcquaIntance(Acquaintance acquaintance){
		List<FaceImage> result = acquaintance.getFaceImage();
		if(result == null){throw new ResourceNotFoundException();}
		return result;
	}
	
	public void deleteFaceImage(Integer acquaintanceId, Integer imageId){
		FaceImage faceImage = faceImageRepository.getOne(imageId);
		Acquaintance acquaintance = null;
		try{
			acquaintance = faceImage.getAcquaintance();
		}catch(EntityNotFoundException e){
			throw new ResourceNotFoundException();
		}
		if(acquaintanceId == acquaintance.getPersonId()){
			faceImageRepository.delete(imageId);
			afiClient.deletePersonFaceImage(acquaintance.getAzureGroupId(), acquaintance.getAzurePersonId(), faceImage.getPersistedFaceId());
		}else{
			throw new ResourceNotFoundException();
		}
	}
	
	public byte[] loadImage(String fileName){
		return null;
	}
}
