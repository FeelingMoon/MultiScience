package co.edu.unbosque.daos;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.data.NumericPoint;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.scatter.ScatterChartModel;

public class FuerzasDAO {

	private double f1, f2, f3, ang1, ang2, ang3;
	private String resultado;
	private ScatterChartModel lineModel;

	public FuerzasDAO(String f1, String f2, String f3, String ang1, String ang2, String ang3) {
		super();

		this.f1 = Double.parseDouble(f1);
		this.f2 = Double.parseDouble(f2);
		this.f3 = Double.parseDouble(f3);
		this.ang1 = Double.parseDouble(ang1);
		this.ang2 = Double.parseDouble(ang2);
		this.ang3 = Double.parseDouble(ang3);
	}

	public ScatterChartModel createLineChart() {

		lineModel = new ScatterChartModel();
		LineChartDataSet dataSetF1 = new LineChartDataSet();
		LineChartDataSet dataSetF2 = new LineChartDataSet();
		LineChartDataSet dataSetF3 = new LineChartDataSet();
		LineChartDataSet dataSetFT = new LineChartDataSet();
		ChartData data = new ChartData();

		ang1 = (ang1 / 180) * Math.PI;
		ang2 = (ang2 / 180) * Math.PI;
		ang3 = (ang3 / 180) * Math.PI;

		double f1X = f1 * Math.cos(ang1);
		double f1Y = f1 * Math.sin(ang1);
		double f2X = f2 * Math.cos(ang2);
		double f2Y = f2 * Math.sin(ang2);
		double f3X = f3 * Math.cos(ang3);
		double f3Y = f3 * Math.sin(ang3);

		double fTX = f1X + f2X + f3X;
		double fTY = f1Y + f2Y + f3Y;

		double fT = Math.sqrt((fTX * fTX) + (fTY * fTY));

		double[] fuerzas = new double[8];
		fuerzas[0] = Math.abs(f1X);
		fuerzas[1] = Math.abs(f1Y);
		fuerzas[2] = Math.abs(f2X);
		fuerzas[3] = Math.abs(f2Y);
		fuerzas[4] = Math.abs(f3X);
		fuerzas[5] = Math.abs(f3Y);
		fuerzas[6] = Math.abs(fTX);
		fuerzas[7] = Math.abs(fTY);

		double max = maximo(fuerzas);

		List<Object> valuesF1 = new ArrayList<>();
		valuesF1.add(new NumericPoint(f1X, f1Y));
		valuesF1.add(new NumericPoint(0, 0));
		dataSetF1.setData(valuesF1);
		dataSetF1.setLabel("Fuerza 1");
		dataSetF1.setBorderColor("rgba(164, 17, 0, 0.3)");
		dataSetF1.setFill(false);
		dataSetF1.setPointRadius(0);
		data.addChartDataSet(dataSetF1);

		List<Object> valuesF2 = new ArrayList<>();
		valuesF2.add(new NumericPoint(f2X, f2Y));
		valuesF2.add(new NumericPoint(0, 0));
		dataSetF2.setData(valuesF2);
		dataSetF2.setLabel("Fuerza 2");
		dataSetF2.setBorderColor("rgba(47, 164, 0, 0.3)");
		dataSetF2.setFill(false);
		dataSetF2.setPointRadius(0);
		data.addChartDataSet(dataSetF2);

		List<Object> valuesF3 = new ArrayList<>();
		valuesF3.add(new NumericPoint(f3X, f3Y));
		valuesF3.add(new NumericPoint(0, 0));
		dataSetF3.setData(valuesF3);
		dataSetF3.setLabel("Fuerza 3");
		dataSetF3.setBorderColor("rgba(162, 0, 164, 0.3)");
		dataSetF3.setFill(false);
		dataSetF3.setPointRadius(0);
		data.addChartDataSet(dataSetF3);

		List<Object> valuesFT = new ArrayList<>();
		valuesFT.add(new NumericPoint(fTX, fTY));
		valuesFT.add(new NumericPoint(0, 0));
		dataSetFT.setData(valuesFT);
		dataSetFT.setLabel("Fuerza Total");
		dataSetFT.setBorderColor("rgba(0, 164, 114, 0.3)");
		dataSetFT.setFill(false);
		dataSetFT.setPointRadius(0);
		data.addChartDataSet(dataSetFT);

		LineChartDataSet dataSetY = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, max + 0.2));
		values2.add(new NumericPoint(0, -max - 0.2));
		dataSetY.setData(values2);
		dataSetY.setLabel("Eje Y");
		dataSetY.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSetY.setFill(false);
		dataSetY.setPointRadius(0);
		data.addChartDataSet(dataSetY);

		LineChartDataSet dataSetX = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(max + 0.2, 0));
		values3.add(new NumericPoint(-max - 0.2, 0));
		dataSetX.setData(values3);
		dataSetX.setLabel("Eje X");
		dataSetX.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSetX.setFill(false);
		dataSetX.setPointRadius(0);
		data.addChartDataSet(dataSetX);

		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Funcion");
		options.setShowLines(true);
		options.setTitle(title);
		CartesianScales cScales = new CartesianScales();
		CartesianLinearAxes linearAxes = new CartesianLinearAxes();
		linearAxes.setType("linear");
		linearAxes.setPosition("bottom");
		cScales.addXAxesData(linearAxes);
		options.setScales(cScales);
		lineModel.setOptions(options);
		lineModel.setData(data);

		resultado = "La fuerza total es: " + fT + " $N$";

		return lineModel;

	}

	public double maximo(double[] f) {
		// TODO Auto-generated method stub

		double max = 0;

		for (int i = 0; i < 8; i++) {
			if (f[i] > max) {
				max = f[i];
			}
		}

		return max;

	}

	public ArrayList<Object> solucion() {

		ArrayList<Object> datos = new ArrayList<>();

		datos.add(createLineChart());
		datos.add(resultado);

		return datos;

	}

}
