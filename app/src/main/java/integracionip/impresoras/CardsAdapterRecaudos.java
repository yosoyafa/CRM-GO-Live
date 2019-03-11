package integracionip.impresoras;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import model.Client;

public class CardsAdapterRecaudos extends BaseAdapter {

    private ArrayList<Client> clients;
    private Activity context;

    public CardsAdapterRecaudos(){

    }

    public CardsAdapterRecaudos(Activity context, ArrayList<Client> clients) {
        this.context = context;
        this.clients = clients;
    }

    @Override
    public int getCount() {
        return clients.size();
    }

    @Override
    public Object getItem(int i) {
        return clients.get(i);
    }

    @Override
    public long getItemId(int i) {
        return clients.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.card_contrato,viewGroup,false);

        final Client client = clients.get(i);

        Button button_pagar = view.findViewById(R.id.button_goto_contrato);
        button_pagar.setText("Pagar");
        button_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToRecaudoDetails(client);
            }
        });

        TextView contrato = view.findViewById(R.id.tvContrato);
        contrato.setText("Contrato: " + client.getNumeroPoliza());
        contrato.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        contrato.setTypeface(null, Typeface.BOLD);

        TextView nombre = view.findViewById(R.id.tvNombre);
        nombre.setText(client.getName());

        TextView cedula = view.findViewById(R.id.tvCedula);
        cedula.setText("CC: " + client.getId());

        TextView money = view.findViewById(R.id.tvDinero);
        money.setText("$" + client.getTotal());


        return view;
    }

    private void goToRecaudoDetails(Client client) {
        Intent detailsActivity = new Intent(context, RecaudoDetailsActivity.class);
        //System.out.println("---------------------------\nCliente que pasa a pagar:\n"+client.toStringRaw());
        detailsActivity.putExtra("client", client.toStringRaw());
        context.startActivity(detailsActivity);
    }
}
