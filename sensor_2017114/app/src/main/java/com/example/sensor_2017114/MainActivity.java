package com.example.sensor_2017114;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaRecorder;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sensor_2017114.database.sensorbasehelper;
import com.example.sensor_2017114.database.sensorschema;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private MediaRecorder microphone;

    SensorManager sensorManager;
    Sensor accel;
    TextView x, y, z, tim, loc, sig, acc;
    private int locationRequestCode = 1000;
    private double erlatitude = 0;
    double erlongitude = 0;
    int c = 0;
    WifiManager wifiManager;
    FusedLocationProviderClient fusedLocationClient;
    Button butaccel;
    Button gpsbutton;
    Button wifibutton,start,stop,micbutton;
    SQLiteDatabase sqLiteDatabase;
    int level=0;
    public String filename = "example2.txt";
    String outputfile="";
    boolean recordperm=false;
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1000: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fusedLocationClient.getLastLocation().addOnSuccessListener(this, location -> {
                        if (location != null) {
                            erlatitude = location.getLatitude();
                            erlongitude = location.getLongitude();

                        }
                    });
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case 200:
                recordperm  = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
    }


    private void scanSuccess() {
        List<ScanResult> results = wifiManager.getScanResults();
        String res = "";
//        for(ScanResult s:results){
//            res=res+s.toString()+"\n";
//        }
        res = wifiManager.getConnectionInfo().getSSID().toString();
        Date date= new Date();

        Timestamp ts = new Timestamp(date.getTime());
        ContentValues contentValues=forWifi(""+ts,""+level,""+res);
        sqLiteDatabase.insert(sensorschema.Wifitable.NAME, null, contentValues);

    }

    private void scanFailure() {

        List<ScanResult> results = wifiManager.getScanResults();

    }

    public static ContentValues forAccelerometer(String time, String x, String y, String z) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(sensorschema.Accelerometertable.Cols.timestamp, time);
        contentValues.put(sensorschema.Accelerometertable.Cols.X, x);
        contentValues.put(sensorschema.Accelerometertable.Cols.Y, y);
        contentValues.put(sensorschema.Accelerometertable.Cols.Z, z);

        return contentValues;

    }
    public static ContentValues forgps(String time, String latitude, String longitude) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(sensorschema.GPStable.Cols.timestamp, time);
        contentValues.put(sensorschema.GPStable.Cols.latitude, latitude);
        contentValues.put(sensorschema.GPStable.Cols.longitude, longitude);




        return contentValues;

    }
    public static ContentValues forWifi(String time, String strength,String accesspoiny) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(sensorschema.Wifitable.Cols.timestamp,time);
        contentValues.put(sensorschema.Wifitable.Cols.Strength,strength);
        contentValues.put(sensorschema.Wifitable.Cols.Accesspoint,accesspoiny);


        return contentValues;

    }
    public static ContentValues forMicrophone(String time, String fname) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(sensorschema.Mictable.Cols.timestamp,time);
        contentValues.put(sensorschema.Mictable.Cols.filename,fname);


        return contentValues;

    }

    public void storacceltofile() {

        Cursor cursor = sqLiteDatabase.query(sensorschema.Accelerometertable.NAME, null, null, null, null, null, null);
        Log.d("seethis", "" + cursor.getCount());
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String tim = cursor.getString(cursor.getColumnIndex("timestamp"));
                String x = cursor.getString(cursor.getColumnIndex("X"));
                String y = cursor.getString(cursor.getColumnIndex("Y"));
                String z = cursor.getString(cursor.getColumnIndex("Z"));
                save(tim, x, y, z);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

    }
    public void storgpstofile() {

        Cursor cursor = sqLiteDatabase.query(sensorschema.GPStable.NAME, null, null, null, null, null, null);
        Log.d("seethis", "" + cursor.getCount());
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String tim = cursor.getString(cursor.getColumnIndex("timestamp"));
                String x = cursor.getString(cursor.getColumnIndex("latitude"));
                String y = cursor.getString(cursor.getColumnIndex("longitude"));
                savegps(tim, x, y);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

    }
    public void storwifitofile() {

        Cursor cursor = sqLiteDatabase.query(sensorschema.Wifitable.NAME, null, null, null, null, null, null);
        Log.d("seethis", "" + cursor.getCount());
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String tim = cursor.getString(cursor.getColumnIndex("timestamp"));
                String x = cursor.getString(cursor.getColumnIndex("Strength"));
                String y = cursor.getString(cursor.getColumnIndex("Accesspoint"));
                savewifi(tim, x, y);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

    }
    public void stormictofile() {

        Cursor cursor = sqLiteDatabase.query(sensorschema.Mictable.NAME, null, null, null, null, null, null);
        Log.d("seethis", "" + cursor.getCount());
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String tim = cursor.getString(cursor.getColumnIndex("timestamp"));
                String x = cursor.getString(cursor.getColumnIndex("filename"));
                savemicrophone(tim, x);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

    }

    public void load(int choice) {
        FileInputStream fis = null;
        Log.d("file","I am here");
        String file="";
        if(choice==0){
            file=filename;
        }
        if(choice==1){
            file=filename;
        }
        if(choice==2){
            file=filename;
        }

        try {
            fis = openFileInput(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }
            if(choice==0){
                x.setText(sb.toString());}
            if(choice==1){
                x.setText(sb.toString());
            }
            if(choice==2){
                x.setText(sb.toString());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void save(String tim, String x, String y, String z) {
        String text = "time=" + tim + "X= " + x + "Y= " + y + "Z= " + z + "\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename,MODE_APPEND);
            fos.write(text.getBytes());


            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + filename,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void savegps(String tim, String x, String y) {
        String text = "time=" + tim + "latitude= " + x + "Longitude= " + y+ "\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, MODE_APPEND);

            fos.write(text.getBytes());

            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + filename,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void savewifi(String tim, String x, String y) {
        String text = "time=" + tim + "Strength= " + x+"%" + "Accesspoint= " + y+ "\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, MODE_APPEND);
            fos.write(text.getBytes());


            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + filename,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void savemicrophone(String tim, String x) {
        String text = "time=" + tim + "filename= " + x+ "\n";
        FileOutputStream fos = null;

        try {
            fos = openFileOutput(filename, MODE_APPEND);
            fos.write(text.getBytes());


            Toast.makeText(this, "Saved to " + getFilesDir() + "/" + filename,
                    Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        c=c+1;
        if(c==1){

        String h = String.valueOf(sensorEvent.timestamp);


        ContentValues contentValues = forAccelerometer(h, "" + sensorEvent.values[0], "" + sensorEvent.values[1], "" + sensorEvent.values[2]);
        sqLiteDatabase.insert(sensorschema.Accelerometertable.NAME, null, contentValues);}
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sqLiteDatabase = new sensorbasehelper(getApplicationContext()).getWritableDatabase();

        x = (TextView) findViewById(R.id.X);

        tim = (TextView) findViewById(R.id.time);

        butaccel = (Button) findViewById(R.id.acceldata);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_NORMAL);
        final SensorEventListener listener = this;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                sensorManager.unregisterListener(listener);
//            }
//        }, 1);


        wifiManager = (WifiManager)
                this.getSystemService(Context.WIFI_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    locationRequestCode);


        } else {

        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("seethis", "doobja1");
        Log.d("seethis", String.valueOf(fusedLocationClient.getLastLocation()));


        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.d("seethis", "ohohoho");

                        if (location != null) {
                            Log.d("seethis", "doobja3");

                            String longitude = "Longitude=" + location.getLongitude();
                            String latitude = "Latitude=" + location.getLatitude();
                            Date date= new Date();

                            Timestamp ts = new Timestamp(date.getTime());
                            Log.d("see", latitude);
                            ContentValues contentValues=forgps(""+ts,latitude,longitude);
                            sqLiteDatabase.insert(sensorschema.GPStable.NAME, null, contentValues);

                        } else {
                            Log.d("seethis", "doobja");
                        }
                    }
                });
        Log.d("seethis", "doobja2");
        Log.d("seethis", "doobja4");


        BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                boolean success = intent.getBooleanExtra(
                        WifiManager.EXTRA_RESULTS_UPDATED, false);
                if (success) {
                    WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                    level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), 100);
                    scanSuccess();



                } else {
                    // scan failure handling
                    scanFailure();
                }
            }
        };

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        this.registerReceiver(wifiScanReceiver, intentFilter);

        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
            scanFailure();
        }
    butaccel.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            c=0;
            storacceltofile();
            load(0);
        }
    });
        wifibutton=(Button)findViewById(R.id.wifibutton);
        gpsbutton=(Button)findViewById(R.id.gpsbutton);
        gpsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storgpstofile();
                load(1);
            }
        });
        wifibutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storwifitofile();
                load(2);
            }
        });
