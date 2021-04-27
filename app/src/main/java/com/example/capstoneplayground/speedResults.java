package com.example.capstoneplayground;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.capstoneplayground.DB.AppDatabase;
@Entity(tableName = AppDatabase.RESULTS_TABLE)
public class speedResults {
    @PrimaryKey(autoGenerate = true)
    private int id;

    private Date date;
    private Double Down;
    private String Network;
    private Double Up;
    private String ping;
    private String jitter;
    private String latitude;
    private String longitude;
    private Double MOS;
    private String stream;
    private String conf;
    private String voip;

    public speedResults() {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd,yyyy HH:mm");
        this.date = new Date(now);
    }

    public speedResults(Double down, String network, Double up, String ping, String jitter, String latitude, String longitude, Double MOS, String stream, String conf, String voip) {
        long now = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("MM dd,yyyy HH:mm");
        this.date = new Date(now);
        this.Down = down;
        this.Network = network;
        this.Up = up;
        this.ping = ping;
        this.jitter = jitter;
        this.latitude = latitude;
        this.longitude = longitude;
        this.MOS = MOS;
        this.stream = stream;
        this.conf = conf;
        this.voip = voip;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public Double getDown() {
        return Down;
    }

    public void setDown(Double down) {
        Down = down;
    }

    public Double getUp() {
        return Up;
    }

    public void setUp(Double up) {
        Up = up;
    }

    public String getPing() {
        return ping;
    }

    public void setPing(String ping) {
        this.ping = ping;
    }

    public String getJitter() {
        return jitter;
    }

    public void setJitter(String jitter) {
        this.jitter = jitter;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Double getMOS() {
        return MOS;
    }

    public void setMOS(Double MOS) {
        this.MOS = MOS;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    public String getConf() {
        return conf;
    }

    public void setConf(String conf) {
        this.conf = conf;
    }

    public String getVoip() {
        return voip;
    }

    public void setVoip(String voip) {
        this.voip = voip;
    }

    public String getNetwork() {
        return Network;
    }

    public void setNetwork(String network) {
        Network = network;
    }

    @Override
    public String toString() {
        return "speedResults{" +
                "id=" + id +
                ", date=" + date +
                ", Down=" + Down +
                ", Network='" + Network + '\'' +
                ", Up=" + Up +
                ", ping='" + ping + '\'' +
                ", jitter='" + jitter + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", MOS=" + MOS +
                ", stream='" + stream + '\'' +
                ", conf='" + conf + '\'' +
                ", voip='" + voip + '\'' +
                '}';
    }
}
