package model;

public class Client {
    private String name, id, total, vigenciaDesde, vigenciaHasta, numeroPoliza, valorContrato, periodicidad, telefono1, telefono2, direccion;

    public Client(String name, String id, String total, String vigenciaDesde, String vigenciaHasta, String numeroPoliza, String valorContrato, String periodicidad, String telefono1, String telefono2, String direccion) {
        this.name = name;
        this.id = id;
        if (total.equals(null)) {
            this.total = "0";
        } else {
            this.total = total;
        }
        this.vigenciaDesde = vigenciaDesde;
        this.vigenciaHasta = vigenciaHasta;
        this.numeroPoliza = numeroPoliza;
        this.valorContrato = valorContrato;
        this.periodicidad = periodicidad;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.direccion = direccion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String toString() {
        String out = "Nombre: " + getName() + "\n" +
                "Cedula: " + getId() + "\n" +
                "Total cartera: $" + getTotal() + "\n"+
                "Vigencia desde: " + getVigenciaDesde() + "\n" +
                "Vigencia hasta: " + getVigenciaHasta() + "\n" +
                "Numero Póliza: " + getNumeroPoliza() + "\n" +
                "Valor contrato: $" + getValorContrato();
        return out;
    }

    public String toStringRaw() {
        String out = name + "," + id + "," + total + "," + vigenciaDesde + "," + vigenciaHasta + "," + numeroPoliza + "," + valorContrato + ","+ periodicidad + "," + telefono1 + "," + telefono2 + "," + direccion;
        return out;
    }

    public String getVigenciaDesde() {
        return vigenciaDesde;
    }

    public void setVigenciaDesde(String vigenciaDesde) {
        this.vigenciaDesde = vigenciaDesde;
    }

    public String getVigenciaHasta() {
        return vigenciaHasta;
    }

    public void setVigenciaHasta(String vigenciaHasta) {
        this.vigenciaHasta = vigenciaHasta;
    }

    public String getNumeroPoliza() {
        return numeroPoliza;
    }

    public void setNumeroPoliza(String numeroPoliza) {
        this.numeroPoliza = numeroPoliza;
    }

    public String getValorContrato() {
        return valorContrato;
    }

    public void setValorContrato(String valorContrato) {
        this.valorContrato = valorContrato;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }
}
