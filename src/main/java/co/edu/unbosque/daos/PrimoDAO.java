package co.edu.unbosque.daos;

import java.util.ArrayList;
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

public class PrimoDAO implements IExperimentoMonteCarlo {

	private BarChartModel barModel;
	private ArrayList<Integer> repeticiones;

	public PrimoDAO() {
		barModel = new BarChartModel();
		repeticiones = new ArrayList<>();
	}

	@Override
	public ArrayList<Object> calcular() {
		repeticiones = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			repeticiones.add(0);
		}
		ArrayList<Object> data = new ArrayList<>();
		int trials = 1000000;
		int primes = 0;
		Random rand = new Random();
		for (int i = 0; i < trials; i++) {
			int num = rand.nextInt(16);
			if (isPrime(num)) {
				primes++;
				if (num == 2) {
					repeticiones.set(0, repeticiones.get(0) + 1);
				} else if (num == 3) {
					repeticiones.set(1, repeticiones.get(1) + 1);
				} else if (num == 5) {
					repeticiones.set(2, repeticiones.get(2) + 1);
				} else if (num == 7) {
					repeticiones.set(3, repeticiones.get(3) + 1);
				} else if (num == 11) {
					repeticiones.set(4, repeticiones.get(4) + 1);
				} else {
					repeticiones.set(5, repeticiones.get(5) + 1);
				}
			}
		}
		double probability = (double) primes / trials * 100;
		data.add("La probabilidad de que un número aleatorio sea primo es: " + probability + "%");
		data.add(generarGrafica());
		return data;
	}

	@Override
	public BarChartModel generarGrafica() {
		barModel = new BarChartModel();
		ChartData data = new ChartData();
		BarChartDataSet barDataSet = new BarChartDataSet();
		List<Number> values = new ArrayList<>();
		List<String> labels = new ArrayList<>();
		for (int i = 0; i < repeticiones.size(); i++) {
			values.add(repeticiones.get(i));
		}
		labels.add("numero 2");
		labels.add("numero 3");
		labels.add("numero 5");
		labels.add("numero 7");
		labels.add("numero 11");
		labels.add("numero 13");
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
		title.setText("Repeticiones de numeros generados");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

	public static boolean isPrime(int n) {
		if (n <= 1)
			return false;
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (n % i == 0)
				return false;
		}
		return true;
	}

}
