/*package com.example.notesspeech;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.RequestBody;

public class OpenAIService extends AppCompatActivity {
    //openai
    private static final String APIKey = "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";
    private static final String APIUrl = "https://api.openai.com/v1/chat/completions";

    //request
    private final OkHttpClient client = new OkHttpClient();
    private final Gson gson = new Gson();
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = getLayoutInflater().inflate(R.layout.activity_test, null);
        text = (TextView) view.findViewById(R.id.testText);
    }

    public void sendMessage(String UserMessage, Callback callback){
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("model", "gpt-3.5-turbo");
        JsonArray messages = new JsonArray();
        JsonObject messagesObj = new JsonObject();
        messagesObj.addProperty("role", "user");
        messagesObj.addProperty("content", "summarize the following text: " + text);
        messages.add(messagesObj);
        jsonObject.add("messages", messages);

        RequestBody body = RequestBody.create(jsonObject.toString(), MediaType.get("application/json; charset=utf-8"));

        Request request = new Request.Builder().url(APIUrl).addHeader("Authorization", "Bearer" + APIKey).post(body).build();

        client.newCall(request).enqueue(callback);
    }
}*/
