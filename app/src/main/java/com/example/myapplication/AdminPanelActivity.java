package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import api.ApiService;
import api.RetrofitClient;
import api.models.BorderInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPanelActivity extends AppCompatActivity {

    private ApiService apiService;
    private EditText keyWordInput, responseRoInput, responseEngInput, responseRuInput;
    private Button btnGet, btnPost, btnPut, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_panel);

        // Initialize UI components
        keyWordInput = findViewById(R.id.input_key_word);
        responseRoInput = findViewById(R.id.input_response_ro);
        responseEngInput = findViewById(R.id.input_response_eng);
        responseRuInput = findViewById(R.id.input_response_ru);

        btnGet = findViewById(R.id.btn_get);
        btnPost = findViewById(R.id.btn_post);
        btnPut = findViewById(R.id.btn_put);
        btnDelete = findViewById(R.id.btn_delete);

        apiService = RetrofitClient.getApiService();

//        Toolbar toolbar = findViewById(R.id.admin_toolbar);
//        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true); // Показывает стрелку назад
            getSupportActionBar().setTitle("Admin Panel"); // Устанавливает заголовок
        }

        // Set up button click listeners
        setupButtonListeners();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish(); // Завершаем текущую Activity и возвращаемся назад
        return true;
    }

    private void setupButtonListeners() {
        btnGet.setOnClickListener(v -> performGetRequest());
        btnPost.setOnClickListener(v -> performPostRequest());
        btnPut.setOnClickListener(v -> performPutRequest());
        btnDelete.setOnClickListener(v -> performDeleteRequest());
    }

    private void performGetRequest() {
        String keyWord = keyWordInput.getText().toString();
        if (!keyWord.isEmpty()) {
            // Logic to fetch data by key_word via GET API
            apiService.getBorderInfoByKeyWord(keyWord).enqueue(new Callback<BorderInfo>() {
                @Override
                public void onResponse(Call<BorderInfo> call, Response<BorderInfo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        // Получение данных из ответа
                        BorderInfo borderInfo = response.body();
                        responseRoInput.setText(borderInfo.getResponseRo());
                        responseEngInput.setText(borderInfo.getResponseEng());
                        responseRuInput.setText(borderInfo.getResponseRu());
                        Toast.makeText(AdminPanelActivity.this, "Data loaded successfully!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminPanelActivity.this, "No data found for the given keyword.", Toast.LENGTH_SHORT).show();
                        responseRoInput.setText("");
                        responseEngInput.setText("");
                        responseRuInput.setText("");
                    }
                }

                @Override
                public void onFailure(Call<BorderInfo> call, Throwable t) {
                    Toast.makeText(AdminPanelActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please enter a keyword!", Toast.LENGTH_SHORT).show();
        }
    }

    private void performPostRequest() {
        String keyWord = keyWordInput.getText().toString();
        String responseRo = responseRoInput.getText().toString();
        String responseEng = responseEngInput.getText().toString();
        String responseRu = responseRuInput.getText().toString();

        if (!keyWord.isEmpty() && !responseRo.isEmpty() && !responseEng.isEmpty() && !responseRu.isEmpty()) {
            // Create BorderInfo object
            BorderInfo newBorderInfo = new BorderInfo();
            newBorderInfo.setKeyWord(keyWord);
            newBorderInfo.setResponseRo(responseRo);
            newBorderInfo.setResponseEng(responseEng);
            newBorderInfo.setResponseRu(responseRu);

            // Send POST-request
            apiService.createBorderInfo(newBorderInfo).enqueue(new Callback<BorderInfo>() {
                @Override
                public void onResponse(Call<BorderInfo> call, Response<BorderInfo> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        Toast.makeText(AdminPanelActivity.this, "Data created successfully!", Toast.LENGTH_SHORT).show();
                        keyWordInput.setText("");
                        responseRoInput.setText("");
                        responseEngInput.setText("");
                        responseRuInput.setText("");
                    } else {
                        Toast.makeText(AdminPanelActivity.this, "Failed to create data. Try again.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<BorderInfo> call, Throwable t) {
                    Toast.makeText(AdminPanelActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
        }
    }

    private void performPutRequest() {
        String keyWord = keyWordInput.getText().toString().trim();
        String responseRo = responseRoInput.getText().toString().trim();
        String responseEng = responseEngInput.getText().toString().trim();
        String responseRu = responseRuInput.getText().toString().trim();

        if (keyWord.isEmpty() || responseRo.isEmpty() || responseEng.isEmpty() || responseRu.isEmpty()) {
            Toast.makeText(this, "Please fill all fields before sending a PUT request.", Toast.LENGTH_SHORT).show();
            return;
        }

        BorderInfo updatedBorderInfo = new BorderInfo();
        updatedBorderInfo.setKeyWord(keyWord);
        updatedBorderInfo.setResponseEng(responseEng);
        updatedBorderInfo.setResponseRo(responseRo);
        updatedBorderInfo.setResponseRu(responseRu);

        apiService.updateBorderInfo(keyWord, updatedBorderInfo).enqueue(new Callback<BorderInfo>() {
            @Override
            public void onResponse(Call<BorderInfo> call, Response<BorderInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BorderInfo updatedInfo = response.body();

                    responseRoInput.setText(updatedInfo.getResponseRo());
                    responseEngInput.setText(updatedInfo.getResponseEng());
                    responseRuInput.setText(updatedInfo.getResponseRu());

                    Toast.makeText(AdminPanelActivity.this, "PUT request successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminPanelActivity.this, "PUT request failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BorderInfo> call, Throwable t) {
                Toast.makeText(AdminPanelActivity.this, "PUT request error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void performDeleteRequest() {
        String keyWord = keyWordInput.getText().toString().trim();

        if (keyWord.isEmpty()) {
            Toast.makeText(this, "Please enter a keyword before sending a DELETE request.", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.deleteBorderInfo(keyWord).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    keyWordInput.setText("");
                    responseRoInput.setText("");
                    responseEngInput.setText("");
                    responseRuInput.setText("");

                    Toast.makeText(AdminPanelActivity.this, "DELETE request successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminPanelActivity.this, "DELETE request failed: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(AdminPanelActivity.this, "DELETE request error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
