package service.manage.acquaintance.client.interceptor;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;


@Component
public class AuthHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

	@Value("${azure.face.api.subscription}")
	private String subscriptionKey;

	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
			
			request.getHeaders().add("Ocp-Apim-Subscription-Key", subscriptionKey);
			return execution.execute(request, body);
	}

}
