package utils;

import android.provider.BaseColumns;
import android.widget.BaseAdapter;

public class DataBase {

    public static final String TABLE_CLIENTS = "clients";
    public static final String TABLE_RECAUDOS = "recaudos";
    public static final String TABLE_TICKETS = "tickets";
    public static final String TABLE_GESTIONES = "gestiones";
    public static final String TABLE_EDICIONES = "ediciones";

    public static final String SQL_CRATE_TABLE_CLIENTS = "CREATE TABLE "
            + DataBase.TABLE_CLIENTS + " ("
            + "key_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataClientColumns.CLIENT_ID + " TEXT,"
            + DataClientColumns.CLIENT_NAME + " TEXT,"
            + DataClientColumns.CLIENT_TOTAL + " TEXT,"
            + DataClientColumns.CLIENT_VIGENCIA_DESDE + " TEXT,"
            + DataClientColumns.CLIENT_VIGENCIA_HASTA + " TEXT,"
            + DataClientColumns.CLIENT_NUMERO_POLIZA + " TEXT,"
            + DataClientColumns.CLIENT_VALOR_CONTRATO + " TEXT,"
            + DataClientColumns.CLIENT_PERIODICIDAD + " TEXT,"
            + DataClientColumns.CLIENT_TEL1 + " TEXT,"
            + DataClientColumns.CLIENT_TEL2 + " TEXT,"
            + DataClientColumns.CLIENT_DIRECCION + " TEXT)";

    public static final String SQL_CRATE_TABLE_RECAUDOS = "CREATE TABLE "
            + DataBase.TABLE_RECAUDOS + " ("
            + "key_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataRecaudoColumns.RECAUDO_CEDULA_CLIENTE + " TEXT,"
            + DataRecaudoColumns.RECAUDO_NUMERADOR_RC + " TEXT,"
            + DataRecaudoColumns.RECAUDO_ID_RECAUDADOR + " TEXT,"
            + DataRecaudoColumns.RECAUDO_USER_RECAUDADOR + " TEXT,"
            + DataRecaudoColumns.RECAUDO_VALOR + " TEXT,"
            + DataRecaudoColumns.RECAUDO_LATITUD + " TEXT,"
            + DataRecaudoColumns.RECAUDO_LONGITUD + " TEXT,"
            + DataRecaudoColumns.RECAUDO_ONLINE + " INTEGER,"
            + DataRecaudoColumns.RECAUDO_FECHA + " TEXT,"
            + DataRecaudoColumns.RECAUDO_OBSERVACIONES + " TEXT,"
            + DataRecaudoColumns.RECAUDO_FDP + " TEXT)";

    public static final String SQL_CRATE_TABLE_TICKETS = "CREATE TABLE "
            + DataBase.TABLE_TICKETS + " ("
            + "key_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataTicketColumns.TICKET_CABECERA + " TEXT,"
            + DataTicketColumns.TICKET_FECHA + " TEXT,"
            + DataTicketColumns.TICKET_VIGD + " TEXT,"
            + DataTicketColumns.TICKET_VIGH + " TEXT,"
            + DataTicketColumns.TICKET_VALORVIGCONTRATO + " TEXT,"
            + DataTicketColumns.TICKET_PERIODICIDAD + " TEXT,"
            + DataTicketColumns.TICKET_NUMRECIBO + " TEXT,"
            + DataTicketColumns.TICKET_NUMCONTRATO + " TEXT,"
            + DataTicketColumns.TICKET_CCCLIENTE + " TEXT,"
            + DataTicketColumns.TICKET_NOMBCLIENTE + " TEXT,"
            + DataTicketColumns.TICKET_VALORRECAUDADO + " TEXT,"
            + DataTicketColumns.TICKET_FDP + " TEXT,"
            + DataTicketColumns.TICKET_OBSERVACION + " TEXT,"
            + DataTicketColumns.TICKET_NOMBASESOR + " TEXT)";

    public static final String SQL_CRATE_TABLE_GESTIONES = "CREATE TABLE "
            + DataBase.TABLE_GESTIONES + " ("
            + "key_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataGestionColumns.GESTION_ID_USUARIO + " TEXT,"
            + DataGestionColumns.GESTION_DOCUMENTO + " TEXT,"
            + DataGestionColumns.GESTION_TIPO_GESTION + " TEXT,"
            + DataGestionColumns.GESTION_ACUERDO_PAGO + " TEXT,"
            + DataGestionColumns.GESTION_FECHA + " TEXT,"
            + DataGestionColumns.GESTION_FECHA_ACUERDO + " TEXT,"
            + DataGestionColumns.GESTION_VALOR_ACUERDO + " TEXT,"
            + DataGestionColumns.GESTION_DESCRIPCION + " TEXT,"
            + DataGestionColumns.GESTION_RESULTADO_GESTION + " TEXT,"
            + DataGestionColumns.GESTION_LATITUD + " TEXT,"
            + DataGestionColumns.GESTION_LONGITUD + " TEXT,"
            + DataGestionColumns.GESTION_ONLINE + " TEXT)";

