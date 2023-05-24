package co.edu.unbosque.daos;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.data.NumericPoint;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.scatter.ScatterChartModel;

public class ParabolaDAO {

	private ScatterChartModel lineModel;
	private double vel;
	private double ang;
	private double i;
	private double maxY;
	private DecimalFormat format;
	private String fun;

	public ParabolaDAO(String vel, String ang) {

		this.vel = Double.parseDouble(vel);
		this.ang = Double.parseDouble(ang);
		this.fun = funcion();
		this.maxY = 0;
		this.i = 0;
		System.out.println(fun);
		this.lineModel = new ScatterChartModel();
		this.format = new DecimalFormat("#.##");

	}

	public String funcion() {

		double rad = convertirRads(ang);
		double aux = vel * Math.cos(rad);
		double aux2 = vel * Math.sin(rad);
		return (-4.9 / (aux * aux)) + "*x^2+" + aux2 / aux + "*x";

	}

	public double convertirRads(double ang2) {
		return (ang * Math.PI) / 180;
	}

	public ScatterChartModel createLineChart() {

		lineModel = new ScatterChartModel();
		LineChartDataSet dataSet = new LineChartDataSet();
		ExprEvaluator eval = new ExprEvaluator();
		ChartData data = new ChartData();
		IExpr ec = eval.eval("Cancel(" + fun + ")");
		List<Object> values = new ArrayList<>();
		maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		i = 0;
		double y = 0;
		
		while(y >= 0) {
			try {
				eval.defineVariable("x", i);
				y = eval.eval(ec).evalDouble();
				values.add(new NumericPoint(i, y));
				i += 0.01;
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}

		for (Object x : values) {
			double tmpY = (double) ((NumericPoint) x).getY();
			if (tmpY > maxY) {
				maxY = tmpY;
			}
			if (tmpY < minY) {
				minY = tmpY;
			}
		}

		dataSet.setData(values);
		dataSet.setLabel("Movimiento parabolico");
		dataSet.setBorderColor("rgba(153, 102, 255, 1)");
		dataSet.setFill(false);
		dataSet.setTension(0.5);
		dataSet.setPointRadius(0);
		data.addChartDataSet(dataSet);

		LineChartDataSet dataSetY = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, maxY));
		values2.add(new NumericPoint(0, minY));
		dataSetY.setData(values2);
		dataSetY.setLabel("Eje Y");
		dataSetY.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSetY.setFill(false);
		dataSetY.setPointRadius(0);
		data.addChartDataSet(dataSetY);

		LineChartDataSet dataSetX = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(i, 0));
		values3.add(new NumericPoint(0, 0));
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

		return lineModel;

	}

	public ArrayList<Object> solucion() {

		ArrayList<Object> datos = new ArrayList<>();
		
		datos.add(createLineChart());
		datos.add("La trayectoria parabolica dada con velocidad" + vel + "$m/s$ y angulo de" + ang +
				"°, se muestra en la siguiente grafica, con altura maxima de " + format.format(maxY) + " metros y recorrio una distancia horinzontal de " 
				+ format.format(i) + " metros.");
		

		return datos;

	}

}
