package com.example.notesspeech;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.core5.http.HttpRequest;
import org.apache.hc.core5.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import okhttp3.*;
import okhttp3.Request.Builder;


public class chat_gpt extends AppCompatActivity {

    public void sendOpenAIRequest() {
        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API key is missing. Please set the OPENAI_API_KEY environment variable.");
            return;
        }

        OkHttpClient client = new OkHttpClient();

        String json = """
        {
            "model": "gpt-3.5-turbo",
            "messages": [
                {
                    "role": "user",
                    "content": "Summarize the following text"
                }
            ]
        }
        """;

        RequestBody body = RequestBody.create(json, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    System.out.println(response.body().string());
                } else {
                    System.err.println("Request failed: " + response.code());
                }
            }
        });
    }

























    /*private OpenAIService openAIService;
    private TextView text;
    private TextView summary;
    *//*private String stringURLEndPoint = "https://api.openai.com/v1/chat/completions";
    private String stringAPIKey = "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";
    private String stringOutput="";*//*

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_chat_gpt);
        setContentView(R.layout.activity_test);

        openAIService = new OpenAIService();
        //View view = getLayoutInflater().inflate(R.layout.activity_main2, null);
        //text = (EditText) view.findViewById(R.id.speech);
        text = (TextView) findViewById(R.id.summaryTEST);
        //summary = findViewById(R.id.summary);
        summary = findViewById(R.id.summaryTEST);


        Button buttonTest = findViewById(R.id.buttonTEST);

        buttonTest.setOnClickListener(v -> {
            String msg = String.valueOf(text);
            if(!msg.isEmpty()){
                openAIService.sendMessage(msg, new Callback() {
                    @Override
                    public void onFailure(@NonNull Call call, @NonNull IOException e) {
                        Log.e("OpenAI-Error", "API call failed", e);
                        runOnUiThread(() -> summary.setText("Error: " + e.getMessage()));
                    }

                    @Override
                    public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                        String responseBody = response.body().string();
                        if (response.isSuccessful()){

                            Log.d("OpenAI-Response", responseBody);
                            runOnUiThread(() -> {
                                summary.setText(parseResponse(responseBody));
                            });
                        } else {
                            Log.e("OpenAI-Error", "API Error: " + response.code() + " Body: " + responseBody);
                                runOnUiThread(() -> summary.setText("API Error: " + response.code()));
                        }

                        //String responseBody = response.body().string();
                        //runOnUiThread(()-> summary.setText(parseResponse(responseBody)));
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
    }*/












    /*public void chatGPT(View view){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray jsonArrayMessage = new JSONArray();
            JSONObject jsonObjectMessage = new JSONObject();

            jsonObjectMessage.put("role","user");
            jsonObject.put("content", "summarize the following text: " + text.getText().toString());
            jsonArrayMessage.put(jsonObjectMessage);
            jsonObject.put("messages", jsonArrayMessage);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringURLEndPoint, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String stext;
                try {
                    stext = response.getJSONArray("choices")
                            .getJSONObject(0)
                            .getJSONObject("message")
                            .getString("content");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

                stringOutput = stringOutput + stext;
                summary.setText(stringOutput);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                summary.setText("Error" + error.getMessage());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> mapHeader = new HashMap<>();
                mapHeader.put("Authorization", "Bearer " + stringAPIKey);
                mapHeader.put("Content-Type", "application/json");
                return mapHeader;
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        int timeoutPeriod = 60000; //60 sec
        RetryPolicy retryPolicy = new DefaultRetryPolicy(timeoutPeriod, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT );
        jsonObjectRequest.setRetryPolicy(retryPolicy);

        Volley.newRequestQueue(getApplicationContext()).add(jsonObjectRequest);
    }*/
}