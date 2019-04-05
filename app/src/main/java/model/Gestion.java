package model;

public class Gestion {

    private String tipoGestion, fecha, fechaAcuerdo, valorAcuerdo, descripcion, resultadoGestion, latitud, longitud, idUsuario, documento;
    private int acuerdoPago, online, key;

    public Gestion(String tipoGestion, String idUsuario, String documento, int acuerdoPago, String fecha, String fechaAcuerdo, String valorAcuerdo, String descripcion, String resultadoGestion, String latitud, String longitud, int online) {
        this.idUsuario = idUsuario;
        this.documento = documento;
        this.tipoGestion = tipoGestion;
        this.fecha = fecha;
        this.fechaAcuerdo = fechaAcuerdo;
        this.valorAcuerdo = valorAcuerdo;
        this.descripcion = descripcion;
        this.resultadoGestion = resultadoGestion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.acuerdoPago = acuerdoPago;
        this.online = online;
    }

    public Gestion(String tipoGestion, String idUsuario, String documento, int acuerdoPago, String fecha, String fechaAcuerdo, String valorAcuerdo, String descripcion, String resultadoGestion, String latitud, String longitud, int online, int key) {
        this.idUsuario = idUsuario;
        this.documento = documento;
        this.tipoGestion = tipoGestion;
        this.fecha = fecha;
        this.fechaAcuerdo = fechaAcuerdo;
        this.valorAcuerdo = valorAcuerdo;
        this.descripcion = descripcion;
        this.resultadoGestion = resultadoGestion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.acuerdoPago = acuerdoPago;
        this.online = online;
        this.key = key;
    }

    public String getTipoGestion() {
        return tipoGestion;
    }

    public void setTipoGestion(String tipoGestion) {
        this.tipoGestion = tipoGestion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getFechaAcuerdo() {
        return fechaAcuerdo;
    }

    public void setFechaAcuerdo(String fechaAcuerdo) {
        this.fechaAcuerdo = fechaAcuerdo;
    }

    public String getValorAcuerdo() {
        return valorAcuerdo;
    }

    public void setValorAcuerdo(String valorAcuerdo) {
        this.valorAcuerdo = valorAcuerdo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getResultadoGestion() {
        return resultadoGestion;
    }

    public void setResultadoGestion(String resultadoGestion) {
        this.resultadoGestion = resultadoGestion;
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

    public int getAcuerdoPago() {
        return acuerdoPago;
    }

    public void setAcuerdoPago(int acuerdoPago) {
        this.acuerdoPago = acuerdoPago;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }
}
