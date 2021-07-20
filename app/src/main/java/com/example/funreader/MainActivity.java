package com.example.funreader;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Dictionary;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String DICTIONARY_URL = "https://api.dictionaryapi.dev/api/v2/entries/en_US/";

    private NumberPicker p1;
    private NumberPicker p2;
    private NumberPicker p3;
    private NumberPicker p4;
    private NumberPicker p5;
    private String[] pVals;

    private Button check;
    private Button reset;
    private TextView result;
    private TextView define;
    private String myWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = findViewById(R.id.tvResult);
        define = findViewById(R.id.tvDefine);

        check = findViewById(R.id.btnCheck);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheck();
            }
        });

        reset = findViewById(R.id.btnReset);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doReset();
            }
        });

        InitPicker();
        getLetters();

    }

    private void doCheck() {
        myWord = pVals[p1.getValue()] + pVals[p2.getValue()] + pVals[p3.getValue()] + pVals[p4.getValue()] + pVals[p5.getValue()];
        myWord = myWord.replaceAll("\\s+","");

        String completeURL = DICTIONARY_URL + myWord;

        Log.d("URL", completeURL);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, completeURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.d("RESULT: ", response);
                        result.setText("GREAT JOB!\n" + myWord + " is a word!");
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FAILURE", "OOPS");
                result.setText("OH NO!\n" + myWord + " is not a word.");
            }
        });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void doReset() {
        p1.setValue(0);
        p2.setValue(0);
        p3.setValue(0);
        p4.setValue(0);
        p5.setValue(0);
        result.setText("");
    }

    private void getLetters(){
        p1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int valuePicker1 = p1.getValue();
                //Log.d("P1", pVals[valuePicker1]);
            }
        });

        p2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int valuePicker2 = p2.getValue();
                //Log.d("P2", pVals[valuePicker2]);
            }
        });

        p3.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int valuePicker3 = p3.getValue();
                //Log.d("P3", pVals[valuePicker3]);
            }
        });

        p4.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int valuePicker4 = p4.getValue();
                //Log.d("P4", pVals[valuePicker4]);
            }
        });

        p5.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                int valuePicker5 = p5.getValue();
                //Log.d("P5", pVals[valuePicker5]);
            }
        });
    }

    private void InitPicker(){
        pVals = new String[] {" ", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        p1 = findViewById(R.id.np1);
        p2 = findViewById(R.id.np2);
        p3 = findViewById(R.id.np3);
        p4 = findViewById(R.id.np4);
        p5 = findViewById(R.id.np5);

        p1.setMaxValue(26);
        p1.setMinValue(0);
        p1.setDisplayedValues(pVals);

        p2.setMaxValue(26);
        p2.setMinValue(0);
        p2.setDisplayedValues(pVals);

        p3.setMaxValue(26);
        p3.setMinValue(0);
        p3.setDisplayedValues(pVals);

        p4.setMaxValue(26);
        p4.setMinValue(0);
        p4.setDisplayedValues(pVals);

        p5.setMaxValue(26);
        p5.setMinValue(0);
        p5.setDisplayedValues(pVals);
    }
}