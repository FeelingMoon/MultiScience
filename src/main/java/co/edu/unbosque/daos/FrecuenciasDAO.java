package co.edu.unbosque.daos;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import co.edu.unbosque.persistence.FrecuenciasDTO;

public class FrecuenciasDAO {

	public ArrayList<FrecuenciasDTO> calcularConExcelSr(InputStream xlsx) {
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
						tmp.setFrecuenciaRelativaAcumulada(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsolutaAcumulada() + "") / datos.size())
										* 1000.0)
								/ 1000.0);
						tmp.setFrecuenciaRelativa(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsoluta() + "") / datos.size()) * 1000.0)
								/ 1000.0);
						fin.set(i, tmp);
					}
				} else {
					fin = null;
				}
			} else {
				fin = null;
			}
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
			fin = null;
		}
		return fin;
	}

	public ArrayList<FrecuenciasDTO> calcularConExcelCr(InputStream xlsx) {
		ArrayList<FrecuenciasDTO> fin = new ArrayList<>();
		ArrayList<Double> datos = new ArrayList<>();
		try {
			Workbook workbook = WorkbookFactory.create(xlsx);
			Sheet sheet = workbook.getSheetAt(0);
			for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
				Row row = sheet.getRow(i);
				for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) {
					Cell cell = row.getCell(j);
					if (cell.getCellType() == CellType.NUMERIC) {
						datos.add(cell.getNumericCellValue());
					} else {
						continue;
					}
				}
			}
			Collections.sort(datos);
			double amplitud = obtenerAmplitud(datos.get(0), datos.get(datos.size() - 1));
			double[] rangos = new double[9];
			int[] cuentas = new int[10];
			rangos[0] = datos.get(0) + amplitud;
			for (int i = 1; i < 9; i++) {
				rangos[i] = rangos[i - 1] + amplitud;
			}
			if (!datos.isEmpty()) {
				for (int i = 0; i < datos.size(); i++) {
					if (datos.get(i) < rangos[0]) {
						cuentas[0]++;
					} else if (datos.get(i) < rangos[1]) {
						cuentas[1]++;
					} else if (datos.get(i) < rangos[2]) {
						cuentas[2]++;
					} else if (datos.get(i) < rangos[3]) {
						cuentas[3]++;
					} else if (datos.get(i) < rangos[4]) {
						cuentas[4]++;
					} else if (datos.get(i) < rangos[5]) {
						cuentas[5]++;
					} else if (datos.get(i) < rangos[6]) {
						cuentas[6]++;
					} else if (datos.get(i) < rangos[7]) {
						cuentas[7]++;
					} else if (datos.get(i) < rangos[8]) {
						cuentas[8]++;
					} else {
						cuentas[9]++;
					}
				}
				fin.add(new FrecuenciasDTO("< " + (Math.round(rangos[0] * 1000.0) / 1000.0), cuentas[0], cuentas[0], 0,
						0));
				for (int i = 1; i < rangos.length; i++) {
					fin.add(new FrecuenciasDTO((Math.round(rangos[i - 1] * 1000.0) / 1000.0) + "-"
							+ (Math.round(rangos[i] * 1000.0) / 1000.0), cuentas[i], 0, 0, 0));
				}
				fin.add(new FrecuenciasDTO("> " + (Math.round(rangos[8] * 1000.0) / 1000.0), cuentas[9], 0, 0, 0));
				if (fin.size() > 1) {
					for (int i = 1; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaAbsolutaAcumulada(
								fin.get(i - 1).getFrecuenciaAbsolutaAcumulada() + fin.get(i).getFrecuenciaAbsoluta());
						fin.set(i, tmp);
					}
					for (int i = 0; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaRelativaAcumulada(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsolutaAcumulada() + "") / datos.size())
										* 1000.0)
								/ 1000.0);
						tmp.setFrecuenciaRelativa(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsoluta() + "") / datos.size()) * 1000.0)
								/ 1000.0);
						fin.set(i, tmp);
					}
				} else {
					fin = null;
				}
			} else {
				fin = null;
			}
		} catch (EncryptedDocumentException | IOException e) {
			e.printStackTrace();
			fin = null;
		}
		return fin;
	}

	public ArrayList<FrecuenciasDTO> calcularConTextoCr(String dato) {
		ArrayList<FrecuenciasDTO> fin = new ArrayList<>();
		ArrayList<Double> datos = new ArrayList<>();
		try {
			String[] dat = dato.split(",");
			for (int i = 0; i < dat.length; i++) {
				try {
					datos.add(Double.parseDouble(dat[i]));
				} catch (Exception e) {
					continue;
				}
			}
			Collections.sort(datos);
			double amplitud = obtenerAmplitud(datos.get(0), datos.get(datos.size() - 1));
			double[] rangos = new double[9];
			int[] cuentas = new int[10];
			rangos[0] = datos.get(0) + amplitud;
			for (int i = 1; i < 9; i++) {
				rangos[i] = rangos[i - 1] + amplitud;
			}
			if (!datos.isEmpty()) {
				for (int i = 0; i < datos.size(); i++) {
					if (datos.get(i) < rangos[0]) {
						cuentas[0]++;
					} else if (datos.get(i) < rangos[1]) {
						cuentas[1]++;
					} else if (datos.get(i) < rangos[2]) {
						cuentas[2]++;
					} else if (datos.get(i) < rangos[3]) {
						cuentas[3]++;
					} else if (datos.get(i) < rangos[4]) {
						cuentas[4]++;
					} else if (datos.get(i) < rangos[5]) {
						cuentas[5]++;
					} else if (datos.get(i) < rangos[6]) {
						cuentas[6]++;
					} else if (datos.get(i) < rangos[7]) {
						cuentas[7]++;
					} else if (datos.get(i) < rangos[8]) {
						cuentas[8]++;
					} else {
						cuentas[9]++;
					}
				}
				fin.add(new FrecuenciasDTO("< " + (Math.round(rangos[0] * 1000.0) / 1000.0), cuentas[0], cuentas[0], 0,
						0));
				for (int i = 1; i < rangos.length; i++) {
					fin.add(new FrecuenciasDTO((Math.round(rangos[i - 1] * 1000.0) / 1000.0) + "-"
							+ (Math.round(rangos[i] * 1000.0) / 1000.0), cuentas[i], 0, 0, 0));
				}
				fin.add(new FrecuenciasDTO("> " + (Math.round(rangos[8] * 1000.0) / 1000.0), cuentas[9], 0, 0, 0));
				if (fin.size() > 1) {
					for (int i = 1; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaAbsolutaAcumulada(
								fin.get(i - 1).getFrecuenciaAbsolutaAcumulada() + fin.get(i).getFrecuenciaAbsoluta());
						fin.set(i, tmp);
					}
					for (int i = 0; i < fin.size(); i++) {
						FrecuenciasDTO tmp = fin.get(i);
						tmp.setFrecuenciaRelativaAcumulada(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsolutaAcumulada() + "") / datos.size())
										* 1000.0)
								/ 1000.0);
						tmp.setFrecuenciaRelativa(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsoluta() + "") / datos.size()) * 1000.0)
								/ 1000.0);
						fin.set(i, tmp);
					}
				} else {
					fin = null;
				}
			} else {
				fin = null;
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			fin = null;
		}
		return fin;
	}

	public ArrayList<FrecuenciasDTO> calcularConTextoSr(String dato) {
		ArrayList<FrecuenciasDTO> fin = new ArrayList<>();
		ArrayList<String> datosString = new ArrayList<>();
		ArrayList<Double> datosNumeric = new ArrayList<>();
		ArrayList<String> datos = new ArrayList<>();
		try {
			String[] dat = dato.split(",");
			for (int i = 0; i < dat.length; i++) {
				try {
					datosNumeric.add(Double.parseDouble(dat[i]));
				} catch (Exception e) {
					datosString.add(dat[i]);
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
						tmp.setFrecuenciaRelativaAcumulada(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsolutaAcumulada() + "") / datos.size())
										* 1000.0)
								/ 1000.0);
						tmp.setFrecuenciaRelativa(Math.round(
								(Double.parseDouble(fin.get(i).getFrecuenciaAbsoluta() + "") / datos.size()) * 1000.0)
								/ 1000.0);
						fin.set(i, tmp);
					}
				} else {
					fin = null;
				}
			} else {
				fin = null;
			}
		} catch (EncryptedDocumentException e) {
			e.printStackTrace();
			fin = null;
		}
		return fin;
	}

	private double obtenerAmplitud(double min, double max) {
		return (max - min) / 10;
	}

}
