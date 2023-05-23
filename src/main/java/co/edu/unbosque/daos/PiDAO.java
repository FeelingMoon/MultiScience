package co.edu.unbosque.daos;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.data.NumericPoint;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;
import org.primefaces.model.charts.scatter.ScatterChartModel;

public class PiDAO implements IExperimentoMonteCarlo {

	private ScatterChartModel scatterModel;

	public PiDAO() {
		scatterModel = new ScatterChartModel();
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		List<Object> values = new ArrayList<>();
		int puntos = 1000000;
		int puntosCirculo = 0;

		for (int i = 0; i < puntos; i++) {
			double x = 2 * Math.random() - 1; // genera x en el rango de -1 a 1
			double y = 2 * Math.random() - 1; // genera y en el rango de -1 a 1
			if (x * x + y * y <= 1) {
				puntosCirculo++;
				if (i < 2000) {
					values.add(new NumericPoint(x, y));
				}
			}
		}

		double piAproximado = 4.0 * puntosCirculo / puntos;
		datos.add("La aproximación de pi dio como resultado: " + piAproximado);
		datos.add(generarScatterGrafica(values));
		return datos;
	}

	@Override
	public BarChartModel generarGrafica() {
		return null;
	}

	public ScatterChartModel generarScatterGrafica(List<Object> values) {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSet = new LineChartDataSet();
		dataSet.setData(values);
		dataSet.setLabel("Circulo generado");
		dataSet.setBorderColor("rgba(153, 102, 255, 1)");
		dataSet.setBackgroundColor("rgba(0,0,0,0.4)");
		dataSet.setFill(false);
		dataSet.setShowLine(false);
		dataSet.setPointRadius(6);
		data.addChartDataSet(dataSet);

		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Circulo generado por los puntos obtenidos");
		options.setTitle(title);
		options.setShowLines(false);
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setType("linear");
		linearAxes.setPosition("bottom");
		cScales.addXAxesData(linearAxes);
		options.setScales(cScales);
		Tooltip tol = new Tooltip();
		tol.setEnabled(false);
		options.setTooltip(tol);
		scatterModel.setOptions(options);
		scatterModel.setData(data);
		return scatterModel;
	}

}
