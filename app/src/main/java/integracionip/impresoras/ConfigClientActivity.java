package integracionip.impresoras;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Edicion;
import model.Persona;
import utils.ConexionHTTP;
import utils.LogicDataBase;

public class ConfigClientActivity extends AppCompatActivity implements PaymentDialog.ExampleDialogListener, LocationListener {

    private Persona persona;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private EditText etNombre, etCedula, etDireccion, etTel1, etTel2;
    private Button buttonSave;
    private LocationManager locationManager;
    private String lat, lon;
    private LogicDataBase db;
    private boolean done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config_client);
        configPersona();
        configWidgets();
        configButtons();
        setLocation();
        configUtils();
    }

    private void configUtils() {
        done = false;
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        db = LoginActivity.getDb();
    }

    private void setLocation() {
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

    private void configButtons() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(changes() && (etTel1.getText().toString().isEmpty() || etTel2.getText().toString().isEmpty() || etDireccion.getText().toString().isEmpty())){
                    Toast.makeText(getApplicationContext(), "Completa la informacion solicitada",
                            Toast.LENGTH_LONG).show();
                }else{
                    if(changes()) {
                        showConfirmChangesDialog();
                    }else{
                        Toast.makeText(getApplicationContext(), "Realiza algún cambio",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    private boolean saveConfig() {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String fecha = df.format(Calendar.getInstance().getTime());
        ConexionHTTP conexionHTTP = new ConexionHTTP();
        String newDir = etDireccion.getText().toString().replace("#","%23");
        conexionHTTP.edicion(sharedPreferences.getString("user","-"),sharedPreferences.getString("id","-"),persona.getNombre(),persona.getCedula(),persona.getTelefono1(),etTel1.getText().toString(),persona.getTelefono2(),etTel2.getText().toString(),persona.getDireccion(),newDir,fecha,lat,lon);
        while (!conexionHTTP.isFinishProcess()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONObject respuesta = conexionHTTP.getRespuesta();
        System.out.println(respuesta);
        if (respuesta != null) {
            try {
                String estado = respuesta.getString("estado");
                if (estado.equals("successful")) {
                    Toast.makeText(getApplicationContext(), "Actualización exitosa.",
                            Toast.LENGTH_LONG).show();
                    Edicion edicion = new Edicion(sharedPreferences.getString("user","-"),sharedPreferences.getString("id","-"),persona.getNombre(),persona.getCedula(),persona.getTelefono1(),etTel1.getText().toString(),persona.getTelefono2(),etTel2.getText().toString(),persona.getDireccion(),etDireccion.getText().toString(),fecha,lat,lon,"1");
                    db.addEdicion(edicion);
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Actualización fallida.",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No se pudo realizar la actualización correctamente.",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Actualización OFFLINE exitosa.\nRecuerda sincronizar cuando tengas acceso a internet.",
                    Toast.LENGTH_LONG).show();
            Edicion edicion = new Edicion(sharedPreferences.getString("user","-"),sharedPreferences.getString("id","-"),persona.getNombre(),persona.getCedula(),persona.getTelefono1(),etTel1.getText().toString(),persona.getTelefono2(),etTel2.getText().toString(),persona.getDireccion(),etDireccion.getText().toString(),fecha,lat,lon,"0");
            db.addEdicion(edicion);
            return true;
        }
        return false;
    }

    private void configWidgets() {
        toolbar = findViewById(R.id.toolbar_config);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Editar datos de cliente");
        etNombre = findViewById(R.id.et_nombreconfig);
        etCedula = findViewById(R.id.et_cedulaconfig);
        etDireccion = findViewById(R.id.et_direccionconfig);
        etTel1 = findViewById(R.id.et_tel1config);
        etTel2 = findViewById(R.id.et_tel2config);

        etNombre.setKeyListener(null);
        etCedula.setKeyListener(null);
        etNombre.setFocusable(false);
        etCedula.setFocusable(false);

        etNombre.setText(persona.getNombre());
        etCedula.setText(persona.getCedula());
        etDireccion.setText(persona.getDireccion());
        etTel1.setText(persona.getTelefono1());
        etTel2.setText(persona.getTelefono2());

        buttonSave = findViewById(R.id.button_saveconfig);
    }

    private void configPersona() {
        String nombre = getIntent().getStringExtra("nombre");
        String cedula = getIntent().getStringExtra("cedula");
        String direccion = getIntent().getStringExtra("direccion");
        String tel1 = getIntent().getStringExtra("tel1");
        String tel2 = getIntent().getStringExtra("tel2");
        persona = new Persona(nombre, cedula, direccion, tel1, tel2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info_config, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info_config:
                showInfoDialog();
                break;
            case android.R.id.home:
                if(!changes()) {
                    finish();
                }else{
                    if(!done){
                        showConfirmExitDialog();
                    }else{
                        finish();
                    }
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showInfoDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tx = new TextView(this);
        tx.setText("\nPuedes cambiar lo número telefónicos o la dirección del cliente.");
        adb.setView(tx);
        tx.setGravity(Gravity.CENTER_HORIZONTAL);
        adb.setTitle("Actualzación de datos de clientes");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        adb.show();
    }

    @Override
    public void onBackPressed() {
        if(!changes()) {
            finish();
        }else{
            if(!done){
                showConfirmExitDialog();
            }else{
                finish();
            }
        }
    }

    private void showConfirmExitDialog() {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tx = new TextView(this);
        tx.setText("\n¿Deseas salir sin guardar los cambios?");
        adb.setView(tx);
        tx.setGravity(Gravity.CENTER_HORIZONTAL);
        adb.setTitle("Hay cambios sin guardar");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
    }

    private void showConfirmChangesDialog() {
        String info = persona.getNombre()+"\nCC: "+persona.getCedula()+"\n";
        if(!persona.getTelefono1().equals(etTel1.getText().toString())){
            info = info + "\nTelefono 1 (actual): "+persona.getTelefono1();
            info = info + "\nTelefono 1 (nuevo): "+etTel1.getText().toString()+"\n";
        }
        if(!persona.getTelefono2().equals(etTel2.getText().toString())){
            info = info + "\nTelefono 2 (actual): "+persona.getTelefono2();
            info = info + "\nTelefono 2 (nuevo): "+etTel2.getText().toString()+"\n";
        }
        if(!persona.getDireccion().equals(etDireccion.getText().toString())){
            info = info + "\nDirección (actual): "+persona.getDireccion();
            info = info + "\nDirección (nuevo): "+etDireccion.getText().toString();
        }
        PaymentDialog paymentDialog = new PaymentDialog();
        Bundle args = new Bundle();
        args.putString("type", "config");
        args.putString("info", info);
        paymentDialog.setArguments(args);
        paymentDialog.show(getSupportFragmentManager(), "example dialog");
    }

    public boolean changes(){
        boolean out = false;
        if(!persona.getTelefono1().equals(etTel1.getText().toString()) ||
                !persona.getTelefono2().equals(etTel2.getText().toString()) ||
                !persona.getDireccion().equals(etDireccion.getText().toString())){
            out = true;
        }
        return out;
    }

    @Override
    public void applyTexts() {
        if(saveConfig()){
            done = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        lon = location.getLongitude()+"";
        lat = location.getLatitude()+"";
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
