package co.edu.unbosque.services;

import java.util.ArrayList;

import co.edu.unbosque.daos.CircuitoDAO;
import co.edu.unbosque.daos.ElectricidadDAO;

public class FisicaElectrica {

	private ElectricidadDAO elec;
	private CircuitoDAO cc;

	public ArrayList<Object> calcularElec(String q1, String q2, String q3, String q4, String q5, String u1, String u2,
			String u3, String u4, String u5, String x1, String y1, String x2, String y2, String x3, String y3,
			String x4, String y4, String x5, String y5) {
		elec = new ElectricidadDAO(q1, q2, q3, q4, q5, u1, u2, u3, u4, u5, x1, y1, x2, y2, x3, y3, x4, y4, x5, y5);
		ArrayList<Object> datos = new ArrayList<>();
		String fuerza = elec.calcularFuerza();
		String campo = elec.calcularCampo();
		String ePotencial = elec.calcularEnergiaPotencial();
		String potencial = elec.calcularPotencialElectrico();
		datos.add("<ul class=\"list-group\"><li class=\"list-group-item\">Fuerza electrica: " + fuerza
				+ "</li><li class=\"list-group-item\">Campo electrico: " + campo
				+ "</li><li class=\"list-group-item\">Energia potencial: " + ePotencial
				+ "</li><li class=\"list-group-item\">Potencial electrico: " + potencial + "<li></ul>");
		datos.add(elec.generarScatterGrafica());
		if (datos.get(1) == null || datos.get(0) == null) {
			return null;
		}
		return datos;
	}

	public ArrayList<Object> calcularResisp(String a1, String a2, String a3, String a4, String v, String u1, String u2,
			String u3, String u4, String uv) {
		ArrayList<Object> tmp = new ArrayList<>();
		cc = new CircuitoDAO(a1, a2, a3, a4, v, u1, u2, u3, u4, uv);
		tmp.add(cc.calcularResisParalelo());
		return tmp;
	}

	public ArrayList<Object> calcularResiss(String a1, String a2, String a3, String a4, String v, String u1, String u2,
			String u3, String u4, String uv) {
		ArrayList<Object> tmp = new ArrayList<>();
		cc = new CircuitoDAO(a1, a2, a3, a4, v, u1, u2, u3, u4, uv);
		tmp.add(cc.calcularResisSerie());
		return tmp;
	}

}
