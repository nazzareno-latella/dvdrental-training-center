package it.nlatella.dvdrental.graphql;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.HttpResponse;
import com.netflix.graphql.dgs.client.RequestExecutor;

@Component
public class GraphQLTemplate {

	private GraphQLClient client;
	private RequestExecutor requestExecutor;

	public GraphQLTemplate(@Value("${graphql.endpoint.url}") String endpointUrl) {
		client = new DefaultGraphQLClient(endpointUrl);

		requestExecutor = (url, headers, body) -> {
			HttpHeaders httpHeaders = new HttpHeaders();
			headers.forEach((k, v) -> {
				httpHeaders.addAll(k, v);
			});
			ResponseEntity<String> exchange = new RestTemplate().exchange(url, HttpMethod.POST,
					new HttpEntity<>(body, httpHeaders), String.class);
			return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
		};
	}

	public GraphQLResponse executeQuery(String query, Map<String, ? extends Object> variables) {
		return client.executeQuery(query, variables, requestExecutor);
	}
}
