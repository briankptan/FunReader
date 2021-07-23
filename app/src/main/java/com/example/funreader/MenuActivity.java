package com.example.funreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity {

    private Button btnAT;
    private Button btnIN;
    private Button elektra;
    private Button riley;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnAT = findViewById(R.id.btnAt);
        btnIN = findViewById(R.id.btnIn);
        elektra = findViewById(R.id.btnElektra);
        riley = findViewById(R.id.btnRiley);

        elektra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playElektra();
            }
        });

        riley.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playRiley();
            }
        });

        btnAT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateAT();
            }
        });

        btnIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateIN();
            }
        });
    }

    private void playRiley() {
        MediaPlayer soundRiley = MediaPlayer.create(this, R.raw.hiriley);
        soundRiley.start();
    }

    private void playElektra() {
        MediaPlayer soundElektra = MediaPlayer.create(this, R.raw.hielektra);
        soundElektra.start();
    }

    private void activateIN() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("2Letters", "in");
        startActivity(intent);
    }

    private void activateAT() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("2Letters", "at");
        startActivity(intent);
    }
}