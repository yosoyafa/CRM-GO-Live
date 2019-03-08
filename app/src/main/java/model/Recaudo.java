package model;

public class Recaudo {
    private String user_recaudador, id_recaudador, cedula_cliente, valor, latitud, longitud, fecha, numerdaor_offline, observaciones, ticket;
    private int online;

    public Recaudo(String user_recaudador, String id_recaudador, String cedula_cliente, String valor, String latitud, String longitud, int online, String fecha, String numerador_offline, String observaciones) {
        this.user_recaudador = user_recaudador;
        this.id_recaudador = id_recaudador;
        this.cedula_cliente = cedula_cliente;
        this.valor = valor;
        this.latitud = latitud;
        this.longitud = longitud;
        this.online = online;
        this.fecha = fecha;
        this.numerdaor_offline = numerador_offline;
        this.observaciones = observaciones;
    }

    public String getUser_recaudador() {
        return user_recaudador;
    }

    public void setUser_recaudador(String user_recaudador) {
        this.user_recaudador = user_recaudador;
    }

    public String getId_recaudador() {
        return id_recaudador;
    }

    public void setId_recaudador(String id_recaudador) {
        this.id_recaudador = id_recaudador;
    }

    public String getCedula_cliente() {
        return cedula_cliente;
    }

    public void setCedula_cliente(String cedula_cliente) {
        this.cedula_cliente = cedula_cliente;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public int isOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String toString(){
        return  "user_recaudador: "+user_recaudador+
                "\nid_recaudador: "+id_recaudador+
                "\ncedula_cliente: "+cedula_cliente+
                "\nvalor: "+valor+
                "\nlatitud: "+latitud+
                "\nlongitud: "+longitud+
                "\nonline: "+online+
                "\nfecha: "+fecha;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNumerdaor_offline() {
        return numerdaor_offline;
    }

    public void setNumerdaor_offline(String numerdaor_offline) {
        this.numerdaor_offline = numerdaor_offline;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
