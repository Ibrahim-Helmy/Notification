package com.example.notificationapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.app.RemoteInput;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private static final String CHANNEL_ID = "personal channel";
    public static final int NOTIFICATION_ID = 2;
    public static final String TEXT_REPLAY = "text_replay";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void displayNotification(View view) {
        createNotification();
    }

    private void createNotification() {

        //intent to handle click on notification
        Intent landingintent = new Intent(this, LandingActivity.class);
        landingintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendinglandigIntent = PendingIntent.getActivity(this, 1, landingintent, PendingIntent.FLAG_ONE_SHOT);
//
//        //pending intent for action button yes
//        Intent yesintent = new Intent(this, YesActivity.class);
//        yesintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingYesIntent = PendingIntent.getActivity(this, 1, yesintent, PendingIntent.FLAG_ONE_SHOT);
//        //pending intent for action button  no
//        Intent nointent = new Intent(this, NoActivity.class);
//        nointent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pendingNoIntent = PendingIntent.getActivity(this, 1, nointent, PendingIntent.FLAG_ONE_SHOT);


        //create channel for version >o
        createNotificationChannel();

        //create notification
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.stat_sys_download)
                .setContentTitle("Image download")
                .setContentText("download in progress...")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true)
                .setContentIntent(pendinglandigIntent)
//                .addAction(R.drawable.ic_save, "save", pendingYesIntent)
//                .addAction(R.drawable.ic_save, "cancel", pendingNoIntent)

                ;

//        // add replay from notification
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//            RemoteInput remoteInput = new RemoteInput.Builder(TEXT_REPLAY).setLabel("Replay").build();
//
//            Intent replayIntent = new Intent(this, RemoteReciver.class);
//            replayIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            PendingIntent replayPendingIntent = PendingIntent.getActivity(this, 1, replayIntent, PendingIntent.FLAG_ONE_SHOT);
//
//            NotificationCompat.Action action = new NotificationCompat.Action.Builder(R.drawable.ic_sms_notification, "Replay", replayPendingIntent)
//                    .addRemoteInput(remoteInput).build();
//
//                builder.addAction(action);
//        }

        // add progress par
        final int max_progress = 100;
        final int current_progress = 0;
        builder.setProgress(max_progress, current_progress, false);

        final NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());

        Thread thread = new Thread() {

            @Override
            public void run() {
                int count = 0;
                try {
                    while (count <= 100) {
                        count = count + 10;
                        sleep(1000);
                        builder.setProgress(max_progress, count, false);
                        notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());
                    }
                    builder.setContentText("download complet success");
                    builder.setProgress(0, 0, false);
                    notificationManagerCompat.notify(NOTIFICATION_ID, builder.build());


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
        };
        thread.start();

    }

    private void createNotificationChannel() {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "ibrahim", NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

}














