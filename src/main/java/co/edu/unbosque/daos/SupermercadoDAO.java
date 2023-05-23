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

public class SupermercadoDAO implements IExperimentoMonteCarlo {

	private double esperaPromedio;

	private BarChartModel barModel;

	private double[] aproximaciones;

	public SupermercadoDAO() {
		this.esperaPromedio = 0;
		barModel = new BarChartModel();
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		int simulations = 1000;
		aproximaciones = new double[simulations];
		double tiempoDeEspera = 0;
		Random rand = new Random();

		for (int i = 0; i < simulations; i++) {
			double tiempoLlegada = variableConDistribucionExponencial(5, rand);
			double tiempoServicio = uniformRandomVariable(2, 20, rand);
			tiempoDeEspera += Math.max(0, tiempoServicio - tiempoLlegada);
			aproximaciones[i] = tiempoDeEspera / (i + 1);
		}
		tiempoDeEspera /= simulations;
		esperaPromedio = tiempoDeEspera;
		datos.add(
				"La espera promedio cuando el tiempo de llegada es de maximo 5 minutos y el tiempo de servicio es entre 2 y 20 minutos sera de: "
						+ esperaPromedio + " minutos.");
		datos.add(generarGrafica());
		return datos;
	}

	@Override
	public BarChartModel generarGrafica() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();
		BarChartDataSet barDataSet = new BarChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		Arrays.sort(aproximaciones);
		double amplitud = obtenerAmplitud(aproximaciones[0], aproximaciones[aproximaciones.length - 1]);
		double[] rangos = new double[9];
		double[] repeticiones = new double[10];
		rangos[0] = aproximaciones[0] + amplitud;
		for (int i = 1; i < 9; i++) {
			rangos[i] = rangos[i - 1] + amplitud;
		}
		for (int i = 0; i < aproximaciones.length; i++) {
			if (aproximaciones[i] < rangos[0]) {
				repeticiones[0]++;
			} else if (aproximaciones[i] < rangos[1]) {
				repeticiones[1]++;
			} else if (aproximaciones[i] < rangos[2]) {
				repeticiones[2]++;
			} else if (aproximaciones[i] < rangos[3]) {
				repeticiones[3]++;
			} else if (aproximaciones[i] < rangos[4]) {
				repeticiones[4]++;
			} else if (aproximaciones[i] < rangos[5]) {
				repeticiones[5]++;
			} else if (aproximaciones[i] < rangos[6]) {
				repeticiones[6]++;
			} else if (aproximaciones[i] < rangos[7]) {
				repeticiones[7]++;
			} else if (aproximaciones[i] < rangos[8]) {
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
		title.setText("Tiempos promedio de espera (En minutos) obtenidos por cada repeticion separados por rangos");
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

	private double variableConDistribucionExponencial(double lambda, Random rand) {
		return Math.log(1 - rand.nextDouble()) / (-lambda);
	}

	public static double uniformRandomVariable(double a, double b, Random rand) {
		return a + (b - a) * rand.nextDouble();
	}

}
