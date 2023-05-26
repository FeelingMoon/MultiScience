package co.edu.unbosque.beans;

import java.io.Serializable;
import java.util.ArrayList;

import org.primefaces.model.charts.scatter.ScatterChartModel;

import co.edu.unbosque.services.Datos;
import co.edu.unbosque.services.FisicaElectrica;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@SuppressWarnings("serial")
@Named("fisicaEBean")
@ViewScoped
public class FisicaEBean implements Serializable {

	private String valor, tipo;
	private String descripcion;
	private String dialogo;
	private String resultado;
	private String rutaImg;
	private String q1, q2, q3, q4, q5;
	private String u1, u2, u3, u4, u5;
	private String x1, y1;
	private String x2, y2;
	private String x3, y3;
	private String x4, y4;
	private String x5, y5;
	private String r1, r2, r3, r4, v;
	private String vx, vy, vz, bx, by, bz, q, ang;
	private ScatterChartModel scatterModel;
	private boolean scatter, img;
	private FisicaElectrica fis;

	@PostConstruct
	public void init() {
		fis = new FisicaElectrica();
		rutaImg = Datos.getRuta("meme");
		valor = "electricidad";
		u1 = "0";
		u2 = "0";
		u3 = "0";
		u4 = "0";
		u5 = "0";
		resultado = "";
		graficaScatterDefault();
		img = true;
		scatter = true;
	}

