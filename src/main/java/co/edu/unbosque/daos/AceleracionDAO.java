package co.edu.unbosque.daos;

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

public class AceleracionDAO {
	
	private double acel,pos;
	private String funcV, funcP;
	private ScatterChartModel lineModel;
	
	public AceleracionDAO(double acel) {
		
		this.acel = acel;
		this.pos = acel/2;
		this.funcV = acel + "x";
		this.funcP = pos + "x^2";
		lineModel = new ScatterChartModel();
		
	}
	
	public ScatterChartModel createLineChart() {
		
		LineChartDataSet dataSetVel = new LineChartDataSet();
		LineChartDataSet dataSetPos = new LineChartDataSet();
		ExprEvaluator eval = new ExprEvaluator();
		ChartData data = new ChartData();
		IExpr ecV = eval.eval("Cancel(" + funcV + ")");
		IExpr ecP = eval.eval("Cancel(" + funcP + ")");
		List<Object> posValues = new ArrayList<>();
		List<Object>velValues = new ArrayList<>();
		
		double y1 = 0;
		double y2 = 0;
		double i = 0;
		double maxH = 40;
		
		if(acel >= 40) {
			maxH = acel;
		}
		
		while(i <= 40 && y1 <= maxH) {
			try {
				eval.defineVariable("x", i);
				y1 = eval.eval(ecV).evalDouble();
				velValues.add(new NumericPoint(i, y1));
				i += 0.01;
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		
		dataSetVel.setData(velValues);
		dataSetVel.setLabel("Velocidad");
		dataSetVel.setBorderColor("rgba(153, 102, 255, 1)");
		dataSetVel.setFill(false);
		dataSetVel.setTension(0.5);
		dataSetVel.setPointRadius(0);
		data.addChartDataSet(dataSetVel);
		i = 0;
		
		
		while(i <= 40 && y2 <= maxH) {
			try {
				eval.defineVariable("x", i);
				y2 = eval.eval(ecP).evalDouble();
				posValues.add(new NumericPoint(i, y2));
				i += 0.01;
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		
		dataSetPos.setData(posValues);
		dataSetPos.setLabel("Posicion");
		dataSetPos.setBorderColor("rgba(164, 17, 0, 1)");
		dataSetPos.setFill(false);
		dataSetPos.setTension(0.5);
		dataSetPos.setPointRadius(0);
		data.addChartDataSet(dataSetPos);
		
		LineChartDataSet dataSetAcel = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		values.add(new NumericPoint(0, acel));
		values.add(new NumericPoint(maxH, acel));
		dataSetAcel.setData(values);
		dataSetAcel.setLabel("Aceleracion");
		dataSetAcel.setBorderColor("rgba(0, 142, 164, 0.3)");
		dataSetAcel.setFill(false);
		dataSetAcel.setPointRadius(0);
		data.addChartDataSet(dataSetAcel);
		
		
		
		LineChartDataSet dataSetY = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, maxH));
		values2.add(new NumericPoint(0, 0));
		dataSetY.setData(values2);
		dataSetY.setLabel("Eje Y");
		dataSetY.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSetY.setFill(false);
		dataSetY.setPointRadius(0);
		data.addChartDataSet(dataSetY);

		LineChartDataSet dataSetX = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(maxH, 0));
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
	
	public ArrayList<Object> solucion(){
		
		ArrayList<Object> datos = new ArrayList<>();
		datos.add(createLineChart());
		datos.add("Aceleracion: " + acel);
		return datos;
		
	}

}
