package com.example.notesspeech;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class test extends AppCompatActivity {
    TextInputEditText inputText;
    Button submit;
    String text;
    TextView errorMsg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_test);

        inputText = findViewById(R.id.texttest);
        submit = findViewById(R.id.buttontest);
        errorMsg = findViewById(R.id.errortest);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = String.valueOf(inputText.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url="http://10.200.3.77/login/text.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Successfully saved text", Toast.LENGTH_SHORT).show();

                                } else {
                                    errorMsg.setText(response);
                                    errorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorMsg.setText(error.getLocalizedMessage());
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("text", text);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}