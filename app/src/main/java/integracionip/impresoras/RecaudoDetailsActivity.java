package integracionip.impresoras;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Set;
import java.util.UUID;

import model.Client;
import model.Recaudo;
import model.Ticket;
import utils.ConexionHTTP;
import utils.LogicDataBase;

public class RecaudoDetailsActivity extends AppCompatActivity implements PaymentDialog.ExampleDialogListener, LocationListener {

    private Client client;
    private TextView tvNombre, tvId, tvTotal, tvPoliza, tvTotalContrato;
    private EditText etObservaciones;
    private FloatingActionButton fabPrint;
    private EditText etValorAPagar;
    private Button btnRecaudo;
    private boolean recaudoDone;
    private SharedPreferences sharedPreferences;
    private String recibo;
    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    private LogicDataBase db;
    private String valorrrr, lat, lon;
    private LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaudo_details);
        valorrrr = "";
        fabPrint = findViewById(R.id.fabPrint);
        fabPrint.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_print_white_24dp));
        tvNombre = findViewById(R.id.tv_nombre);
        tvId = findViewById(R.id.tv_id);
        tvTotal = findViewById(R.id.tv_total);
        btnRecaudo = findViewById(R.id.button_recaudo);
        etObservaciones = findViewById(R.id.et_observaciones);
        etValorAPagar = findViewById(R.id.et_valor_a_pagar);
        tvPoliza = findViewById(R.id.tv_poliza);
        tvTotalContrato = findViewById(R.id.tv_valor_contrato);
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        setOnClickButtons();
        configClient();
        recaudoDone = false;
        recibo = "";
        db = LoginActivity.getDb();
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
        android.location.Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
    }

    private void configClient() {
        String clt = getIntent().getStringExtra("client");
        String[] cli = clt.split(",");
        client = new Client(cli[0],cli[1],cli[2],cli[3],cli[4],cli[5],cli[6]);

        tvNombre.setText(client.getName());
        tvNombre.setTextSize(16);
        tvId.setText("Cedula: "+client.getId());
        tvTotal.setText("Total cartera: $"+client.getTotal());
        tvPoliza.setText("Número de Contrato: "+client.getNumeroPoliza());
        tvTotalContrato.setText("Valor contrato: $"+client.getValorContrato());
        //tvValorAPagar.setText("Valor a pagar: ");
    }

    private void setOnClickButtons() {
        fabPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(recaudoDone){
                    try{
                        findBluetoothDevice();
                        openBluetoothPrinter();
                        printData(recibo);
                        disconnectBT();
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(getApplicationContext(), "Se debe realizar el recaudo antes de imprimir",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnRecaudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String value = etValorAPagar.getText().toString();
                if(!value.isEmpty()){
                    valorrrr = value;
                    confirmPayment(value);
                }else{
                    Toast.makeText(getApplicationContext(), "Ingresa un valor a pagar",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
//        btnPrint.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(recaudoDone){
//                    try{
//                        findBluetoothDevice();
//                        openBluetoothPrinter();
//                        printData(recibo);
//                        disconnectBT();
//                    }catch(Exception e){
//                        e.printStackTrace();
//                        Toast.makeText(getApplicationContext(), "Se debe realizar el recaudo antes de imprimir",
//                                Toast.LENGTH_LONG).show();
//                    }
//                }
//            }
//        });
    }

    private void confirmPayment(String value) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Resumen del pago");
//
//        // Set up the input
//        final TextView tv = new TextView(this);
//        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
//        tv.setText(client.toString()+"\nValor a pagar: $"+value);
//        builder.setView(tv);
//        final String val = value;
//        // Set up the buttons
//        builder.setPositiveButton("Confirmar pago", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                pay(val);
//                //goMainScreen();
//            }
//        });
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//
//        builder.show();
        PaymentDialog paymentDialog = new PaymentDialog();
        Bundle args = new Bundle();
        args.putString("info", client.toString()+"\n"+etObservaciones.getText().toString()+"\nValor a pagar: $"+value);
        paymentDialog.setArguments(args);
        paymentDialog.show(getSupportFragmentManager(), "example dialog");

    }

    private boolean pay(String value) {
        ConexionHTTP conexion = new ConexionHTTP();
        String user = sharedPreferences.getString("user", "-");
        System.out.println("valor: " + value);
        String cedula = client.getId();
        String id = sharedPreferences.getString("id", "-");
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy,HH:mm:ss");
        String fecha = df.format(Calendar.getInstance().getTime());
        //System.out.println("------------------------\nFecha: "+fecha);
        String observaciones = etObservaciones.getText().toString();
        if(observaciones.isEmpty()){
            observaciones = "-";
        }
        conexion.recaudo(user, lat, lon, cedula, value, id,fecha,"-", observaciones);
        while (!conexion.isFinishProcess()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONObject respuesta = conexion.getRespuesta();
        System.out.println(respuesta);
        if (!(respuesta==null)) {
            //recuado ONLINE
            try {
                String idRecaudo = respuesta.getString("id");
                String estado = respuesta.getString("estado");
                if (estado.equals("successful")) {
                    recaudoDone = true;
                    blockButtonRecaudo();
                    generarTicket(fecha, observaciones, idRecaudo);
                    Toast.makeText(getApplicationContext(), "Recaudo exitoso, proceda a imprimir el recibo",
                            Toast.LENGTH_LONG).show();
                    db.addRecaudo(new Recaudo(user,id,cedula,value,lat,lon,1,fecha,"-", observaciones));
                    return true;
                } else {
                    Toast.makeText(getApplicationContext(), "Recaudo FALLIDO",
                            Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            //RECAUDO OFFLINE
            int numerador_rc = Integer.parseInt(sharedPreferences.getString("numerador_rc", "-"));
            db.addRecaudo(new Recaudo(user,id,cedula,value,lat,lon,0,fecha,numerador_rc+"", observaciones));
            numerador_rc++;
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("numerador_rc",numerador_rc+"");
            editor.commit();
            recaudoDone = true;
            blockButtonRecaudo();
            generarTicket(fecha, observaciones, sharedPreferences.getString("iniciales_numerador","-")+numerador_rc);
            Toast.makeText(getApplicationContext(), "Recaudo OFFLINE exitoso, proceda a imprimir el recibo.\nRecuerda sincronizar cuando tengas acceso a internet.",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    private void generarTicket(String fecha, String observaciones, String numRecibo) {

        String[] date = fecha.split(",");
        String date1 = date[0].replace("-","/");

        Ticket ticket = new Ticket(sharedPreferences.getString("cabecera","-"),sharedPreferences.getString("nit","-"),
                sharedPreferences.getString("tel","-"),date1,client.getVigenciaDesde(),client.getVigenciaHasta(),"-VALORVIGCONTRATO-",
                "-VALORCUOTA-","-SALDOVIGENCIA-","-CUOTASVENCIDAS-","-CUOTAACTUAL-","-PENDIENTES",
                numRecibo,client.getNumeroPoliza(),client.getId(),client.getName(),valorrrr,observaciones,sharedPreferences.getString("name","-"));
        db.addTicket(ticket);
        recibo = ticket.toString();
    }

    private void blockButtonRecaudo() {
        btnRecaudo.setEnabled(false);
    }

    public void findBluetoothDevice(){

        try{

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter==null){
                Toast.makeText(getApplicationContext(), "No se encontro bluetoothadapter",
                        Toast.LENGTH_LONG).show();
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBT,0);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){

                    // My Bluetoth printer name is BTP_F09F1A
                    if(pairedDev.getName().equals("APEX2")){
                        bluetoothDevice=pairedDev;
                        System.out.println("Bluetooth Printer Attached: "+pairedDev.getName());
                        break;
                    }
                }
            }

            System.out.println("Bluetooth Printer Attached");
        }catch(Exception ex){
            ex.printStackTrace();
        }

    }

    void openBluetoothPrinter() throws IOException {
        try{

            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket=bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream=bluetoothSocket.getOutputStream();
            inputStream=bluetoothSocket.getInputStream();

            beginListenData();

        }catch (Exception ex){

        }
    }

    void beginListenData(){
        try{

            final Handler handler =new Handler();
            final byte delimiter=10;
            stopWorker =false;
            readBufferPosition=0;
            readBuffer = new byte[1024];

            thread=new Thread(new Runnable() {
                @Override
                public void run() {

                    while (!Thread.currentThread().isInterrupted() && !stopWorker){
                        try{
                            int byteAvailable = inputStream.available();
                            if(byteAvailable>0){
                                byte[] packetByte = new byte[byteAvailable];
                                inputStream.read(packetByte);

                                for(int i=0; i<byteAvailable; i++){
                                    byte b = packetByte[i];
                                    if(b==delimiter){
                                        byte[] encodedByte = new byte[readBufferPosition];
                                        System.arraycopy(
                                                readBuffer,0,
                                                encodedByte,0,
                                                encodedByte.length
                                        );
                                        final String data = new String(encodedByte,"US-ASCII");
                                        readBufferPosition=0;
                                        handler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                System.out.println(data);
                                            }
                                        });
                                    }else{
                                        readBuffer[readBufferPosition++]=b;
                                    }
                                }
                            }
                        }catch(Exception ex){
                            stopWorker=true;
                        }
                    }

                }
            });

            thread.start();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Printing Text to Bluetooth Printer //
    void printData(String msg) throws  IOException{
        try{
            msg+="\n";
            //msg = CHAR_ESC+msg;
            outputStream.write(msg.getBytes("ASCII"));
            System.out.println("Printing Text...");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    // Disconnect Printer //
    void disconnectBT() throws IOException{
        try {
            stopWorker=true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            System.out.println("Printer Disconnected.");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void goMainScreen() {
        Intent mainActivity = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(mainActivity);
        this.finish();
    }

    @Override
    public void applyTexts() {
        if(pay(valorrrr)){
            etValorAPagar.setEnabled(false);
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

    public EditText getEtObservaciones() {
        return etObservaciones;
    }

    public void setEtObservaciones(EditText etObservaciones) {
        this.etObservaciones = etObservaciones;
    }
}
