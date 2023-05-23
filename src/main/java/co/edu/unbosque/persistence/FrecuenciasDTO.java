package co.edu.unbosque.persistence;

public class FrecuenciasDTO {

	private String valor;
	private int frecuenciaAbsoluta;
	private int frecuenciaAbsolutaAcumulada;
	private double frecuenciaRelativa;
	private double frecuenciaRelativaAcumulada;

	public FrecuenciasDTO(String valor, int frecuenciaAbsoluta, int frecuenciaAbsolutaAcumulada,
			double frecuenciaRelativa, double frecuenciaRelativaAcumulada) {
		super();
		this.valor = valor;
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
		this.frecuenciaAbsolutaAcumulada = frecuenciaAbsolutaAcumulada;
		this.frecuenciaRelativa = frecuenciaRelativa;
		this.frecuenciaRelativaAcumulada = frecuenciaRelativaAcumulada;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public int getFrecuenciaAbsoluta() {
		return frecuenciaAbsoluta;
	}

	public void setFrecuenciaAbsoluta(int frecuenciaAbsoluta) {
		this.frecuenciaAbsoluta = frecuenciaAbsoluta;
	}

	public int getFrecuenciaAbsolutaAcumulada() {
		return frecuenciaAbsolutaAcumulada;
	}

	public void setFrecuenciaAbsolutaAcumulada(int frecuenciaAbsolutaAcumulada) {
		this.frecuenciaAbsolutaAcumulada = frecuenciaAbsolutaAcumulada;
	}

	public double getFrecuenciaRelativa() {
		return frecuenciaRelativa;
	}

	public void setFrecuenciaRelativa(double frecuenciaRelativa) {
		this.frecuenciaRelativa = frecuenciaRelativa;
	}

	public double getFrecuenciaRelativaAcumulada() {
		return frecuenciaRelativaAcumulada;
	}

	public void setFrecuenciaRelativaAcumulada(double frecuenciaAbsollutaAcumulada) {
		this.frecuenciaRelativaAcumulada = frecuenciaAbsollutaAcumulada;
	}

	public FrecuenciasDTO build() {
		return this;
	}

	@Override
	public String toString() {
		return "FrecuenciasDTO [valor=" + valor + ", frecuenciaAbsoluta=" + frecuenciaAbsoluta
				+ ", frecuenciaAbsolutaAcumulada=" + frecuenciaAbsolutaAcumulada + ", frecuenciaRelativa="
				+ frecuenciaRelativa + ", frecuenciaRelativaAcumulada=" + frecuenciaRelativaAcumulada + "]";
	}

}
