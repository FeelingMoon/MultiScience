package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.primefaces.model.charts.scatter.ScatterChartModel;

import co.edu.unbosque.services.Datos;
import co.edu.unbosque.services.FisicaM;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@SuppressWarnings("serial")
@Named("fisicaBean")
@ViewScoped
public class FisicaMBean implements Serializable{

	private boolean scatter, bar;
	private String vel, ang, valor, resultado;
	private ScatterChartModel scatterModel;
	private FisicaM fis;
	private String descripcion;
	private String dialogo;
	
	@PostConstruct
	public void init() {
		
		scatterModel = new ScatterChartModel();
		fis = new FisicaM();
		graficaScatterDefault();
		String pagina = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (pagina.equals("/fisicaM.xhtml")) {
			valor = "parabola";
			showSticky("Bienvenido a su simulador para fisica mecanica.");
		}
		scatter = true;
		bar = true;
		setResultado("");
		
		
	}
	
	public void calcular() {
		
		ArrayList<Object> datos = new ArrayList<>();
		
		if(valor.equals("parabola")) {	
			datos = fis.expParabola(vel, ang);
		}
		
		if (datos != null) {
			setResultado((String) datos.get(0));
			scatterModel = (ScatterChartModel) datos.get(1);
		}
		
		vel = "";
		ang = "";
		
	}

	public void graficaScatterDefault() {
		scatterModel = Datos.getScatterGraphicDefault();
	}

	public String descripcion() {
		if (valor == null) {
			valor = "integral";
		}
		descripcion = Datos.getDescripcion(valor);
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
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
	}

	public boolean isScatter() {
		return scatter;
	}

	public void setScatter(boolean scatter) {
		this.scatter = scatter;
	}

	public boolean isBar() {
		return bar;
	}

	public void setBar(boolean bar) {
		this.bar = bar;
	}

	public String getVel() {
		return vel;
	}

	public void setVel(String vel) {
		this.vel = vel;
	}

	public String getAng() {
		return ang;
	}

	public void setAng(String ang) {
		this.ang = ang;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public ScatterChartModel getScatterModel() {
		return scatterModel;
	}

	public void setScatterModel(ScatterChartModel scatterModel) {
		this.scatterModel = scatterModel;
	}

	public String getDescripcion() {
		descripcion();
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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}
	
}
