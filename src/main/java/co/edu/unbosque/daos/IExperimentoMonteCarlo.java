package co.edu.unbosque.daos;

import java.util.ArrayList;

import org.primefaces.model.charts.bar.BarChartModel;

public interface IExperimentoMonteCarlo {

	public ArrayList<Object> calcular();

	public BarChartModel generarGrafica();

}
