package co.edu.unbosque.daos;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TeXUtilities;
import org.matheclipse.core.form.tex.TeXParser;
import org.matheclipse.core.interfaces.IExpr;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.data.NumericPoint;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.scatter.ScatterChartModel;

import co.edu.unbosque.services.Datos;

public class DerivarDAO implements IExperimentoMonteCarlo {

	private String funcion, tiende;
	private ScatterChartModel scatterModel;

	public DerivarDAO(String funcion, String tiende) {
		this.funcion = funcion;
		this.tiende = tiende;
		scatterModel = new ScatterChartModel();
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			StringWriter writer = new StringWriter();
			TeXUtilities texUtil = new TeXUtilities(util.getEvalEngine(), false);
			IExpr e = util.eval("D(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
			texUtil.toTeX(e, writer);
			datos.add("La derivada de la funcion $" + funcion + "$ dio como resultado $" + writer.toString() + "$");
			datos.add(generarScatterGrafica(util,
					util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"), e));
		} catch (Exception e) {
			datos.add("La derivada no se pudo solucionar.");
			datos.add(Datos.getScatterGraphicDefault());
		}
		return datos;
	}

	// -1 derecha - 1 izquierda
	public ArrayList<Object> calcularLim() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			String[] tn = tiende.split(",");
			System.out.println(parser.toExpression(funcion.replace("\\cdot", "*")).toString());
			String c = null;
			if (tn[0].contains("Infinity")) {
				c = tn[0];
			} else {
				c = Double.parseDouble(tn[0]) + "";
			}
			int dir = Integer.parseInt(tn[1]);
			if (dir == 0) {
				IExpr b = util.eval("Limit[ "
						+ util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")")
								.toString()
						+ ", x -> " + c + "]");
				System.out.println(b.toString());
				datos.add("El limite de la funcion $" + funcion + "$ cuando x tiende a $"
						+ c.replace("Infinity", "\\infty") + "$ dio como resultado $"
						+ b.toString().replace("Infinity", "\\infty") + "$");
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"),
						-30, 30, true));
			} else {
				IExpr b = util.eval("Limit[ "
						+ util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")")
								.toString()
						+ ", x -> " + c + ", Direction -> " + dir + "]");
				System.out.println(b.toString());
				String direccion = "";
				if (dir < 0) {
					direccion = "la derecha";
				} else if (dir > 0) {
					direccion = "la izquierda";
				}
				datos.add("El limite de la funcion $" + funcion + "$ cuando x tiende a $"
						+ c.replace("Infinity", "\\infty") + "$ por " + direccion + " dio como resultado $"
						+ b.toString().replace("Infinity", "\\infty") + "$");
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"),
						-30, 30, true));
			}
		} catch (Exception e) {
			datos = null;
		}
		return datos;
	}

	public ArrayList<Object> calcularRectaTang() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			System.out.println(parser.toExpression(funcion.replace("\\cdot", "*")).toString());
			double x = Double.parseDouble(tiende.split(",")[0]);
			double y = Double.parseDouble(tiende.split(",")[1]);
			System.out.println("x=" + x + ", y=" + y + ".");
			IExpr derivada = util.eval("D(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
			System.out.println("Derivada=" + derivada.toString());
			util.defineVariable("x", x);
			double m = util.eval(derivada).evalDouble();
			util.clearVariables();
			System.out.println("Pendiente=" + m + ".");
			double b = Double.parseDouble(util.eval("Solve[ " + (m * x) + " + x == " + y + ", x]").toString()
					.replace("{{", "").replace("}}", "").split("->")[1]);
			IExpr tang = util.eval("Cancel(" + m + "x+" + b + ")");
			System.out.println("Recta tangente=" + tang.toString());
			StringWriter writer = new StringWriter();
			TeXUtilities texUtil = new TeXUtilities(util.getEvalEngine(), false);
			texUtil.toTeX(tang, writer);
			datos.add("La recta tangente de la funcion $" + funcion + "$ dio como resultado $y = " + writer.toString()
					+ "$");
			datos.add(generarScatterGraficaTang(util,
					util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"), tang,
					x));
		} catch (Exception e) {
			datos = null;
		}
		return datos;
	}

	public ScatterChartModel generarScatterGrafica(String original, ExprEvaluator eval, IExpr funcion, double limInf,
			double limSup, boolean isInfinite) {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		System.out.println(limInf + " - " + limSup);
		System.out.println(funcion.toString());
		for (double i = limInf; i <= limSup; i += 0.1) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(funcion).evalDouble();
				if (isInfinite && (y < -40 || y > 40)) {
					continue;
				}
				values.add(new NumericPoint(i, y));
			} catch (Exception e) {
				continue;
			}
		}
		eval.clearVariables();
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
		dataSet.setLabel(original);
		dataSet.setBorderColor("rgba(153, 102, 255, 1)");
		dataSet.setFill(true);
		dataSet.setTension(0.5);
		dataSet.setPointRadius(0);
		data.addChartDataSet(dataSet);

		LineChartDataSet dataSet2 = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, maxY));
		values2.add(new NumericPoint(0, minY));
		dataSet2.setData(values2);
		dataSet2.setLabel("Eje Y");
		dataSet2.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet2.setFill(false);
		dataSet2.setPointRadius(0);
		data.addChartDataSet(dataSet2);

		LineChartDataSet dataSet3 = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(limInf, 0));
		values3.add(new NumericPoint(limSup, 0));
		dataSet3.setData(values3);
		dataSet3.setLabel("Eje X");
		dataSet3.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet3.setFill(false);
		dataSet3.setPointRadius(0);
		data.addChartDataSet(dataSet3);
		// Options
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
		scatterModel.setOptions(options);
		scatterModel.setData(data);
		return scatterModel;
	}

	public ScatterChartModel generarScatterGrafica(ExprEvaluator eval, IExpr sinDerE, IExpr DerE) {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSetSinInt = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		for (double i = -30; i <= 30; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(sinDerE).evalDouble();
				values.add(new NumericPoint(i, y));
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		eval.clearVariables();
		for (Object x : values) {
			double tmpY = (double) ((NumericPoint) x).getY();
			if (tmpY > maxY) {
				maxY = tmpY;
			}
			if (tmpY < minY) {
				minY = tmpY;
			}
		}
		dataSetSinInt.setData(values);
		dataSetSinInt.setLabel("Sin derivar");
		dataSetSinInt.setBorderColor("rgba(153, 102, 255, 1)");
		dataSetSinInt.setFill(false);
		dataSetSinInt.setTension(0.5);
		dataSetSinInt.setPointRadius(0);
		data.addChartDataSet(dataSetSinInt);

		LineChartDataSet dataSetInt = new LineChartDataSet();
		List<Object> valuesInt = new ArrayList<>();
		for (double i = -30; i <= 30; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(DerE).evalDouble();
				valuesInt.add(new NumericPoint(i, y));
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		eval.clearVariables();
		for (Object x : valuesInt) {
			double tmpY = (double) ((NumericPoint) x).getY();
			if (tmpY > maxY) {
				maxY = tmpY;
			}
			if (tmpY < minY) {
				minY = tmpY;
			}
		}
		dataSetInt.setData(valuesInt);
		dataSetInt.setLabel("Derivado");
		dataSetInt.setBorderColor("rgba(204, 255, 102, 1)");
		dataSetInt.setFill(false);
		dataSetInt.setTension(0.5);
		dataSetInt.setPointRadius(0);
		data.addChartDataSet(dataSetInt);

		LineChartDataSet dataSet2 = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, maxY));
		values2.add(new NumericPoint(0, minY));
		dataSet2.setData(values2);
		dataSet2.setLabel("Eje Y");
		dataSet2.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet2.setFill(false);
		dataSet2.setPointRadius(0);
		data.addChartDataSet(dataSet2);

		LineChartDataSet dataSet3 = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(-30, 0));
		values3.add(new NumericPoint(30, 0));
		dataSet3.setData(values3);
		dataSet3.setLabel("Eje X");
		dataSet3.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet3.setFill(false);
		dataSet3.setPointRadius(0);
		data.addChartDataSet(dataSet3);
		// Options
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
		scatterModel.setOptions(options);
		scatterModel.setData(data);
		return scatterModel;
	}

	public ScatterChartModel generarScatterGraficaTang(ExprEvaluator eval, IExpr sinDerE, IExpr DerE, double ex) {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSetSinInt = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		for (double i = ex - 10; i <= ex + 10; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(sinDerE).evalDouble();
				values.add(new NumericPoint(i, y));
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		eval.clearVariables();
		for (Object x : values) {
			double tmpY = (double) ((NumericPoint) x).getY();
			if (tmpY > maxY) {
				maxY = tmpY;
			}
			if (tmpY < minY) {
				minY = tmpY;
			}
		}
		dataSetSinInt.setData(values);
		dataSetSinInt.setLabel("Funcion");
		dataSetSinInt.setBorderColor("rgba(153, 102, 255, 1)");
		dataSetSinInt.setFill(false);
		dataSetSinInt.setTension(0.5);
		dataSetSinInt.setPointRadius(0);
		data.addChartDataSet(dataSetSinInt);

		LineChartDataSet dataSetInt = new LineChartDataSet();
		List<Object> valuesInt = new ArrayList<>();
		for (double i = ex - 10; i <= ex + 10; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(DerE).evalDouble();
				valuesInt.add(new NumericPoint(i, y));
				eval.clearVariables();
			} catch (Exception e) {
				continue;
			}
		}
		eval.clearVariables();
		for (Object x : valuesInt) {
			double tmpY = (double) ((NumericPoint) x).getY();
			if (tmpY > maxY) {
				maxY = tmpY;
			}
			if (tmpY < minY) {
				minY = tmpY;
			}
		}
		dataSetInt.setData(valuesInt);
		dataSetInt.setLabel("Recta tangente");
		dataSetInt.setBorderColor("rgba(204, 255, 102, 1)");
		dataSetInt.setFill(false);
		dataSetInt.setTension(0.5);
		dataSetInt.setPointRadius(0);
		data.addChartDataSet(dataSetInt);

		LineChartDataSet dataSet2 = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, maxY));
		values2.add(new NumericPoint(0, minY));
		dataSet2.setData(values2);
		dataSet2.setLabel("Eje Y");
		dataSet2.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet2.setFill(false);
		dataSet2.setPointRadius(0);
		data.addChartDataSet(dataSet2);

		LineChartDataSet dataSet3 = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(ex - 10, 0));
		values3.add(new NumericPoint(ex + 10, 0));
		dataSet3.setData(values3);
		dataSet3.setLabel("Eje X");
		dataSet3.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet3.setFill(false);
		dataSet3.setPointRadius(0);
		data.addChartDataSet(dataSet3);
		// Options
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
		scatterModel.setOptions(options);
		scatterModel.setData(data);
		return scatterModel;
	}

	@Override
	public BarChartModel generarGrafica() {
		return null;
	}

	public String getFuncion() {
		return funcion;
	}

	public void setFuncion(String funcion) {
		this.funcion = funcion;
	}

	public ScatterChartModel getScatterModel() {
		return scatterModel;
	}

	public void setScatterModel(ScatterChartModel scatterModel) {
		this.scatterModel = scatterModel;
	}
}
