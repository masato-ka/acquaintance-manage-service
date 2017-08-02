package service.manage.acquaintance.domain.service.exception;

public class PersonGroupNotFoundException extends RuntimeException {

	public PersonGroupNotFoundException(){
		super("指定されたグループは存在しません。");
	}
}
