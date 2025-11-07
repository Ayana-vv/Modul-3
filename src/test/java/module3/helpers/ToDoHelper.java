package module3.helpers;

import com.fasterxml.jackson.databind.ObjectMapper;
import module3.entities.Task;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ToDoHelper {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;

    public ToDoHelper() {
        httpClient = HttpClientBuilder.create().build();
    }

    public int createTask(Task task) throws IOException {
        HttpPost request = new HttpPost(URL);
        ObjectMapper objectMapper = new ObjectMapper();
        String myContent = objectMapper.writeValueAsString(task);

        StringEntity stringEntity = new StringEntity(myContent, ContentType.APPLICATION_JSON);
        request.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(request);

        String stringBody = EntityUtils.toString(response.getEntity());
        Task createdTask = objectMapper.readValue(stringBody, Task.class);
        return createdTask.getId();
    }
    public void deleteTask(int id) throws IOException {
        HttpDelete httpDelete = new HttpDelete(URL + "/" + id);
        httpClient.execute(httpDelete);
    }
}
