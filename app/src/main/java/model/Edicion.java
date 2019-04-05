package model;

public class Edicion {

    private String user, id, nombre, cedula, tel1viejo, tel1nuevo, tel2nuevo, tel2viejo, direccionVieja, direccionNueva, fecha, lat, lon, online;
    private int key;

    public Edicion(String user, String id, String nombre, String cedula, String tel1viejo, String tel1nuevo, String tel2viejo, String tel2nuevo, String direccionVieja, String direccionNueva, String fecha, String lat, String lon, String online) {
        this.user = user;
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.tel1viejo = tel1viejo;
        this.tel1nuevo = tel1nuevo;
        this.tel2nuevo = tel2nuevo;
        this.tel2viejo = tel2viejo;
        this.direccionVieja = direccionVieja;
        this.direccionNueva = direccionNueva;
        this.fecha = fecha;
        this.lat = lat;
        this.lon = lon;
        this.online = online;
    }

    public Edicion(String user, String id, String nombre, String cedula, String tel1viejo, String tel1nuevo, String tel2viejo, String tel2nuevo, String direccionVieja, String direccionNueva, String fecha, String lat, String lon, String online, int key) {
        this.user = user;
        this.id = id;
        this.nombre = nombre;
        this.cedula = cedula;
        this.tel1viejo = tel1viejo;
        this.tel1nuevo = tel1nuevo;
        this.tel2nuevo = tel2nuevo;
        this.tel2viejo = tel2viejo;
        this.direccionVieja = direccionVieja;
        this.direccionNueva = direccionNueva;
        this.fecha = fecha;
        this.lat = lat;
        this.lon = lon;
        this.online = online;
        this.key = key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getTel1viejo() {
        return tel1viejo;
    }

    public void setTel1viejo(String tel1viejo) {
        this.tel1viejo = tel1viejo;
    }

    public String getTel1nuevo() {
        return tel1nuevo;
    }

    public void setTel1nuevo(String tel1nuevo) {
        this.tel1nuevo = tel1nuevo;
    }

    public String getTel2nuevo() {
        return tel2nuevo;
    }

    public void setTel2nuevo(String tel2nuevo) {
        this.tel2nuevo = tel2nuevo;
    }

    public String getTel2viejo() {
        return tel2viejo;
    }

    public void setTel2viejo(String tel2viejo) {
        this.tel2viejo = tel2viejo;
    }

    public String getDireccionVieja() {
        return direccionVieja;
    }

    public void setDireccionVieja(String direccionVieja) {
        this.direccionVieja = direccionVieja;
    }

    public String getDireccionNueva() {
        return direccionNueva;
    }

    public void setDireccionNueva(String direccionNueva) {
        this.direccionNueva = direccionNueva;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getOnline() {
        return online;
    }

    public void setOnline(String online) {
        this.online = online;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
