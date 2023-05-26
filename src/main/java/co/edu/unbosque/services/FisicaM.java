package co.edu.unbosque.services;

import java.util.ArrayList;

import co.edu.unbosque.daos.AceleracionDAO;
import co.edu.unbosque.daos.FuerzasDAO;
import co.edu.unbosque.daos.ParabolaDAO;

public class FisicaM {

	private ParabolaDAO pardao;
	private AceleracionDAO aceldao;
	private FuerzasDAO fudao;

	public ArrayList<Object> expParabola(String vel, String ang) {
		pardao = new ParabolaDAO(vel, ang);
		return pardao.solucion();

	}
	
	public ArrayList<Object> expAceleracion(String acel){
		aceldao = new AceleracionDAO(Double.parseDouble(acel));
		return aceldao.solucion();
	}
	
	public ArrayList<Object> expFuerzas(String f1, String f2, String f3, String ang1, String ang2, String ang3){
		fudao = new FuerzasDAO(f1, f2, f3, ang1, ang2, ang3);
		return fudao.solucion();
	}

}
