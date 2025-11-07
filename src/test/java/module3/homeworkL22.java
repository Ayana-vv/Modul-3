package module3;

import com.fasterxml.jackson.databind.ObjectMapper;
import module3.helpers.HttpCode;
import module3.entities.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class homeworkL22 {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    @DisplayName("Тест на получение списка")
    public void getTasksTest() throws IOException {
        HttpGet request = new HttpGet(URL);
        HttpResponse response = httpClient.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringBody = EntityUtils.toString(response.getEntity());
        Task[] tasks = objectMapper.readValue(stringBody, Task[].class);
        Arrays.stream(tasks).forEach(System.out::println);
    }

    @Test
    @DisplayName("Тест на создание новой задачи")
    public void addNewTaskTest() throws IOException {
        HttpPost request = new HttpPost(URL);
        String myContent = """
                {
                    "title": "1112 java",
                    "completed": true
                }""";
        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @Test
    @DisplayName("Тест на отметку выполненной задачи")
    public void completedTaskTest() throws IOException {
        HttpPost request = new HttpPost(URL);
        String myContent = """
                {
                    "title": "1112 java",
                    "completed": true
                }""";
        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }
}
