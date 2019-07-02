package integracionip.impresoras;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Handler;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import model.Client;
import model.Ticket;

public class CardsAdapterTickets extends BaseAdapter {

    private ArrayList<Ticket> tickets;
    private Activity context;
    private SharedPreferences sharedPreferences;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private BluetoothDevice bluetoothDevice;
    private OutputStream outputStream;
    private InputStream inputStream;
    private Thread thread;
    private byte[] readBuffer;
    private int readBufferPosition;
    volatile boolean stopWorker;

    public CardsAdapterTickets() {

    }

    public CardsAdapterTickets(Activity context, ArrayList<Ticket> tickets) {
        this.context = context;
        this.tickets = tickets;
        sharedPreferences = context.getSharedPreferences("Session", Context.MODE_PRIVATE);
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
        view = LayoutInflater.from(context).inflate(R.layout.card_ticket, viewGroup, false);

        final Ticket ticket = tickets.get(i);

        Button button_pagar = view.findViewById(R.id.button_goto_contrato);
        button_pagar.setText("Imprimir");
        button_pagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run()
                    {
                        try {
                            int in = printTicket(ticket);
                            if(in == 0){
                                context.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(context, "Problemas con conexión a la impresora",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });
                            }
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        TextView contrato = view.findViewById(R.id.tvContrato);
        contrato.setText("Contrato: " + ticket.getNumContrato());


        TextView nombre = view.findViewById(R.id.tvNombre);
        nombre.setText(ticket.getNombCliente());
        nombre.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 17);
        nombre.setTypeface(null, Typeface.BOLD);

        TextView cedula = view.findViewById(R.id.tvCedula);
        cedula.setText("CC: " + ticket.getCcCliente());

        TextView money = view.findViewById(R.id.tvDinero);
        money.setText("$" + ticket.getValorRecaudado());

        TextView observ = view.findViewById(R.id.tvObservaciones);
        String obv = ticket.getObservacion();
        if(obv.equals("-")){
            observ.setText("Sin observaciones");
        }else{
            observ.setText(ticket.getObservacion());
        }
        observ.setTypeface(null, Typeface.ITALIC);

        TextView tvNumRecibo = view.findViewById(R.id.tvNumRecibo);
        tvNumRecibo.setText(ticket.getNumRecibo());

        return view;
    }

    private int printTicket(Ticket ticket) {
        int out;
        try {
            findBluetoothDevice();
            openBluetoothPrinter();
            printData(ticket.toString());
            disconnectBT();
            out = 1;
        }catch(Exception e){
            e.printStackTrace();
            out = 0;
        }
        return out;
    }

    public void findBluetoothDevice(){

        try{

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if(bluetoothAdapter==null){
                Toast.makeText(context, "No se encontro bluetoothadapter",
                        Toast.LENGTH_LONG).show();
            }
            if(bluetoothAdapter.isEnabled()){
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivityForResult(enableBT,0);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if(pairedDevice.size()>0){
                for(BluetoothDevice pairedDev:pairedDevice){

                    // My Bluetoth printer name is BTP_F09F1A
                    if(pairedDev.getName().equals(sharedPreferences.getString("impresora", "-"))){
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

    void openBluetoothPrinter() {
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
                                        final String data = new String(encodedByte, StandardCharsets.US_ASCII);
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
    void printData(String msg) {
        try{
            if(outputStream != null) {
                msg += "\n";
                //msg = CHAR_ESC+msg;
                outputStream.write(msg.getBytes(StandardCharsets.US_ASCII));
                System.out.println("Printing Text...");
            }else{
                Toast.makeText(context, "Problemas con conexión a la impresora",
                        Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            Toast.makeText(context, "Problemas con conexión a la impresora",
                    Toast.LENGTH_LONG).show();
        }
    }

    // Disconnect Printer //
    void disconnectBT() {
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
}
