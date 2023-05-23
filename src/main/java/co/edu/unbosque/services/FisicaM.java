package co.edu.unbosque.services;

import java.util.ArrayList;

import co.edu.unbosque.daos.ParabolaDAO;

public class FisicaM {

	private ParabolaDAO pardao;

	public ArrayList<Object> expParabola(String vel, String ang) {
		pardao = new ParabolaDAO(vel, ang);
		return pardao.solucion();

	}

}
