package com.example.nac_marvelapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private TextView resposta;
    private Spinner currency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText valor = findViewById(R.id.etMain_value);
        currency = findViewById(R.id.spinnerCurrency);
        String[] items = new String[]{"BRL", "USD", "EUR"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        currency.setAdapter(adapter);

        resposta = findViewById(R.id.etMain_resposta);

        Button btnConverter = findViewById(R.id.btnMain_buscarBitcoin);

        btnConverter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stringValue = valor.getText().toString();
                if(stringValue.matches("")){stringValue = "0";}
                String stringCurrency = currency.getSelectedItem().toString();
                requestData("https://blockchain.info/tobtc",stringCurrency,stringValue);
            }
        });
    }

    private void requestData(String url,String currency, String value) {
        RequestPackage requestPackage = new RequestPackage();
        requestPackage.setMethod("GET");
        requestPackage.setParam("currency",currency);
        requestPackage.setParam("value",value);
        requestPackage.setUrl(url);

        Downloader downloader = new Downloader();

        downloader.execute(requestPackage);
    }

    private class Downloader extends AsyncTask<RequestPackage, String, String> {
        @Override
        protected String doInBackground(RequestPackage... params) {
            return HttpManager.getData(params[0]);
        }


        @Override
        protected void onPostExecute(String result) {
//                JSONObject jsonObject = new JSONObject(result);
//                  String jsonReturn = jsonObject.getString("title");
                resposta.setText(result+" Biticoins");
        }
    }

}

