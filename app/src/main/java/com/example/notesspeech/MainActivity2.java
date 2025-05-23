package com.example.notesspeech;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private final int record = 1;
    ImageView image;
    TextView text;
    Button summaryBtn, save, test;

    //chatgpt summary
    /*private TextView summary = findViewById(R.id.summary);
    private String stringURLEndPoint = "https://api.openai.com/v1/chat/completions";
    private String stringAPIKey = "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";
    private String stringOutput = "";*/

    public TextView getText(){
        return text;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        text = findViewById(R.id.speech);
        image = findViewById(R.id.image);
        summaryBtn = findViewById(R.id.summaryBtn);
        save = findViewById(R.id.save);
        test = findViewById(R.id.testBtn);

        //save.setVisibility(View.INVISIBLE);
        //summaryBtn.setVisibility(View.INVISIBLE);


        /*test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*String testTxt = text.getText().toString();
                Intent intent = new Intent(getApplicationContext(), test.class);
                intent.putExtra("test", testTxt);
                startActivity(intent)*//*;

                Intent intent2 = new Intent(MainActivity2.this, notes.class);
                startActivity(intent2);
            }
        });*/


        summaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*OpenAIClient openAIClient = new OpenAIClient("[myApiKey]");
                try {
                    openAIClient.sendMessage("Tell me a joke");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }*/

                //String speechText = text.getText().toString();

                /*chat_gpt myAI = new chat_gpt();
                myAI.sendOpenAIRequest();
                while (true){
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }*/

                String speechText = text.getText().toString();
                Intent intentN = new Intent(getApplicationContext(), save_note.class);
                intentN.putExtra("summary", speechText);
                startActivity(intentN);
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //text = String.valueOf(inputText.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url="http://192.168.100.13/login/text.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Successfully saved text", Toast.LENGTH_SHORT).show();

                                } else {
                                    //errorMsg.setText(response);
                                    //errorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //errorMsg.setText(error.getLocalizedMessage());
                        //errorMsg.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("text", String.valueOf(text));
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            //    intent.putExtra(RecognizerIntent.EXTRA_ENABLE_LANGUAGE_DETECTION, true);
            //    intent.putExtra(RecognizerIntent.EXTRA_ENABLE_LANGUAGE_SWITCH, true);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_DETECTION_ALLOWED_LANGUAGES,"ro-RO");
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
            //    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "ro-RO");
            //    intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_MINIMUM_LENGTH_MILLIS, 100000);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_COMPLETE_SILENCE_LENGTH_MILLIS, 10000000);
                intent.putExtra(RecognizerIntent.EXTRA_SPEECH_INPUT_POSSIBLY_COMPLETE_SILENCE_LENGTH_MILLIS, 10000000);

                try{
                    startActivityForResult(intent, record);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(MainActivity2.this, "Not working", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case record:{
                if (resultCode == RESULT_OK && null != data){
                    ArrayList result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    text.setText((String) result.get(0));
                }
                break;
            }
        }
    }











    /*public void chatGPT(View view){
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("model", "gpt-3.5-turbo");
            JSONArray jsonArrayMessage = new JSONArray();
            JSONObject jsonObjectMessage = new JSONObject();

            jsonObjectMessage.put("role","user");
            jsonObject.put("content", "summarize the following text: " + text);
            jsonArrayMessage.put(jsonObjectMessage);
            jsonObject.put("messages", jsonArrayMessage);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, stringURLEndPoint, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String stext = null;
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
            public void onErrorResponse(VolleyError volleyError) {

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
