package client;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.nio.charset.StandardCharsets;

public final class RequestClient {


    @Value("${openai.api.key}")
    private static String openaiApiKey;
    private static final HttpClient httpClient = HttpClient.newHttpClient();


    /**
     * @param uri
     * @param contentType
     * @return
     * @throws URISyntaxException
     */
    public static HttpRequest.Builder prepareRequestBuilder(String uri, String contentType) throws URISyntaxException {
        return HttpRequest.newBuilder()
                .uri(new URI(uri))
                .headers("Content-Type", contentType, "Authorization", "Bearer " + openaiApiKey);

    }

    /**
     * @param data
     * @param request
     * @return
     */
    public static HttpRequest embedDataRequest(String data, HttpRequest.Builder request) {
        JSONObject jsonData = new JSONObject();
        jsonData.put("input", data);
        jsonData.put("model", "text-embedding-ada-002");
        jsonData.put("encoding_format", "float");

        return request.POST(HttpRequest.BodyPublishers.ofString(jsonData.toString(), StandardCharsets.UTF_8))
                .build();
    }
}
