package co.edu.unbosque.daos;

public class MagnetismoDAO {

	private String vx, vy, vz, uv, bx, by, bz, ub, q, uq, ang;

	public MagnetismoDAO(String vx, String vy, String vz, String uv, String bx, String by, String bz, String ub,
			String q, String uq, String ang) {
		this.vx = vx;
		this.vy = vy;
		this.vz = vz;
		this.uv = uv;
		this.bx = bx;
		this.by = by;
		this.bz = bz;
		this.ub = ub;
		this.q = q;
		this.uq = uq;
		this.ang = ang;
	}

	public String calcularFuerzaM() {
		String data = null;
		try {
			double v = Double.parseDouble(vx) * Math.pow(10, Double.parseDouble(uv));
			double b = Double.parseDouble(bx) * Math.pow(10, Double.parseDouble(ub));
			double qd = Double.parseDouble(q) * Math.pow(10, Double.parseDouble(uq));
			double angulo = Double.parseDouble(ang);
			double fuerza = qd * v * b * Math.sin(angulo);
			data = "La magnitud de la fuerza dio como resultado: $" + fuerza + "$ $N$";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular la fuerza magnetica con magnitudes";
		}
		return data;
	}

	public String calcularFuerzaV() {
		String data = null;
		try {
			double qD = Double.parseDouble(q) * Math.pow(10, Double.parseDouble(uq));
			double vxD = Double.parseDouble(vx) * Math.pow(10, Double.parseDouble(uv));
			double vyD = Double.parseDouble(vy) * Math.pow(10, Double.parseDouble(uv));
			double vzD = Double.parseDouble(vz) * Math.pow(10, Double.parseDouble(uv));
			double mv = Math.sqrt(Math.pow(vxD, 2) + Math.pow(vyD, 2) + Math.pow(vzD, 2));
			double bxD = Double.parseDouble(bx) * Math.pow(10, Double.parseDouble(ub));
			double byD = Double.parseDouble(by) * Math.pow(10, Double.parseDouble(ub));
			double bzD = Double.parseDouble(bz) * Math.pow(10, Double.parseDouble(ub));
			double mb = Math.sqrt(Math.pow(bxD, 2) + Math.pow(byD, 2) + Math.pow(bzD, 2));
			double fx = qD * ((vyD * bzD) - (byD * vzD));
			double fy = qD * ((vxD * bzD) - (bxD * vzD));
			double fz = qD * ((vxD * byD) - (bxD * vyD));
			double mf = Math.sqrt(Math.pow(fx, 2) + Math.pow(fy, 2) + Math.pow(fz, 2));
			double angvb = Math.acos(((vxD * bxD) + (vyD * byD) + (vzD * bzD)) / (mv * mb));
			double angvf = Math.acos(((vxD * fx) + (vyD * fy) + (vzD * fz)) / (mv * mf));
			double angbf = Math.acos(((bxD * fx) + (byD * fy) + (bzD * fz)) / (mb * mf));
			data = "El calculo con una $carga=" + q + "*10^" + uq
					+ "$ $C$  dio como resultado que el angulo entre $\\vec{V}$ y $\\vec{B}$ es " + angvb
					+ " $rad$, el angulo entre $\\vec{V}$ y $\\vec{F}$ es " + angvf
					+ " $rad$ y el angulo entre $\\vec{B}$ y $\\vec{F}$ es " + angbf
					+ " $rad$ y ademas, los siguientes datos:<br><ul class\"list-group\">"
					+ "<li class\"list-group-item\">El vector de velocidad con componentes $" + vx + "\\hat{i} + (" + vy
					+ ")\\hat{j} + (" + vz + ")\\hat{z} * 10^" + uv + "$ $m/s$, tiene una magnitud aproximada de " + mv
					+ " $m/s$ </li>" + "<li class\"list-group-item\">El vector de campo magnetico con componentes $"
					+ bx + "\\hat{i} + (" + by + ")\\hat{j} + (" + bz + ")\\hat{z} * 10^" + ub
					+ "$ $T$, tiene una magnitud aproximada de " + mb + " $m/s$ </li>"
					+ "<li class\"list-group-item\">El vector de fuerza con componentes $" + fx + "\\hat{i} - (" + fy
					+ ")\\hat{j} + (" + fz + ")\\hat{z}$ $N$, tiene una magnitud aproximada de " + mf
					+ " $m/s$ </li></ul>";
		} catch (Exception e) {
			e.printStackTrace();
			data = "Error al intentar calcular la fuerza con vectores";
		}
		return data;
	}

}
