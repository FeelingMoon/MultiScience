package co.edu.unbosque.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.data.NumericPoint;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.scatter.ScatterChartModel;

import co.edu.unbosque.persistence.FileHandler;
import co.edu.unbosque.persistence.FrecuenciasDTO;

public class Datos {

	private static Properties properties;

	static {
		properties = FileHandler
				.getProperties(Datos.class.getResourceAsStream("/co/edu/unbosque/persistence/datos.properties"));
	}

	public static String getDescripcion(String valor) {
		return properties.getProperty("descripcion." + valor);
	}

	public static String getDialogo(String valor) {
		return properties.getProperty("dialogo." + valor);
	}

	public static String getLink() {
		return properties.getProperty("link.integral");
	}

	public static String getRuta(String valor) {
		return properties.getProperty("ruta." + valor);
	}

	public static BarChartModel getBarGraphicDefault() {
		BarChartModel barModel = new BarChartModel();
		ChartData data = new ChartData();

		BarChartDataSet barDataSet = new BarChartDataSet();

		List<Number> values = new ArrayList<>();
		values.add(1);
		values.add(3);
		values.add(4);
		values.add(5);
		values.add(6);
		values.add(7);
		barDataSet.setData(values);

		List<String> bgColor = new ArrayList<>();
		bgColor.add("rgba(255, 99, 132, 0.2)");
		bgColor.add("rgba(255, 159, 64, 0.2)");
		bgColor.add("rgba(255, 205, 86, 0.2)");
		bgColor.add("rgba(75, 192, 192, 0.2)");
		bgColor.add("rgba(54, 162, 235, 0.2)");
		bgColor.add("rgba(153, 102, 255, 0.2)");
		barDataSet.setBackgroundColor(bgColor);

		List<String> borderColor = new ArrayList<>();
		borderColor.add("rgb(255, 99, 132)");
		borderColor.add("rgb(255, 159, 64)");
		borderColor.add("rgb(255, 205, 86)");
		borderColor.add("rgb(75, 192, 192)");
		borderColor.add("rgb(54, 162, 235)");
		borderColor.add("rgb(153, 102, 255)");
		borderColor.add("rgb(201, 203, 207)");
		barDataSet.setBorderColor(borderColor);
		barDataSet.setBorderWidth(1);

		data.addChartDataSet(barDataSet);

		List<String> labels = new ArrayList<>();
		labels.add("Perdidas Enero");
		labels.add("Perdidas Febrero");
		labels.add("Perdidas Marzo");
		labels.add("Perdidas abril");
		labels.add("Perdidas mayo");
		labels.add("Perdidas Junio");
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
		title.setText("Materias perdidas por mes");
		options.setTitle(title);
		Legend legend = new Legend();
		legend.setDisplay(true);
		legend.setPosition("none");
		options.setLegend(legend);
		barModel.setOptions(options);
		return barModel;
	}

	public static ScatterChartModel getScatterGraphicDefault() {
		ScatterChartModel scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSet = new LineChartDataSet();
		List<Object> values = new ArrayList<>();
		for (double i = -10; i <= 10; i += 0.1) {
			values.add(new NumericPoint(i, Math.sin(i)));
		}
		dataSet.setData(values);
		dataSet.setLabel("sin(x)");
		dataSet.setBorderColor("rgba(153, 102, 255, 1)");
		dataSet.setFill(true);
		dataSet.setTension(0.5);
		dataSet.setPointRadius(0);
		data.addChartDataSet(dataSet);

		LineChartDataSet dataSet2 = new LineChartDataSet();
		List<Object> values2 = new ArrayList<>();
		values2.add(new NumericPoint(0, 1));
		values2.add(new NumericPoint(0, -1));
		dataSet2.setData(values2);
		dataSet2.setLabel("Eje X");
		dataSet2.setBorderColor("rgba(0, 0, 0, 0.3)");
		dataSet2.setFill(false);
		dataSet2.setPointRadius(0);
		data.addChartDataSet(dataSet2);

		LineChartDataSet dataSet3 = new LineChartDataSet();
		List<Object> values3 = new ArrayList<>();
		values3.add(new NumericPoint(-10, 0));
		values3.add(new NumericPoint(10, 0));
		dataSet3.setData(values3);
		dataSet3.setLabel("Eje Y");
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

	public static ArrayList<FrecuenciasDTO> getDatosTabla() {
		InputStream xlsx = Datos.class.getResourceAsStream("/co/edu/unbosque/persistence/plantilla.xlsx");
		ArrayList<FrecuenciasDTO> fin = new ArrayList<>();
		ArrayList<String> datosString = new ArrayList<>();
		ArrayList<Double> datosNumeric = new ArrayList<>();
		ArrayList<String> datos = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(xlsx);
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(j);
					if (cell.getCellType() == CellType.STRING) {
						datosString.add(cell.getStringCellValue().replace(" ", "").toLowerCase());
					} else if (cell.getCellType() == CellType.NUMERIC) {
						datosNumeric.add(cell.getNumericCellValue());
					} else {
						continue;
					}
				}
			}
			Collections.sort(datosString);
			Collections.sort(datosNumeric);
			for (int i = 0; i < datosString.size(); i++) {
				datos.add(datosString.get(i));
			}
			for (int i = 0; i < datosNumeric.size(); i++) {
				datos.add(String.valueOf(datosNumeric.get(i)));
			}
			if (!datos.isEmpty()) {
				for (int i = 0; i < datos.size(); i++) {
					String actual = datos.get(i);
					int cont = 0;
					boolean existe = false;
					for (int j = 0; j < fin.size(); j++) {
						if (actual.equals(fin.get(j).getValor())) {
							existe = true;
							break;
						}
					}
					if (!existe) {
						for (int j = 0; j < datos.size(); j++) {
							if (actual.equals(datos.get(j))) {
								cont++;
							}
						}
					} else {
						continue;
					}
					if (fin.isEmpty()) {
						fin.add(new FrecuenciasDTO(actual, cont, cont, 0, 0));
					} else {
						fin.add(new FrecuenciasDTO(actual, cont, 0, 0, 0));
					}
				}
				if (fin.size() > 1) {
					for (int i = 1; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaAbsolutaAcumulada(
								fin.get(i - 1).getFrecuenciaAbsolutaAcumulada() + fin.get(i).getFrecuenciaAbsoluta());
						fin.set(i, tmp);
					}
					for (int i = 0; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaRelativaAcumulada(
								Double.parseDouble(fin.get(i).getFrecuenciaAbsolutaAcumulada() + "") / datos.size());
						tmp.setFrecuenciaRelativa(
								Double.parseDouble(fin.get(i).getFrecuenciaAbsoluta() + "") / datos.size());
						fin.set(i, tmp);
					}
				} else {
					fin = null;
				}
			} else {
				fin = null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			fin = null;
		}
		return fin;
	}
}
