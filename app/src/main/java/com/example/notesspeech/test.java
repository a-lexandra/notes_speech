package com.example.notesspeech;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class test extends AppCompatActivity {

    private OpenAIService openAIService;
    private TextView text;
    private TextView summary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        openAIService = new OpenAIService();
        text = (TextView) findViewById(R.id.summaryTEST);
        summary = findViewById(R.id.summaryTEST);


        Button buttonTest = findViewById(R.id.buttonTEST);

        buttonTest.setOnClickListener(v -> {
            String msg = String.valueOf(text);
            if(!msg.isEmpty()){
                openAIService.sendMessage(msg, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        runOnUiThread(() -> summary.setText("Error: " + e.getMessage()));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        runOnUiThread(()-> summary.setText(parseResponse(responseBody)));
                    }
                });
            }
        });
    }


    private String parseResponse(String responseBody){
        try{
            JsonObject jsonObject = new JsonObject();
            JsonArray choices = jsonObject.getAsJsonArray("choices");

            if(choices != null && choices.size()>0){
                return choices.get(0).getAsJsonObject().getAsJsonObject("message").get("content").getAsString();
            }
        } catch (Exception e){
            return "Error parsing response";
        }
        return "No response from AI";
    }
}