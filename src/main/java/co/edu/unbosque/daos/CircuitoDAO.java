package co.edu.unbosque.daos;

import co.edu.unbosque.persistence.CapacitoresDTO;
import co.edu.unbosque.persistence.ResistenciaDTO;

public class CircuitoDAO {

	private String a1, a2, a3, a4, v;
	private String u1, u2, u3, u4, uv;

	public CircuitoDAO(String a1, String a2, String a3, String a4, String v, String u1, String u2, String u3, String u4,
			String uv) {
		super();
		this.a1 = a1;
		this.a2 = a2;
		this.a3 = a3;
		this.a4 = a4;
		this.v = v;
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
		this.uv = uv;
	}

	public String calcularResisParalelo() {
		String data = null;
		try {
			double req = 0;
			ResistenciaDTO[] resistencias = new ResistenciaDTO[4];
			double[] tmp = new double[4];
			tmp[0] = Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1));
			tmp[1] = Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2));
			tmp[2] = Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3));
			tmp[3] = Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4));
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));
			for (int i = 0; i < resistencias.length; i++) {
				resistencias[i] = new ResistenciaDTO();
				resistencias[i].setVoltaje(v1);
				resistencias[i].setResistencia(tmp[i]);
				resistencias[i].setCorriente(v1 / tmp[i]);
				req += 1 / tmp[i];
			}
			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $R_eq=$ $"
					+ Math.pow(req, -1) + "$ $\\Omega$, $V_eq=$ $" + v1 + "$ $V$, $I_eq=$ $" + (v1 / Math.pow(req, -1))
					+ "$ $A$ y arrojo finalmente los siguiente resultados para cada resistencia:<br><ul class\"list-group\">";
			for (int i = 0; i < resistencias.length; i++) {
				data += "<li class\"list-group-item\">$R_" + (i + 1) + "=$ $" + resistencias[i].getResistencia()
						+ "$ $\\Omega$, $V=$ $" + resistencias[i].getVoltaje() + "$ $V$ y $I=$ $"
						+ resistencias[i].getCorriente() + "$ $A$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de resistencias en paralelo";
		}
		return data;
	}

	public String calcularResisSerie() {
		String data = null;
		try {
			double req = 0;
			ResistenciaDTO[] resistencias = new ResistenciaDTO[4];
			double[] tmp = new double[4];
			tmp[0] = Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1));
			tmp[1] = Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2));
			tmp[2] = Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3));
			tmp[3] = Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4));
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));
			for (int i = 0; i < tmp.length; i++) {
				req += tmp[i];
			}
			double ieq = (v1 / req);
			for (int i = 0; i < resistencias.length; i++) {
				resistencias[i] = new ResistenciaDTO();
				resistencias[i].setResistencia(tmp[i]);
				resistencias[i].setCorriente(ieq);
				resistencias[i].setVoltaje(resistencias[i].getCorriente() * resistencias[i].getResistencia());
			}
			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $R_eq=$ $" + req
					+ "$ $\\Omega$, $V_eq=$ $" + v1 + "$ $V$, $I_eq=$ $" + (v1 / req)
					+ "$ $A$ y arrojo finalmente los siguiente resultados para cada resistencia:<br><ul class\"list-group\">";
			for (int i = 0; i < resistencias.length; i++) {
				data += "<li class\"list-group-item\">$R_" + (i + 1) + "=$ $" + resistencias[i].getResistencia()
						+ "$ $\\Omega$, $V=$ $" + resistencias[i].getVoltaje() + "$ $V$ y $I=$ $"
						+ resistencias[i].getCorriente() + "$ $A$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de resistencias en paralelo";
		}
		return data;
	}

	public String calcularResism() {
		String data = null;
		try {
			ResistenciaDTO[] resis = new ResistenciaDTO[4];
			resis[0] = new ResistenciaDTO();
			resis[0].setResistencia(Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1)));
			resis[1] = new ResistenciaDTO();
			resis[1].setResistencia(Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2)));
			resis[2] = new ResistenciaDTO();
			resis[2].setResistencia(Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3)));
			resis[3] = new ResistenciaDTO();
			resis[3].setResistencia(Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4)));
			ResistenciaDTO a = new ResistenciaDTO();
			a.setResistencia(resis[3].getResistencia() + resis[2].getResistencia());
			ResistenciaDTO b = new ResistenciaDTO();
			b.setResistencia(Math.pow(((1 / a.getResistencia()) + (1 / resis[1].getResistencia())), -1));
			ResistenciaDTO req = new ResistenciaDTO();
			req.setResistencia(b.getResistencia() + resis[0].getResistencia());
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));
			req.setVoltaje(v1);
			req.setCorriente(v1 / req.getResistencia());
			resis[0].setCorriente(req.getCorriente());
			resis[0].setVoltaje(resis[0].getCorriente() * resis[0].getResistencia());
			b.setCorriente(req.getCorriente());
			b.setVoltaje(b.getCorriente() * b.getResistencia());
			resis[1].setVoltaje(b.getVoltaje());
			resis[1].setCorriente(resis[1].getVoltaje() / resis[1].getResistencia());
			a.setVoltaje(b.getVoltaje());
			a.setCorriente(a.getVoltaje() / a.getResistencia());
			resis[2].setCorriente(a.getCorriente());
			resis[2].setVoltaje(resis[2].getCorriente() * resis[2].getResistencia());
			resis[3].setCorriente(a.getCorriente());
			resis[3].setVoltaje(resis[3].getCorriente() * resis[3].getResistencia());
			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $R_eq=$ $"
					+ req.getResistencia() + "$ $\\Omega$, $V_eq=$ $" + v1 + "$ $V$, $I_eq=$ $" + req.getCorriente()
					+ "$ $A$ y arrojo finalmente los siguiente resultados para cada resistencia:<br><ul class\"list-group\">";
			for (int i = 0; i < resis.length; i++) {
				data += "<li class\"list-group-item\">$R_" + (i + 1) + "=$ $" + resis[i].getResistencia()
						+ "$ $\\Omega$, $V=$ $" + resis[i].getVoltaje() + "$ $V$ y $I=$ $" + resis[i].getCorriente()
						+ "$ $A$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de resistencias mixto";
		}
		return data;
	}

	public String calcularCondSerie() {
		String data = null;
		try {
			double req = 0;
			CapacitoresDTO[] capacitores = new CapacitoresDTO[4];
			double[] tmp = new double[4];
			tmp[0] = Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1));
			tmp[1] = Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2));
			tmp[2] = Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3));
			tmp[3] = Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4));
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));
			for (int i = 0; i < tmp.length; i++) {
				req += 1 / tmp[i];
			}
			req = Math.pow(req, -1);
			for (int i = 0; i < capacitores.length; i++) {
				capacitores[i] = new CapacitoresDTO();
				capacitores[i].setCapacitancia(tmp[i]);
				capacitores[i].setCarga(v1 * req);
				capacitores[i].setVoltaje((v1 * req) / tmp[i]);
			}
			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $C_eq=$ $" + req
					+ "$ $F$, $V_eq=$ $" + v1 + "$ $V$, $Q_eq=$ $" + (v1 * req)
					+ "$ $C$ y arrojo finalmente los siguiente resultados para cada capacitor:<br><ul class\"list-group\">";
			for (int i = 0; i < capacitores.length; i++) {
				data += "<li class\"list-group-item\">$C_" + (i + 1) + "=$ $" + capacitores[i].getCapacitancia()
						+ "$ $F$, $V=$ $" + capacitores[i].getVoltaje() + "$ $V$ y $Q=$ $" + capacitores[i].getCarga()
						+ "$ $C$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de capacitores en serie";
		}
		return data;
	}

	public String calcularCondParalelo() {
		String data = null;
		try {
			double req = 0;
			CapacitoresDTO[] capacitores = new CapacitoresDTO[4];
			double[] tmp = new double[4];
			tmp[0] = Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1));
			tmp[1] = Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2));
			tmp[2] = Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3));
			tmp[3] = Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4));
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));
			for (int i = 0; i < tmp.length; i++) {
				req += tmp[i];
			}
			for (int i = 0; i < capacitores.length; i++) {
				capacitores[i] = new CapacitoresDTO();
				capacitores[i].setCapacitancia(tmp[i]);
				capacitores[i].setVoltaje(v1);
				capacitores[i].setCarga(tmp[i] * v1);
			}
			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $C_eq=$ $" + req
					+ "$ $F$, $V_eq=$ $" + v1 + "$ $V$, $Q_eq=$ $" + (v1 * req)
					+ "$ $C$ y arrojo finalmente los siguiente resultados para cada capacitor:<br><ul class\"list-group\">";
			for (int i = 0; i < capacitores.length; i++) {
				data += "<li class\"list-group-item\">$C_" + (i + 1) + "=$ $" + capacitores[i].getCapacitancia()
						+ "$ $F$, $V=$ $" + capacitores[i].getVoltaje() + "$ $V$ y $Q=$ $" + capacitores[i].getCarga()
						+ "$ $C$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de capacitores en paralelo";
		}
		return data;
	}

	public String calcularCondMixto() {
		String data = null;
		try {
			CapacitoresDTO[] capacitores = new CapacitoresDTO[4];
			double[] tmp = new double[4];
			tmp[0] = Double.parseDouble(a1) * Math.pow(10, Double.parseDouble(u1));
			tmp[1] = Double.parseDouble(a2) * Math.pow(10, Double.parseDouble(u2));
			tmp[2] = Double.parseDouble(a3) * Math.pow(10, Double.parseDouble(u3));
			tmp[3] = Double.parseDouble(a4) * Math.pow(10, Double.parseDouble(u4));
			double v1 = Double.parseDouble(v) * Math.pow(10, Double.parseDouble(uv));

			for (int i = 0; i < capacitores.length; i++) {
				capacitores[i] = new CapacitoresDTO();
				capacitores[i].setCapacitancia(tmp[i]);
			}
			CapacitoresDTO a = new CapacitoresDTO();
			a.setCapacitancia((capacitores[3].getCapacitancia() * capacitores[2].getCapacitancia())
					/ (capacitores[3].getCapacitancia() + capacitores[2].getCapacitancia()));
			CapacitoresDTO b = new CapacitoresDTO();
			b.setCapacitancia(a.getCapacitancia() + capacitores[1].getCapacitancia());

			CapacitoresDTO ceq = new CapacitoresDTO();
			ceq.setCapacitancia((b.getCapacitancia() * capacitores[0].getCapacitancia())
					/ (b.getCapacitancia() + capacitores[0].getCapacitancia()));
			ceq.setVoltaje(v1);
			ceq.setCarga(v1 * ceq.getCapacitancia());

			capacitores[0].setCarga(ceq.getCarga());
			capacitores[0].setVoltaje(capacitores[0].getCarga() / capacitores[0].getCapacitancia());

			b.setCarga(ceq.getCarga());
			b.setVoltaje(b.getCarga() / b.getCapacitancia());

			capacitores[1].setVoltaje(b.getVoltaje());
			capacitores[1].setCarga(capacitores[1].getVoltaje() * capacitores[1].getCapacitancia());

			a.setVoltaje(b.getVoltaje());
			a.setCarga(a.getVoltaje() * a.getCapacitancia());

			capacitores[2].setCarga(a.getCarga());
			capacitores[2].setVoltaje(capacitores[2].getCarga() / capacitores[2].getCapacitancia());

			capacitores[3].setCarga(a.getCarga());
			capacitores[3].setVoltaje(capacitores[3].getCarga() / capacitores[3].getCapacitancia());

			data = "El calculo con un voltaje de $" + v1 + "$ $V$ dio como resultado equivalente $C_eq=$ $"
					+ ceq.getCapacitancia() + "$ $F$, $V_eq=$ $" + v1 + "$ $V$, $Q_eq=$ $" + ceq.getCarga()
					+ "$ $C$ y arrojo finalmente los siguiente resultados para cada capacitor:<br><ul class\"list-group\">";
			for (int i = 0; i < capacitores.length; i++) {
				data += "<li class\"list-group-item\">$C_" + (i + 1) + "=$ $" + capacitores[i].getCapacitancia()
						+ "$ $F$, $V=$ $" + capacitores[i].getVoltaje() + "$ $V$ y $Q=$ $" + capacitores[i].getCarga()
						+ "$ $C$</li>";
			}
			data += "</ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular el circuito de capacitores mixtos";
		}
		return data;
	}

}
