package integracionip.impresoras;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import model.Client;
import model.Gestion;
import utils.ConexionHTTP;
import utils.LogicDataBase;
import utils.PrefixEditText;

public class GestionActivity extends AppCompatActivity implements LocationListener, PaymentDialog.ExampleDialogListener {

    private TextView tvNombre, tvId;
    private Spinner spinnerResultado;
    private CheckBox checkBoxAcuerdo;
    private ImageButton dateButton;
    private PrefixEditText etValorAcuerdo;
    private EditText etDescripcion, etDate;
    private Button buttonSave;
    private Toolbar toolbar;
    private LocationManager locationManager;
    private Client client;
    private SharedPreferences sharedPreferences;
    private LogicDataBase db;

    public final Calendar calendar = Calendar.getInstance();
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);
    final int year = calendar.get(Calendar.YEAR);
    private String lon;
    private String lat;
    private String tipoGestion;
    private boolean acuerdoPago;
    private String fecha;
    private String valorAcuerdo;
    private String descripcion;
    private String resultadoGestion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion);
        configUtils();
        setWidgets();
        setSpinners();
        setButtons();
        setLocation();
        configClient();
    }

    private void configUtils() {
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        db = LoginActivity.getDb();
    }

    private void configClient() {
        String clt = getIntent().getStringExtra("client");
        String[] cli = clt.split(",");
        client = new Client(cli[0],cli[1],cli[2],cli[3],cli[4],cli[5],cli[6],cli[7],cli[8],cli[9],cli[10]);
        System.out.println("---------ESTE ES EL CLIENTE QUE LLEGO--------\n"+client.toStringRaw());
        tvNombre.setText(client.getName());
        tvNombre.setTextSize(16);
        tvId.setText("Cedula: "+client.getId());
        //tvValorAPagar.setText("Valor a pagar: ");
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

    private void datePicker() {

        DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el month ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10)? "0" + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                //Formateo el month obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10)? "0" + String.valueOf(mesActual):String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etDate.setText(diaFormateado + "/" + mesFormateado + "/" + year);
            }

        }, year, month, day);
        //Muestro el widget
        datePicker.show();
    }

    private void setButtons() {
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkBoxAcuerdo.isChecked()){

                    if(!etValorAcuerdo.getText().toString().isEmpty() &&
                            Integer.parseInt(etValorAcuerdo.getText().toString()) != 0 &&
                            !etDate.getText().toString().isEmpty() &&
                            !etDescripcion.getText().toString().isEmpty()){
                        acuerdoPago = checkBoxAcuerdo.isChecked();
                        fecha = etDate.getText().toString();
                        valorAcuerdo = etValorAcuerdo.getText().toString();
                        descripcion = etDescripcion.getText().toString();
                        resultadoGestion = spinnerResultado.getSelectedItem().toString();
                        displayDialog(tipoGestion, acuerdoPago, fecha, valorAcuerdo, descripcion, resultadoGestion);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ingresa toda la información solicitada.",
                                Toast.LENGTH_LONG).show();
                    }
                }else{
                    if(!etDescripcion.getText().toString().isEmpty()){
                        acuerdoPago = checkBoxAcuerdo.isChecked();
                        fecha = "";
                        valorAcuerdo = "";
                        descripcion = etDescripcion.getText().toString();
                        resultadoGestion = spinnerResultado.getSelectedItem().toString();
                        displayDialog(tipoGestion, acuerdoPago, fecha, valorAcuerdo, descripcion, resultadoGestion);
                    }else{
                        Toast.makeText(getApplicationContext(), "Ingresa toda la información solicitada.",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker();
            }
        });
    }

    private void displayDialog(String tipoGestion, boolean acuerdoPago, String fecha, String valorAcuerdo, String descripcion, String resultadoGestion) {
        String acuerdoPagoStr = "No";
        if(acuerdoPago){
            acuerdoPagoStr = "Si";
        }

        String info = client.toString() + "\n  \nTipo de Gestión:"+tipoGestion + "\nAcuerdo de pago: " + acuerdoPagoStr + "\nFecha acuerdo: "
                + fecha +"\nValor Acuerdo: $"+valorAcuerdo +"\nDescripción: "+descripcion+ "\nResultado Gestión: "+resultadoGestion;

        PaymentDialog paymentDialog = new PaymentDialog();
        Bundle args = new Bundle();
        args.putString("type", "gestion");
        args.putString("info", info);
        paymentDialog.setArguments(args);
        paymentDialog.show(getSupportFragmentManager(), "example dialog");
    }

    private void setSpinners() {
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.resultado_gestion, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerResultado.setAdapter(adapter1);
    }

    public void setWidgets(){
        tipoGestion = "GestionAPP";
        tvNombre = findViewById(R.id.tv_nombre_gestion);
        tvId = findViewById(R.id.tv_id_gestion);
        spinnerResultado = findViewById(R.id.spinner_resultado_gestion);
        checkBoxAcuerdo = findViewById(R.id.checkBox_acuerdo_de_pago);
        checkBoxAcuerdo.setSelected(true);
        dateButton = findViewById(R.id.imageButton_date);
        etDate = findViewById(R.id.et_fecha);
        etValorAcuerdo = findViewById(R.id.et_valor_acuerdo);
        etDescripcion = findViewById(R.id.et_descripcion);
        buttonSave = findViewById(R.id.button_savegestion);
        toolbar = findViewById(R.id.toolbar_gestion);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void applyTexts() {
        if(saveGestion(tipoGestion, acuerdoPago, fecha, valorAcuerdo, descripcion, resultadoGestion, lat, lon)){
            finish();
        }
    }

    private boolean saveGestion(String tipoGestion, boolean acuerdoPago, String fecha, String valorAcuerdo, String descripcion, String resultadoGestion, String lat, String lon) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String f = df.format(Calendar.getInstance().getTime());
        ConexionHTTP conexionHTTP = new ConexionHTTP();
        fecha = fecha.replace("/","-");
        String acuerdoPagoStr = "0";
        if (acuerdoPago) {
            acuerdoPagoStr = "1";
        }
        String resultadoGestionNum = resultadoGestionNum(resultadoGestion);
        System.out.println("------USER ID------\n"+sharedPreferences.getString("id", "-")+"-------------------\n");
        System.out.println("------CC CLIENTE------\n"+client.getId()+"-------------------\n");
        conexionHTTP.gestion(tipoGestion, sharedPreferences.getString("id", "-"), client.getId(), f, lat, lon, acuerdoPagoStr, fecha, valorAcuerdo, descripcion, resultadoGestionNum, sharedPreferences.getString("user","-"));
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
                    Toast.makeText(getApplicationContext(), "Gestión exitosa",
                            Toast.LENGTH_LONG).show();
                    Gestion gestion = new Gestion(tipoGestion, sharedPreferences.getString("id", "-"), client.getId(), Integer.parseInt(acuerdoPagoStr), f, fecha, valorAcuerdo, descripcion, resultadoGestion, lat, lon, 1);
                    db.addGestion(gestion);
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Gestión fallida.",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "No se pudo realizar la gestión correctamente.",
                        Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Gestion OFFLINE exitosa.\nRecuerda sincronizar cuando tengas acceso a internet.",
                    Toast.LENGTH_LONG).show();
            Gestion gestion = new Gestion(tipoGestion, sharedPreferences.getString("id", "-"), client.getId(), Integer.parseInt(acuerdoPagoStr), f, fecha, valorAcuerdo, descripcion, resultadoGestion, lat, lon, 0);
            db.addGestion(gestion);
            return true;
        }
        return false;
    }

    private String resultadoGestionNum(String resultadoGestion) {
        String out = "";
        if(resultadoGestion.equals("Titular se contacta")){
            out = "1";
        }else if(resultadoGestion.equals("Mensaje con tercero")){
            out = "3";
        }else if(resultadoGestion.equals("No hay contacto")){
            out = "4";
        }else if(resultadoGestion.equals("Retiro")){
            out = "15";
        }
        return out;
    }

    private void goMainActivity() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        this.finish();
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkBox_acuerdo_de_pago:
                if (checked) {
                    etValorAcuerdo.setEnabled(true);
                    dateButton.setEnabled(true);
                    etDate.setEnabled(true);
                }else{
                    etValorAcuerdo.setEnabled(false);
                    dateButton.setEnabled(false);
                    etDate.setEnabled(false);
                    etDate.setText("");
                    etValorAcuerdo.setText("");
                }
                break;

        }
    }

}
