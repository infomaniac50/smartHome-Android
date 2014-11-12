package com.example.smarthome;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    private Toast myToast;

    String Smove;
    String link = "nikus.noip.me";
    String textFromPreferences;
    int Imove;
    String service;
    boolean notifi;


    private static final String DEBUG_TAG = "HttpExample";
    private EditText urlText;
    private TextView textView;


    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_LINK = "link";
    private static final String PREFERENCES_SERVICE = "service";
    private SharedPreferences preferences;


    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);
        textFromPreferences = preferences.getString(PREFERENCES_LINK, "");


        updatingTimer = new Timer();

        myToast = Toast.makeText(getApplicationContext(),
                "Alarm is ON",
                Toast.LENGTH_SHORT);
        myToast.show();

        service = "true";
        saveData(PREFERENCES_SERVICE, service);
    }

    @Override
    public void onDestroy() {


        service = "false";
        saveData(PREFERENCES_SERVICE, service);

        updatingTimer.cancel();
        myToast.setText("Alarm is OFF");
        myToast.show();

        super.onDestroy();
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        updatingTimer.scheduleAtFixedRate(notify, 5 * 1000, 60 * 1000);


    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    private Timer updatingTimer;
    private TimerTask notify = new TimerTask() {

        @Override
        public void run() {

            notifi = false;

            URLConnection urlConnection = null;
            URL url = null;


            try {
                url = new URL("http", textFromPreferences, 80, "/alarm");
                urlConnection = url.openConnection();

                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader reader = new BufferedReader(inputStreamReader);


                StringBuffer stringBuffer = new StringBuffer();

                while ((Smove = reader.readLine()) != null) {
                    stringBuffer.append(Smove);
                    Imove = Integer.parseInt(Smove);
                }

                while (Imove == 1 && notifi == false) {

                    notification();
                    notifi = true;
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    };


    private void notification() {

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);


        mBuilder.setContentTitle("Alarm!");
        mBuilder.setContentText("Motion is detected in your room");
        mBuilder.setSmallIcon(R.drawable.notifi);


        Intent mIntent = new Intent(this, MainActivity.class);
        PendingIntent mPendingIntent = PendingIntent.getActivity(this, 0, mIntent, 0);

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        mBuilder.setSound(alarmSound);
        mBuilder.setContentIntent(mPendingIntent);

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());

    }

    private void saveData(String key, String values) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(key, values);
        preferencesEditor.commit();
    }


}
    

    
    
    


