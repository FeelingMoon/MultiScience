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

import co.edu.unbosque.persistence.CargaDTO;

public class ElectricidadDAO {

	private static final double K = 8.9 * Math.pow(10, 9);
	private static final String RGB_NEGATIVO = "rgba(0, 0, 0, 1)";
	private static final String RGB_POSITIVO = "rgba(255, 0, 0, 1)";

	private ScatterChartModel scatterModel;
	private String q1, q2, q3, q4, q5;
	private String u1, u2, u3, u4, u5;
	private String x1, y1;
	private String x2, y2;
	private String x3, y3;
	private String x4, y4;
	private String x5, y5;

	public ElectricidadDAO(String q1, String q2, String q3, String q4, String q5, String u1, String u2, String u3,
			String u4, String u5, String x1, String y1, String x2, String y2, String x3, String y3, String x4,
			String y4, String x5, String y5) {
		this.q1 = q1;
		this.q2 = q2;
		this.q3 = q3;
		this.q4 = q4;
		this.q5 = q5;
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
		this.u4 = u4;
		this.u5 = u5;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.x4 = x4;
		this.y4 = y4;
		this.x5 = x5;
		this.y5 = y5;
	}

	public String calcularFuerza() {
		String data = new String();
		try {
			double sumaX = 0;
			double sumaY = 0;
			if (q1 != null && !q1.equals("")) {
				if (x1 == null || x1.equals("")) {
					x1 = "0";
				}
				if (y1 == null || y1.equals("")) {
					y1 = "0";
				}
				if (u1 == null || u1.equals("")) {
					u1 = "0";
				}
				double u1D = Double.parseDouble(u1);
				double q1D = Double.parseDouble(q1) * Math.pow(10, u1D);
				double x1D = Double.parseDouble(x1);
				double y1D = Double.parseDouble(y1);
				double x = 0;
				double y = 0;
				double q = 0;
				double u = 0;
				int mX = 1;
				int mY = 1;
				if (q2 != null && !q2.equals("")) {
					if (x2 == null || x2.equals("")) {
						x2 = "0";
					}
					if (y2 == null || y2.equals("")) {
						y2 = "0";
					}
					if (u2 == null || u2.equals("")) {
						u2 = "0";
					}
					u = Double.parseDouble(u2);
					q = Double.parseDouble(q2) * Math.pow(10, u);
					x = Double.parseDouble(x2);
					y = Double.parseDouble(y2);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDirecciones(q1D, q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.cos(a)) * mX;
					sumaY += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.sin(a)) * mY;
				}
				if (q3 != null && !q3.equals("")) {
					if (x3 == null || x3.equals("")) {
						x3 = "0";
					}
					if (y3 == null || y3.equals("")) {
						y3 = "0";
					}
					if (u3 == null || u3.equals("")) {
						u3 = "0";
					}
					u = Double.parseDouble(u3);
					q = Double.parseDouble(q3) * Math.pow(10, u);
					x = Double.parseDouble(x3);
					y = Double.parseDouble(y3);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDirecciones(q1D, q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.cos(a)) * mX;
					sumaY += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.sin(a)) * mY;
				}
				if (q4 != null && !q4.equals("")) {
					if (x4 == null || x4.equals("")) {
						x4 = "0";
					}
					if (y4 == null || y4.equals("")) {
						y4 = "0";
					}
					if (u4 == null || u4.equals("")) {
						u4 = "0";
					}
					u = Double.parseDouble(u4);
					q = Double.parseDouble(q4) * Math.pow(10, u);
					x = Double.parseDouble(x4);
					y = Double.parseDouble(y4);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDirecciones(q1D, q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.cos(a)) * mX;
					sumaY += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.sin(a)) * mY;
				}
				if (q5 != null && !q5.equals("")) {
					if (x5 == null || x5.equals("")) {
						x5 = "0";
					}
					if (y5 == null || y5.equals("")) {
						y5 = "0";
					}
					if (u5 == null || u5.equals("")) {
						u5 = "0";
					}
					u = Double.parseDouble(u5);
					q = Double.parseDouble(q5) * Math.pow(10, u);
					x = Double.parseDouble(x5);
					y = Double.parseDouble(y5);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDirecciones(q1D, q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.cos(a)) * mX;
					sumaY += Math.abs(K * ((Math.abs(q1D) * q) / Math.pow(r, 2)) * Math.cos(a)) * mY;
				}
				data = "La fuerza obtenida tras hacer los calculo fue: $" + redondear(sumaX) + "\\hat{\\imath} + ("
						+ redondear(sumaY) + ")\\hat{\\jmath}$ $N$ y la fuerza electrica neta da: $"
						+ redondear((Math.sqrt(Math.pow(sumaX, 2) + Math.pow(sumaY, 2))))
						+ "$ $N$ Y tiene un angulo de: $" + redondear((Math.atan(sumaY / sumaX))) + "$ $rad$";
			} else {
				data = "No fue posible calcular la fuerza electrica al no tener una carga q1";
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = "Ocurrio algun error al calcular la fuerza electrica";
		}
		return data;
	}

	public String calcularCampo() {
		String data = new String();
		try {
			double sumaX = 0;
			double sumaY = 0;
			if (q1 == null || q1.equals("")) {
				q1 = "0";
			}
			if (q1 != null && !q1.equals("")) {
				if (x1 == null || x1.equals("")) {
					x1 = "0";
				}
				if (y1 == null || y1.equals("")) {
					y1 = "0";
				}
				double x1D = Double.parseDouble(x1);
				double y1D = Double.parseDouble(y1);
				double x = 0;
				double y = 0;
				double q = 0;
				double u = 0;
				int mX = 1;
				int mY = 1;
				if (q2 != null && !q2.equals("")) {
					if (x2 == null || x2.equals("")) {
						x2 = "0";
					}
					if (y2 == null || y2.equals("")) {
						y2 = "0";
					}
					if (u2 == null || u2.equals("")) {
						u2 = "0";
					}
					u = Double.parseDouble(u2);
					q = Double.parseDouble(q2) * Math.pow(10, u);
					x = Double.parseDouble(x2);
					y = Double.parseDouble(y2);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDireccionesCampo(q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += K * (q / Math.pow(r, 2)) * Math.cos(a) * mX;
					sumaY += K * (q / Math.pow(r, 2)) * Math.sin(a) * mY;
				}
				if (q3 != null && !q3.equals("")) {
					if (x3 == null || x3.equals("")) {
						x3 = "0";
					}
					if (y3 == null || y3.equals("")) {
						y3 = "0";
					}
					if (u3 == null || u3.equals("")) {
						u3 = "0";
					}
					u = Double.parseDouble(u3);
					q = Double.parseDouble(q3) * Math.pow(10, u);
					x = Double.parseDouble(x3);
					y = Double.parseDouble(y3);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDireccionesCampo(q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += K * (q / Math.pow(r, 2)) * Math.cos(a) * mX;
					sumaY += K * (q / Math.pow(r, 2)) * Math.sin(a) * mY;
				}
				if (q4 != null && !q4.equals("")) {
					if (x4 == null || x4.equals("")) {
						x4 = "0";
					}
					if (y4 == null || y4.equals("")) {
						y4 = "0";
					}
					if (u4 == null || u4.equals("")) {
						u4 = "0";
					}
					u = Double.parseDouble(u4);
					q = Double.parseDouble(q4) * Math.pow(10, u);
					x = Double.parseDouble(x4);
					y = Double.parseDouble(y4);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDireccionesCampo(q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += K * (q / Math.pow(r, 2)) * Math.cos(a) * mX;
					sumaY += K * (q / Math.pow(r, 2)) * Math.sin(a) * mY;
				}
				if (q5 != null && !q5.equals("")) {
					if (x5 == null || x5.equals("")) {
						x5 = "0";
					}
					if (y5 == null || y5.equals("")) {
						y5 = "0";
					}
					if (u5 == null || u5.equals("")) {
						u5 = "0";
					}
					u = Double.parseDouble(u5);
					q = Double.parseDouble(q5) * Math.pow(10, u);
					x = Double.parseDouble(x5);
					y = Double.parseDouble(y5);
					mX = 1;
					mY = 1;
					if (y1D == y && x1D == x) {
						mX = 0;
						mY = 0;
					} else {
						int[] direcciones = getDireccionesCampo(q, y1D, y, x1D, x);
						mX = direcciones[0];
						mY = direcciones[1];
					}
					q = Math.abs(q);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					double a = 0;
					try {
						a = Math.atan(Math.abs((y - y1D)) / Math.abs((x - x1D)));
					} catch (Exception e) {
						a = 90;
					}
					sumaX += K * (q / Math.pow(r, 2)) * Math.cos(a) * mX;
					sumaY += K * (q / Math.pow(r, 2)) * Math.sin(a) * mY;
				}
				data = "El campo obtenido tras hacer los calculo fue: $" + redondear(sumaX) + "\\hat{\\imath} + ("
						+ redondear(sumaY) + ")\\hat{\\jmath}$ $\\frac{N}{C}$ y la fuerza electrica neta da: $"
						+ redondear((Math.sqrt(Math.pow(sumaX, 2) + Math.pow(sumaY, 2))))
						+ "$ $\\frac{N}{C}$ Y tiene un angulo de: $" + redondear((Math.atan(sumaY / sumaX)))
						+ "$ $rad$";
			} else {
				data = "No fue posible calcular el campo electrico al no tener un punto especifico en el plano";
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = "Ocurrio algun error al calcular el campo electrico";
		}
		return data;
	}

	public String calcularEnergiaPotencial() {
		String data = null;
		try {
			ArrayList<CargaDTO> cargas = new ArrayList<>();
			double energia = 0;
			if (q1 != null && !q1.equals("")) {
				if (x1 == null || x1.equals("")) {
					x1 = "0";
				}
				if (y1 == null || y1.equals("")) {
					y1 = "0";
				}
				if (u1 == null || u1.equals("")) {
					u1 = "0";
				}
				cargas.add(new CargaDTO(Double.parseDouble(q1) * Math.pow(10, Double.parseDouble(u1)),
						Double.parseDouble(x1), Double.parseDouble(y1)));
			}
			if (q2 != null && !q2.equals("")) {
				if (x2 == null || x2.equals("")) {
					x2 = "0";
				}
				if (y2 == null || y2.equals("")) {
					y2 = "0";
				}
				if (u2 == null || u2.equals("")) {
					u2 = "0";
				}
				cargas.add(new CargaDTO(Double.parseDouble(q2) * Math.pow(10, Double.parseDouble(u2)),
						Double.parseDouble(x2), Double.parseDouble(y2)));
			}
			if (q3 != null && !q3.equals("")) {
				if (x3 == null || x3.equals("")) {
					x3 = "0";
				}
				if (y3 == null || y3.equals("")) {
					y3 = "0";
				}
				if (u3 == null || u3.equals("")) {
					u3 = "0";
				}
				cargas.add(new CargaDTO(Double.parseDouble(q3) * Math.pow(10, Double.parseDouble(u3)),
						Double.parseDouble(x3), Double.parseDouble(y3)));
			}
			if (q4 != null && !q4.equals("")) {
				if (x4 == null || x4.equals("")) {
					x4 = "0";
				}
				if (y4 == null || y4.equals("")) {
					y4 = "0";
				}
				if (u4 == null || u4.equals("")) {
					u4 = "0";
				}
				cargas.add(new CargaDTO(Double.parseDouble(q4) * Math.pow(10, Double.parseDouble(u4)),
						Double.parseDouble(x4), Double.parseDouble(y4)));
			}
			if (q5 != null && !q5.equals("")) {
				if (x5 == null || x5.equals("")) {
					x5 = "0";
				}
				if (y5 == null || y5.equals("")) {
					y5 = "0";
				}
				if (u5 == null || u5.equals("")) {
					u5 = "0";
				}
				cargas.add(new CargaDTO(Double.parseDouble(q5) * Math.pow(10, Double.parseDouble(u5)),
						Double.parseDouble(x5), Double.parseDouble(y5)));
			}
			if (cargas.size() > 1) {
				if (cargas.size() == 2) {
					energia += (cargas.get(0).getCarga() * cargas.get(1).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(1).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(1).getY() - cargas.get(0).getY()), 2));
				} else if (cargas.size() == 3) {
					energia += (cargas.get(0).getCarga() * cargas.get(1).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(1).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(1).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(1).getY()), 2));
				} else if (cargas.size() == 4) {
					energia += (cargas.get(0).getCarga() * cargas.get(1).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(1).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(1).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(1).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(1).getY()), 2));
					energia += (cargas.get(2).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(2).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(2).getY()), 2));
				} else {
					energia += (cargas.get(0).getCarga() * cargas.get(1).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(1).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(1).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(0).getCarga() * cargas.get(4).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(4).getX() - cargas.get(0).getX()), 2)
									+ Math.pow((cargas.get(4).getY() - cargas.get(0).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(2).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(2).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(2).getY() - cargas.get(1).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(1).getY()), 2));
					energia += (cargas.get(1).getCarga() * cargas.get(4).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(4).getX() - cargas.get(1).getX()), 2)
									+ Math.pow((cargas.get(4).getY() - cargas.get(1).getY()), 2));
					energia += (cargas.get(2).getCarga() * cargas.get(3).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(3).getX() - cargas.get(2).getX()), 2)
									+ Math.pow((cargas.get(3).getY() - cargas.get(2).getY()), 2));
					energia += (cargas.get(2).getCarga() * cargas.get(4).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(4).getX() - cargas.get(2).getX()), 2)
									+ Math.pow((cargas.get(4).getY() - cargas.get(2).getY()), 2));
					energia += (cargas.get(3).getCarga() * cargas.get(4).getCarga())
							/ Math.sqrt(Math.pow((cargas.get(4).getX() - cargas.get(3).getX()), 2)
									+ Math.pow((cargas.get(4).getY() - cargas.get(3).getY()), 2));
				}
				data = "La energia potencial dio como resultado: $" + redondear((K * energia)) + "$ $J$";
			} else {
				data = "No hay cargas suficientes para realizar el calculo de la energia potencial";
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = "Ocurrio algun error al calcular la energia potencial";
		}
		return data;
	}

	public String calcularPotencialElectrico() {
		String data = new String();
		try {
			double potencial = 0;
			if (q1 == null || q1.equals("")) {
				q1 = "0";
			}
			if (q1 != null && !q1.equals("")) {
				if (x1 == null || x1.equals("")) {
					x1 = "0";
				}
				if (y1 == null || y1.equals("")) {
					y1 = "0";
				}
				double x1D = Double.parseDouble(x1);
				double y1D = Double.parseDouble(y1);
				double x = 0;
				double y = 0;
				double q = 0;
				double u = 0;
				if (q2 != null && !q2.equals("")) {
					if (x2 == null || x2.equals("")) {
						x2 = "0";
					}
					if (y2 == null || y2.equals("")) {
						y2 = "0";
					}
					if (u2 == null || u2.equals("")) {
						u2 = "0";
					}
					u = Double.parseDouble(u2);
					q = Double.parseDouble(q2) * Math.pow(10, u);
					x = Double.parseDouble(x2);
					y = Double.parseDouble(y2);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					potencial += K * (q / r);
				}
				if (q3 != null && !q3.equals("")) {
					if (x3 == null || x3.equals("")) {
						x3 = "0";
					}
					if (y3 == null || y3.equals("")) {
						y3 = "0";
					}
					if (u3 == null || u3.equals("")) {
						u3 = "0";
					}
					u = Double.parseDouble(u3);
					q = Double.parseDouble(q3) * Math.pow(10, u);
					x = Double.parseDouble(x3);
					y = Double.parseDouble(y3);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					potencial += K * (q / r);
				}
				if (q4 != null && !q4.equals("")) {
					if (x4 == null || x4.equals("")) {
						x4 = "0";
					}
					if (y4 == null || y4.equals("")) {
						y4 = "0";
					}
					if (u4 == null || u4.equals("")) {
						u4 = "0";
					}
					u = Double.parseDouble(u4);
					q = Double.parseDouble(q4) * Math.pow(10, u);
					x = Double.parseDouble(x4);
					y = Double.parseDouble(y4);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					potencial += K * (q / r);
				}
				if (q5 != null && !q5.equals("")) {
					if (x5 == null || x5.equals("")) {
						x5 = "0";
					}
					if (y5 == null || y5.equals("")) {
						y5 = "0";
					}
					if (u5 == null || u5.equals("")) {
						u5 = "0";
					}
					u = Double.parseDouble(u5);
					q = Double.parseDouble(q5) * Math.pow(10, u);
					x = Double.parseDouble(x5);
					y = Double.parseDouble(y5);
					//
					double r = Math.sqrt(Math.pow((x - x1D), 2) + Math.pow((y - y1D), 2));
					potencial += K * (q / r);
				}
				data = "El potencial electrico dio como resultado: $" + redondear(potencial) + "$ $V$";
			} else {
				data = "No fue posible calcular el potencial electrico al no tener un punto especifico en el plano";
			}
		} catch (Exception e) {
			e.printStackTrace();
			data = "Ocurrio algun error al calcular el potencial electrico";
		}
		return data;
	}

	private int[] getDirecciones(double q1D, double q, double y1D, double y, double x1D, double x) {
		int mX = 1;
		int mY = 1;
		boolean igX = false;
		boolean igY = false;
		if ((q1D > 0 && q > 0) || (q1D < 0 && q < 0)) {
			if (y1D < y) {
				mY = -1;
			} else if (y1D > y) {
				mY = 1;
			} else {
				igY = true;
			}
			if (x1D < x) {
				mX = -1;
			} else if (x1D > x) {
				mX = 1;
			} else {
				igX = true;
			}
			if (igY) {
				mY = mX;
			} else if (igX) {
				mX = mY;
			}
		} else {
			if (y1D < y) {
				mY = 1;
			} else if (y1D > y) {
				mY = -1;
			} else {
				igY = true;
			}
			if (x1D < x) {
				mX = 1;
			} else if (x1D > x) {
				mX = -1;
			} else {
				igX = true;
			}
			if (igY) {
				mY = mX;
			} else if (igX) {
				mX = mY;
			}
		}
		int[] res = { mX, mY };
		return res;
	}

	private int[] getDireccionesCampo(double q, double y1D, double y, double x1D, double x) {
		int mX = 1;
		int mY = 1;
		boolean igX = false;
		boolean igY = false;
		if (q > 0) {
			if (y1D < y) {
				mY = -1;
			} else if (y1D > y) {
				mY = 1;
			} else {
				igY = true;
			}
			if (x1D < x) {
				mX = -1;
			} else if (x1D > x) {
				mX = 1;
			} else {
				igX = true;
			}
			if (igY) {
				mY = mX;
			} else if (igX) {
				mX = mY;
			}
		} else {
			if (y1D < y) {
				mY = 1;
			} else if (y1D > y) {
				mY = -1;
			} else {
				igY = true;
			}
			if (x1D < x) {
				mX = 1;
			} else if (x1D > x) {
				mX = -1;
			} else {
				igX = true;
			}
			if (igY) {
				mY = mX;
			} else if (igX) {
				mX = mY;
			}
		}
		int[] res = { mX, mY };
		return res;
	}

	public ScatterChartModel generarScatterGrafica() {
		scatterModel = new ScatterChartModel();
		ChartData data = new ChartData();
		LineChartDataSet dataSetq1 = new LineChartDataSet();
		LineChartDataSet dataSetq2 = new LineChartDataSet();
		LineChartDataSet dataSetq3 = new LineChartDataSet();
		LineChartDataSet dataSetq4 = new LineChartDataSet();
		LineChartDataSet dataSetq5 = new LineChartDataSet();
		List<Object> valuesq1 = new ArrayList<>();
		List<Object> valuesq2 = new ArrayList<>();
		List<Object> valuesq3 = new ArrayList<>();
		List<Object> valuesq4 = new ArrayList<>();
		List<Object> valuesq5 = new ArrayList<>();
		if (q1.equals("") || q1 == null) {
			return null;
		} else {
			if (x1 == null || x1.equals("")) {
				x1 = "0";
			} else if (y1 == null || y1.equals("")) {
				y1 = "0";
			}
			valuesq1 = new ArrayList<>();
			valuesq1.add(new NumericPoint(Double.parseDouble(x1), Double.parseDouble(y1)));
			dataSetq1.setData(valuesq1);
			dataSetq1.setLabel(q1 + " C");
			if (Double.parseDouble(q1) < 0) {
				dataSetq1.setBorderColor(RGB_NEGATIVO);
				dataSetq1.setPointBackgroundColor(RGB_NEGATIVO);
			} else {
				dataSetq1.setBorderColor(RGB_POSITIVO);
				dataSetq1.setPointBackgroundColor(RGB_POSITIVO);
			}
			dataSetq1.setFill(false);
			dataSetq1.setPointRadius(5);
			data.addChartDataSet(dataSetq1);
			if (!q2.equals("") && q2 != null) {
				if (x2 == null || x2.equals("")) {
					x2 = "0";
				} else if (y2 == null || y2.equals("")) {
					y2 = "0";
				}
				valuesq2 = new ArrayList<>();
				valuesq2.add(new NumericPoint(Double.parseDouble(x2), Double.parseDouble(y2)));
				dataSetq2.setData(valuesq2);
				dataSetq2.setLabel(q2 + " C");
				if (Double.parseDouble(q2) < 0) {
					dataSetq2.setBorderColor(RGB_NEGATIVO);
					dataSetq2.setPointBackgroundColor(RGB_NEGATIVO);
				} else {
					dataSetq2.setBorderColor(RGB_POSITIVO);
					dataSetq2.setPointBackgroundColor(RGB_POSITIVO);
				}
				dataSetq2.setFill(false);
				dataSetq2.setPointRadius(5);
				data.addChartDataSet(dataSetq2);
			}
			if (!q3.equals("") && q3 != null) {
				if (x3 == null || x3.equals("")) {
					x3 = "0";
				} else if (y3 == null || y3.equals("")) {
					y3 = "0";
				}
				valuesq3 = new ArrayList<>();
				valuesq3.add(new NumericPoint(Double.parseDouble(x3), Double.parseDouble(y3)));
				dataSetq3.setData(valuesq3);
				dataSetq3.setLabel(q3 + " C");
				if (Double.parseDouble(q3) < 0) {
					dataSetq3.setBorderColor(RGB_NEGATIVO);
					dataSetq3.setPointBackgroundColor(RGB_NEGATIVO);
				} else {
					dataSetq3.setBorderColor(RGB_POSITIVO);
					dataSetq3.setPointBackgroundColor(RGB_POSITIVO);
				}
				dataSetq3.setFill(false);
				dataSetq3.setPointRadius(5);
				data.addChartDataSet(dataSetq3);
			}
			if (!q4.equals("") && q4 != null) {
				if (x4 == null || x4.equals("")) {
					x4 = "0";
				} else if (y4 == null || y4.equals("")) {
					y4 = "0";
				}
				valuesq4 = new ArrayList<>();
				valuesq4.add(new NumericPoint(Double.parseDouble(x4), Double.parseDouble(y4)));
				dataSetq4.setData(valuesq4);
				dataSetq4.setLabel(q4 + " C");
				if (Double.parseDouble(q4) < 0) {
					dataSetq4.setBorderColor(RGB_NEGATIVO);
					dataSetq4.setPointBackgroundColor(RGB_NEGATIVO);
				} else {
					dataSetq4.setBorderColor(RGB_POSITIVO);
					dataSetq4.setPointBackgroundColor(RGB_POSITIVO);
				}
				dataSetq4.setFill(false);
				dataSetq4.setPointRadius(5);
				data.addChartDataSet(dataSetq4);
			}
			if (!q5.equals("") && q5 != null) {
				if (x5 == null || x5.equals("")) {
					x5 = "0";
				} else if (y5 == null || y5.equals("")) {
					y5 = "0";
				}
				valuesq5 = new ArrayList<>();
				valuesq5.add(new NumericPoint(Double.parseDouble(x5), Double.parseDouble(y5)));
				dataSetq5.setData(valuesq5);
				dataSetq5.setLabel(q5 + " C");
				if (Double.parseDouble(q5) < 0) {
					dataSetq5.setBorderColor(RGB_NEGATIVO);
					dataSetq5.setPointBackgroundColor(RGB_NEGATIVO);
				} else {
					dataSetq5.setBorderColor(RGB_POSITIVO);
					dataSetq5.setPointBackgroundColor(RGB_POSITIVO);
				}
				dataSetq5.setFill(false);
				dataSetq5.setPointRadius(10);
				data.addChartDataSet(dataSetq5);
			}
		}
		// Options
		LineChartOptions options = new LineChartOptions();
		Title title = new Title();
		title.setDisplay(true);
		title.setText("Cargas");
		options.setShowLines(false);
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

	private double redondear(double a) {
		int decimales = 4;
		double factor = Math.pow(10, decimales);
		return Math.round(a * factor) / factor;
	}
}
