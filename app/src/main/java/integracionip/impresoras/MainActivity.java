package integracionip.impresoras;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONObject;

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
    private Button buttonLogout, buttonDownload, buttonSearch, buttonSync, buttonHistorial;
    private LogicDataBase db;
    private CardView cardSearch, cardHistory, cardDownload, cardSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        buttonDownload = findViewById(R.id.button_download_file);
//        buttonLogout = findViewById(R.id.button_logout);
//        buttonSearch = findViewById(R.id.button_search);
//        buttonSync = findViewById(R.id.button_sync);
//        buttonHistorial = findViewById(R.id.button_historial);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textView2);
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        String tv_nombre = sharedPreferences.getString("user", "-");
        textView.setText("Bienvenido, " + tv_nombre);

        cardDownload = findViewById(R.id.cardDownload);
        cardSearch = findViewById(R.id.cardSearch);
        cardSync = findViewById(R.id.cardSync);
        cardHistory = findViewById(R.id.cardHistory);

        setOnClickButtons();
        db = LoginActivity.getDb();
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

//        buttonLogout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logoutDialog();
//            }
//        });

    }

    private void downloadFile() {
        ConexionHTTP conexion = new ConexionHTTP();
        conexion.downloadFile(sharedPreferences.getString("id", "-"));
        while (!conexion.isFinishProcess()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        JSONArray respuesta = conexion.getResponseArray();
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
                    if (total == null) {
                        total = "0";
                    }
                    Client cliente = new Client(name, id, total, vigencia_desde, vigencia_hasta, numeropoliza, valorcontrato);
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
            Toast.makeText(this, "No se encontró cliente con esa cédula",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void sync() {
        ArrayList<Recaudo> recaudos2sync = db.selectRecaudos();
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

                // Set up the input
                final TextView tv = new TextView(this);
                // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
                tv.setText(msg);
                tv.setGravity(Gravity.CENTER_HORIZONTAL);
                builder.setView(tv);

                // Set up the buttons
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
            Toast.makeText(this, "No se pudo realizar la descarga, revisa tu conexión a internet",
                    Toast.LENGTH_LONG).show();
        }
    }

    private boolean sync1recaudo(Recaudo rec){
        System.out.println(rec.toString());

        ConexionHTTP conexion = new ConexionHTTP();
        conexion.recaudo(rec.getUser_recaudador(), rec.getLatitud(), rec.getLongitud(), rec.getCedula_cliente(), rec.getValor(), rec.getId_recaudador(), rec.getFecha(), rec.getNumerdaor_offline(), rec.getObservaciones());
        while (!conexion.isFinishProcess()) {
            try {
                Thread.sleep(100);
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
        if(tickets!=null && !tickets.isEmpty()){
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

//        Toast.makeText(this, "Esta opcion aún no está disponible",
//                Toast.LENGTH_LONG).show();
    }

    private void logoutDialog(){

        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        TextView tx = new TextView(this);
        tx.setText("\nHas iniciado sesión como\n"+sharedPreferences.getString("user","-"));
        adb.setView(tx);
        tx.setGravity(Gravity.CENTER_HORIZONTAL);
        adb.setTitle("¿Cerrar sesión?");
        //adb.setIcon(android.R.drawable.ic_dialog_alert);
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

    public CardView getCardSearch() {
        return cardSearch;
    }

    public void setCardSearch(CardView cardSearch) {
        this.cardSearch = cardSearch;
    }

    public CardView getCardHistory() {
        return cardHistory;
    }

    public void setCardHistory(CardView cardHistory) {
        this.cardHistory = cardHistory;
    }

    public CardView getCardDownload() {
        return cardDownload;
    }

    public void setCardDownload(CardView cardDownload) {
        this.cardDownload = cardDownload;
    }

    public CardView getCardSync() {
        return cardSync;
    }

    public void setCardSync(CardView cardSync) {
        this.cardSync = cardSync;
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
                logoutDialog();
        }
        return super.onOptionsItemSelected(item);
    }
}
