package com.example.notesspeech;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;

public class create_account extends AppCompatActivity {

    //flutter -> android studio
    //dart programming language
    //created a web page i think
    //wants to create an android app
    //will be excused from english classes and dirigentie and tic

    TextInputEditText inputName, inputEmail, inputPassword;
    Button submit;
    TextView errorMsg;
    ProgressBar loading;

    String name, email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        inputName = findViewById(R.id.name);
        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        errorMsg = findViewById(R.id.error);
        loading = findViewById(R.id.loading);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMsg.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);
                name = String.valueOf(inputName.getText());
                email = String.valueOf((inputEmail.getText()));
                password = String.valueOf(inputPassword.getText());

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url ="http://10.200.2.226/login/register.php";

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.setVisibility(View.GONE);
                                if(response.equals("success")){
                                    Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), log_in.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    errorMsg.setText(response);
                                    errorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        errorMsg.setText(error.getLocalizedMessage());
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                }){
                    protected Map<String, String> getParams(){
                        Map<String, String> paramV = new HashMap<>();
                        paramV.put("name", name);
                        paramV.put("email", email);
                        paramV.put("password", password);
                        return paramV;
                    }
                };
                queue.add(stringRequest);
            }
        });
    }
}