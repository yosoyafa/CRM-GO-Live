package integracionip.impresoras;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import model.Client;
import model.Recaudo;
import model.Ticket;
import utils.ConexionHTTP;
import utils.DataBase;
import utils.LogicDataBase;


public class MainActivity extends AppCompatActivity implements SearchDialog.ExampleDialogListener{

    private TextView textView;
    private SharedPreferences sharedPreferences;
    private Toolbar toolbar;
    private LogicDataBase db;
    private CardView cardSearch, cardHistory, cardDownload, cardSync, cardClients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView2);
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        String tv_nombre = sharedPreferences.getString("name", "-").split(" ")[0];
        textView.setText("Bienvenido, " + tv_nombre);

        cardDownload = findViewById(R.id.cardDownload);
        cardSearch = findViewById(R.id.cardSearch);
        cardSync = findViewById(R.id.cardSync);
        cardHistory = findViewById(R.id.cardHistory);
        cardClients = findViewById(R.id.cardClients);

        setOnClickButtons();
        db = LoginActivity.getDb();
        notificationsClient();
    }

    private void setOnClickButtons() {
        cardDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                downloadFile();
            }
        });
        cardSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search("search");
            }
        });
        cardSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sync();
            }
        });
        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search("historial");
            }
        });
        cardClients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goClients();
            }
        });
    }

    private void goClients() {
        //Intent clientsActivity = new Intent(getApplicationContext(), ClientsActivity.class);
        //startActivity(clientsActivity);
    }

    private void funcionalidadNoDisponible() {
        Toast.makeText(this, "Esta funcionalidad aún no está disponible",
                Toast.LENGTH_LONG).show();
    }

    private void downloadFile() {
        ConexionHTTP conexion = new ConexionHTTP();
        conexion.downloadFile(sharedPreferences.getString("id", "-"));
        while (!conexion.isFinishProcess()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONArray respuesta = conexion.getResponseArray();
        store(conexion.getDataString());

        if (respuesta != null) {
            db.resetClients();
            try {

                for (int a = 0; a < respuesta.length(); a++) {
                    JSONObject client = respuesta.getJSONObject(a);
                    String name = client.getString("name");
                    String id = client.getString("numero_documento");
                    String total = client.getString("totalcartera");
                    String vigencia_desde = client.getString("vigenciadesde");
                    String vigencia_hasta = client.getString("vigenciahasta");
                    String numeropoliza = client.getString("numeropoliza");
                    String valorcontrato = client.getString("valorcontrato");
                    String periodicidad = client.getString("periodicidad1");
                    if (total == null) {
                        total = "0";
                    }
                    Client cliente = new Client(name, id, total, vigencia_desde, vigencia_hasta, numeropoliza, valorcontrato, periodicidad);
                    db.addClient(cliente);
                }

                //}
                Toast.makeText(this, "Descarga completa",
                        Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "ERROR JSON",
                        Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(this, "No se pudo realizar la descarga, revisa tu conexión a internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void store(String fileContents) {
        if(isExternalStorageWritable()){
            if(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                File file = new File(Environment.getExternalStorageDirectory(), "cartera.txt");
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(fileContents.getBytes());
                    fos.close();
                } catch (Exception e) {
                    System.out.println("error escribiendo");
                    e.printStackTrace();
                }
            }else{
                System.out.println("cannot write-manifestpermissions");
            }
        }else{
            System.out.println("cannot write-isexternalstoragewritable");
        }
    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    private void search(String action) {
        Bundle bundle = new Bundle();
        bundle.putString("action", action);
        SearchDialog searchDialog = new SearchDialog();
        searchDialog.setArguments(bundle);
        searchDialog.show(getSupportFragmentManager(), "example dialog");
    }

    private void searchById(String s) {
        ArrayList<Client> clients = db.searchClient(s);
        if(clients!=null && !clients.isEmpty()){
            goRecaudoActivity(clients);
        }else{
            ArrayList<Client> clients1 = searchClientOnline(s);
            if(!clients1.isEmpty()){
                goRecaudoActivity(clients1);
            }else{
                Toast.makeText(this, "No se encontró cliente con esa cédula",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private ArrayList<Client> searchClientOnline(String cc) {
        ArrayList<Client> clients = new ArrayList<Client>();
        ConexionHTTP conexionHTTP = new ConexionHTTP();
        conexionHTTP.buscarCCenCartera(sharedPreferences.getString("id","-"), cc);
        while (!conexionHTTP.isFinishProcess()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONArray respuesta = conexionHTTP.getResponseArray();
        try {
            for (int a = 0; a < respuesta.length(); a++) {
                JSONObject client = respuesta.getJSONObject(a);
                String name = client.getString("name");
                String id = client.getString("numero_documento");
                String total = client.getString("totalcartera");
                String vigencia_desde = client.getString("vigenciadesde");
                String vigencia_hasta = client.getString("vigenciahasta");
                String numeropoliza = client.getString("numeropoliza");
                String valorcontrato = client.getString("valorcontrato");
                String periodicidad = client.getString("periodicidad1");
                if (total == null) {
                    total = "0";
                }
                Client cliente = new Client(name, id, total, vigencia_desde, vigencia_hasta, numeropoliza, valorcontrato, periodicidad);
                clients.add(cliente);
                System.out.println(cliente.toStringRaw());
                db.addClient(cliente);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return clients;
    }

    private void sync() {
        System.out.println(db.getTableAsString("recaudos"));
        ArrayList<Recaudo> recaudos2sync = db.selectRecaudosOffline();

        if (recaudos2sync != null) {
            if (recaudos2sync.size() != 0) {
                int success = 0;
                for (int a = 0; a < recaudos2sync.size(); a++) {
                    Recaudo actual = recaudos2sync.get(a);
                    if (actual.isOnline() == 0) {
                        boolean b = sync1recaudo(actual);
                        if (b) {
                            success++;
                        }
                    }
                }
                String msg = "Se han sincronizado " + success + " recaudos de " + recaudos2sync.size() + " (" + success + "/" + recaudos2sync.size() + ")";
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Sincronización de recaudos offline");
                final TextView tv = new TextView(this);
                tv.setText(msg);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(tv);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            } else {
                Toast.makeText(this, "No hay recaudos por sincronizar",
                        Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "No se pudo realizar la sincronización, revisa tu conexión a internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean sync1recaudo(Recaudo rec){
        System.out.println(rec.toString());
        ConexionHTTP conexion = new ConexionHTTP();
        conexion.recaudo(rec.getUser_recaudador(), rec.getLatitud(), rec.getLongitud(), rec.getCedula_cliente(), rec.getValor(), rec.getId_recaudador(), rec.getFecha(), rec.getNumerdaor_offline(), rec.getObservaciones());
        while (!conexion.isFinishProcess()) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONObject respuesta = conexion.getRespuesta();
        //System.out.println(respuesta);
        if (respuesta!=null) {
            try {
                String estado = respuesta.getString("estado");
                if (estado.equals("successful")) {
                    db.deleteRecaudo(rec);
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }else{
            return false;
        }

    }

    private void goRecaudoActivity(ArrayList<Client> c) {
        Intent recaudoActivity = new Intent(getApplicationContext(), RecaudoActivity.class);
        for(int a = 0; a<c.size(); a++){
            recaudoActivity.putExtra("client"+a, c.get(a).toStringRaw());
        }
        recaudoActivity.putExtra("num_clients", c.size());
        startActivity(recaudoActivity);
    }

    private void logout() {
        sync();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.putBoolean("logged", false);
        editor.commit();
        db.resetClients();
        db.resetRecaudos();
        goLoginScreen();
    }

    private void goLoginScreen() {
        Intent mainActivity = new Intent(getApplicationContext(), LoginActivity.class);
        mainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(mainActivity);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    public void applyTexts(String data) {

        String[] values = data.split(",");
        if(values[0].equals("search")){
            searchById(values[1]);
        }else if(values[0].equals("historial")){
            historial(values[1]);
        }

    }

    private void historial(String cc) {
        System.out.println(db.getTableAsString(DataBase.TABLE_TICKETS));
        ArrayList<Ticket> tickets = db.searchTickets(cc);
        if(tickets!=null){
            goHistorialActivity(tickets);
        }else{
            Toast.makeText(this, "No se encontró cliente con esa cédula",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void goHistorialActivity(ArrayList<Ticket> tickets) {
        Intent historialActivity = new Intent(getApplicationContext(), HistorialActivity.class);
        for (int a = 0; a < tickets.size(); a++) {
            historialActivity.putExtra("ticket" + a, tickets.get(a).toStringRaw());
        }
        historialActivity.putExtra("num_tickets", tickets.size());
        startActivity(historialActivity);
    }

    private void logoutDialog(){

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tx = new TextView(this);
        tx.setText("\nHas iniciado sesión como\n"+sharedPreferences.getString("user","-"));
        adb.setView(tx);
        tx.setGravity(Gravity.CENTER_HORIZONTAL);
        adb.setTitle("¿Cerrar sesión?");
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        adb.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                if(areRecaudos2sync()){
                    recaudos2syncDialog();
                }else {
                    logoutDialog();
                }
        }
        return super.onOptionsItemSelected(item);
    }

    private void recaudos2syncDialog() {

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tx = new TextView(this);
        tx.setText("\nAún hay recaudos fuera de línea que no han sido sincronizados.\nEspera a tener acceso a internet para sincronizarlos y poder cerrar sesión.");
        adb.setView(tx);
        tx.setGravity(Gravity.CENTER_HORIZONTAL);
        adb.setTitle("¿Cerrar sesión?");
        //adb.setIcon(android.R.drawable.ic_dialog_alert);
        adb.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        adb.show();

    }

    private boolean areRecaudos2sync() {
        ArrayList recs = db.selectRecaudosOffline();
        return !recs.isEmpty();
    }

    private void notificationsClient(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("MyNotifications","MyNotifications", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "success on subscribe";
                        if (!task.isSuccessful()) {
                            msg = "failed to subscribe";
                        }
                        System.out.println(msg);
                    }
                });
    }
}
