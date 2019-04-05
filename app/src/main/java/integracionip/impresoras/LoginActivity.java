package integracionip.impresoras;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.karan.churi.PermissionManager.PermissionManager;

import org.json.JSONObject;

import utils.ConexionHTTP;
import utils.LogicDataBase;
import utils.Security;

public class LoginActivity extends AppCompatActivity implements LocationListener {

    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private Button mEmailSignInButton;
    private SharedPreferences sharedPreferences;
    private static LogicDataBase db;
    private LocationManager locationManager;
    private String latitude, longitude;
    private Toolbar toolbar;
    private PermissionManager permissionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        db = new LogicDataBase(getApplicationContext());
        SharedPreferences.Editor edit = sharedPreferences.edit();
        toolbar = findViewById(R.id.toolbar_login);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //getSupportActionBar().setTitle("Recaudos App");
        permissions();
        if (sharedPreferences.getBoolean("logged", false)) {
            goMainScreen();
        } else {
            setContentView(R.layout.activity_login);
            mPasswordView = findViewById(R.id.password);
            mEmailView = findViewById(R.id.email);
            mEmailSignInButton = findViewById(R.id.email_sign_in_button);
            mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View view) {
                                                          login();
                                                      }
                                                  }
            );
        }
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        android.location.Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }

    private void permissions() {
        permissionManager = new PermissionManager() { };
        permissionManager.checkAndRequestPermissions(this);
    }

    private void login() {
        String user = mEmailView.getText().toString();
        String pass = mPasswordView.getText().toString();
        ConexionHTTP conexion = new ConexionHTTP();
        if (!user.isEmpty() && !pass.isEmpty()) {
            conexion.login(user, latitude, longitude);
            while (!conexion.isFinishProcess()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            JSONObject respuesta = conexion.getRespuesta();

            try {
                String hashWeb = respuesta.getString("user_hash");
                Security security = new Security();
                String hashLocal = security.md5(pass);
                System.out.println("HASH LOCAL: " + hashLocal);
                System.out.println("HASH WEB: " + hashWeb);
                if (!respuesta.getString("user_name").equals("Usuario NO Autorizado para APP")) {
                    if (hashWeb.equals(hashLocal)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("user", user);
                        editor.putString("name", respuesta.getString("first_name")+" "+respuesta.getString("last_name"));
                        editor.putString("id", respuesta.getString("id"));
                        editor.putBoolean("logged", true);
                        editor.putString("iniciales_numerador",respuesta.getString("iniciales_numerador"));
                        editor.putString("numerador_rc",respuesta.getString("numerador_rc_manual"));
                        editor.putString("cabecera",respuesta.getString("agencia")+"\n"+respuesta.getString("puntodeventa"));
                        editor.putString("impresora", respuesta.getString("impresora"));
                        editor.commit();
                        goMainScreen();
                    } else {
                        Toast.makeText(this, "Usuario o contraseña incorrecta",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(this, "Usuario NO Autorizado para APP ",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Llena todos los campos",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void goMainScreen() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
    }

    public static LogicDataBase getDb() {
        return db;
    }

    public void setDb(LogicDataBase db) {
        LoginActivity.db = db;
    }

    @Override
    public void onLocationChanged(android.location.Location location) {
        try {
            longitude = location.getLongitude() + "";
            latitude = location.getLatitude() + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
