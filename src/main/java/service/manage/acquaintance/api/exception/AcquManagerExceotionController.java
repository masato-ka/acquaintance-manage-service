package service.manage.acquaintance.api.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import service.manage.acquaintance.domain.service.exception.ApiLimitedException;
import service.manage.acquaintance.domain.service.exception.MalformedRequestException;
import service.manage.acquaintance.domain.service.exception.PersonGroupNotFoundException;
import service.manage.acquaintance.domain.service.exception.ResourceNotFoundException;
import service.manage.acquaintance.domain.service.exception.TrainingResourceLockException;
import service.manage.acquaintance.domain.service.exception.UserResourceLimiteException;

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

	@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
	@ExceptionHandler(ApiLimitedException.class)
	public void personGroupNotFoundExceptionHandler(HttpServletRequest req, ApiLimitedException e){
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(TrainingResourceLockException.class)
	public void trainingResourceLockExceptionHandler(HttpServletRequest req, TrainingResourceLockException e){
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
