package integracionip.impresoras;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import model.Client;
import model.Persona;
import utils.LogicDataBase;

public class RecaudoActivity extends AppCompatActivity {

    private ArrayList<Client> clients;
    private Toolbar toolbar;
    private Persona persona;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaudo);
        toolbar = findViewById(R.id.toolbar_recaudos);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        configClients();
        configPersona();
        getSupportActionBar().setTitle(persona.getNombre());
    }

    private void configPersona() {
        Client client = clients.get(0);
        if(client!=null){
            persona = new Persona(client.getName(), client.getId(), client.getDireccion(), client.getTelefono1(), client.getTelefono2());
        }
    }

    private void configClients() {
        int numClients = getIntent().getIntExtra("num_clients", 0);
        clients = new ArrayList<Client>();
        for (int a = 0; a < numClients; a++) {
            String clt = getIntent().getStringExtra("client" + a);
            String[] cli = clt.split(",");
            Client client = new Client(cli[0], cli[1], cli[2], cli[3], cli[4], cli[5], cli[6], cli[7], cli[8], cli[9], cli[10]);
            clients.add(client);
        }
        addCards(clients);
    }

    private void addCards(ArrayList<Client> clients) {
        if (!clients.isEmpty()) {
            ListView listContratos = findViewById(R.id.lv);
            ListAdapter adapter = new CardsAdapterRecaudos(this, clients);
            listContratos.setAdapter(adapter);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recaudo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_config:
                goConfigClientActivity();
                break;
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void goConfigClientActivity() {
        Intent configActivity = new Intent(getApplicationContext(), ConfigClientActivity.class);
        configActivity.putExtra("nombre", persona.getNombre());
        configActivity.putExtra("cedula", persona.getCedula());
        configActivity.putExtra("direccion", persona.getDireccion());
        configActivity.putExtra("tel1", persona.getTelefono1());
        configActivity.putExtra("tel2", persona.getTelefono2());
        startActivity(configActivity);
    }
}
