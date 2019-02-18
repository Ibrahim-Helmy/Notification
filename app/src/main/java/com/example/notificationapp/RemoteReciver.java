package com.example.notificationapp;

import android.app.NotificationManager;
import android.net.Uri;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Map;

public class RemoteReciver extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_reciver);

        textView = findViewById(R.id.text_remote);

        Bundle resultsFromIntent = RemoteInput.getResultsFromIntent(getIntent());
        if (resultsFromIntent != null) {

            String txtReplay = resultsFromIntent.getCharSequence(MainActivity.TEXT_REPLAY).toString();

            textView.setText(txtReplay);
        }

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                 notificationManager.cancel(MainActivity.NOTIFICATION_ID);
    }
}
