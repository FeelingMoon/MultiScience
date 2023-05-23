package co.edu.unbosque.daos;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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

public class IntegralDAO implements IExperimentoMonteCarlo {
	private String funcion;
	private String rango;
	private double[] aproximaciones;
	private ScatterChartModel scatterModel;
	private ThreadLocalRandom rnd;

	public IntegralDAO(String funcion, String rango) {
		this.funcion = funcion;
		this.rango = rango;
		scatterModel = new ScatterChartModel();
		rnd = ThreadLocalRandom.current();
	}

	@Override
	public ArrayList<Object> calcular() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			String[] limites = rango.split(",");

			if (limites[0].toLowerCase().equalsIgnoreCase("-infinity")
					&& !limites[1].toLowerCase().equalsIgnoreCase("infinity")) {
				System.out.println("-Infinity");
				IExpr rangSup = util.parse(limites[1].replace("e", "E") + "*x");
				double b = util.eval(rangSup + "/. x->" + 1).evalDouble();
				IExpr e = util
						.eval("Integrate(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
				util.defineVariable("x", b);
				double sup = util.eval(e).evalDouble();
				util.clearVariables();
				IExpr a = util.eval("Limit[ " + e.toString() + ", x -> -Infinity]");
				System.out.println(a.toString());
				if (a.toString().equals("Indeterminate")) {
					datos.add("No fue posible calcular la integral de la funcion $" + funcion + "$ de $-\\infty$ a $"
							+ b + "$, pero es posible que la solucion sea: $" + (sup) + "$");
				} else if (a.toString().contains("Infinity")) {
					datos.add("La integral de la funcion $" + funcion + "$ de $-\\infty$ a $" + b
							+ "$ dio como resultado $\\infty$");
				} else {
					try {
						datos.add("La integral de la funcion $" + funcion + "$ de $-\\infty$ a $" + b
								+ "$ dio como resultado " + (sup - a.evalDouble()));
					} catch (Exception e2) {
						datos.add("La integral de la funcion $" + funcion + "$ de $-\\infty$ a $" + b
								+ "$ dio como resultado " + sup + "-" + a.toString());
					}
				}
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"),
						-25, b, true));
			} else if (!limites[0].toLowerCase().equalsIgnoreCase("-infinity")
					&& limites[1].toLowerCase().equalsIgnoreCase("infinity")) {
				System.out.println("Infinity");
				IExpr rangSup = util.parse(limites[0].replace("e", "E") + "*x");
				double a = util.eval(rangSup + "/. x->" + 1).evalDouble();
				System.out.println(a);
				IExpr e = util
						.eval("Integrate(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
				System.out.println(e.toString());
				util.defineVariable("x", a);
				double inf = util.eval(e).evalDouble();
				util.clearVariables();
				IExpr b = util.eval("Limit[ " + e.toString() + ", x -> Infinity]");
				System.out.println(b.toString());
				if (b.toString().contains("Indeterminate")) {
					System.out.println(true);
					datos.add("No fue posible calcular la integral de la funcion $" + funcion + "$ de $" + a
							+ "$ a $\\infty$, pero es posible que la solucion sea: $" + (0 - inf) + "$");
				} else if (b.toString().contains("Infinity")) {
					System.out.println(false);
					datos.add("La integral de la funcion $" + funcion + "$ de $" + a
							+ "$ a $\\infty$ dio como resultado $\\infty$");
				} else {
					try {
						datos.add("La integral de la funcion $" + funcion + "$ de $" + a
								+ "$ a $\\infty$ dio como resultado " + (b.evalDouble() - inf));
					} catch (Exception e2) {
						datos.add("La integral de la funcion $" + funcion + "$ de $" + a
								+ "$ a $\\infty$ dio como resultado " + b.toString() + "-" + inf);
					}
				}
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"), a,
						25, true));
			} else if (limites[0].toLowerCase().equalsIgnoreCase("-infinity")
					&& limites[1].toLowerCase().equalsIgnoreCase("infinity")) {
				System.out.println("-Infinity - Infinity");
				IExpr e = util
						.eval("Integrate(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
				IExpr a = util.eval("Limit[ " + e.toString() + ", x -> -Infinity]");
				IExpr b = util.eval("Limit[ " + e.toString() + ", x -> Infinity]");
				System.out.println("Limite inferior: " + a.toString());
				System.out.println("Limite superios: " + b.toString());
				if (b.toString().equals("Indeterminate")) {
					double valor = 0;
					try {
						valor = 0 - a.evalDouble();
					} catch (Exception e2) {
						try {
							valor = b.evalDouble();
						} catch (Exception e3) {
							valor = Double.NaN;
						}
					}
					if (valor != Double.NaN) {
						datos.add("No fue posible calcular la integral de la funcion $" + funcion
								+ "$ de $-\\infty$ a $\\infty$, pero es posible que la solucion sea: $" + valor + "$");
					} else {
						datos.add("No fue posible calcular la integral de la funcion $" + funcion
								+ "$ de $-\\infty$ a $\\infty$, pero es posible que la solucion sea: $\\infty$");
					}
				} else if (b.toString().contains("Infinity") || a.toString().contains("Infinity")) {
					datos.add("La integral de la funcion $" + funcion
							+ "$ de $-\\infty$ a $\\infty$ dio como resultado $\\infty$");
				} else {
					try {
						datos.add("La integral de la funcion $" + funcion
								+ "$ de $-\\infty$ a $\\infty$ dio como resultado "
								+ (b.evalDouble() - a.evalDouble()));
					} catch (Exception e2) {
						datos.add("La integral de la funcion $" + funcion
								+ "$ de $-\\infty$ a $\\infty$ dio como resultado " + b.toString() + "-"
								+ a.toString());
					}
				}
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"),
						-25, 25, true));
			} else {
				IExpr rangInf = util.parse(limites[0].replace("e", "E") + "*x");
				IExpr rangSup = util.parse(limites[1].replace("e", "E") + "*x");
				util.defineVariable("x", 1);
				double a = util.eval(rangInf + "/. x->" + 1).evalDouble();
				double b = util.eval(rangSup + "/. x->" + 1).evalDouble();
				util.clearVariables();
				IExpr e = util
						.eval("Integrate(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ", x)");
				util.defineVariable("x", a);
				double inf = util.eval(e).evalDouble();
				util.clearVariables();
				util.defineVariable("x", b);
				double sup = util.eval(e).evalDouble();
				util.clearVariables();
				double integral = sup - inf;
				datos.add("La integral $" + funcion + "$ definida entre " + a + " y " + b + " dio como resultado: "
						+ integral);
				datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util,
						util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"), a,
						b, false));
			}
		} catch (Exception e) {
			e.printStackTrace();
			datos = null;
		}
		return datos;
	}

	public ArrayList<Object> calcularIndef() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			StringWriter writer = new StringWriter();
			TeXUtilities texUtil = new TeXUtilities(util.getEvalEngine(), false);
			IExpr e = util.eval(
					"Integrate(Simplify(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + "), x)");
			texUtil.toTeX(e, writer);
			datos.add("La integral indefinida $" + funcion + "$ dio como resultado $" + writer.toString() + " + c$");
			datos.add(generarScatterGraficaIndef(util,
					util.eval("Simplify(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")"), e));
		} catch (Exception e) {
			datos.add("La integral no se pudo solucionar.");
			datos.add(Datos.getScatterGraphicDefault());
		}
		return datos;
	}

	public ArrayList<Object> calcularAprox() {
		ArrayList<Object> datos = new ArrayList<>();
		try {
			// Separar el rango en límite inferior y superior
			ExprEvaluator util = new ExprEvaluator();
			TeXParser parser = new TeXParser(util.getEvalEngine());
			String[] limites = rango.split(",");
			IExpr rangInf = util.parse(limites[0].replace("e", "E") + "*x");
			IExpr rangSup = util.parse(limites[1].replace("e", "E") + "*x");
			util.defineVariable("x", 1);
			double a = util.eval(rangInf + "/. x->" + 1).evalDouble();
			double b = util.eval(rangSup + "/. x->" + 1).evalDouble();
			util.clearVariables();
			int n = 10000;
			aproximaciones = new double[n];
			// Crear un objeto Expression para evaluar la función
			double sum = 0;
			IExpr e = util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")");
			Sumatoria[] suma = new Sumatoria[10];
			for (int i = 0; i < suma.length; i++) {
				suma[i] = new Sumatoria(n, funcion, a, b);
				suma[i].start();
			}
			while (suma[0].isAlive() || suma[1].isAlive() || suma[2].isAlive() || suma[3].isAlive() || suma[4].isAlive()
					|| suma[5].isAlive() || suma[6].isAlive() || suma[7].isAlive() || suma[8].isAlive()
					|| suma[9].isAlive()) {
				Thread.sleep(1000);
			}
			for (int i = 0; i < suma.length; i++) {
				sum += suma[i].getSum();
			}
			double integral = (b - a) * sum / (n * 10);
			datos.add("La aproximacion de la integral $" + funcion + "$ tomando 1.000.000 puntos entre " + a + " y " + b
					+ " dio como resultado: " + integral);
			datos.add(generarScatterGrafica(parser.toExpression(funcion.replace("\\cdot", "*")).toString(), util, e, a,
					b, false));
		} catch (Exception e) {
			datos.add("La integral es indeterminada.");
			datos.add(Datos.getScatterGraphicDefault());
		}
		return datos;
	}

	@Override
	public BarChartModel generarGrafica() {
		return null;
	}

	public ScatterChartModel generarScatterGraficaIndef(ExprEvaluator eval, IExpr sinIntE, IExpr intIndefE) {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSetSinInt = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		double maxY = Double.MIN_VALUE;
		double minY = Double.MAX_VALUE;
		for (double i = -25; i <= 25; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(sinIntE).evalDouble();
				if (y < -40 || y > 40) {
					continue;
				}
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
		dataSetSinInt.setLabel("Sin integrar");
		dataSetSinInt.setBorderColor("rgba(153, 102, 255, 1)");
		dataSetSinInt.setFill(false);
		dataSetSinInt.setTension(0.5);
		dataSetSinInt.setPointRadius(0);
		data.addChartDataSet(dataSetSinInt);

		LineChartDataSet dataSetInt = new LineChartDataSet();
		List<Object> valuesInt = new ArrayList<>();
		for (double i = -25; i <= 25; i += 0.01) {
			try {
				eval.defineVariable("x", i);
				double y = eval.eval(intIndefE).evalDouble();
				if (y > 40 || y < -40) {
					continue;
				}
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
		dataSetInt.setLabel("Integrado c=0");
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
		values3.add(new NumericPoint(-25, 0));
		values3.add(new NumericPoint(25, 0));
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

	private class Sumatoria extends Thread {
		private ExprEvaluator util;
		private int n;
		private IExpr e;
		private double a, b;
		private double sum;
		private TeXParser parser;

		public Sumatoria(int n, String funcion, double a, double b) {
			this.util = new ExprEvaluator();
			this.n = n;
			parser = new TeXParser(util.getEvalEngine());
			this.e = util.eval("Cancel(" + parser.toExpression(funcion.replace("\\cdot", "*")).toString() + ")");
			this.a = a;
			this.b = b;
		}

		@Override
		public void run() {
			for (int i = 0; i < n; i++) {
				double x = a + rnd.nextDouble() * (b - a);
				aproximaciones[i] = x;
				util.defineVariable("x", x);
				sum += util.eval(e).evalDouble();
			}
			util.clearVariables();
		}

		public double getSum() {
			return sum;
		}
	}
}
