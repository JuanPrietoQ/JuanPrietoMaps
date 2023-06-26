package ec.edu.tecnologicoloja.jpmaps;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;

    private EditText latitudTextView;
    private EditText longitudTextView;
    private EditText altitudTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitudTextView = findViewById(R.id.editTextText);
        longitudTextView = findViewById(R.id.editTextText2);
        altitudTextView = findViewById(R.id.editTextText3);

        Button btnObtener = findViewById(R.id.button);
        Button btnMapa = findViewById(R.id.button2);

        btnMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v==btnMapa){
                    startMapActivity();
                }

            }
        });
        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obtenerDatos();
            }
        });

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    private void obtenerDatos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                     1000);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                obtenerDatos();
            } else {
                Toast.makeText(this, "Permiso de ubicación denegado.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void startMapActivity() {
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitud = location.getLatitude();
        double longitud = location.getLongitude();
        double altitud = location.getAltitude();

        latitudTextView.setText("" + latitud);
        longitudTextView.setText("" + longitud);
        altitudTextView.setText("" + altitud);
    }
}
