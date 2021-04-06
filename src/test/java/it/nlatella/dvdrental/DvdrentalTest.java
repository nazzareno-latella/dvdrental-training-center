package it.nlatella.dvdrental;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.TypeRef;
import com.netflix.graphql.dgs.client.DefaultGraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLClient;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import com.netflix.graphql.dgs.client.HttpResponse;
import com.netflix.graphql.dgs.client.RequestExecutor;

import it.nlatella.dvdrental.data.entity.Film;

public class DvdrentalTest {
	public static void main(String[] args) {
		GraphQLClient client = new DefaultGraphQLClient("http://localhost:8080/graphql");

		String query = "query films {\r\n"
				+ "    films {\r\n"
				+ "        title\r\n"
				+ "        description\r\n"
				+ "        releaseYear\r\n"
				+ "        rentalRate\r\n"
				+ "    }\r\n"
				+ "}";
		Map<String, ? extends Object> variables = Collections.emptyMap();
		RequestExecutor requestExecutor = (url, headers, body) -> {
			HttpHeaders httpHeaders = new HttpHeaders();
			headers.forEach((k, v) -> {
				httpHeaders.addAll(k, v);
			});
			ResponseEntity<String> exchange = new RestTemplate().exchange(url, HttpMethod.POST,
					new HttpEntity<>(body, httpHeaders), String.class);
			return new HttpResponse(exchange.getStatusCodeValue(), exchange.getBody());
		};

		GraphQLResponse graphQLResponse = client.executeQuery(query, variables, requestExecutor);
		List<Film> films = graphQLResponse.extractValueAsObject("data.films[*]", new TypeRef<List<Film>>() {
		});
		List<String> titles = graphQLResponse.extractValueAsObject("data.films[*].title", new TypeRef<List<String>>() {
		});

		System.out.println("film[0]: " + films.get(0));
		System.out.println("titles " + titles);
	}
}
