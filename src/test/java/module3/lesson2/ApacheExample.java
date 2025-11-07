package module3.lesson2;

import com.fasterxml.jackson.databind.ObjectMapper;
import module3.entities.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApacheExample {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    public void sendGetTestCheckStatusCode() throws IOException {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        System.out.println(request);
        System.out.println(response.getStatusLine().getProtocolVersion());
        System.out.println(response.getStatusLine().getStatusCode());
        System.out.println(response.getStatusLine().getReasonPhrase());
        assertEquals(200, response.getStatusLine().getStatusCode());
    }

    @Test
    public void sendGetTestCheckStatusBody() throws IOException {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
//        System.out.println(EntityUtils.toString(response.getEntity()));
        ObjectMapper objectMapper = new ObjectMapper();
        String stringBody = EntityUtils.toString(response.getEntity());
        Task[] tasks = objectMapper.readValue(stringBody, Task[].class);
        Arrays.stream(tasks).forEach(System.out::println);
    }

    @Test
    public void sendGetTestCheckContentType() throws IOException {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
//        System.out.println(response.getAllHeaders()[2].getName());
//        System.out.println(response.getAllHeaders()[2].getValue());
//        System.out.println(response.getHeaders("Content-Type")[0].getName());
//        System.out.println(response.getHeaders("Content-Type")[0].getValue());
        assertEquals("application/json; charset=utf-8", response.getHeaders("Content-Type")[0].getValue());
    }
}
