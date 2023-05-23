package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.primefaces.model.file.UploadedFile;

import co.edu.unbosque.persistence.FrecuenciasDTO;
import co.edu.unbosque.services.Datos;
import co.edu.unbosque.services.Experimentos;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@SuppressWarnings("serial")
@Named("frecuenciasBean")
@ViewScoped
public class FrecuenciasBean implements Serializable {

	private String descripcion;
	private String dialogo;
	private UploadedFile file;
	private ArrayList<FrecuenciasDTO> datos;
	private String valor;
	private Experimentos exp;
	private String csr;
	private String dato;

	public FrecuenciasBean() {
		exp = new Experimentos();
		datos = Datos.getDatosTabla();
		file = null;
		valor = "excel";
		dato = "";
		csr = "sr";
		dialogo = "";
		descripcion = "";
	}

	public void calcular() {
		if (valor.equals("excel") && csr.equals("sr")) {
			datos = exp.tablaFrecuenciasSinRangoXLSX(file);
		} else if (valor.equals("excel") && csr.equals("cr")) {
			datos = exp.tablaFrecuenciasConRangoXLSX(file);
		} else if (valor.equals("texto") && csr.equals("sr")) {
			datos = exp.tablaFrecuenciasSinRangoTexto(dato);
		} else if (valor.equals("texto") && csr.equals("cr")) {
			datos = exp.tablaFrecuenciasConRangoTexto(dato);
		}
		if (datos == null) {
			datos = Datos.getDatosTabla();
			showError("Error al intentar calcular");
		} else {
			showSticky("Tabla generada correctamente");
		}
	}

	public String descrpicion() {
		if (valor == null) {
			valor = "excel";
		}
		descripcion = Datos.getDescripcion("excel");
		dialogo = Datos.getDialogo(valor);
		return descripcion;
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

	public String getDescripcion() {
		descrpicion();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getDialogo() {
		return dialogo;
	}

	public void setDialogo(String dialogo) {
		this.dialogo = dialogo;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
		if (file != null) {
			showSticky("Archivo " + file.getFileName() + " subido correctamente");
			calcular();
		}
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Experimentos getExp() {
		return exp;
	}

	public void setExp(Experimentos exp) {
		this.exp = exp;
	}

	public ArrayList<FrecuenciasDTO> getDatos() {
		return datos;
	}

	public void setDatos(ArrayList<FrecuenciasDTO> datos) {
		this.datos = datos;
	}

	public String getCsr() {
		return csr;
	}

	public void setCsr(String csr) {
		this.csr = csr;
	}

	public String getDato() {
		return dato;
	}

	public void setDato(String dato) {
		this.dato = dato;
	}

}
