package com.example.capstoneplayground;

import android.content.Context;
import android.icu.text.AlphabeticIndex;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.SocketKeepalive;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Switch;

import androidx.annotation.LongDef;

import java.lang.reflect.Field;
import java.util.Random;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.net.*;

public class backend {
    public static void runSystemCommand(String command) {
        //Log.d("Test","In SystemCommand");
        Runtime runtime = Runtime.getRuntime();
        try {
            //Log.d("Test","Trying Ping");
            Process p = runtime.exec(command);
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
            final int pID = pid;
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    String result = "";
                    int st = (int)System.currentTimeMillis();
                    //Log.d("Test","start time: "+st);
                    //Log.d("Test","Reading Ping");
                    BufferedReader inputStream = new BufferedReader(
                            new InputStreamReader(is));
                    String s = "";
                    int time;
                    try{
                        while ((s = inputStream.readLine()) != null) {
                            Log.d("pinging",s);
                            //get time put into arraylist
                            if((time = (int)System.currentTimeMillis()) - st >=  10000){
                                //Log.d("Ping","Time: "+(time-st));
                                if(pID != -1) {
                                    result = s;
                                    runtime.exec("kill -SIGINT " + pID);
                                }else{
                                    p.destroy();
                                }
                            }
                        }
                        //Log.d("test",result);
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            });
            t.start();
            p.waitFor();
            t.join();
            //Log.d("Test","Outside Loop");
            p.destroy();
        } catch (Exception e) {
            //Log.d("Test","Error");
            e.printStackTrace();
        }
    }
    public static double MOS(double packet, double jit, double lat){
        double A,B,C,mos;
        A = (lat + jit) * 2;
        if(A < 160){
            B = 93.2 - (A/40);
        }else{
            B = 93.2 - (A - 120) / 10;
        }
        C = B - (packet*2.5);
        mos = 1 + (0.035)*C + (0.000007)*C*(C-60)*(100-C);
        return mos;
    }
    public static int randomInt(int i,int max){
        Random rand = new Random();
        if(i < max) {
            int upper =  5;
            int newRand = rand.nextInt(upper);
            int fin = i+newRand;
            return fin;
        }else if (i != max){
            int fin = i;
            int drop = rand.nextInt(2);
            fin -= drop;
            return fin;
        }else{
            return max;
        }
    }
    public static double Speed(ArrayList<Integer> s) {
        int total =0;
        //Log.d("Speeds","");
        for (int i =0; i< s.size(); i++){
            //Log.d("",s.get(i)+"");
            total += s.get(i);
        }
        Log.d("aftermath",total+" "+s.size());
        double speed = total / s.size()-1;
        return  speed;

    }
    public static void speedTest(){
        //what should the base units be
        int size = 100;
        int packetSize = 1;
        ArrayList<Integer> timetaken = new ArrayList<Integer>();
        int amount = size / packetSize;
        double longitude = 0;
        double latitude = 0;

        //Down Speed
        //change text to download
        Random nRand= new Random();
        double DownSpeed;
        int maxd = 100;
        int startSpeed= nRand.nextInt(100);
        String spd = ""+startSpeed;
        Log.d("start d speed",spd);
        timetaken.add(startSpeed);
        for(int i =0;i< amount-1;i++){
            timetaken.add(randomInt(startSpeed,maxd));
            //update progress here after certain amount
            DownSpeed = Speed(timetaken);
            Log.d("Down Speed",""+DownSpeed);
            try{
                //update image and go up 1%
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }


        //Up Speed
        //Change text to upload
        timetaken.clear();
        int maxup = (int)(startSpeed/5);
        Log.d("Max up",""+maxup);
        startSpeed = nRand.nextInt((int)(startSpeed/5))+1;
        String uspd = ""+startSpeed;
        Log.d("Start u speed",uspd);
        double upSpeed;
        int st = (int)System.currentTimeMillis();
        for( int i =0;i< amount-1 ;i++){
            timetaken.add(randomInt(startSpeed,maxup));
            upSpeed = Speed(timetaken);
            String prnt =""+ upSpeed;
            Log.d("Up speed",prnt);
            int tm = (int)System.currentTimeMillis();
            int actme = tm - st;
            String tme = ""+actme;
            //Log.d("Up time",tme);
            try{
                Thread.sleep(200);
            }catch (InterruptedException e){
                e.printStackTrace();
            }


        }

        double latency = 0; //temp
        double jitter = 0; //temp
        //use Ping for latency and jitter
        String ip = "127.0.0.1";
        Log.d("ip ",ip);
        runSystemCommand("ping "+ ip);

        //MOS Value
        //int mosV = MOS(DownSpeed,upSpeed,latency,jitter);


    }
    public static String Stream(Double down){
        if(down <1.1){
            return "N/A";
        }else if(down < 2.5){
            return "SD";
        }else if(down < 20 ){
            return "HD";
        }else{
            return "4K";
        }
    }
    public static String Conference(Double down, Double up){
        if(down <1 || up < 0.8){
            return "N/A";
        }else if(down <1.5 || up < 1.5){
            return "SD";
        }else {
            return "HD";
        }
    }
    public static String VOIP(Double mos){
        if(mos < 3.6){
            return "Poor";
        }else {
            return "Good";
        }
    }
    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected())
            return "-"; // not connected
        if (info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:     // api< 8: replace by 11
                case TelephonyManager.NETWORK_TYPE_GSM:      // api<25: replace by 16
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:   // api< 9: replace by 12
                case TelephonyManager.NETWORK_TYPE_EHRPD:    // api<11: replace by 14
                case TelephonyManager.NETWORK_TYPE_HSPAP:    // api<13: replace by 15
                case TelephonyManager.NETWORK_TYPE_TD_SCDMA: // api<25: replace by 17
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:      // api<11: replace by 13
                case TelephonyManager.NETWORK_TYPE_IWLAN:    // api<25: replace by 18
                case 19: // LTE_CA
                    return "4G";
                case TelephonyManager.NETWORK_TYPE_NR:       // api<29: replace by 20
                    return "5G";
                default:
                    return "?";
            }
        }
        return "?";
    }

}
