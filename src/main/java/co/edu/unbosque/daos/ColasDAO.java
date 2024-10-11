package co.edu.unbosque.daos;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;

public class ColasDAO implements IExperimentoMonteCarlo {
	
	private BarChartModel barModel;
	private double[] promedio;

	public ColasDAO() {
		barModel = new BarChartModel();
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		double tasaLlegada = 0.5;
		double tasaSalida = 0.4;
		int iteraciones = 1000;
		promedio = new double[iteraciones];
		Queue<Integer> cola = new LinkedList<>();
		double tiempoEsperaTotal = 0;
		for (int i = 0; i < iteraciones; i++) {
			if (Math.random() < tasaLlegada) {
				cola.add(i);
			}
			if (!cola.isEmpty() && Math.random() < tasaSalida) {
				int tiempoLlegada = cola.remove();
				tiempoEsperaTotal += i - tiempoLlegada;
			}
			promedio[i] = tiempoEsperaTotal;
		}
		double tiempoPromedioEspera = tiempoEsperaTotal / iteraciones;
		datos.add("Tiempo promedio de espera: " + tiempoPromedioEspera + " minutos.");
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
		Arrays.sort(promedio);
		double amplitud = obtenerAmplitud(promedio[0], promedio[promedio.length - 1]);
		double[] rangos = new double[4];
		double[] repeticiones = new double[5];
		rangos[0] = promedio[0] + amplitud;

		for (int i = 1; i < 4; i++) {
			rangos[i] = rangos[i - 1] + amplitud;
		}
		for (int i = 0; i < promedio.length; i++) {
			if (promedio[i] < rangos[0]) {
				repeticiones[0]++;
			} else if (promedio[i] < rangos[1]) {
				repeticiones[1]++;
			} else if (promedio[i] < rangos[2]) {
				repeticiones[2]++;
			} else if (promedio[i] < rangos[3]) {
				repeticiones[3]++;
			} else {
				repeticiones[4]++;
			}
		}
		values.add((repeticiones[0] / promedio.length) * 100);
		values.add((repeticiones[1] / promedio.length) * 100);
		values.add((repeticiones[2] / promedio.length) * 100);
		values.add((repeticiones[3] / promedio.length) * 100);
		values.add((repeticiones[4] / promedio.length) * 100);
		labels.add("< " + (Math.round(rangos[0] * 100.0) / 1000.000));
		labels.add("[" + (Math.round(rangos[0] * 1000.000) / 1000.000) + ", "
				+ (Math.round(rangos[1] * 1000.000) / 1000.000) + "]");
		labels.add("[" + (Math.round(rangos[1] * 1000.000) / 1000.000) + ", "
				+ (Math.round(rangos[2] * 1000.000) / 1000.000) + "]");
		labels.add("[" + (Math.round(rangos[2] * 1000.000) / 1000.000) + ", "
				+ (Math.round(rangos[3] * 1000.000) / 1000.000) + "]");
		labels.add("> " + (Math.round(rangos[3] * 1000.000) / 1000.000));
		barDataSet.setData(values);
		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(255, 206, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");
		barDataSet.setBackgroundColor(bgColor);
		List<String> borderColor = new ArrayList<>();
		borderColor.add("rgba(255, 99, 132, 1)");
		borderColor.add("rgba(54, 162, 235, 1)");
		borderColor.add("rgba(255, 206, 86, 1)");
		borderColor.add("rgba(75, 192, 192, 1)");
		borderColor.add("rgba(153, 102, 255, 1)");
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
		title.setText(
				"Pobabilidad de obtener un tiempo de espera de la cola durante la simulacion separados por rango.");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

	private double obtenerAmplitud(double min, double max) {
		return (max - min) / 5;
	}
}
