package co.edu.unbosque.services;

import java.io.IOException;
import java.util.ArrayList;

import org.primefaces.model.file.UploadedFile;

import co.edu.unbosque.daos.BlackjackDAO;
import co.edu.unbosque.daos.ColasDAO;
import co.edu.unbosque.daos.DadosDAO;
import co.edu.unbosque.daos.DerivarDAO;
import co.edu.unbosque.daos.EnfermedadDAO;
import co.edu.unbosque.daos.FrecuenciasDAO;
import co.edu.unbosque.daos.IntegralDAO;
import co.edu.unbosque.daos.InternetDAO;
import co.edu.unbosque.daos.MonedaDAO;
import co.edu.unbosque.daos.PiDAO;
import co.edu.unbosque.daos.PrimoDAO;
import co.edu.unbosque.daos.SupermercadoDAO;
import co.edu.unbosque.persistence.FrecuenciasDTO;

public class Experimentos {
	private SupermercadoDAO supermercado;
	private IntegralDAO integral;
	private EnfermedadDAO enfermedad;
	private DadosDAO dados;
	private PiDAO pi;
	private ColasDAO colas;
	private PrimoDAO primo;
	private BlackjackDAO blackjack;
	private MonedaDAO moneda;
	private InternetDAO internet;
	private DerivarDAO derivar;
	private FrecuenciasDAO freq;

	public ArrayList<Object> expSupermercado() {
		supermercado = new SupermercadoDAO();
		return supermercado.calcular();
	}

	public ArrayList<Object> expIntegralAprox(String funcion, String rango) {
		integral = new IntegralDAO(funcion, rango);
		return integral.calcularAprox();
	}

	public ArrayList<Object> expEnfermedad(String tasaInfeccion, String tasaRecuperacion) {
		enfermedad = new EnfermedadDAO(tasaInfeccion, tasaRecuperacion);
		return enfermedad.calcular();
	}

	public ArrayList<Object> expDados() {
		dados = new DadosDAO();
		return dados.calcular();
	}

	public ArrayList<Object> expPi() {
		pi = new PiDAO();
		return pi.calcular();
	}

	public ArrayList<Object> expColas() {
		colas = new ColasDAO();
		return colas.calcular();
	}

	public ArrayList<Object> expPrimo() {
		primo = new PrimoDAO();
		return primo.calcular();
	}

	public ArrayList<Object> expBlackJack() {
		blackjack = new BlackjackDAO();
		return blackjack.calcular();
	}

	public ArrayList<Object> expMoneda() {
		moneda = new MonedaDAO();
		return moneda.calcular();
	}

	public ArrayList<Object> expInternet(String distancia, String viabilidad) {
		internet = new InternetDAO(distancia, viabilidad);
		return internet.calcular();
	}

	public ArrayList<Object> integralDef(String funcion, String rango) {
		integral = new IntegralDAO(funcion, rango);
		return integral.calcular();
	}

	public ArrayList<Object> integralIndef(String funcion) {
		integral = new IntegralDAO(funcion, "");
		return integral.calcularIndef();
	}

	public ArrayList<Object> derivada(String funcion) {
		derivar = new DerivarDAO(funcion, "");
		return derivar.calcular();
	}

	public ArrayList<Object> lim(String funcion, String rango) {
		derivar = new DerivarDAO(funcion, rango);
		return derivar.calcularLim();
	}

	public ArrayList<Object> rectTang(String funcion, String rango) {
		derivar = new DerivarDAO(funcion, rango);
		return derivar.calcularRectaTang();
	}

	public ArrayList<FrecuenciasDTO> tablaFrecuenciasSinRangoXLSX(UploadedFile file) {
		freq = new FrecuenciasDAO();
		try {
			return freq.calcularConExcelSr(file.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}

	public ArrayList<FrecuenciasDTO> tablaFrecuenciasConRangoXLSX(UploadedFile file) {
		freq = new FrecuenciasDAO();
		try {
			return freq.calcularConExcelCr(file.getInputStream());
		} catch (IOException e) {
			return null;
		}
	}

	public ArrayList<FrecuenciasDTO> tablaFrecuenciasConRangoTexto(String datos) {
		freq = new FrecuenciasDAO();
		try {
			return freq.calcularConTextoCr(datos);
		} catch (Exception e) {
			return null;
		}
	}

	public ArrayList<FrecuenciasDTO> tablaFrecuenciasSinRangoTexto(String datos) {
		freq = new FrecuenciasDAO();
		try {
			return freq.calcularConTextoSr(datos);
		} catch (Exception e) {
			return null;
		}
	}
}
