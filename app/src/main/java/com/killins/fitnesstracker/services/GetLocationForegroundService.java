package com.killins.fitnesstracker.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.ui.workout.RunTracker;

import java.util.ArrayList;
import java.util.List;

import static com.killins.fitnesstracker.App.CHANNEL_ID;

public class GetLocationForegroundService extends Service {

    private Notification notification;
    private LocalBroadcastManager localBroadcastManager;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LocationRequest locationRequest;
    private List<Location> locations = new ArrayList<>();
    private final String TAG = "getLocationService";

    private final int REFRESH_LOCATION_PERIOD = 1000 * 15; // when screen off, location services can take up to 2x longer between periods

    @Override
    public void onCreate() {
        super.onCreate();

        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        locationRequest = new LocationRequest()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(REFRESH_LOCATION_PERIOD)
                .setFastestInterval(REFRESH_LOCATION_PERIOD);

        try {
            fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } catch (SecurityException e){
            Log.e(TAG, "onCreate: Lost location permissions", e);
        }

    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            Location l = locationResult.getLastLocation();
            if(l != null) {
                Log.d(TAG, "onLocationResult: Lat: " + l.getLatitude() + ", Lon: " + l.getLongitude());
                locations.add(l);
            }
            sendBroadcast();
        }
    };
    /**
     * Runs when the onStart is called on the service, which can happen multiple times in the lifecycle
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        long timerStartTime = intent.getLongExtra("com.killins.fitnessTracker.TIMERSTART", 0);
        Intent notificationIntent = new Intent(this, RunTracker.class);
        notificationIntent.putExtra("com.killins.fitnessTracker.TIMERSTART", timerStartTime);
        notificationIntent.putExtra("com.killins.fitnessTracker.RUNNING", true);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Run Tracker")
                .setContentText("Keep up the Awesome work!")
                .setSmallIcon(R.drawable.ic_baseline_directions_run_24)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fusedLocationProviderClient.removeLocationUpdates(locationCallback);
        sendBroadcast();
    }

    private void sendBroadcast () {
        Intent intent = new Intent("com.runTracker.RUN_DONE");
        intent.putExtra("com.runTracker.RUN_DATA", getLocationsData());
        localBroadcastManager.sendBroadcast(intent);
    }

    private double[] getLocationsData(){
        if(locations.size() < 1) return new double[0];

        double[] data = new double[locations.size()*2 + 1];
        int i = 0;
        float distance = 0;

        for(int d=0; d<locations.size()-1; d++){
            distance += locations.get(d).distanceTo(locations.get(d+1));
        }

        for (Location l : locations){
            data[i] = l.getLatitude();
            i++;
            data[i] = l.getLongitude();
            i++;
        }
        data[i]=distance;
        return data;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
