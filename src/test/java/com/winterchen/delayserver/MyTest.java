package com.winterchen.delayserver;

import com.google.gson.Gson;
import com.winterchen.delayserver.dto.DefaultDelayMessageDTO;
import okhttp3.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class MyTest {

    public static void main(String[] args) throws IOException {
        String absolutePath = new File("/Users", ".").getAbsolutePath();

        File absoluteFile = new File("/Users").getAbsoluteFile();

        String canonicalPath = new File("/Users", ".").getCanonicalPath();

        String canonicalPath1 = new File("/Users").getCanonicalPath();



        System.out.println(absolutePath);
        System.out.println(absoluteFile);
        System.out.println(canonicalPath);
        System.out.println(canonicalPath1);


    }

    @Test
    public void test(){

        for (int i = 0; i < 1000; i++) {


            DefaultDelayMessageDTO messageDTO = new DefaultDelayMessageDTO();
            messageDTO.setId(Integer.toString(i));
            messageDTO.setExpireTime(3L);
            messageDTO.setMessage(Integer.toString(i));
            messageDTO.setCallbackPath("http://127.0.0.1:8099/test/success");
            messageDTO.setCurrentTime(new Date().getTime());
            messageDTO.setRetryCount(3);


            Gson gson = new Gson();
            String content = gson.toJson(messageDTO);


//            String content =  "{\n    \"id\": \"0923840293429384024\",\n    \"expireTime\": 3,\n    \"message\": \"hello\",\n    \"callbackPath\": \"http://127.0.0.1:8099/test/success\",\n    \"currentTime\": 29387492384,\n    \"retryCount\": 3\n}";



            push(content);
            System.out.println(new Date());
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public void push(String content){
        OkHttpClient client = new OkHttpClient();


        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,content);
        Request request = new Request.Builder()
                .url("http://127.0.0.1:8099/api/v1/default/delay")
                .post(body)
                .addHeader("Content-Type", "application/json")
                .addHeader("cache-control", "no-cache")
                .build();



        try {
            Response response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public  void getMessage(){

        String content =  "{\n    \"id\": \"0923840293429384024\",\n    \"expireTime\": 3,\n    \"message\": \"hello\",\n    \"callbackPath\": \"http://127.0.0.1:8099/test/success\",\n    \"currentTime\": 29387492384,\n    \"retryCount\": 3\n}";
        System.out.println(content);

        DefaultDelayMessageDTO messageDTO = new DefaultDelayMessageDTO();
        messageDTO.setId("1");
        messageDTO.setExpireTime(3L);
        messageDTO.setMessage("1");
        messageDTO.setCallbackPath("http://127.0.0.1:8099/test/success");
        messageDTO.setCurrentTime(new Date().getTime());
        messageDTO.setRetryCount(3);


        Gson gson = new Gson();
        String message = gson.toJson(messageDTO);

        System.out.println(message);


    }

}
