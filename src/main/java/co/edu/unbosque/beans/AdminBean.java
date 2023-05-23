package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.scatter.ScatterChartModel;

import co.edu.unbosque.persistence.FrecuenciasDTO;
import co.edu.unbosque.services.Datos;
import co.edu.unbosque.services.Experimentos;
import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@SuppressWarnings("serial")
@Named("adminBean")
@ViewScoped
public class AdminBean implements Serializable {
	private List<FrecuenciasDTO> freqSinRan;
	private String valor;
	private String descripcion;
	private String dialogo;
	private String resultado;
	private String funcion, rango;
	private String val1, val2;
	private BarChartModel barModel;
	private ScatterChartModel scatterModel;
	private Experimentos exp;
	private boolean scatter, bar;

	@PostConstruct
	public void init() {
		freqSinRan = new ArrayList<>();
		freqSinRan.add(new FrecuenciasDTO("Muestra", 0, 0, 0, 0));
		barModel = new BarChartModel();
		scatterModel = new ScatterChartModel();
		exp = new Experimentos();
		graficaBarDefault();
		graficaScatterDefault();
		String pagina = FacesContext.getCurrentInstance().getViewRoot().getViewId();
		if (pagina.equals("/calculo.xhtml")) {
			valor = "integ";
		} else {
			valor = "integral";
		}
		scatter = true;
		bar = true;
		resultado = "";
		funcion = "";
		rango = "";
		val1 = "";
		val2 = "";
		showSticky("Bienvenido a su calculadora cientifica favorita.");
	}

	public void calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		if (valor.equals("supermercado")) {
			datos = exp.expSupermercado();
		} else if (valor.equals("integral")) {
			datos = exp.expIntegralAprox(funcion, rango);
		} else if (valor.equals("enfermedad")) {
			datos = exp.expEnfermedad(val1, val2);
		} else if (valor.equals("dados")) {
			datos = exp.expDados();
		} else if (valor.equals("pi")) {
			datos = exp.expPi();
		} else if (valor.equals("colas")) {
			datos = exp.expColas();
		} else if (valor.equals("primo")) {
			datos = exp.expPrimo();
		} else if (valor.equals("blackjack")) {
			datos = exp.expBlackJack();
		} else if (valor.equals("moneda")) {
			datos = exp.expMoneda();
		} else if (valor.equals("internet")) {
			datos = exp.expInternet(val1, val2);
		} else if (valor.equals("integ")) {
			datos = exp.integralDef(funcion, rango);
		} else if (valor.equals("indef")) {
			datos = exp.integralIndef(funcion);
		} else if (valor.equals("derivada")) {
			datos = exp.derivada(funcion);
		} else if (valor.equals("lim")) {
			datos = exp.lim(funcion, rango);
		} else if (valor.equals("tang")) {
			datos = exp.rectTang(funcion, rango);
		}
		if (datos != null) {
			resultado = (String) datos.get(0);
			if (!valor.equals("integral") && !valor.equals("pi") && !valor.equals("integ") && !valor.equals("indef")
					&& !valor.equals("derivada") && !valor.equals("lim") && !valor.equals("tang")) {
				barModel = (BarChartModel) datos.get(1);
				graficaScatterDefault();
				bar = true;
				scatter = false;
			} else {
				scatterModel = (ScatterChartModel) datos.get(1);
				graficaBarDefault();
				bar = false;
				scatter = true;
			}
		} else {
			resultado = "";
			barModel = Datos.getBarGraphicDefault();
			showError("Error al intentar realizar la accion, corrija los datos y vuelva a intentarlo.");
		}
		funcion = "";
		rango = "";
		val1 = "";
		val2 = "";
	}

	public String obtenerLink() {
		return Datos.getLink();
	}

	public void graficaBarDefault() {
		barModel = Datos.getBarGraphicDefault();
	}

	public void graficaScatterDefault() {
		scatterModel = Datos.getScatterGraphicDefault();
	}

	public String descrpicion() {
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
		FacesContext.getCurrentInstance().addMessage("sticky-key",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
	}

	public BarChartModel getBarModel() {
		return barModel;
	}

	public void setBarModel(BarChartModel barModel) {
		this.barModel = barModel;
	}

	public String getDialogo() {
		return dialogo;
	}

	public void setDialogo(String dialogo) {
		this.dialogo = dialogo;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public String getRango() {
		return rango;
	}

	public void setRango(String rango) {
		this.rango = rango;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		descrpicion();
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getVal1() {
		return val1;
	}

	public void setVal1(String val1) {
		this.val1 = val1;
	}

	public String getVal2() {
		return val2;
	}

	public void setVal2(String val2) {
		this.val2 = val2;
	}

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
	}

	public Experimentos getExp() {
		return exp;
	}

	public void setExp(Experimentos exp) {
		this.exp = exp;
	}

	public ScatterChartModel getScatterModel() {
		return scatterModel;
	}

	public void setScatterModel(ScatterChartModel scatterModel) {
		this.scatterModel = scatterModel;
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

	public List<FrecuenciasDTO> getFreqSinRan() {
		return freqSinRan;
	}

	public void setFreqSinRan(List<FrecuenciasDTO> freqSinRan) {
		this.freqSinRan = freqSinRan;
	}

}
