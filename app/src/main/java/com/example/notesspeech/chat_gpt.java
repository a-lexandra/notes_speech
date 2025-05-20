package com.example.notesspeech;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
//import com.openai.OpenAIClient;
//import com.openai.api.CompletionRequest;
//import com.openai.api.CompletionResponse;
//import com.openai.models.responses.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class chat_gpt /*extends AppCompatActivity*//* implements chat_gpt1*/ {

    //public static final String API_KEY = "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";


    /*public static String summarizeText(String inputText) {
        OpenAIClient client = OpenAIOkHttpClient.builder()
                .apiKey(API_KEY)
                .build();

        ResponseCreateParams params = ResponseCreateParams.builder()
                .model("gpt-4")
                .input("Summarize: " + inputText)
                .build();
        Response response = client.responses().create(params);
        return response.toString();
    }

    @Override
    public void asdf() {
        try {
            new Thread(() -> {
                String aa = summarizeText("OpenAI, Inc. is an American artificial intelligence (AI) research organization founded in December 2015 and headquartered in San Francisco, California. It aims to develop \"safe and beneficial\" artificial general intelligence (AGI), which it defines as \"highly autonomous systems that outperform humans at most economically valuable work\". As a leading organization in the ongoing AI boom, OpenAI is known for the GPT family of large language models, the DALL-E series of text-to-image models, and a text-to-video model named Sora. Its release of ChatGPT in November 2022 has been credited with catalyzing widespread interest in generative AI.");
                System.out.println("aa = " + aa);
                Log.d("SUMMARY", "aa = " + aa);
            }).start();
        } catch (Exception e){
            Log.d("ERROR", "Failed to summarize", e);
        }


    }*/




    //String text = "Android is an operating system based on a modified version of the Linux kernel and other open-source software, designed primarily for touchscreen-based mobile devices such as smartphones and tablets";

    public String summaryText;
    public String sendOpenAIRequest(String text) {


        //MainActivity2 mainActivity2 = new MainActivity2();
        //String text = mainActivity2.getText().toString();

        //String text = "Android is an operating system based on a modified version of the Linux kernel and other open-source software, designed primarily for touchscreen-based mobile devices such as smartphones and tablets";


        //String apiKey = "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";

        /*if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("API key is missing. Please set the OPENAI_API_KEY environment variable.");
            return apiKey;
        }*/

        OkHttpClient client = new OkHttpClient();

        List<Map<String, String>> messages = new ArrayList<>();

        String summary = "Summarize the following text: " + text;
        // Summarize the following text: Android is an operating system based on a modified version of the Linux kernel and other open-source software, designed primarily for touchscreen-based mobile devices such as smartphones and tablets

        HashMap <String, String> innerData = new HashMap<>();
        innerData.put("role", "user");
        innerData.put("content", summary);
        //innerData.put("speechText", "sdhfgsdh");

        messages.add(innerData);

        HashMap<String, Object> jsonn = new HashMap<>();
        jsonn.put("model", "gpt-3.5-turbo");
        jsonn.put("messages", messages);

        Gson gson = new Gson();
        String jsonBody = gson.toJson(jsonn);

        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));
        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .addHeader("Authorization", "Bearer apiKey")
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            //String summary;
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("OpenAI", "triggered");
                if (response.isSuccessful()) {
                    try {
                        System.out.println(response.body().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //Log.d("OpenAI", response.body().string());
                    summaryText = String.valueOf(response.body());

                } else {
                    System.err.println("Request failed: " + response.code());
                }
            }

        });

        return summaryText;
    }


    //public String getSummary(){ return summaryText;}


























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