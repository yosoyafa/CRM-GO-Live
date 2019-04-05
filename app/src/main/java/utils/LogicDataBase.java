package utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;

import java.util.ArrayList;

import model.Client;
import model.Edicion;
import model.Gestion;
import model.Recaudo;
import model.Ticket;

public class LogicDataBase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "BaseDeDatos.db";
    private static final int DATABASE_VERSION = 11;

    public LogicDataBase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBase.SQL_CRATE_TABLE_CLIENTS);
        db.execSQL(DataBase.SQL_CRATE_TABLE_RECAUDOS);
        db.execSQL(DataBase.SQL_CRATE_TABLE_TICKETS);
        db.execSQL(DataBase.SQL_CRATE_TABLE_GESTIONES);
        db.execSQL(DataBase.SQL_CRATE_TABLE_EDICIONES);
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
            values.put(DataBase.DataClientColumns.CLIENT_TEL1, client.getTelefono1());
            values.put(DataBase.DataClientColumns.CLIENT_TEL2, client.getTelefono2());
            values.put(DataBase.DataClientColumns.CLIENT_DIRECCION, client.getDireccion());
            db.insert(DataBase.TABLE_CLIENTS, null, values);
            db.close();
        }
    }

    public void addGestion(Gestion gestion){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(DataBase.DataGestionColumns.GESTION_TIPO_GESTION, gestion.getTipoGestion());
            values.put(DataBase.DataGestionColumns.GESTION_ID_USUARIO, gestion.getIdUsuario());
            values.put(DataBase.DataGestionColumns.GESTION_DOCUMENTO, gestion.getDocumento());
            values.put(DataBase.DataGestionColumns.GESTION_ACUERDO_PAGO, gestion.getAcuerdoPago()+"");
            values.put(DataBase.DataGestionColumns.GESTION_FECHA, gestion.getFecha());
            values.put(DataBase.DataGestionColumns.GESTION_FECHA_ACUERDO, gestion.getFechaAcuerdo());
            values.put(DataBase.DataGestionColumns.GESTION_VALOR_ACUERDO, gestion.getValorAcuerdo());
            values.put(DataBase.DataGestionColumns.GESTION_DESCRIPCION, gestion.getDescripcion());
            values.put(DataBase.DataGestionColumns.GESTION_RESULTADO_GESTION, gestion.getResultadoGestion());
            values.put(DataBase.DataGestionColumns.GESTION_LATITUD, gestion.getLatitud());
            values.put(DataBase.DataGestionColumns.GESTION_LONGITUD, gestion.getLongitud());
            values.put(DataBase.DataGestionColumns.GESTION_ONLINE, gestion.getOnline()+"");
            db.insert(DataBase.TABLE_GESTIONES, null, values);
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
            values.put(DataBase.DataRecaudoColumns.RECAUDO_FDP, recuado.getForma_de_pago());
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
            values.put(DataBase.DataTicketColumns.TICKET_FDP, ticket.getFormaDePago());
            values.put(DataBase.DataTicketColumns.TICKET_OBSERVACION, ticket.getObservacion());
            values.put(DataBase.DataTicketColumns.TICKET_NOMBASESOR, ticket.getNombAsesor());
            db.insert(DataBase.TABLE_TICKETS, null, values);
            db.close();
        }
    }

    public void addEdicion(Edicion edicion){
        SQLiteDatabase db = getWritableDatabase();
        if(db != null){
            ContentValues values = new ContentValues();
            values.put(DataBase.DataEdicionColumns.EDICION_USER, edicion.getUser());
            values.put(DataBase.DataEdicionColumns.EDICION_ID, edicion.getId());
            values.put(DataBase.DataEdicionColumns.EDICION_NOMBRE, edicion.getNombre());
            values.put(DataBase.DataEdicionColumns.EDICION_CEDULA, edicion.getCedula());
            values.put(DataBase.DataEdicionColumns.EDICION_TEL1VIEJO, edicion.getTel1viejo());
            values.put(DataBase.DataEdicionColumns.EDICION_TEL1NUEVO, edicion.getTel1nuevo());
            values.put(DataBase.DataEdicionColumns.EDICION_TEL2VIEJO, edicion.getTel2viejo());
            values.put(DataBase.DataEdicionColumns.EDICION_TEL2NUEVO, edicion.getTel2nuevo());
            values.put(DataBase.DataEdicionColumns.EDICION_DIRECCION_VIEJA, edicion.getDireccionVieja());
            values.put(DataBase.DataEdicionColumns.EDICION_DIRECCION_NUEVA, edicion.getDireccionNueva());
            values.put(DataBase.DataEdicionColumns.EDICION_FECHA, edicion.getFecha());
            values.put(DataBase.DataEdicionColumns.EDICION_LATITUD, edicion.getLat());
            values.put(DataBase.DataEdicionColumns.EDICION_LONGITUD, edicion.getLon());
            values.put(DataBase.DataEdicionColumns.EDICION_ONLINE, edicion.getOnline());
            db.insert(DataBase.TABLE_EDICIONES, null, values);
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
                DataBase.DataClientColumns.CLIENT_PERIODICIDAD,
                DataBase.DataClientColumns.CLIENT_TEL1,
                DataBase.DataClientColumns.CLIENT_TEL2,
                DataBase.DataClientColumns.CLIENT_DIRECCION
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
                    String tel1 = cursor.getString(8);
                    String tel2 = cursor.getString(9);
                    String direccion = cursor.getString(10);
                    Client cl = new Client(name, ced, total, vigDesde, vigHasta, numPoliza, valorContrato, periodicidad, tel1, tel2, direccion);
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
                DataBase.DataRecaudoColumns.RECAUDO_OBSERVACIONES,
                DataBase.DataRecaudoColumns.RECAUDO_FDP
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
                    String observaciones = cursor.getString(9);
                    String fdp = cursor.getString(10);
                    Recaudo r = new Recaudo(ced,id_rec,user_rec,valor,lati,longi,online,fecha,rc,observaciones,fdp);
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
                DataBase.DataTicketColumns.TICKET_FDP,
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
                    String p13 = cursor.getString(13);

                    Ticket tick = new Ticket(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13);
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

    public ArrayList<Edicion> selectEdicionesOffline(){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        ArrayList<Edicion> ediciones2sync = new ArrayList<Edicion>();
        try {
            Cursor cursor = db.rawQuery("select * from "+DataBase.TABLE_EDICIONES+" where online = 0", null);
            ediciones2sync = new ArrayList<Edicion>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String p1 = cursor.getString(cursor.getColumnIndex("user"));
                    String p2 = cursor.getString(cursor.getColumnIndex("id"));
                    String p3 = cursor.getString(cursor.getColumnIndex("nombre"));
                    String p4 = cursor.getString(cursor.getColumnIndex("cedula"));
                    String p5 = cursor.getString(cursor.getColumnIndex("tel1viejo"));
                    String p6 = cursor.getString(cursor.getColumnIndex("tel1nuevo"));
                    String p7 = cursor.getString(cursor.getColumnIndex("tel2viejo"));
                    String p8 = cursor.getString(cursor.getColumnIndex("tel2nuevo"));
                    String p9 = cursor.getString(cursor.getColumnIndex("direccionVieja"));
                    String p10 = cursor.getString(cursor.getColumnIndex("direccionNueva"));
                    String p11 = cursor.getString(cursor.getColumnIndex("fecha"));
                    String p12 = cursor.getString(cursor.getColumnIndex("latitud"));
                    String p13 = cursor.getString(cursor.getColumnIndex("longitud"));
                    String p14 = cursor.getString(cursor.getColumnIndex("online"));
                    int key = cursor.getInt(cursor.getColumnIndex("key_id"));
                    Edicion edicion = new Edicion(p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13,p14,key);
                    ediciones2sync.add(edicion);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return ediciones2sync;
    }

    public ArrayList<Ticket> selectTickets(){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        ArrayList<Ticket> tickets = new ArrayList<Ticket>();
        try {
            Cursor cursor = db.rawQuery("select * from "+DataBase.TABLE_TICKETS, null);
            tickets = new ArrayList<Ticket>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
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
                    String p13 = cursor.getString(13);

                    Ticket tick = new Ticket(p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,p10,p11,p12,p13);
                    tickets.add(tick);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return tickets;
    }

    public ArrayList<Recaudo> selectRecaudosOffline(){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        ArrayList<Recaudo> recaudos2sync = new ArrayList<Recaudo>();
        try {
            Cursor cursor = db.rawQuery("select * from "+DataBase.TABLE_RECAUDOS+" where online = 0", null);
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
                    int online = cursor.getInt(cursor.getColumnIndex("online"));
                    String fdp = cursor.getString(cursor.getColumnIndex("forma_de_pago"));
                    int key = cursor.getInt(cursor.getColumnIndex("key_id"));
                    Recaudo recaudo = new Recaudo(user_recaudador,id_recaudador,cedula_cliente,valor,lat,lon,online,fecha,numerador_rc, observaciones, fdp, key);
                    recaudos2sync.add(recaudo);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return recaudos2sync;
    }

    public ArrayList<Gestion> selectGestionesOffline(){
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor cursor = null;
        ArrayList<Gestion> gestiones2sync = new ArrayList<Gestion>();
        try {
            Cursor cursor = db.rawQuery("select * from "+DataBase.TABLE_GESTIONES+" where online = 0", null);
            gestiones2sync = new ArrayList<Gestion>();
            if (cursor.moveToFirst()) {
                while (!cursor.isAfterLast()) {
                    String tipoGestion = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_TIPO_GESTION));
                    String idUsuario = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_ID_USUARIO));
                    String documento = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_DOCUMENTO));
                    String acuerdoPago = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_ACUERDO_PAGO));
                    String fecha = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_FECHA));
                    String fechaAcuerdo = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_FECHA_ACUERDO));
                    String valorAcuerdo = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_VALOR_ACUERDO));
                    String descripcion = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_DESCRIPCION));
                    String resultadoGestion = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_RESULTADO_GESTION));
                    String latitud = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_LATITUD));
                    String longitud = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_LONGITUD));
                    String online = cursor.getString(cursor.getColumnIndex(DataBase.DataGestionColumns.GESTION_ONLINE));
                    int key = cursor.getInt(cursor.getColumnIndex("key_id"));
                    Gestion gestion = new Gestion(tipoGestion,idUsuario,documento,Integer.parseInt(acuerdoPago),fecha,fechaAcuerdo,valorAcuerdo, descripcion,resultadoGestion,latitud,longitud,Integer.parseInt(online),key);
                    gestiones2sync.add(gestion);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return gestiones2sync;
    }

    public void resetClients() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_CLIENTS);
    }

    public void resetRecaudos() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_RECAUDOS);
    }

    public void resetGestiones() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_GESTIONES);
    }

    public void resetEdits() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("delete from "+ DataBase.TABLE_EDICIONES);
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

    public void updateRecaudo(Recaudo rec){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBase.DataRecaudoColumns.RECAUDO_ONLINE,1);
        int key = rec.getKey();
        db.update(DataBase.TABLE_RECAUDOS, cv, "key_id = ?", new String[]{key+""});
    }

    public void updateEdicion(Edicion edicion){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBase.DataEdicionColumns.EDICION_ONLINE,"1");
        int key = edicion.getKey();
        db.update(DataBase.TABLE_EDICIONES, cv, "key_id = ?", new String[]{key+""});
    }

    public void updateGestion(Gestion gestion){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(DataBase.DataGestionColumns.GESTION_ONLINE,1);
        int key = gestion.getKey();
        db.update(DataBase.TABLE_GESTIONES, cv, "key_id = ?", new String[]{key+""});
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
                tableString += "\n---------------\n";

            } while (allRows.moveToNext());
        }

        return "----"+tableName+"----\n"+tableString;
    }

    public void deleteDups(String table) {
        if(table.equals("clients")) {
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("delete from "+DataBase.TABLE_CLIENTS+" where rowid not in (select min(rowid) from "+DataBase.TABLE_CLIENTS+" group by "+DataBase.DataClientColumns.CLIENT_NUMERO_POLIZA+");");
        }
    }

    public int limpiarGestiones(){
        SQLiteDatabase db = getWritableDatabase();
        String where1 = DataBase.DataGestionColumns.GESTION_DOCUMENTO + " IS NULL";
        int noed1 = db.delete(DataBase.TABLE_GESTIONES, where1, null);

        String where2 = DataBase.DataGestionColumns.GESTION_ID_USUARIO + " IS NULL";
        int noed2 = db.delete(DataBase.TABLE_GESTIONES, where2, null);

        return noed1+noed2;
    }

}