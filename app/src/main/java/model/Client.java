package model;

public class Client {
    private String name, id, total, vigenciaDesde, vigenciaHasta, numeroPoliza, valorContrato, periodicidad;

    public Client(String name, String id, String total, String vigenciaDesde, String vigenciaHasta, String numeroPoliza, String valorContrato, String periodicidad) {
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
                "Total cartera: $" + getTotal()+ "\n"+
                "Vigencia desde: " + getVigenciaDesde() + "\n" +
                "Vigencia hasta: " + getVigenciaHasta() + "\n" +
                "Numero Póliza: " + getNumeroPoliza() + "\n" +
                "Valor contrato: $" + getValorContrato();
        return out;
    }

    public String toStringRaw() {
        String out = name + "," + id + "," + total + "," + vigenciaDesde + "," + vigenciaHasta + "," + numeroPoliza + "," + valorContrato + ","+ periodicidad;
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
}
