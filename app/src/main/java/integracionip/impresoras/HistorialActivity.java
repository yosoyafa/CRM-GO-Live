package integracionip.impresoras;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import model.Ticket;

public class HistorialActivity extends AppCompatActivity {

    private ArrayList<Ticket> tickets;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);
        toolbar = findViewById(R.id.toolbar_historial);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        getSupportActionBar().setTitle("Historial de Recibos");
        configTickets();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

    public void configTickets() {
        int numTickets = getIntent().getIntExtra("num_tickets", 0);
        tickets = new ArrayList<Ticket>();
        for (int a = 0; a < numTickets; a++) {
            String clt = getIntent().getStringExtra("ticket" + a);
            String[] cli = clt.split(",");
            Ticket ticket = new Ticket(cli[0], cli[1], cli[2], cli[3], cli[4], cli[5], cli[6], cli[7], cli[8], cli[9], cli[10], cli[11], cli[12]);
            tickets.add(ticket);
        }
        addCards();
    }

    public void addCards() {
        if (!tickets.isEmpty()) {
            ListView listContratos = findViewById(R.id.lv_historial);
            ListAdapter adapter = new CardsAdapterTickets(this, tickets);
            listContratos.setAdapter(adapter);
        } else {
            System.out.println("no hay tickets");
        }
    }
}
