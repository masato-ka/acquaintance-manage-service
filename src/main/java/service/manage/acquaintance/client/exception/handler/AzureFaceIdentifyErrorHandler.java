package service.manage.acquaintance.client.exception.handler;

import java.io.IOException;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class AzureFaceIdentifyErrorHandler implements ResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse arg0) throws IOException {
		// TODO Auto-generated method stub
		

	}

	@Override
	public boolean hasError(ClientHttpResponse arg0) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
