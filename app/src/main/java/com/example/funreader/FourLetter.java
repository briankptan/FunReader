package com.example.funreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class FourLetter extends AppCompatActivity {

    public static final String DICTIONARY_URL = "https://api.dictionaryapi.dev/api/v2/entries/en_US/";

    private NumberPicker p1;
    private NumberPicker p2;
    private NumberPicker p3;
    private NumberPicker p4;
    private String[] pVals;

    private Button check;
    private Button reset;
    private TextView result;
    private TextView define;
    private Switch soundSwitch;
    private Switch lockSwitch;

    private String myWord;
    private String soundURL;

    private Context mContext;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four_letter);

        result = findViewById(R.id.tvResult);

        define = findViewById(R.id.tvDefine);
        define.setMovementMethod(new ScrollingMovementMethod());

        soundSwitch = findViewById(R.id.swSound);
        soundSwitch.setChecked(true);

        lockSwitch = findViewById(R.id.swLock);

        lockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    p2.setEnabled(false);
                    p3.setEnabled(false);
                    p4.setEnabled(false);
                }
                else{
                    p2.setEnabled(true);
                    p3.setEnabled(true);
                    p4.setEnabled(true);
                }
            }
        });

        check = findViewById(R.id.btnCheck);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCheck2();
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

    private void doCheck2(){
        myWord = pVals[p1.getValue()] + pVals[p2.getValue()] + pVals[p3.getValue()] + pVals[p4.getValue()];
        myWord = myWord.replaceAll("\\s+","");

        define.setText("");

        String completeURL = DICTIONARY_URL + myWord;
        Log.d("URL", completeURL);

        mContext = getApplicationContext();

        RequestQueue requestQueue = Volley.newRequestQueue(mContext);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                completeURL,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            JSONObject item = response.getJSONObject(0);
                            String theWord = item.getString("word");
                            Log.d("WORD", theWord);

                            String item2 = response.getJSONObject(0).getJSONArray("phonetics").getJSONObject(0).getString("audio");
                            String audioURL = "https:" + item2.toString();
                            Log.d("Audio", audioURL);
                            if (soundSwitch.isChecked()){
                                playAudio(audioURL);
                            }
                            result.setText("GREAT JOB!\n" + "You spelled " + myWord);

                            for (int i = 0; i < response.length(); i++){
                                String item3 = response.getJSONObject(i).getJSONArray("meanings").getJSONObject(0).getString("partOfSpeech");
                                if (item3.equals("noun")){
                                    String item4 = response.getJSONObject(i).getJSONArray("meanings").getJSONObject(0).getJSONArray("definitions").
                                            getJSONObject(0).getString("definition");
                                    //define.setText(item4);
                                    Log.d("define", item4);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR", "FAILURE");
                        if (soundSwitch.isChecked()){
                            playError();
                        }
                        result.setText("OH NO!\n" + myWord + " is not a word.");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    private void woohoo() {
        ArrayList<Integer> soundList = new ArrayList<Integer>();
        soundList.add(R.raw.woohoo);
        soundList.add(R.raw.wahsoclever);
        soundList.add(R.raw.whoagood);
        soundList.add(R.raw.nice);

        Random random = new Random();
        int rand = random.nextInt(soundList.size());
        MediaPlayer play = MediaPlayer.create(this, soundList.get(rand));
        play.start();
    }

    private void playError() {
        ArrayList<Integer> soundList = new ArrayList<Integer>();
        soundList.add(R.raw.uhhnope);
        soundList.add(R.raw.uhidontknow);

        Random random = new Random();
        int rand = random.nextInt(soundList.size());
        MediaPlayer play = MediaPlayer.create(this, soundList.get(rand));
        play.start();
    }

    private void playAudio(String audioURL) {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioAttributes(
                new AudioAttributes.Builder()
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .build());
        try{
            mediaPlayer.setDataSource(audioURL);
            mediaPlayer.prepare();
            mediaPlayer.start();
            //mediaPlayer.stop();
            woohoo();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doReset() {
        define.setText("");
        pVals = new String[] {" ", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        p1.setValue(0);
        p2.setValue(0);
        p3.setValue(0);
        p4.setValue(0);
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
    }

    private void InitPicker(){
        pVals = new String[] {" ", "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};

        p1 = findViewById(R.id.np1);
        p2 = findViewById(R.id.np2);
        p3 = findViewById(R.id.np3);
        p4 = findViewById(R.id.np4);

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

    }
}