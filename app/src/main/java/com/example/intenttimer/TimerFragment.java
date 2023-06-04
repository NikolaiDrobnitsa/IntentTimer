package com.example.intenttimer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TimerFragment extends Fragment {

    private NumberPicker hourPicker;
    private NumberPicker minutePicker;
    private NumberPicker secondPicker;
    private Button startButton;
    private Button stopButton;
    private TextView countdownTextView;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning = false;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_timer, container, false);

        hourPicker = view.findViewById(R.id.hourPicker);
        minutePicker = view.findViewById(R.id.minutePicker);
        secondPicker = view.findViewById(R.id.secondPicker);
        startButton = view.findViewById(R.id.startButton);
        stopButton = view.findViewById(R.id.stopButton);
        countdownTextView = view.findViewById(R.id.countdownTextView);

        hourPicker.setMinValue(0);
        hourPicker.setMaxValue(23);
        minutePicker.setMinValue(0);
        minutePicker.setMaxValue(59);
        secondPicker.setMinValue(0);
        secondPicker.setMaxValue(59);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isTimerRunning) {
                    int hours = hourPicker.getValue();
                    int minutes = minutePicker.getValue();
                    int seconds = secondPicker.getValue();
                    long totalMilliseconds = (hours * 60 * 60 * 1000) + (minutes * 60 * 1000) + (seconds * 1000);

                    countDownTimer = new CountDownTimer(totalMilliseconds, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            updateCountdownText(millisUntilFinished);
                        }

                        @Override
                        public void onFinish() {
                            countdownTextView.setText("Таймер завершен!");
                            resetTimer();
                        }
                    };

                    countDownTimer.start();
                    isTimerRunning = true;
                    startButton.setEnabled(false);
                    stopButton.setEnabled(true);
                    hourPicker.setEnabled(false);
                    minutePicker.setEnabled(false);
                    secondPicker.setEnabled(false);
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isTimerRunning) {
                    countDownTimer.cancel();
                    countdownTextView.setText("Таймер остановлен!");
                    resetTimer();
                }
            }
        });

        return view;
    }

    private void updateCountdownText(long millisUntilFinished) {
        int hours = (int) (millisUntilFinished / (1000 * 60 * 60)) % 24;
        int minutes = (int) (millisUntilFinished / (1000 * 60)) % 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        countdownTextView.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }

    private void resetTimer() {
        isTimerRunning = false;
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        hourPicker.setEnabled(true);
        minutePicker.setEnabled(true);
        secondPicker.setEnabled(true);
        hourPicker.setValue(0);
        minutePicker.setValue(0);
        secondPicker.setValue(0);
    }
}

