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

public class DadosDAO implements IExperimentoMonteCarlo {

	private BarChartModel barModel;
	private int[] repeticiones;

	public DadosDAO() {
		super();
		this.barModel = new BarChartModel();
		this.repeticiones = new int[11];
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		Random r = new Random();
		double numLanzamientos = 1000;
		repeticiones = new int[11];
		double pares = 0;
		for (int i = 0; i < numLanzamientos; i++) {
			int dado1 = r.nextInt(6) + 1;
			int dado2 = r.nextInt(6) + 1;
			int suma = dado1 + dado2;
			repeticiones[suma - 2]++;
			if (suma % 2 == 0) {
				pares++;
			}
		}
		double tmp = pares / numLanzamientos;
		datos.add("La proporcion de pares que salieron fue de: " + (tmp * 100) + "% pares.");
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
		for (int i = 0; i < repeticiones.length; i++) {
			values.add(repeticiones[i]);
			labels.add("suma " + (i + 2));
		}
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
		title.setText("Repeticiones de la suma de los dados");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

}
