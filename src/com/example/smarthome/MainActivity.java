package com.example.smarthome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends Activity {
    TextView tempIn;
    TextView tempOut;
    TextView window;
    TextView move;
    TextView rain;
    TextView time;
    Button button1;


    Context context;

    double tempin;
    double tempout;

    int Imove;

    boolean net;

    String Stempin;
    String Stempout;
    String Swindow;
    String Smove;
    String Srain;
    String link;

    String textFromPreferences;

    private static final String PREFERENCES_NAME = "myPreferences";
    private static final String PREFERENCES_TEMP_IN = "tempin";
    private static final String PREFERENCES_TEMP_OUT = "tempout";
    private static final String PREFERENCES_WINDOW = "window";
    private static final String PREFERENCES_RAIN = "rain";
    private static final String PREFERENCES_MOVE = "move";
    private static final String PREFERENCES_TIME = "time";
    private static final String PREFERENCES_LINK = "link";
    private SharedPreferences preferences;


    public class TempIn extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http", link, 80, "/tempin");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                tempin = Double.parseDouble(sb.toString());
                Stempin = Double.toString(tempin / 100.00);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    public class TempOut extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http", link, 80, "/tempout");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                tempout = Double.parseDouble(sb.toString());
                Stempout = Double.toString(tempout / 100.00);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
    }


    public class Window extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http", link, 80, "/window");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                Swindow = (sb.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }


    }


    public class Moves extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http", link, 80, "/alarm");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                Smove = (sb.toString());
                Imove = Integer.parseInt(Smove);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }


    }


    public class Rain extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                URL url = new URL("http", link, 80, "/rain");
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                conn.connect();
                Thread.sleep(500);
                StringBuilder sb = new StringBuilder();
                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader in = new BufferedReader(isr);
                String line;

                while ((line = in.readLine()) != null) {
                    sb.append(line);

                }
                in.close();
                Srain = (sb.toString());


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }


    }


    private void saveData(String key, String values) {
        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putString(key, values);
        preferencesEditor.commit();
    }


    private void restoreData(String key) {
        textFromPreferences = preferences.getString(key, "");
    }


    private void getDataFromHome() {

        TempIn tempin = new TempIn();
        tempin.execute(null, null, null);

        TempOut tempout = new TempOut();
        tempout.execute(null, null, null);

        Window Window = new Window();
        Window.execute(null, null, null);

        Rain Rain = new Rain();
        Rain.execute(null, null, null);

        Moves moves = new Moves();
        moves.execute(null, null, null);


    }


    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            net = true;
            return true;

        } else {
            net = false;
            return false;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);


        preferences = getSharedPreferences(PREFERENCES_NAME, Activity.MODE_PRIVATE);


        tempIn = (TextView) findViewById(R.id.textView1);
        tempOut = (TextView) findViewById(R.id.textView4);
        window = (TextView) findViewById(R.id.textView7);
        move = (TextView) findViewById(R.id.textView8);
        rain = (TextView) findViewById(R.id.textView10);
        time = (TextView) findViewById(R.id.textView11);
        button1 = (Button) findViewById(R.id.button1);


        restoreData(PREFERENCES_TIME);
        time.setText(textFromPreferences);
        restoreData(PREFERENCES_TEMP_IN);
        tempIn.setText(textFromPreferences + " \u00B0C");
        restoreData(PREFERENCES_TEMP_OUT);
        tempOut.setText(textFromPreferences + " \u00B0C");
        //rain**************************************************************
        restoreData(PREFERENCES_RAIN);
        if (textFromPreferences.equals("YES")) {
            rain.setText(R.string.yes);
        }

        if (textFromPreferences.equals("NO")) {
            rain.setText(R.string.no);
        }

        //window************************************************************
        restoreData(PREFERENCES_WINDOW);
        if (textFromPreferences.equals("open")) {
            window.setText(R.string.open);
        }

        if (textFromPreferences.equals("close")) {
            window.setText(R.string.close);
        }
        //move**************************************************************
        restoreData(PREFERENCES_MOVE);
        if (textFromPreferences.equals("1")) {
            move.setText(R.string.yes);
        }

        if (textFromPreferences.equals("0")) {
            move.setText(R.string.no);
        }


        //link***************************************************************
        restoreData(PREFERENCES_LINK);
        link = textFromPreferences;
        getDataFromHome();


        OnClickListener kliknij = new OnClickListener() {
            @Override
            public void onClick(View arg0) {


                isOnline();
                if (net) {
                    restoreData(PREFERENCES_LINK);
                    link = textFromPreferences;

                    getDataFromHome();
                    if (Srain != null) {
                        SimpleDateFormat simpleDateHere = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                        time.setText(simpleDateHere.format(new Date()));
                        saveData(PREFERENCES_TIME, simpleDateHere.format(new Date()));
                    }

                    if (Stempin != null) {
                        tempIn.setText(Stempin + " \u00B0C");
                        saveData(PREFERENCES_TEMP_IN, Stempin);
                    }
                    if (Stempout != null) {
                        tempOut.setText(Stempout + " \u00B0C");
                        saveData(PREFERENCES_TEMP_OUT, Stempout);
                    }
                    if (Swindow != null) {

                        if (Swindow.equals("OPEN")) {
                            window.setText(R.string.open);
                        }

                        if (Swindow.equals("CLOSE")) {
                            window.setText(R.string.close);
                        }

                        saveData(PREFERENCES_WINDOW, Swindow);
                    }
                    if (Smove != null) {

                        if (Imove == 1) {
                            move.setText(R.string.yes);
                        }

                        if (Imove == 0) {
                            move.setText(R.string.no);
                        }

                        saveData(PREFERENCES_MOVE, Smove);
                    }
                    if (Srain != null) {
                        if (Srain.equals("YES")) {
                            rain.setText(R.string.yes);
                        }

                        if (Srain.equals("NO")) {
                            rain.setText(R.string.no);
                        }
                        saveData(PREFERENCES_RAIN, Srain);

                    }


                } else {
                    Context context = getApplicationContext();
                    CharSequence text = "Turn on wifi or mobile data";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }


            }
        };
        button1.setOnClickListener(kliknij);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_settings:
                context = getApplicationContext();
                Intent intent = new Intent(context, Settings.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

	
   
  
