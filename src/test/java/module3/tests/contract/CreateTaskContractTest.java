package module3.tests.contract;

import com.fasterxml.jackson.databind.ObjectMapper;
import module3.helpers.HttpCode;
import module3.entities.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class CreateTaskContractTest {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
    }

    @Test
    public void createTaskCode200() throws IOException {
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
    public void createTaskWithoutBody() throws IOException {
        HttpPost request = new HttpPost(URL);

        HttpResponse response = httpClient.execute(request);
        int statusCode = response.getStatusLine().getStatusCode();
        assertThat(statusCode).isEqualTo(HttpCode.OK);
    }

    @Test
    public void createTaskResponseBody() throws IOException {
        HttpPost request = new HttpPost(URL);
        String myContent = """
                {
                    "title": "1112 java",
                    "completed": true
                }""";
        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);

        HttpResponse response = httpClient.execute(request);
        ObjectMapper objectMapper = new ObjectMapper();
        String stringBody = EntityUtils.toString(response.getEntity());
        Task task = objectMapper.readValue(stringBody, Task.class);
        assertThat(task.getTitle()).isEqualTo("1112 java");
    }
}
