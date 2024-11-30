package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import api.ApiService;
import api.RetrofitClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private ApiService apiService;
    private EditText editTextUsername, editTextPassword;
    private Button buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        // Initialize UI components
        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);

        apiService = RetrofitClient.getApiService();

        // Display the "back" button in the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Enable the back button
            getSupportActionBar().setTitle("Loghin Panel");
        }

        // Set up login button click listener
        buttonLogin.setOnClickListener(v -> {
            String username = editTextUsername.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            /*if (authenticateUser(username, password)) {
                // If authentication is successful, open AdminPanelActivity
                Intent intent = new Intent(LoginActivity.this, AdminPanelActivity.class);
                startActivity(intent);
                finish(); // Close login activity
            } else {
                // Show error if credentials are wrong
                Toast.makeText(LoginActivity.this, "Invalid login credentials", Toast.LENGTH_SHORT).show();
            }*/
            if (!username.isEmpty() && !password.isEmpty()) {
                authenticateUser(username, password);
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Simple authentication logic (can be replaced with real server-side verification)
    private void authenticateUser(String username, String password) {
        //return username.equals("admin") && password.equals("password123");
        Call<ResponseBody> call = apiService.authenticateUser(username, password);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    String responseText = null;
                    try {
                        responseText = response.body().string();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Toast.makeText(LoginActivity.this, responseText, Toast.LENGTH_SHORT).show();
                    // If authentication is successful, open AdminPanelActivity
                    startActivity(new Intent(LoginActivity.this, AdminPanelActivity.class));
                    finish(); // Close login activity
                } else {
                    Toast.makeText(LoginActivity.this, "Authorization failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Go back to the previous activity
        return true;
    }
}
