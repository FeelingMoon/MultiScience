package co.edu.unbosque.persistence;

public class CargaDTO {

	double carga;
	double x;
	double y;

	public CargaDTO(double carga, double x, double y) {
		super();
		this.carga = carga;
		this.x = x;
		this.y = y;
	}

	public double getCarga() {
		return carga;
	}

	public void setCarga(double carga) {
		this.carga = carga;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

}
