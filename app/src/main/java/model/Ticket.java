package model;

public class Ticket {

    public final static String LINEA = "----------------------------------";

    private String cabecera, nit, tel, fecha, vigD, vigH, valorVigContrato, valorCuota, saldoVigencia, cuotasVencidas, cuotaActual, pendientes, numRecibo;
    private String numContrato, ccCliente, nombCliente, valorRecaudado, observacion, nombAsesor;

    public Ticket(String cabecera, String nit, String tel, String fecha, String vigD, String vigH, String valorVigContrato, String valorCuota, String saldoVigencia, String cuotasVencidas, String cuotaActual, String pendientes, String numRecibo, String numContrato, String ccCliente, String nombCliente, String valorRecaudado, String observacion, String nombAsesor) {
        this.cabecera = cabecera;
        this.nit = nit;
        this.tel = tel;
        this.fecha = fecha;
        this.vigD = vigD;
        this.vigH = vigH;
        this.valorVigContrato = valorVigContrato;
        this.valorCuota = valorCuota;
        this.saldoVigencia = saldoVigencia;
        this.cuotasVencidas = cuotasVencidas;
        this.cuotaActual = cuotaActual;
        this.pendientes = pendientes;
        this.numRecibo = numRecibo;
        this.numContrato = numContrato;
        this.ccCliente = ccCliente;
        this.nombCliente = nombCliente;
        this.valorRecaudado = valorRecaudado;
        this.observacion = observacion;
        this.nombAsesor = nombAsesor;
    }

    public String toStringRaw(){
        return cabecera+","+nit+","+tel+","+fecha+","+vigD+","+vigH+","+valorVigContrato+","+valorCuota+","+saldoVigencia+","+cuotasVencidas+","+
                cuotaActual+","+pendientes+","+numRecibo+","+numContrato+","+ccCliente+","+nombCliente+","+valorRecaudado+","+observacion+","+nombAsesor;
    }

    public String toString() {
        String out = "";
        out = cabecera + "\n" + "Nit: " + nit + "\nTel: " + tel + "\nFecha: " + fecha + "\n\nVigencia Desde: " + vigD +
                "\nVigencia Hasta: " + vigH + "\n\nValor Vig. Contrato: $" + valorVigContrato + "\nValor Cuota: $" + valorCuota +
                "\nPeriodicidad de Pago: Mensual\n\nSaldo Vigencia: $" + saldoVigencia + "\n\nCuotas Vencidas: $" + cuotasVencidas +
                "\nCuota Actual: $" + cuotaActual + "\n\nPendiente Recaudar: $" + pendientes + "\n\n" + LINEA +
                "\n\nRECIBO DE CAJA\n" + numRecibo + "\nContrato Nro: " + numContrato + "\nTipo Registro: Recaudo\n  Pagador\nDto: CC " + ccCliente +
                "\n " + nombCliente + "\n\nValor Recaudado: $" + valorRecaudado + "\n" + "Forma de Pago: Efectivo\nObservacion: " + observacion +
                "\n\n\n" + LINEA + "\nFIRMA DEL CONTRATANTE\n\nAsesor: " + nombAsesor;
        return out;
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getValorCuota() {
        return valorCuota;
    }

    public void setValorCuota(String valorCuota) {
        this.valorCuota = valorCuota;
    }

    public String getSaldoVigencia() {
        return saldoVigencia;
    }

    public void setSaldoVigencia(String saldoVigencia) {
        this.saldoVigencia = saldoVigencia;
    }

    public String getCuotasVencidas() {
        return cuotasVencidas;
    }

    public void setCuotasVencidas(String cuotasVencidas) {
        this.cuotasVencidas = cuotasVencidas;
    }

    public String getCuotaActual() {
        return cuotaActual;
    }

    public void setCuotaActual(String cuotaActual) {
        this.cuotaActual = cuotaActual;
    }

    public String getPendientes() {
        return pendientes;
    }

    public void setPendientes(String pendientes) {
        this.pendientes = pendientes;
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
}