    public static final String SQL_CRATE_TABLE_EDICIONES = "CREATE TABLE "
            + DataBase.TABLE_EDICIONES + " ("
            + "key_id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + DataEdicionColumns.EDICION_USER + " TEXT,"
            + DataEdicionColumns.EDICION_ID + " TEXT,"
            + DataEdicionColumns.EDICION_NOMBRE + " TEXT,"
            + DataEdicionColumns.EDICION_CEDULA + " TEXT,"
            + DataEdicionColumns.EDICION_TEL1VIEJO + " TEXT,"
            + DataEdicionColumns.EDICION_TEL1NUEVO + " TEXT,"
            + DataEdicionColumns.EDICION_TEL2VIEJO + " TEXT,"
            + DataEdicionColumns.EDICION_TEL2NUEVO+ " TEXT,"
            + DataEdicionColumns.EDICION_DIRECCION_VIEJA + " TEXT,"
            + DataEdicionColumns.EDICION_DIRECCION_NUEVA + " TEXT,"
            + DataEdicionColumns.EDICION_FECHA + " TEXT,"
            + DataEdicionColumns.EDICION_LATITUD + " TEXT,"
            + DataEdicionColumns.EDICION_LONGITUD + " TEXT,"
            + DataEdicionColumns.EDICION_ONLINE + " TEXT)";

    public static abstract class DataEdicionColumns implements BaseColumns {
        public static final String EDICION_USER = "user";
        public static final String EDICION_ID = "id";
        public static final String EDICION_NOMBRE = "nombre";
        public static final String EDICION_CEDULA = "cedula";
        public static final String EDICION_TEL1VIEJO = "tel1viejo";
        public static final String EDICION_TEL1NUEVO = "tel1nuevo";
        public static final String EDICION_TEL2VIEJO = "tel2viejo";
        public static final String EDICION_TEL2NUEVO = "tel2nuevo";
        public static final String EDICION_DIRECCION_VIEJA = "direccionVieja";
        public static final String EDICION_DIRECCION_NUEVA = "direccionNueva";
        public static final String EDICION_FECHA = "fecha";
        public static final String EDICION_LATITUD = "latitud";
        public static final String EDICION_LONGITUD = "longitud";
        public static final String EDICION_ONLINE = "online";
    }

    public static abstract class DataGestionColumns implements BaseColumns {
        public static final String GESTION_TIPO_GESTION = "tipoGestion";
        public static final String GESTION_ACUERDO_PAGO = "acuerdoPago";
        public static final String GESTION_FECHA = "fecha";
        public static final String GESTION_FECHA_ACUERDO = "fechaAcuerdo";
        public static final String GESTION_VALOR_ACUERDO = "valorAcuerdo";
        public static final String GESTION_DESCRIPCION = "descripcion";
        public static final String GESTION_RESULTADO_GESTION = "resultadoGestion";
        public static final String GESTION_LATITUD = "latitud";
        public static final String GESTION_LONGITUD = "longitud";
        public static final String GESTION_ONLINE = "online";
        public static final String GESTION_ID_USUARIO = "idUsuario";
        public static final String GESTION_DOCUMENTO = "documento";
    }

    public static abstract class DataTicketColumns implements BaseColumns {
        public static final String TICKET_CABECERA = "cabecera";
        public static final String TICKET_FECHA = "fecha";
        public static final String TICKET_VIGD = "vigD";
        public static final String TICKET_VIGH = "vigH";
        public static final String TICKET_VALORVIGCONTRATO = "valorVigContrato";
        public static final String TICKET_PERIODICIDAD = "periodicidad";
        public static final String TICKET_NUMRECIBO = "numRecibo";
        public static final String TICKET_NUMCONTRATO = "numContrato";
        public static final String TICKET_CCCLIENTE = "ccCliente";
        public static final String TICKET_NOMBCLIENTE = "nombCliente";
        public static final String TICKET_VALORRECAUDADO = "valorRecaudado";
        public static final String TICKET_OBSERVACION = "observacion";
        public static final String TICKET_NOMBASESOR = "nombAsesor";
        public static final String TICKET_FDP = "formaDePago";
    }

    public static abstract class DataClientColumns implements BaseColumns {
        public static final String CLIENT_NAME = "nombre";
        public static final String CLIENT_ID = "id";
        public static final String CLIENT_TOTAL = "total";
        public static final String CLIENT_VIGENCIA_DESDE = "vigencia_desde";
        public static final String CLIENT_VIGENCIA_HASTA = "vigencia_hasta";
        public static final String CLIENT_NUMERO_POLIZA = "numero_poliza";
        public static final String CLIENT_VALOR_CONTRATO = "valor_contrato";
        public static final String CLIENT_PERIODICIDAD = "periodicidad";
        public static final String CLIENT_TEL1 = "telefono1";
        public static final String CLIENT_TEL2 = "telefono2";
        public static final String CLIENT_DIRECCION = "direccion";
    }

    public static abstract class DataRecaudoColumns implements BaseColumns {
        public static final String RECAUDO_USER_RECAUDADOR = "user_recaudador";
        public static final String RECAUDO_ID_RECAUDADOR = "id_recaudador";
        public static final String RECAUDO_CEDULA_CLIENTE = "cedula_cliente";
        public static final String RECAUDO_VALOR = "valor";
        public static final String RECAUDO_LATITUD = "latitud";
        public static final String RECAUDO_LONGITUD = "longitud";
        public static final String RECAUDO_ONLINE = "online";
        public static final String RECAUDO_FECHA = "fecha";
        public static final String RECAUDO_NUMERADOR_RC = "numerador_rc";
        public static final String RECAUDO_OBSERVACIONES = "observaciones";
        public static final String RECAUDO_FDP = "forma_de_pago";
    }

}