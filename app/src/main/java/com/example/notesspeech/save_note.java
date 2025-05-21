package com.example.notesspeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class save_note extends AppCompatActivity {
    TextView text;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_save_note);

        text = findViewById(R.id.note);
        save = findViewById(R.id.save);

        Intent intent = getIntent();
        String str = intent.getStringExtra("summary");
        text.setText(str);

        String speechText = String.valueOf(text.getText());

        chat_gpt chatGpt = new chat_gpt();
        chatGpt.sendOpenAIRequest(speechText);
        while(chatGpt.responseReceived == false){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        String summary = chatGpt.summaryText;

        //String summary = chatGpt.getSummary();
        text.setText(summary);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
    }
}