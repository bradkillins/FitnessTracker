package com.killins.fitnesstracker.ui.workout;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.killins.fitnesstracker.R;
import com.killins.fitnesstracker.services.GetLocationForegroundService;

public class RunTracker extends AppCompatActivity
    implements OnMapReadyCallback {

    private static final String TAG = "RunTrackerActivity";
    private GoogleMap map;
    private CameraPosition cameraPosition;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient fusedLocationProviderClient;

    // A default location (Bow Valley College) and default zoom to use when location permission is
    // not granted.
    private final LatLng defaultLocation = new LatLng(51.04689, -114.05603);
    private static final int DEFAULT_ZOOM = 17;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean locationPermissionGranted;

    // The last-known location retrieved by the Fused Location Provider.
    private Location lastKnownLocation;

    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_RUNTIMER = "run_timer";
    private static final String KEY_RUNNING = "is_running?";

    private LocalBroadcastManager localBroadcastManager;
    private Long timerStartTime;
    private boolean running = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_tracker);
        running = getIntent().getBooleanExtra("com.killins.fitnessTracker.RUNNING", false);
        long intentTimerStart = getIntent().getLongExtra("com.killins.fitnessTracker.TIMERSTART",0);
        if(intentTimerStart > 0) timerStartTime = intentTimerStart;
        //restore previously saved state
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
            timerStartTime = savedInstanceState.getLong(KEY_RUNTIMER);
            running = savedInstanceState.getBoolean(KEY_RUNNING);
        }

        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        // Construct a FusedLocationProviderClient.
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        Button startButton = findViewById(R.id.startButton);
        if(running) startButton.setEnabled(false);
        //Add onclick listener to start button
        startButton.setOnClickListener(v -> {
            v.setEnabled(false);
            running = true;
            startTimer();
            Intent serviceIntent = new Intent(this, GetLocationForegroundService.class);
            serviceIntent.putExtra("com.killins.fitnessTracker.TIMERSTART", timerStartTime);
            startService(serviceIntent);
        });

        //Add onclick listener to stop button
        findViewById(R.id.stopButton).setOnClickListener(v -> {
            timerHandler.removeCallbacks(tickTimer);
            Intent serviceIntent = new Intent(this, GetLocationForegroundService.class);
            stopService(serviceIntent);
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (map != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, map.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, lastKnownLocation);
            outState.putLong(KEY_RUNTIMER, timerStartTime);
            outState.putBoolean(KEY_RUNNING, running);
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;

        // Prompt the user for permission.
        getLocationPermission();
        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();
    }

    /**
     *  Request location permission, so that we can get the location of the
     *  device. The result of the permission request is handled by a callback,
     *  onRequestPermissionsResult.
     */
    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            locationPermissionGranted = grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            updateLocationUI();
        }
    }

    /**
     * Updates the map's current location marker and enables/disables
     * the setLocation button based on if user granted location permission.
     */
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (locationPermissionGranted) {
                Log.e("location", "location added");
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        try {
            if (locationPermissionGranted) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        lastKnownLocation = task.getResult();
                        if (lastKnownLocation != null) {
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(lastKnownLocation.getLatitude(),
                                            lastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        }
                    } else {
                        Log.d(TAG, "Current location is null. Using defaults.");
                        Log.e(TAG, "Exception: %s", task.getException());
                        map.moveCamera(CameraUpdateFactory
                                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    private void updateAfterBroadcastReceived(double[] locations) {
        map.clear();

        PolylineOptions pathOptions = new PolylineOptions();
        for (int i = 0; i < locations.length - 1; i += 2) {

            LatLng latLng = new LatLng(locations[i], locations[i + 1]);
            if (i == 0)
                map.addMarker(new MarkerOptions().position(latLng).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            if (i == locations.length - 3 && i != 0)
                map.addMarker(new MarkerOptions().position(latLng));

            pathOptions.add(latLng);
        }
        map.addPolyline(pathOptions);

        int distance = 0;

        if(locations.length > 0)
               distance = (int) locations[locations.length-1];

        TextView distanceView = findViewById(R.id.distance_textview);
        distanceView.setText(getString(R.string.distance_placeholder,distance));
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter("com.runTracker.RUN_DONE");
        localBroadcastManager.registerReceiver(receiver, filter);
        //resume timer if it was already running
        if(timerStartTime != null) timerHandler.post(tickTimer);
    }

    @Override
    protected void onStop() {
        super.onStop();
        localBroadcastManager.unregisterReceiver(receiver);
        timerHandler.removeCallbacks(tickTimer);
    }

    private void startTimer(){
        timerStartTime = System.currentTimeMillis();
        timerHandler.post(tickTimer);
    }

    Handler timerHandler = new Handler();

    private final Runnable tickTimer = new Runnable() {
        @Override
        public void run() {
            long currentTime = System.currentTimeMillis();
            long elapsedTime = (currentTime - timerStartTime) / 1000;
            int min = (int)elapsedTime/60;
            int sec = (int)elapsedTime % 60;

            String formattedElapsed = String.format("%1$02d : %2$02d", min, sec);
            TextView timeTextView = findViewById(R.id.time_textview);
            timeTextView.setText(getString(R.string.time_placeholder, formattedElapsed));
            timerHandler.postDelayed(this, 1000);
        }
    };

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            double[] data = intent.getDoubleArrayExtra("com.runTracker.RUN_DATA");
            updateAfterBroadcastReceived(data);
            String msg = "received";
            if (data.length > 0)
                msg = "length: " + data.length + ", 1st Lat: " + data[0] + ", 1st Lon: " + data[1] + ", Dist: " + data[data.length - 1];

            Log.d(TAG, "onReceive: " + msg);
        }
    };
}
