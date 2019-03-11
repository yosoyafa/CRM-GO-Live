package integracionip.impresoras;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import model.Client;
import model.Ticket;

public class CardsAdapterTickets extends BaseAdapter {

    private ArrayList<Ticket> tickets;
    private Activity context;

    public CardsAdapterTickets() {

    }

    public CardsAdapterTickets(Activity context, ArrayList<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
    }

    @Override
    public int getCount() {
        return tickets.size();
    }

    @Override
    public Object getItem(int i) {
        return tickets.get(i);
    }

    @Override
    public long getItemId(int i) {
        return tickets.get(i).hashCode();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.card_contrato, viewGroup, false);

        final Ticket ticket = tickets.get(i);

        Button button_pagar = view.findViewById(R.id.button_goto_contrato);
        button_pagar.setText("Imprimir");
        button_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //goToRecaudoDetails(ticket);
            }
        });

        TextView contrato = view.findViewById(R.id.tvContrato);
        contrato.setText("Numero de contrato: " + ticket.getNumContrato());


        TextView nombre = view.findViewById(R.id.tvNombre);
        nombre.setText("Nombre: " + ticket.getNombCliente());
        nombre.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        nombre.setTypeface(null, Typeface.BOLD);

        TextView cedula = view.findViewById(R.id.tvCedula);
        cedula.setText("CC: " + ticket.getCcCliente());

        TextView money = view.findViewById(R.id.tvDinero);
        money.setText("$" + ticket.getValorRecaudado());

        return view;
    }

    private void goToRecaudoDetails(Client client) {
        Intent detailsActivity = new Intent(context, RecaudoDetailsActivity.class);
        //System.out.println("---------------------------\nCliente que pasa a pagar:\n"+client.toStringRaw());
        detailsActivity.putExtra("client", client.toStringRaw());
        context.startActivity(detailsActivity);
    }
}
