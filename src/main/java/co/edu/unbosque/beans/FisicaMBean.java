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
	private String vel, ang, valor, resultado, acel, f1, f2, f3, ang1, ang2, ang3;
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
			try {
			datos = fis.expParabola(vel, ang);
			}catch(NumberFormatException e) {
				showError("Error al intentar realizar la accion, corrija los datos y vuelva a intentarlo.");
			}
		} else if(valor.equals("aceleracion")) {
			try {
			datos = fis.expAceleracion(acel);
			}catch(NumberFormatException e) {
				showError("Error al intentar realizar la accion, corrija los datos y vuelva a intentarlo.");
			}
		}else if(valor.equals("fuerzas")) {
			try {
			datos = fis.expFuerzas(f1, f2, f3, ang1, ang2, ang3);
			}catch(NumberFormatException e) {
				showError("Error al intentar realizar la accion, corrija los datos y vuelva a intentarlo.");
			}
		}
		
		if (datos != null) {
			try {
			setResultado((String) datos.get(1));
			scatterModel = (ScatterChartModel) datos.get(0);
			}catch(IndexOutOfBoundsException e) {
				
			}
		}
		
		acel = "0";
		vel = "0";
		ang = "0";
		f1 = "0";
		f2 = "0";
		f3 = "0";
		ang1 = "0";
		ang2 = "0";
		ang3 = "0";
		
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

	public String getAcel() {
		return acel;
	}

	public void setAcel(String acel) {
		this.acel = acel;
	}

	public String getF1() {
		return f1;
	}

	public void setF1(String f1) {
		this.f1 = f1;
	}

	public String getF2() {
		return f2;
	}

	public void setF2(String f2) {
		this.f2 = f2;
	}

	public String getF3() {
		return f3;
	}

	public void setF3(String f3) {
		this.f3 = f3;
	}

	public String getAng1() {
		return ang1;
	}

	public void setAng1(String ang1) {
		this.ang1 = ang1;
	}

	public String getAng2() {
		return ang2;
	}

	public void setAng2(String ang2) {
		this.ang2 = ang2;
	}

	public String getAng3() {
		return ang3;
	}

	public void setAng3(String ang3) {
		this.ang3 = ang3;
	}
	
}
