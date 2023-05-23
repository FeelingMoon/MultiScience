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

public class EnfermedadDAO implements IExperimentoMonteCarlo {

	private String tasaInfeccionS;
	private String tasaRecuperacionS;
	private BarChartModel barModel;
	private double[] infectado;

	public EnfermedadDAO(String tasaInfeccionS, String tasaRecuperacionS) {
		barModel = new BarChartModel();
		this.tasaInfeccionS = tasaInfeccionS;
		this.tasaRecuperacionS = tasaRecuperacionS;
	}

	@Override
	public ArrayList<Object> calcular() {
		try {
			ArrayList<Object> datos = new ArrayList<>();
			int tamanoPoblacion = 1000;
			double tasaInfeccion = Double.parseDouble(tasaInfeccionS);
			double tasaRecuperacion = Double.parseDouble(tasaRecuperacionS);
			int duracionSimulacion = 1000;
			infectado = new double[duracionSimulacion];
			infectado[0] = 1;
			boolean[] infectados = new boolean[tamanoPoblacion];
			infectados[0] = true; // infectar a un individuo al inicio
			Random aleatorio = new Random();
			for (int t = 1; t < duracionSimulacion; t++) {
				int numeroInfectados = 0;
				for (int i = 0; i < tamanoPoblacion; i++) {
					if (infectados[i]) {
						numeroInfectados++;
						if (aleatorio.nextDouble() < tasaRecuperacion) {
							infectados[i] = false;
							numeroInfectados--;
						}
					} else {
						if (aleatorio.nextDouble() < tasaInfeccion) {
							infectados[i] = true;
						}
					}
				}
				infectado[t] = numeroInfectados;
			}
			datos.add("El numero de infectados tras pasar 1.000 dias en una poblacion de 1.000 es: "
					+ infectado[infectado.length - 1]);
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
		Arrays.sort(infectado);
		double amplitud = obtenerAmplitud(infectado[0], infectado[infectado.length - 1]);
		double[] rangos = new double[9];
		double[] repeticiones = new double[10];
		rangos[0] = infectado[0] + amplitud;

		for (int i = 1; i < 9; i++) {
			rangos[i] = rangos[i - 1] + amplitud;
		}
		for (int i = 0; i < infectado.length; i++) {
			if (infectado[i] < rangos[0]) {
				repeticiones[0]++;
			} else if (infectado[i] < rangos[1]) {
				repeticiones[1]++;
			} else if (infectado[i] < rangos[2]) {
				repeticiones[2]++;
			} else if (infectado[i] < rangos[3]) {
				repeticiones[3]++;
			} else if (infectado[i] < rangos[4]) {
				repeticiones[4]++;
			} else if (infectado[i] < rangos[5]) {
				repeticiones[5]++;
			} else if (infectado[i] < rangos[6]) {
				repeticiones[6]++;
			} else if (infectado[i] < rangos[7]) {
				repeticiones[7]++;
			} else if (infectado[i] < rangos[8]) {
				repeticiones[8]++;
			} else {
				repeticiones[9]++;
			}
		}
		values.add(repeticiones[0] / 10);
		values.add(repeticiones[1] / 10);
		values.add(repeticiones[2] / 10);
		values.add(repeticiones[3] / 10);
		values.add(repeticiones[4] / 10);
		values.add(repeticiones[5] / 10);
		values.add(repeticiones[6] / 10);
		values.add(repeticiones[7] / 10);
		values.add(repeticiones[8] / 10);
		values.add(repeticiones[9] / 10);
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
		title.setText("Porcentaje de probabilidad de obtener infectados segun la cantidad");
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