//        outputfile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";
        outputfile=getExternalCacheDir().getAbsolutePath();
        outputfile+="/recording.3gp";
//        filename=  Environment.getExternalStorageDirectory().getAbsolutePath() + "/data.txt";

        microphone = new MediaRecorder();
        microphone.setAudioSource(MediaRecorder.AudioSource.MIC);
        microphone.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        microphone.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        microphone.setOutputFile(outputfile);
        start=(Button)findViewById(R.id.startrecord);
        stop=(Button)findViewById(R.id.stoprecord);
        micbutton=(Button)findViewById(R.id.microphonebutton);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                microphone = new MediaRecorder();
                microphone.setAudioSource(MediaRecorder.AudioSource.MIC);
                microphone.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                microphone.setOutputFile(outputfile);
                microphone.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

                try {
                    microphone.prepare();
                    microphone.start();
                } catch (Exception e) {

                }


                Toast.makeText(getApplicationContext(), "Recording has started", Toast.LENGTH_LONG).show();
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{

                    microphone.stop();

                microphone.release();
                microphone = null;}
                catch (Exception e){

                }

                Toast.makeText(getApplicationContext(), "Recording Stopped", Toast.LENGTH_LONG).show();
            }
        });
        micbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date date= new Date();

                Timestamp ts = new Timestamp(date.getTime());
                ContentValues contentValues=forMicrophone(""+ts,""+outputfile);
                sqLiteDatabase.insert(sensorschema.Mictable.NAME, null, contentValues);
                stormictofile();
                load(2);


            }
        });



    }
    @Override
    public void onStop() {
        super.onStop();
        if (microphone != null) {
            microphone.release();
            microphone = null;
        }


    }




}
