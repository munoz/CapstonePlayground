package com.example.capstoneplayground;


import com.example.capstoneplayground.DB.AppDatabase;
import com.example.capstoneplayground.DB.speedResultsdao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.mbms.MbmsErrors;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.android.material.button.MaterialButton;

import android.app.AlertDialog;
import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.content.DialogInterface;
import android.provider.Settings;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class AnimatedCircleActivity extends AppCompatActivity {

    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String latitude, longitude;
    speedResultsdao mSpeedResults;

    ProgressBar prog_ping;
    ProgressBar prog_jitter;
    ProgressBar prog_down;
    ProgressBar prog_up;
    TextView curTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animated_circle);

        prog_ping = findViewById(R.id.progress_bar_ping);
        prog_jitter = findViewById(R.id.progress_bar_jitter);
        prog_down = findViewById(R.id.progress_bar_down);
        prog_up = findViewById(R.id.progress_bar_up);
        curTest = findViewById(R.id.text_view_progress);

        Button up = findViewById(R.id.button_incr);
        up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProgressBar();
            }
        });
        Button ping = findViewById(R.id.button_ping);
        ping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocationManager nManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    OnGPS();
                } else {
                    getLocation();
                }
            }
        });


    }



    public static Intent intentFactory(Context context) {
        Intent intent = new Intent(context, AnimatedCircleActivity.class);

        return intent;
    }

    private void updateProgressBar() {
        speedResults thistest = new speedResults();
        thistest.setNetwork(backend.getNetworkClass(getApplicationContext()));

        Double lat = 36.6517;
        Double longi = 121.7978;
        thistest.setLatitude(lat.toString());
        thistest.setLongitude(longi.toString());

        prog_up.setProgress(0);
        prog_down.setProgress(0);
        prog_ping.setProgress(0);
        int size = 100;
        int packetSize = 1;
        Queue<Integer> timetaken = new LinkedList<>();
        int amount = size / packetSize;
        Random nRand = new Random();

        int maxd = nRand.nextInt(100)+5;
        int startSpeed= nRand.nextInt(maxd/2);
        Handler handler = new Handler();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
///////////////////////////////////////DOWNLOAD//////////////////////////////////
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        curTest.setText("Download");
                    }
                });
                double DownSpeed;
                String spd = "" + startSpeed;
                Log.d("start d speed", spd);
                timetaken.add(startSpeed);
                prog_up.setProgress(prog_up.getProgress() + 1);
                int prevsped = startSpeed;
                for (int i = 0; i < amount - 1; i++) {
                    Integer currsped = backend.randomInt(prevsped,maxd);
                    timetaken.add(currsped);
                    prevsped = currsped;
                    //update progress here after certain amount

                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                curTest.setText(currsped.toString());
                                prog_up.setProgress(prog_up.getProgress() + 1);
                            }
                        });
                        if(timetaken.size() >= 5){
                            timetaken.remove();
                            Integer hold = timetaken.size();
//                            Log.d("timetaken size",hold.toString());
//                            System.out.println("Whats in timetaken?");
//                            for(int j = 0; j < hold; j++){
//                                System.out.print(timetaken.toArray()[j]+" ");
//                            }
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                ArrayList<Integer> overall= new ArrayList<>(timetaken);
                DownSpeed = backend.Speed(overall);
                Log.d("Down Speed", "" + DownSpeed);
                Log.d("Last speed ",""+prevsped);
                thistest.setDown(DownSpeed);
                thistest.setStream(backend.Stream(DownSpeed));
///////////////////////////////////////////UPLOAD////////////////////////////////////////

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        curTest.setText("Upload");
                    }
                });
                timetaken.clear();

                int maxup = nRand.nextInt(maxd-2)+2;
                Log.d("Max up", "" + maxup);
                int startuSpeed = 0;
                String uspd = "" + startuSpeed;
                timetaken.add(startuSpeed);
                prog_down.setProgress(prog_down.getProgress() + 1);
                Log.d("Start u speed", uspd);
                double upSpeed;
                int st = (int) System.currentTimeMillis();
                int prevspeed = startSpeed;
                for (int i = 0; i < amount - 1; i++) {
                    Integer curspeed = backend.randomInt(prevspeed,maxup);
                    timetaken.add(curspeed);
                    prevspeed = curspeed;

                    int tm = (int) System.currentTimeMillis();
                    int actme = tm - st;
                    String tme = "" + actme;
                    //Log.d("Up time",tme);
                    try {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                curTest.setText(curspeed.toString());
                                prog_down.setProgress(prog_down.getProgress() + 1);
                            }
                        });
                        if(timetaken.size() >= 5){
                            Integer remove = timetaken.remove();
                            Integer hold = timetaken.size();
                            //Log.d("timetaken size",hold.toString());
                        }
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                overall = new ArrayList<>(timetaken);
                upSpeed = backend.Speed(overall);
                String prnt = "" + upSpeed;
                Log.d("Up speed", prnt);
                thistest.setUp(upSpeed);
                thistest.setConf(backend.Conference(DownSpeed,upSpeed));
