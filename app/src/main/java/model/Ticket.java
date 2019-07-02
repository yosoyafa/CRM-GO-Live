package model;

public class Ticket {

    public final static String LINEA = "----------------------------------";

    private String formaDePago, cabecera, fecha, vigD, vigH, valorVigContrato, numRecibo, numContrato, ccCliente, nombCliente, valorRecaudado, observacion, nombAsesor, periodicidad;

    public Ticket(String cabecera, String fecha, String vigD, String vigH, String valorVigContrato, String periodicidad,
                  String numRecibo, String numContrato, String ccCliente, String nombCliente, String valorRecaudado,
                  String observacion, String nombAsesor, String fdp) {

        this.cabecera = cabecera;
        this.formaDePago = fdp;
        this.fecha = fecha;
        this.vigD = vigD;
        this.vigH = vigH;
        this.valorVigContrato = valorVigContrato;
        this.numRecibo = numRecibo;
        this.numContrato = numContrato;
        this.ccCliente = ccCliente;
        this.nombCliente = nombCliente;
        this.valorRecaudado = valorRecaudado;
        this.observacion = observacion;
        this.nombAsesor = nombAsesor;
        this.periodicidad = periodicidad;
    }

    public String toStringRaw(){
        return cabecera+","+fecha+","+vigD+","+vigH+","+valorVigContrato+","+periodicidad+","+numRecibo+","+numContrato+","+ccCliente+","+nombCliente+","+valorRecaudado+","+observacion+","+nombAsesor+","+formaDePago;
    }

    public String toString() {
        String out = "";
        out = cabecera + "\n" + "\nFecha: " + fecha + "\n  \nVigencia Desde: " + vigD +
                "\nVigencia Hasta: " + vigH + "\n\nValor Vig. Contrato: $" + valorVigContrato +
                "\nPeriodicidad de Pago: "+periodicidad+
                "\n  \n" + LINEA +
                "\n\nRECIBO DE CAJA\n" + numRecibo + "\nContrato Nro: " + numContrato + "\nTipo Registro: Recaudo\n  Pagador\nDto: CC " + ccCliente +
                "\n " + nombCliente + "\n\nValor Recaudado: $" + valorRecaudado + "\n" + "Forma de Pago: "+formaDePago+"\nObservacion: " + observacion +
                "\n   \n   \n" + LINEA + "\nFIRMA DEL CONTRATANTE\n\nAsesor: " + nombAsesor;
        return out;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getVigD() {
        return vigD;
    }

    public void setVigD(String vigD) {
        this.vigD = vigD;
    }

    public String getVigH() {
        return vigH;
    }

    public void setVigH(String vigH) {
        this.vigH = vigH;
    }

    public String getValorVigContrato() {
        return valorVigContrato;
    }

    public void setValorVigContrato(String valorVigContrato) {
        this.valorVigContrato = valorVigContrato;
    }

    public String getNumContrato() {
        return numContrato;
    }

    public void setNumContrato(String numContrato) {
        this.numContrato = numContrato;
    }

    public String getCcCliente() {
        return ccCliente;
    }

    public void setCcCliente(String ccCliente) {
        this.ccCliente = ccCliente;
    }

    public String getNombCliente() {
        return nombCliente;
    }

    public void setNombCliente(String nombCliente) {
        this.nombCliente = nombCliente;
    }

    public String getValorRecaudado() {
        return valorRecaudado;
    }

    public void setValorRecaudado(String valorRecaudado) {
        this.valorRecaudado = valorRecaudado;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getNombAsesor() {
        return nombAsesor;
    }

    public void setNombAsesor(String nombAsesor) {
        this.nombAsesor = nombAsesor;
    }

    public String getNumRecibo() {
        return numRecibo;
    }

    public void setNumRecibo(String numRecibo) {
        this.numRecibo = numRecibo;
    }

    public String getPeriodicidad() {
        return periodicidad;
    }

    public void setPeriodicidad(String periodicidad) {
        this.periodicidad = periodicidad;
    }

    public String getFormaDePago() {
        return formaDePago;
    }

    public void setFormaDePago(String formaDePago) {
        this.formaDePago = formaDePago;
    }
}
