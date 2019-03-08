package integracionip.impresoras;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import model.Client;
import utils.LogicDataBase;

public class RecaudoActivity extends AppCompatActivity {

    private ArrayList<Client> clients;
    private SharedPreferences sharedPreferences;
    private LogicDataBase db;
    private FrameLayout mRelativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recaudo);
        sharedPreferences = getSharedPreferences("Session", Context.MODE_PRIVATE);
        db = LoginActivity.getDb();
        mRelativeLayout = findViewById(R.id.rl);
        configClients();
    }

    private void configClients() {
        int numClients = getIntent().getIntExtra("num_clients", 0);
        clients = new ArrayList<Client>();
        for (int a = 0; a < numClients; a++) {
            String clt = getIntent().getStringExtra("client" + a);
            String[] cli = clt.split(",");
            Client client = new Client(cli[0], cli[1], cli[2], cli[3], cli[4], cli[5], cli[6]);
            clients.add(client);
        }
        addCards(clients);
    }

    private void addCards(ArrayList<Client> clients) {
        if (!clients.isEmpty()) {
            ListView listContratos = findViewById(R.id.lv);
            ListAdapter adapter = new CardsAdapter(this, clients);
            listContratos.setAdapter(adapter);
        }
    }



}

