package service.manage.acquaintance.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.http.HttpStatus;

import service.manage.acquaintance.domain.service.exception.ApiLimitedException;
import service.manage.acquaintance.domain.service.exception.MalformedRequestException;
import service.manage.acquaintance.domain.service.exception.NoHumanFaceException;
import service.manage.acquaintance.domain.service.exception.ResourceNotFoundException;
import service.manage.acquaintance.domain.service.exception.TrainingResourceLockException;
import service.manage.acquaintance.domain.service.exception.UserResourceLimiteException;
import service.manage.acquaintance.domain.service.exception.PersonGroupNotFoundException;

@ControllerAdvice
public class AcquManagerExceotionController extends ResponseEntityExceptionHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(ResourceNotFoundException.class)
	public void  resourceNotFoundExceptionHandler(HttpServletRequest req, ResourceNotFoundException e){
    }
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PersonGroupNotFoundException.class)
	public void personGroupNotFoundException(HttpServletRequest req, PersonGroupNotFoundException e){
		
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NoHumanFaceException.class)
	public void noFumanFaceException(HttpServletRequest req, NoHumanFaceException e){
		
	}

	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(ApiLimitedException.class)
	public void personGroupNotFoundExceptionHandler(HttpServletRequest req, ApiLimitedException e){
		//時間を開けてリクエストを投げるようにしたい。
		
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(TrainingResourceLockException.class)
	public void trainingResourceLockExceptionHandler(HttpServletRequest req, TrainingResourceLockException e){
		//時間を開けてリクエストを投げるようにしたい。
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MalformedRequestException.class)
	public void malformedRequestExceptionHandler(HttpServletRequest req, MalformedRequestException e){
		
	}
		
	@ResponseStatus(HttpStatus.FORBIDDEN)
	@ExceptionHandler(UserResourceLimiteException.class)
	public void userResourceLimiteExceptionHandler(HttpServletRequest req, UserResourceLimiteException e){
		
	}
	
}