	public void calcular() {
		ArrayList<Object> tmp = null;
		if (valor.equals("electricidad")) {
			tmp = fis.calcularElec(q1, q2, q3, q4, q5, u1, u2, u3, u4, u5, x1, y1, x2, y2, x3, y3, x4, y4, x5, y5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				scatterModel = (ScatterChartModel) tmp.get(1);
				img = false;
				scatter = true;
			}
		} else if ((valor + tipo).equals("resisp")) {
			tmp = fis.calcularResisp(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if ((valor + tipo).equals("resiss")) {
			tmp = fis.calcularResiss(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if ((valor + tipo).equals("resism")) {
			tmp = fis.calcularResism(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if ((valor + tipo).equals("conds")) {
			tmp = fis.calcularConds(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if ((valor + tipo).equals("condp")) {
			tmp = fis.calcularCondp(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if ((valor + tipo).equals("condm")) {
			tmp = fis.calcularCondm(r1, r2, r3, r4, v, u1, u2, u3, u4, u5);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				rutaImg = Datos.getRuta(valor + tipo);
				img = true;
				scatter = false;
			}
		} else if (valor.equals("magnev")) {
			tmp = fis.calcularMagnev(vx, vy, vz, u2, bx, by, bz, u3, q, u1, "0");
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				img = false;
				scatter = false;
			}
		} else if (valor.equals("magnem")) {
			tmp = fis.calcularMagnem(vx, vy, vz, u2, bx, by, bz, u3, q, u1, ang);
			if (tmp != null) {
				resultado = (String) tmp.get(0);
				img = false;
				scatter = false;
			}
		}
		if (tmp == null) {
			rutaImg = Datos.getRuta("meme");
			scatterModel = Datos.getScatterGraphicDefault();
			img = true;
			scatter = true;
		}
		q1 = null;
		q2 = null;
		q3 = null;
		q4 = null;
		q5 = null;
		u1 = "0";
		u2 = "0";
		u3 = "0";
		u4 = "0";
		u5 = "0";
		x1 = null;
		y1 = null;
		x2 = null;
		y2 = null;
		x3 = null;
		y3 = null;
		x4 = null;
		y4 = null;
		x5 = null;
		y5 = null;
		vx = null;
		vy = null;
		vz = null;
		bx = null;
		by = null;
		bz = null;
		q = null;
	}

	public void graficaScatterDefault() {
		scatterModel = Datos.getScatterGraphicDefault();
	}

	public String descrpicion() {
		if (valor == null) {
			valor = "electricidad";
		}
		descripcion = Datos.getDescripcion(valor);
		dialogo = Datos.getDialogo(valor);
		rutaImg = Datos.getRuta(valor + tipo);
		return descripcion;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		if (valor.contains("resis")) {
			this.valor = "resis";
			this.tipo = valor.charAt(valor.length() - 1) + "";
		} else if (valor.contains("cond")) {
			this.valor = "cond";
			this.tipo = valor.charAt(valor.length() - 1) + "";
		} else {
			this.valor = valor;
		}

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

	public String getResultado() {
		return resultado;
	}

	public void setResultado(String resultado) {
		this.resultado = resultado;
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

	public boolean isImg() {
		return img;
	}

	public void setImg(boolean img) {
		this.img = img;
	}

	public String getRutaImg() {
		return rutaImg;
	}

	public void setRutaImg(String rutaImg) {
		this.rutaImg = rutaImg;
	}

	public String getQ1() {
		return q1;
	}

	public void setQ1(String q1) {
		this.q1 = q1;
	}

	public String getQ2() {
		return q2;
	}

	public void setQ2(String q2) {
		this.q2 = q2;
	}

	public String getQ3() {
		return q3;
	}

	public void setQ3(String q3) {
		this.q3 = q3;
	}

	public String getQ4() {
		return q4;
	}

	public void setQ4(String q4) {
		this.q4 = q4;
	}

	public String getQ5() {
		return q5;
	}

	public void setQ5(String q5) {
		this.q5 = q5;
	}

	public String getU1() {
		return u1;
	}

	public void setU1(String u1) {
		this.u1 = u1;
	}

	public String getU2() {
		return u2;
	}

	public void setU2(String u2) {
		this.u2 = u2;
	}

	public String getU3() {
		return u3;
	}

	public void setU3(String u3) {
		this.u3 = u3;
	}

	public String getU4() {
		return u4;
	}

	public void setU4(String u4) {
		this.u4 = u4;
	}

	public String getU5() {
		return u5;
	}

	public void setU5(String u5) {
		this.u5 = u5;
	}

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public String getY1() {
		return y1;
	}

	public void setY1(String y1) {
		this.y1 = y1;
	}

	public String getX2() {
		return x2;
	}

	public void setX2(String x2) {
		this.x2 = x2;
	}

	public String getY2() {
		return y2;
	}

	public void setY2(String y2) {
		this.y2 = y2;
	}

	public String getX3() {
		return x3;
	}

	public void setX3(String x3) {
		this.x3 = x3;
	}

	public String getY3() {
		return y3;
	}

	public void setY3(String y3) {
		this.y3 = y3;
	}

	public String getX4() {
		return x4;
	}

	public void setX4(String x4) {
		this.x4 = x4;
	}

	public String getY4() {
		return y4;
	}

	public void setY4(String y4) {
		this.y4 = y4;
	}

	public String getX5() {
		return x5;
	}

	public void setX5(String x5) {
		this.x5 = x5;
	}

	public String getY5() {
		return y5;
	}

	public void setY5(String y5) {
		this.y5 = y5;
	}

	public String getR1() {
		return r1;
	}

	public void setR1(String r1) {
		this.r1 = r1;
	}

	public String getR2() {
		return r2;
	}

	public void setR2(String r2) {
		this.r2 = r2;
	}

	public String getR3() {
		return r3;
	}

	public void setR3(String r3) {
		this.r3 = r3;
	}

	public String getR4() {
		return r4;
	}

	public void setR4(String r4) {
		this.r4 = r4;
	}

	public FisicaElectrica getFis() {
		return fis;
	}

	public void setFis(FisicaElectrica fis) {
		this.fis = fis;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getVx() {
		return vx;
	}

	public void setVx(String vx) {
		this.vx = vx;
	}

	public String getVy() {
		return vy;
	}

	public void setVy(String vy) {
		this.vy = vy;
	}

	public String getVz() {
		return vz;
	}

	public void setVz(String vz) {
		this.vz = vz;
	}

	public String getBx() {
		return bx;
	}

	public void setBx(String bx) {
		this.bx = bx;
	}

	public String getBy() {
		return by;
	}

	public void setBy(String by) {
		this.by = by;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getAng() {
		return ang;
	}

	public void setAng(String ang) {
		this.ang = ang;
	}

}
