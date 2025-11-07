package module3.tests.contract;

import module3.helpers.HttpCode;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GetToDoListTest {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    private HttpGet request;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
        request = new HttpGet(URL);
    }

    @Test
    public void sendGetTestCheckStatusCode() throws IOException {
        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }
}
