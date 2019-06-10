package com.example.retrofittest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.retrofittest.pojos.WResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";
    private EditText editText;
    private Button button;
    private TextView responseText;

    NetworkClient networkClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        editText = findViewById(R.id.city_name);
        button = findViewById(R.id.city_click);
        responseText = findViewById(R.id.response_text);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchWeatherDetails();
            }
        });

    }

    private void fetchWeatherDetails() {

        Retrofit retrofit = NetworkClient.getRetrofitClient();

        WeatherAPIs weatherAPIs = retrofit.create(WeatherAPIs.class);

        Call<WResponse> call = weatherAPIs.getWeatherByCity(editText.getText().toString(), "6d57a308f6913bca69a2e14a398a4142");

        call.enqueue(new Callback<WResponse>() {
                @Override
                public void onResponse(Call<WResponse> call, Response<WResponse> response) {
                    /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                     */
                    if (response.body() != null) {
                        WResponse wResponse = response.body();
                        responseText.setText("Temp: " + wResponse.getMain().getTemp() + "\n " +
                                "Humidity: " + wResponse.getMain().getHumidity() + "\n" +
                                "Pressure: " + wResponse.getMain().getPressure());
                    }
                }
            @Override
            public void onFailure(Call<WResponse> call, Throwable t) {
                Log.d(TAG, "failed code");
                call.cancel();
            }
        });

    }
}
