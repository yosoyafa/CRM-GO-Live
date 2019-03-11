package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import model.Client;
import model.Recaudo;
import model.Ticket;

public class LogicDataBase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "BaseDeDatos.db";
    private static final int DATABASE_VERSION = 1;

    public LogicDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase.SQL_CRATE_TABLE_TICKETS);
        db.execSQL(DataBase.SQL_CRATE_TABLE_CLIENTS);
        db.execSQL(DataBase.SQL_CRATE_TABLE_RECAUDOS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addClient(Client client){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(DataBase.DataClientColumns.CLIENT_ID, client.getId());
            values.put(DataBase.DataClientColumns.CLIENT_NAME, client.getName());
            values.put(DataBase.DataClientColumns.CLIENT_TOTAL, client.getTotal());
            values.put(DataBase.DataClientColumns.CLIENT_VIGENCIA_DESDE, client.getVigenciaDesde());
            values.put(DataBase.DataClientColumns.CLIENT_VIGENCIA_HASTA, client.getVigenciaHasta());
            values.put(DataBase.DataClientColumns.CLIENT_NUMERO_POLIZA, client.getNumeroPoliza());
            values.put(DataBase.DataClientColumns.CLIENT_VALOR_CONTRATO, client.getValorContrato());
            values.put(DataBase.DataClientColumns.CLIENT_PERIODICIDAD, client.getPeriodicidad());
            db.insert(DataBase.TABLE_CLIENTS, null, values);
            db.close();
        }
    }

    public void addRecaudo(Recaudo recuado){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(DataBase.DataRecaudoColumns.RECAUDO_CEDULA_CLIENTE, recuado.getCedula_cliente());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_ID_RECAUDADOR, recuado.getId_recaudador());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_USER_RECAUDADOR, recuado.getUser_recaudador());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_VALOR, recuado.getValor());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_LATITUD, recuado.getLatitud());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_LONGITUD, recuado.getLongitud());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_ONLINE, recuado.isOnline());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_FECHA, recuado.getFecha());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_NUMERADOR_RC, recuado.getNumerdaor_offline());
            values.put(DataBase.DataRecaudoColumns.RECAUDO_OBSERVACIONES, recuado.getObservaciones());
            db.insert(DataBase.TABLE_RECAUDOS, null, values);
            db.close();
        }
    }

    public void addTicket(Ticket ticket){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(DataBase.DataTicketColumns.TICKET_CABECERA, ticket.getCabecera());
            values.put(DataBase.DataTicketColumns.TICKET_FECHA, ticket.getFecha());
            values.put(DataBase.DataTicketColumns.TICKET_VIGD, ticket.getVigD());
            values.put(DataBase.DataTicketColumns.TICKET_VIGH, ticket.getVigH());
            values.put(DataBase.DataTicketColumns.TICKET_VALORVIGCONTRATO, ticket.getValorVigContrato());
            values.put(DataBase.DataTicketColumns.TICKET_PERIODICIDAD, ticket.getPeriodicidad());
            values.put(DataBase.DataTicketColumns.TICKET_NUMRECIBO, ticket.getNumRecibo());
            values.put(DataBase.DataTicketColumns.TICKET_NUMCONTRATO, ticket.getNumContrato());
            values.put(DataBase.DataTicketColumns.TICKET_CCCLIENTE, ticket.getCcCliente());
            values.put(DataBase.DataTicketColumns.TICKET_NOMBCLIENTE, ticket.getNombCliente());
            values.put(DataBase.DataTicketColumns.TICKET_VALORRECAUDADO, ticket.getValorRecaudado());
            values.put(DataBase.DataTicketColumns.TICKET_OBSERVACION, ticket.getObservacion());
            values.put(DataBase.DataTicketColumns.TICKET_NOMBASESOR, ticket.getNombAsesor());
            db.insert(DataBase.TABLE_TICKETS, null, values);
            db.close();
        }
    }

    public ArrayList<Client> searchClient(String id){
        ArrayList<Client> clientes = new ArrayList<Client>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] parameters = {id};
        String[] fields = {
                DataBase.DataClientColumns.CLIENT_ID,
                DataBase.DataClientColumns.CLIENT_NAME,
                DataBase.DataClientColumns.CLIENT_TOTAL,
                DataBase.DataClientColumns.CLIENT_VIGENCIA_DESDE,
                DataBase.DataClientColumns.CLIENT_VIGENCIA_HASTA,
                DataBase.DataClientColumns.CLIENT_NUMERO_POLIZA,
                DataBase.DataClientColumns.CLIENT_VALOR_CONTRATO,
                DataBase.DataClientColumns.CLIENT_PERIODICIDAD
        };

        try {
            Cursor cursor = db.query(DataBase.TABLE_CLIENTS, fields, fields[0] + "=?", parameters, null, null, null);
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    String ced = cursor.getString(0);
                    String name = cursor.getString(1);
                    String total = cursor.getString(2);
                    String vigDesde = cursor.getString(3);
                    String vigHasta = cursor.getString(4);
                    String numPoliza = cursor.getString(5);
                    String valorContrato = cursor.getString(6);
                    String periodicidad = cursor.getString(7);
                    Client cl = new Client(name, ced, total, vigDesde, vigHasta, numPoliza, valorContrato, periodicidad);
                    clientes.add(cl);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return clientes;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Recaudo> searchRecaudo(String cedula){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Recaudo> recaudos = new ArrayList<Recaudo>();
        String[] parameters = {cedula};
        String[] fields = {
                DataBase.DataRecaudoColumns.RECAUDO_CEDULA_CLIENTE,
                DataBase.DataRecaudoColumns.RECAUDO_NUMERADOR_RC,
                DataBase.DataRecaudoColumns.RECAUDO_ID_RECAUDADOR,
                DataBase.DataRecaudoColumns.RECAUDO_USER_RECAUDADOR,
                DataBase.DataRecaudoColumns.RECAUDO_VALOR,
                DataBase.DataRecaudoColumns.RECAUDO_LATITUD,
                DataBase.DataRecaudoColumns.RECAUDO_LONGITUD,
                DataBase.DataRecaudoColumns.RECAUDO_ONLINE,
                DataBase.DataRecaudoColumns.RECAUDO_FECHA,
                DataBase.DataRecaudoColumns.RECAUDO_OBSERVACIONES
        };

        try {
            Cursor cursor = db.query(DataBase.TABLE_RECAUDOS, fields, fields[0] + "=?", parameters, null, null, null);
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    String ced = cursor.getString(0);
                    String rc = cursor.getString(1);
                    String id_rec = cursor.getString(2);
                    String user_rec = cursor.getString(3);
                    String valor = cursor.getString(4);
                    String lati = cursor.getString(5);
                    String longi = cursor.getString(6);
                    String online_str = cursor.getString(7);
                    int online = 0;
                    try {
                        online = Integer.parseInt(online_str);
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                    String fecha = cursor.getString(8);
                    String observaciones = cursor.getString(6);
                    Recaudo r = new Recaudo(ced,id_rec,user_rec,valor,lati,longi,online,fecha,rc,observaciones);
                    recaudos.add(r);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return recaudos;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Ticket> searchTickets(String cedula){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        String[] parameters = {cedula};
        String[] fields = {
                DataBase.DataTicketColumns.TICKET_CABECERA,
                DataBase.DataTicketColumns.TICKET_FECHA,
                DataBase.DataTicketColumns.TICKET_VIGD,
                DataBase.DataTicketColumns.TICKET_VIGH,
                DataBase.DataTicketColumns.TICKET_VALORVIGCONTRATO,
                DataBase.DataTicketColumns.TICKET_PERIODICIDAD,
                DataBase.DataTicketColumns.TICKET_NUMRECIBO,
                DataBase.DataTicketColumns.TICKET_NUMCONTRATO,
                DataBase.DataTicketColumns.TICKET_CCCLIENTE,
                DataBase.DataTicketColumns.TICKET_NOMBCLIENTE,
                DataBase.DataTicketColumns.TICKET_VALORRECAUDADO,
                DataBase.DataTicketColumns.TICKET_OBSERVACION,
                DataBase.DataTicketColumns.TICKET_NOMBASESOR
        };

        try {
            Cursor cursor = db.query(DataBase.TABLE_TICKETS, fields, fields[8] + "=?", parameters, null, null, null);
            if(cursor.moveToFirst()){
                while(!cursor.isAfterLast()){
                    String p0 = cursor.getString(0);
                    String p1 = cursor.getString(1);
                    String p2 = cursor.getString(2);
                    String p3 = cursor.getString(3);
                    String p4 = cursor.getString(4);
                    String p5 = cursor.getString(5);
                    String p6 = cursor.getString(6);
                    String p7 = cursor.getString(7);
                    String p8 = cursor.getString(8);
                    String p9 = cursor.getString(9);
                    String p10 = cursor.getString(10);
                    String p11 = cursor.getString(11);
                    String p12 = cursor.getString(12);

                    Ticket tick = new Ticket(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12);
                    tickets.add(tick);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            return tickets;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<Recaudo> selectRecaudos(){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        ArrayList<Recaudo> recaudos2sync = new ArrayList<Recaudo>();
        try {
            Cursor cursor = db.rawQuery("select * from "+DataBase.TABLE_RECAUDOS, null);
            recaudos2sync = new ArrayList<Recaudo>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String user_recaudador = cursor.getString(cursor.getColumnIndex("user_recaudador"));
                    String id_recaudador= cursor.getString(cursor.getColumnIndex("id_recaudador"));
                    String cedula_cliente= cursor.getString(cursor.getColumnIndex("cedula_cliente"));
                    String valor= cursor.getString(cursor.getColumnIndex("valor"));
                    String lat= cursor.getString(cursor.getColumnIndex("latitud"));
                    String lon= cursor.getString(cursor.getColumnIndex("longitud"));
                    String fecha= cursor.getString(cursor.getColumnIndex("fecha"));
                    String numerador_rc = cursor.getString(cursor.getColumnIndex("numerador_rc"));
                    String observaciones = cursor.getString(cursor.getColumnIndex("observaciones"));
                    Recaudo recaudo = new Recaudo(user_recaudador,id_recaudador,cedula_cliente,valor,lat,lon,0,fecha,numerador_rc, observaciones);
                    recaudos2sync.add(recaudo);
                    cursor.moveToNext();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return recaudos2sync;
    }

    public void resetClients() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_CLIENTS);
    }

    public void resetRecaudos() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_RECAUDOS);
    }

    public void resetTickets() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_TICKETS);
    }

    public void deleteRecaudo(Recaudo rec) {
        SQLiteDatabase db = getWritableDatabase();
        String num = rec.getNumerdaor_offline();
        db.delete(DataBase.TABLE_RECAUDOS, DataBase.DataRecaudoColumns.RECAUDO_NUMERADOR_RC + "=" + num, null);
    }

    public String getTableAsString(String tableName) {
        SQLiteDatabase db = getReadableDatabase();
        String tableString = String.format("Table %s:\n", tableName);
        Cursor allRows = db.rawQuery("SELECT * FROM " + tableName, null);
        if (allRows.moveToFirst()) {
            String[] columnNames = allRows.getColumnNames();
            do {
                for (String name : columnNames) {
                    tableString += String.format("%s: %s\n", name,
                            allRows.getString(allRows.getColumnIndex(name)));
                }
                tableString += "\n";

            } while (allRows.moveToNext());
        }

        return tableString;
    }
}