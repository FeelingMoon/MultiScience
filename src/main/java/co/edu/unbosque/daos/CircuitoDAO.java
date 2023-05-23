package co.edu.unbosque.daos;

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
			data = "Error al intentar calcular el circuito de resistencias en paralelo";
		}
		return data;
	}

	private double redondear(double a) {
		int decimales = 4;
		double factor = Math.pow(10, decimales);
		return Math.round(a * factor) / factor;
	}

}
