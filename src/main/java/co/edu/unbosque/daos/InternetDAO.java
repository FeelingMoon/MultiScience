package co.edu.unbosque.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

public class InternetDAO implements IExperimentoMonteCarlo {

	private String dist;
	private String viabilidad;
	private double[] latencias;
	private BarChartModel barModel;

	public InternetDAO(String distancia, String viabilidad) {
		this.dist = distancia;
		this.viabilidad = viabilidad;
		latencias = new double[1000];
		barModel = new BarChartModel();
	}

	@Override
	public ArrayList<Object> calcular() {
		try {
			ArrayList<Object> datos = new ArrayList<>();
			int repeticiones = 1000;
			latencias = new double[repeticiones];
			double distancia = Double.parseDouble(dist); // Distancia en kilómetros entre origen y destino
			double velocidadTransmision = 299792458 / 1.5; // Velocidad de transmisión de la señal en m/s
			double variabilidadLatencia = Double.parseDouble(viabilidad); // Variabilidad de la latencia en milisegundos
			double latenciaPromedio = 0;

			Random rnd = new Random();

			for (int i = 0; i < repeticiones; i++) {
				double tiempoTransmision = distancia / velocidadTransmision;
				double variabilidad = rnd.nextDouble() * variabilidadLatencia * 2 - variabilidadLatencia;
				double latencia = tiempoTransmision * 1000 + variabilidad;

				latenciaPromedio += latencia;
				latencias[i] = latencia;
			}

			latenciaPromedio /= repeticiones;

			datos.add("Estimación de la latencia promedio: " + latenciaPromedio + " ms");
			datos.add(generarGrafica());
			return datos;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public BarChartModel generarGrafica() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();
		BarChartDataSet barDataSet = new BarChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		Arrays.sort(latencias);
		double amplitud = obtenerAmplitud(latencias[0], latencias[latencias.length - 1]);
		double[] rangos = new double[9];
		double[] repeticiones = new double[10];
		rangos[0] = latencias[0] + amplitud;

		for (int i = 1; i < 9; i++) {
			rangos[i] = rangos[i - 1] + amplitud;
		}
		for (int i = 0; i < latencias.length; i++) {
			if (latencias[i] < rangos[0]) {
				repeticiones[0]++;
			} else if (latencias[i] < rangos[1]) {
				repeticiones[1]++;
			} else if (latencias[i] < rangos[2]) {
				repeticiones[2]++;
			} else if (latencias[i] < rangos[3]) {
				repeticiones[3]++;
			} else if (latencias[i] < rangos[4]) {
				repeticiones[4]++;
			} else if (latencias[i] < rangos[5]) {
				repeticiones[5]++;
			} else if (latencias[i] < rangos[6]) {
				repeticiones[6]++;
			} else if (latencias[i] < rangos[7]) {
				repeticiones[7]++;
			} else if (latencias[i] < rangos[8]) {
				repeticiones[8]++;
			} else {
				repeticiones[9]++;
			}
		}
		values.add(repeticiones[0]);
		values.add(repeticiones[1]);
		values.add(repeticiones[2]);
		values.add(repeticiones[3]);
		values.add(repeticiones[4]);
		values.add(repeticiones[5]);
		values.add(repeticiones[6]);
		values.add(repeticiones[7]);
		values.add(repeticiones[8]);
		values.add(repeticiones[9]);
		labels.add("< " + (Math.round(rangos[0] * 10) / 10));
		labels.add("[" + (Math.round(rangos[0] * 10) / 10) + ", " + (Math.round(rangos[1] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[1] * 10) / 10) + ", " + (Math.round(rangos[2] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[2] * 10) / 10) + ", " + (Math.round(rangos[3] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[3] * 10) / 10) + ", " + (Math.round(rangos[4] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[4] * 10) / 10) + ", " + (Math.round(rangos[5] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[5] * 10) / 10) + ", " + (Math.round(rangos[6] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[6] * 10) / 10) + ", " + (Math.round(rangos[7] * 10) / 10) + "]");
		labels.add("[" + (Math.round(rangos[7] * 10) / 10) + ", " + (Math.round(rangos[8] * 10) / 10) + "]");
		labels.add("> " + (Math.round(rangos[8] * 10) / 10));
		barDataSet.setData(values);
		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(255, 206, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(99, 255, 132, 0.2)");
		bgColor.add("rgba(162, 54, 235, 0.2)");
		bgColor.add("rgba(206, 255, 86, 0.2)");
		bgColor.add("rgba(192, 75, 192, 0.2)");
		barDataSet.setBackgroundColor(bgColor);
		List<String> borderColor = new ArrayList<>();
		borderColor.add("rgba(255, 99, 132, 1)");
		borderColor.add("rgba(54, 162, 235, 1)");
		borderColor.add("rgba(255, 206, 86, 1)");
		borderColor.add("rgba(75, 192, 192, 1)");
		borderColor.add("rgba(153, 102, 255, 1)");
		borderColor.add("rgba(255, 159, 64, 1)");
		borderColor.add("rgba(99, 255, 132, 1)");
		borderColor.add("rgba(162, 54, 235, 1)");
		borderColor.add("rgba(206, 255, 86, 1)");
		borderColor.add("rgba(192, 75, 192, 1)");
		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);
		data.addChartDataSet(barDataSet);
		data.setLabels(labels);
		barModel.setData(data);
		BarChartOptions options = new BarChartOptions();
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setOffset(true);
		linearAxes.setBeginAtZero(false);
		CartesianLinearTicks ticks = new CartesianLinearTicks();
		linearAxes.setTicks(ticks);
		cScales.addYAxesData(linearAxes);
		options.setScales(cScales);
		Title title = new Title();
		title.setDisplay(true);
		title.setFontSize(20);
		title.setText("Latencias obtenidas por cada repeticion separadas por rangos");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

	private double obtenerAmplitud(double min, double max) {
		return (max - min) / 10;
	}
}