/////////////////////////////////////////////PING//////////////////////////////////

                Log.d("Test","In SystemCommand");
                Runtime runtime = Runtime.getRuntime();
                try {
                    Log.d("Test","Trying Ping");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            curTest.setText("Ping");
                        }
                    });
                    Process p = runtime.exec("ping 127.0.0.1");

                    int pid=-1;
                    try{
                        Field f = p.getClass().getDeclaredField("pid");
                        f.setAccessible(true);
                        pid = f.getInt(p);
                        f.setAccessible(false);
                    }catch (Throwable e){
                        pid = -1;
                    }
                    final InputStream is = p.getInputStream();
                    // reading output stream of the command
                    Integer conv = pid;
                    Log.d("Process id",conv.toString());
                    final int pID = pid;
                    final funString result = new funString();
                    final funString packets = new funString();
                    Thread t = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            result.SetValue("");
                            int st = (int)System.currentTimeMillis();
                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DATE,-1);
                            long yesterday = calendar.getTimeInMillis();
                            int uniqueId = (int) (yesterday +st );

                            Log.d("Test","start time: "+uniqueId);
                            Log.d("Test","Reading Ping");
                            BufferedReader inputStream = new BufferedReader(
                                    new InputStreamReader(p.getInputStream()));
                            String s = "";
                            int time;
                            long yester = yesterday;
                            try{
                                Log.d("first line",""+inputStream.readLine());
                                while ((s = inputStream.readLine()) != null) {
                                    Log.d("pinging",s);
                                    //get time put into arraylist
                                    //progresss ping progressbar every second plus 8
                                    if((time = (int)(yester + System.currentTimeMillis())-uniqueId) >= 1000){
                                        String[] arrofs = s.split(" ");
                                        if(arrofs[0].equals("64")) {
                                            Log.d("ping output",arrofs[6].substring(5));

                                            yester += (int) (yester + System.currentTimeMillis()) - uniqueId;
                                            handler.post(new Runnable() {
                                                @Override
                                                public void run() {
                                                    prog_ping.setProgress(prog_ping.getProgress() + 8);
                                                }
                                            });
                                        }
                                        if(arrofs.length > 3) {
                                            if (arrofs[1].equals("packets")) {
                                                packets.SetValue(s);
                                            }
                                        }
                                    }
                                    if((time = (int)(yesterday + System.currentTimeMillis())) - uniqueId >=  10000){
                                        //Log.d("Ping","Time: "+(time-st));
                                        if(pID != -1) {
                                            result.SetValue(s);
                                            Log.d("percent ping",""+prog_ping.getProgress());
                                            runtime.exec("kill -SIGINT " + pID);
                                        }else{
                                            p.destroy();
                                        }
                                    }
                                }
                                if(s == null){
                                    Log.d("Test","Input stream Null");
                                }
                                Log.d("test",result.GetValue());
                            }catch (IOException e){
                                e.printStackTrace();
                            }
                            if(p.isAlive()){
                                p.destroy();
                            }

                        }
                    });
                    t.start();
                    p.waitFor();
                    t.join();
                    Log.d("Result?",result.GetValue());
                    Log.d("Test","Outside Loop");
                    Log.d("final perc",""+prog_ping.getProgress());
                    p.destroy();
                    String[] parsePing = result.GetValue().split("/");
                    Integer pbase = nRand.nextInt(140)+40;
                    Double ping = Double.parseDouble(parsePing[4]);
                    Double jitter = Double.parseDouble(parsePing[6].substring(0,parsePing[6].length()-3));
                    //ping *=10;
                    Integer jbase = nRand.nextInt(10);
                    //jitter *= 10;
                    new DecimalFormat("#.##").format(ping);
                    new DecimalFormat("#.##").format(jitter);
                    Log.d("jitter before",""+jitter);
                    Log.d("latency before",""+ping);
                    //ping += pbase;
                    //jitter+= jbase;
                    Log.d("jitter",""+jitter);
                    Log.d("latency",""+ping);
                    thistest.setJitter(jitter.toString());
                    thistest.setPing(ping.toString());
                    String pktls = packets.GetValue();
                    String[] fndpkt = pktls.split(" ");
                    String loss = fndpkt[5].substring(0,fndpkt[5].length()-1);
                    Integer los = Integer.parseInt(loss);
                    thistest.setMOS(backend.MOS(los,jitter,ping));
                    thistest.setVoip(backend.VOIP(thistest.getMOS()));
                    System.out.println("This test:\n"+thistest);
                    mSpeedResults.insert(thistest);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            prog_ping.setProgress(100);
                        }
                    });
                    System.out.println(thistest);
                } catch (Exception e) {
                    //Log.d("Test","Error");
                    e.printStackTrace();
                }
            }
        });
        //Download

        double DownSpeed;
        t.start();

    }
    private void OnGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new  DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(
                AnimatedCircleActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                AnimatedCircleActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION);
        } else {
            Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (locationGPS != null) {
                double lat = locationGPS.getLatitude();
                double longi = locationGPS.getLongitude();
                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);
            } else {
                Log.d("GPS", "NULL");
            }
        }
    }
}