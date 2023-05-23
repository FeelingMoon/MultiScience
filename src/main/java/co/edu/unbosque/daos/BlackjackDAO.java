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

public class BlackjackDAO implements IExperimentoMonteCarlo {

	private BarChartModel barModel;
	private int[] victorias;

	public BlackjackDAO() {
		barModel = new BarChartModel();
		victorias = new int[4];
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		int intentos = 1000;
		victorias = new int[4];
		Random rand = new Random();
		// Realizar los intentos
		for (int i = 0; i < intentos; i++) {
			// Crear y barajar la baraja de cartas
			int[] baraja = new int[52];
			for (int j = 0; j < baraja.length; j++) {
				baraja[j] = j;
			}
			for (int j = 0; j < baraja.length; j++) {
				int indice = rand.nextInt(baraja.length);
				int temp = baraja[j];
				baraja[j] = baraja[indice];
				baraja[indice] = temp;
			}
			// Repartir cartas al jugador hasta que su total sea mayor o igual a 21
			int totalJugador = 0;
			int indiceCarta = 0;
			boolean tieneAs = false;
			while (totalJugador < 21) {
				int valorCarta = baraja[indiceCarta] % 13 + 1;
				if (valorCarta > 10) {
					valorCarta = 10;
				} else if (valorCarta == 1) {
					tieneAs = true;
					valorCarta = 11;
				}
				totalJugador += valorCarta;
				if (totalJugador > 21 && tieneAs) {
					totalJugador -= 10;
					tieneAs = false;
				}
				indiceCarta++;
			}
			// Si el total es exactamente 21 y el jugador tiene entre 2 y 5 cartas, se
			// cuenta como una victoria
			if (totalJugador == 21 && indiceCarta >= 2 && indiceCarta <= 5) {
				victorias[indiceCarta - 2]++;
			}
		}
		double probabilidad = (double) victorias[0] / intentos * 100;
		datos.add("La probabilidad de ganar en Blackjack con 2 cartas en " + intentos + " intentos es: " + probabilidad
				+ "%");
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
		for (int i = 0; i < victorias.length; i++) {
			values.add(victorias[i]);
			labels.add((i + 2) + " cartas.");
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
		title.setText("Veces ganadas con la menor cantidad de cartas posible");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

}
