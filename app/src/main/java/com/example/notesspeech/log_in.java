package com.example.notesspeech;

import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class log_in extends AppCompatActivity {

    TextInputEditText inputEmail, inputPassword;
    Button submit;
    TextView errorMsg;
    ProgressBar loading;

    String name, email, password, apiKey;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);

        inputEmail = findViewById(R.id.email);
        inputPassword = findViewById(R.id.password);
        submit = findViewById(R.id.submit);
        errorMsg = findViewById(R.id.error);
        loading = findViewById(R.id.loading);

        preferences = getSharedPreferences("myapp", MODE_PRIVATE);

        if(preferences.getString("logged in", "false").equals("true")){
            Intent intent = new Intent(getApplicationContext(), home_page.class);
            startActivity(intent);
            finish();
        }

        /*submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(log_in.this, MainActivity2.class);
                startActivity(intent);
            }
        });*/

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMsg.setVisibility(View.GONE);
                loading.setVisibility(View.VISIBLE);

                email = Objects.requireNonNull(inputEmail.getText()).toString().trim();
                password = Objects.requireNonNull(inputPassword.getText()).toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    loading.setVisibility(View.GONE);
                    errorMsg.setText("Please enter both email and password.");
                    errorMsg.setVisibility(View.VISIBLE);
                    return;
                }

                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                String url = "http://192.168.100.13/login/login.php";

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                loading.setVisibility(View.GONE);
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    String status = jsonObject.getString("status");

                                    if (status.equals("success")) {
                                        name = jsonObject.getString("name");
                                        email = jsonObject.getString("email");

                                        SharedPreferences.Editor editor = preferences.edit();
                                        editor.putString("logged in", "true");
                                        editor.putString("name", name);
                                        editor.putString("email", email);
                                        editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), home_page.class);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        errorMsg.setText("Login failed. Please check your credentials.");
                                        errorMsg.setVisibility(View.VISIBLE);
                                    }
                                } catch (JSONException e) {
                                    errorMsg.setText("Error parsing response.");
                                    errorMsg.setVisibility(View.VISIBLE);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        errorMsg.setText("Network error: " + error.getLocalizedMessage());
                        errorMsg.setVisibility(View.VISIBLE);
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> paramV = new HashMap<>();
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
