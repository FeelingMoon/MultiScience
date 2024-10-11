package co.edu.unbosque.beans;

import java.io.Serializable;

import co.edu.unbosque.services.Datos;
import co.edu.unbosque.services.Redes;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@SuppressWarnings("serial")
@Named("redesBean")
@ViewScoped
public class RedesBean implements Serializable {

	private String ip, resultado, valor, dialogo, descripcion;
	private int numMask;
	private String mask;
	private Redes red;
	private String[] resultados;
	private boolean ipCal;

	@PostConstruct
	public void init() {
		ip = "";
		valor = "ipConMask";
		resultado = "";
		ipCal = true;
		red = new Redes();
		rellenarResultados();
	}

	public void rellenarResultados() {
		resultados = new String[13];
		for (int i = 0; i < resultados.length; i++) {
			resultados[i] = "No se ha calculado nada.";
		}
		resultados[6] = "X.X.X.";
		resultados[7] = "X";
	}

	public void calcular() {
		if (valor.equals("ipConNumMask")) {
			if (numMask < 0 || numMask > 32) {
				showError("Mascara invalida, imposible calcular.");
			} else {
				resultados = red.calcularIP(ip.split("\\."), numMask);
				if (resultados == null) {
					rellenarResultados();
					showError("IP invalida, imposible calcular.");
				} else {
					showSticky("IP calculada correctamente.");
				}
			}
		} else if (valor.equals("ipConMask")) {
			resultados = red.calcularIP(ip.split("\\."), mask.split("\\."));
			if (resultados == null) {
				rellenarResultados();
				showError("IP o mascara invalida, imposible calcular.");
			} else {
				showSticky("IP calculada correctamente.");
			}
		}
	}

	public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public void showError(String mensaje) {
		addMessage(FacesMessage.SEVERITY_ERROR, "Error", mensaje);
	}

	public void showSticky(String mensaje) {
		FacesContext.getCurrentInstance().addMessage("sticky-key",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
	}

	public String descripcion() {
		descripcion = Datos.getDescripcion(valor);
		dialogo = Datos.getDialogo(valor);
		return descripcion;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Redes getRed() {
		return red;
	}

	public void setRed(Redes red) {
		this.red = red;
	}

	public String[] getResultados() {
		return resultados;
	}

	public void setResultados(String[] resultados) {
		this.resultados = resultados;
	}

	public int getNumMask() {
		return numMask;
	}

	public void setNumMask(int numMask) {
		this.numMask = numMask;
	}

	public boolean isIpCal() {
		return ipCal;
	}

	public void setIpCal(boolean ipCal) {
		this.ipCal = ipCal;
	}

	public String getMask() {
		return mask;
	}

	public void setMask(String mask) {
		this.mask = mask;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDialogo() {
		return dialogo;
	}

	public void setDialogo(String dialogo) {
		this.dialogo = dialogo;
	}

	public String getDescripcion() {
		descripcion();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

}
