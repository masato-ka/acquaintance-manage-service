package service.manage.acquaintance.domain.service.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import lombok.extern.slf4j.Slf4j;
import service.manage.acquaintance.domain.service.exception.ApiLimitedException;
import service.manage.acquaintance.domain.service.exception.MalformedRequestException;
import service.manage.acquaintance.domain.service.exception.NoHumanFaceException;
import service.manage.acquaintance.domain.service.exception.NoTrainSearchException;
import service.manage.acquaintance.domain.service.exception.ResourceNotFoundException;
import service.manage.acquaintance.domain.service.exception.TrainingResourceLockException;
import service.manage.acquaintance.domain.service.exception.UserResourceLimiteException;

@Slf4j
@Aspect
@Component
public class AzureClientExceptionAspect {
	
	@AfterThrowing(value = "within(service.manage.acquaintance.domain.service.PersonSearchService)", throwing="e")
	public void processExceptionSearchService(JoinPoint jp, HttpClientErrorException e){
		if(e.getStatusCode().equals(HttpStatus.BAD_REQUEST)){
			throw new NoTrainSearchException("Failed search image because did not complete pre train.");
		}
	}
	
	
	@AfterThrowing(value = "within(service.manage.acquaintance.domain.service.AcquaintanceService)", throwing="e")
	public void processException(JoinPoint jp, HttpClientErrorException e){
		if(HttpStatus.NOT_FOUND == e.getStatusCode()){
			//指定したリソースが存在しない.
			log.error(e.getResponseBodyAsString());
			throw new ResourceNotFoundException();
		}else if(HttpStatus.BAD_REQUEST == e.getStatusCode()){
			//リクエストデータが不正
			//TODO 処理メッセージごとに違うエラーを返すように詳細化すること。
			
			e.getMessage();
			log.error(e.getResponseBodyAsString());
			throw new MalformedRequestException();
		}else if(HttpStatus.FORBIDDEN == e.getStatusCode()){
			//ユーザ作成数の上限に到達
			log.error(e.getResponseBodyAsString());
			throw new UserResourceLimiteException();
		}else if(HttpStatus.CONFLICT == e.getStatusCode()){
			//指定したグループに対してトレーニングを実施中
			log.error(e.getResponseBodyAsString());
			throw new TrainingResourceLockException();
		}else if(HttpStatus.TOO_MANY_REQUESTS == e.getStatusCode()){
			//単位時間当たりのリクエスト上限に到達。
			log.error(e.getResponseBodyAsString());
			throw new ApiLimitedException();
		}
/*
		{
		  "timestamp": 1502948694781,
		  "status": 500,
		  "error": "Internal Server Error",
		  "exception": "org.springframework.web.client.HttpClientErrorException",
		  "message": "400 Bad Request",
		  "path": "/api/v1/searcher"
		}
*/
		
	}
	
}
