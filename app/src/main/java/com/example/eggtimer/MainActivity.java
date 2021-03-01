package com.example.eggtimer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timerTextView;
    SeekBar timerSeekbar;
    Button goButton;
    CountDownTimer countDownTimer;
    Boolean timerIsActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timerSeekbar = findViewById(R.id.timerSeekBar);
        timerTextView = findViewById(R.id.timerTextView);
        goButton = findViewById(R.id.goButton);

        timerSeekbar.setMax(600);
        timerSeekbar.setProgress(30);

        timerSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void updateTimer(int secondsLeft) {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String time = String.format("%02d:%02d", minutes, seconds);
        timerTextView.setText(time);
    }

    private void resetTimer() {
        timerIsActive = false;
        goButton.setText("Go!");
        timerSeekbar.setEnabled(true);
        timerSeekbar.setProgress(30);
        updateTimer(30);
        countDownTimer.cancel();
    }

    public void goClick(View v) {
        if (timerIsActive) {
            resetTimer();
        } else {
            timerIsActive = true;
            goButton.setText("Stop!");
            timerSeekbar.setEnabled(false);
            countDownTimer = new CountDownTimer(timerSeekbar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        }
    }
}