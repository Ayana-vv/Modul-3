package module3.tests.contract;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import module3.entities.Task;
import module3.helpers.HttpCode;
import module3.helpers.ToDoHelper;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class UpdateTaskContractTest {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private HttpClient httpClient;
    private ToDoHelper toDoHelper;
    private final String TITLE = "NewTitleBeforeUpdate";
    private int createdTaskId;

    @BeforeEach
    public void setUp() {
        httpClient = HttpClientBuilder.create().build();
        toDoHelper = new ToDoHelper();
    }
    @AfterEach
    public void tearDown() throws IOException {
        toDoHelper.deleteTask(createdTaskId);
    }

    @Test
//    @Disabled("Не запускается")
    public void updateTaskCode200() throws IOException {
        //Создали объект
        createdTaskId = toDoHelper.createTask(new Task("beforeUpdate", true));

        //Изменили имя title
        HttpPatch httpPatch = new HttpPatch(URL + "/" + createdTaskId);
        StringEntity stringEntity = prepareTask(httpPatch);
        httpPatch.setEntity(stringEntity);
        HttpResponse httpResponse = httpClient.execute(httpPatch);

        //Проверили, что код 200
        int code = httpResponse.getStatusLine().getStatusCode();
        assertThat(code).isEqualTo(HttpCode.OK);

        //Получили список
        HttpGet httpGet = new HttpGet(URL);
        HttpResponse response = httpClient.execute(httpGet);

        ObjectMapper objectMapper = new ObjectMapper();
        String stringBody = EntityUtils.toString(response.getEntity());
        Task[] tasks = objectMapper.readValue(stringBody, Task[].class);

        //Проверили title
        assertThat(tasks).anyMatch((task -> task.getTitle().equals(TITLE)));
    }

    private StringEntity prepareTask(HttpPatch httpPatch) throws JsonProcessingException {
        Task task = new Task(TITLE);
        ObjectMapper objectMapper = new ObjectMapper();
        String myContent = objectMapper.writeValueAsString(task);
        return new StringEntity(myContent, ContentType.APPLICATION_JSON);
    }
}