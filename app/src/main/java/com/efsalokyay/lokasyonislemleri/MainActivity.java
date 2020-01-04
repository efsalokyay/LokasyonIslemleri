package com.efsalokyay.lokasyonislemleri;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private int izinKontrol;
    private Button btn_konum;
    private TextView txt_enlem, txt_boylam;
    private String konumSaglayici = "gps";
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_konum = findViewById(R.id.btn_konum);
        txt_boylam = findViewById(R.id.txt_boylam);
        txt_enlem = findViewById(R.id.txt_enlem);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        btn_konum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

                if (izinKontrol != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(
                            MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            100);
                } else {

                    Location konum = locationManager.getLastKnownLocation(konumSaglayici);

                    if (konum != null) {
                        onLocationChanged(konum);
                    } else {
                        txt_boylam.setText("Konum aktiif değil!");
                        txt_enlem.setText("Konum aktiif değil!");
                    }
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 100) {

            izinKontrol = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "İzin verildi.", Toast.LENGTH_SHORT).show();

                Location konum = locationManager.getLastKnownLocation(konumSaglayici);

                if (konum != null) {
                    onLocationChanged(konum);
                } else {
                    txt_boylam.setText("Konum aktiif değil!");
                    txt_enlem.setText("Konum aktiif değil!");
                }
            } else {
                Toast.makeText(this, "İzin verilmedi.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {

        double enlem = location.getLongitude();
        double boylam = location.getLatitude();

        txt_boylam.setText("Boylam : " + boylam);
        txt_enlem.setText("Enlem : " + enlem);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
