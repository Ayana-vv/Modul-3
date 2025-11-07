package module3.tests.contract;

import module3.helpers.HttpCode;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class GetToDoListWithOkHttpTest {
    private  final static String URL = "https://todo-app-sky.herokuapp.com/";

    private OkHttpClient httpClient;

    private Request request;

    @BeforeEach
    public void setUp() {
        httpClient = new OkHttpClient();
        request = new Request.Builder().url(URL).get().build();
    }

    @Test
    public void sendGetTestCheckStatusCode() throws IOException {
        Response response = httpClient.newCall(request).execute();
        int statusCode = response.code();
        assertThat(statusCode).isEqualTo(HttpCode.OK);

        System.out.println(Arrays.toString(response.headers().getNamesAndValues$okhttp()));
        System.out.println(response.body().string());
    }
}
