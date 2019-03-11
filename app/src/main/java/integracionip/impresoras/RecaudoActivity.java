package integracionip.impresoras;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import model.Client;
import utils.LogicDataBase;

public class RecaudoActivity extends AppCompatActivity {

    private ArrayList<Client> clients;
    private Toolbar toolbar;

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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    private void configClients() {
        int numClients = getIntent().getIntExtra("num_clients", 0);
        clients = new ArrayList<Client>();
        for (int a = 0; a < numClients; a++) {
            String clt = getIntent().getStringExtra("client" + a);
            String[] cli = clt.split(",");
            Client client = new Client(cli[0], cli[1], cli[2], cli[3], cli[4], cli[5], cli[6], cli[7]);
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
}

