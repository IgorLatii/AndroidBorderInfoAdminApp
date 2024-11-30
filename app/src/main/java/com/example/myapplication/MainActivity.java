package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

import api.ApiService;
import api.RetrofitClient;
import api.models.BorderInfo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView responseDisplay;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        responseDisplay = findViewById(R.id.response_display);
        apiService = RetrofitClient.getApiService();

        setupNavigationMenu();

        Button openMenuButton = findViewById(R.id.open_menu_button);
        openMenuButton.setOnClickListener(v -> drawerLayout.openDrawer(Gravity.LEFT));
    }

    private void setupNavigationMenu() {
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.btn_citizens) {
                fetchBorderInfo("citizens");
            } else if (itemId == R.id.btn_minors) {
                fetchBorderInfo("minors");
            } else if (itemId == R.id.btn_validity) {
                fetchBorderInfo("validity");
            } else if (itemId == R.id.btn_foregneirs) {
                fetchBorderInfo("foregneirs");
            } else if (itemId == R.id.btn_purpose) {
                fetchBorderInfo("purpose");
            } else if (itemId == R.id.btn_visas) {
                fetchBorderInfo("visas");
            } else if (itemId == R.id.btn_accepted) {
                fetchBorderInfo("accepted");
            } else if (itemId == R.id.btn_calculator) {
                fetchBorderInfo("calculator");
            } else if (itemId == R.id.btn_crossings) {
                fetchBorderInfo("crossings");
            } else if (itemId == R.id.btn_permit) {
                fetchBorderInfo("permis");
            } else if (itemId == R.id.btn_vehicles) {
                fetchBorderInfo("vehicles");
            } else if (itemId == R.id.btn_assurance) {
                fetchBorderInfo("assurance");
            } else if (itemId == R.id.btn_vinieta) {
                fetchBorderInfo("vinieta");
            } else if (itemId == R.id.btn_contacts) {
                fetchBorderInfo("contacts");
            } else if (itemId == R.id.btn_admin) {
                openAdminPanel();
            }

            drawerLayout.closeDrawers(); // Close menu after selection
            return true;
        });
    }

    private void fetchBorderInfo(String keyWord) {
        Call<BorderInfo> call = apiService.getBorderInfoByKeyWord(keyWord);
        call.enqueue(new Callback<BorderInfo>() {
            @Override
            public void onResponse(Call<BorderInfo> call, Response<BorderInfo> response) {
                if (response.isSuccessful() && response.body() != null) {
                    BorderInfo borderInfo = response.body();
                    responseDisplay.setText(borderInfo.getResponseRo());
                    Toast.makeText(MainActivity.this, "Info Found", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Not Found", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<BorderInfo> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openAdminPanel() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
