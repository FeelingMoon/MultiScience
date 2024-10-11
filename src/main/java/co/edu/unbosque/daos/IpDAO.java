package co.edu.unbosque.daos;

public class IpDAO {

	private int[] ip;
	private int[] mascara;
	private int numMascara;
	private String[] solucion;
	private ConvertidorDAO convertidor;

	public IpDAO() {
	}

	public IpDAO(int[] ip, int numMascara) {
		convertidor = new ConvertidorDAO();
		solucion = new String[13];
		this.numMascara = numMascara;
		mascara = convertidor.numMaskToMask(numMascara);
		this.ip = ip;
	}

	public IpDAO(int[] ip, int[] mask) {
		convertidor = new ConvertidorDAO();
		solucion = new String[13];
		this.mascara = mask;
		numMascara = convertidor.MaskToNumMask(mask);
		this.ip = ip;
	}

	public String[] calcular() {
		calcularIpDeRedYPrimerIpUtil();
		calcularIpDeBroadcastYUltimaIpUtil();
		calcularCantidadDeIpUtiles();
		calcularClaseDeIp();
		calcularIpPublicaOPrivada();
		calcularPorcionDeRedHosts();
		solucion[10] = ip[0] + "." + ip[1] + "." + ip[2] + "." + ip[3];
		solucion[11] = mascara[0] + "." + mascara[1] + "." + mascara[2] + "." + mascara[3];
		solucion[12] = numMascara + "";
		return solucion;
	}

	public void calcularIpDeRedYPrimerIpUtil() {
		solucion[0] = "";
		solucion[3] = "";
		for (int i = 0; i < ip.length; i++) {
			int ipRed = (ip[i] & mascara[i]);
			solucion[0] += ipRed + ".";
			if (i == (ip.length - 1)) {
				ipRed += 1;
			}
			solucion[3] += ipRed + ".";
		}
		solucion[3] = solucion[3].substring(0, solucion[3].length() - 1) + " - ";
		solucion[0] = solucion[0].substring(0, solucion[0].length() - 1);
	}

	public void calcularIpDeBroadcastYUltimaIpUtil() {
		solucion[1] = "";
		for (int i = 0; i < ip.length; i++) {
			int ipBroadcast = (ip[i] | (~mascara[i] & 0xFF));
			solucion[1] += +ipBroadcast + ".";
			if (i == ip.length - 1) {
				ipBroadcast -= 1;
			}
			solucion[3] += ipBroadcast + ".";
		}
		solucion[3] = solucion[3].substring(0, solucion[3].length() - 1);
		solucion[1] = solucion[1].substring(0, solucion[1].length() - 1);
	}

	public void calcularCantidadDeIpUtiles() {
		if(numMascara == 32) {
			solucion[2] = 1 + "";
		}else {
			solucion[2] = ((int) Math.pow(2, (32 - numMascara)) - 2) + "";
		}
	}

	public void calcularClaseDeIp() {
		if (ip[0] >= 0 && ip[0] <= 127) {
			solucion[4] = "Clase A";
			solucion[8] = "255.0.0.0";
			solucion[9] = "/8";
		} else if (ip[0] >= 128 && ip[0] <= 191) {
			solucion[4] = "Clase B";
			solucion[8] = "255.255.0.0";
			solucion[9] = "/16";
		} else if (ip[0] >= 192 && ip[0] <= 223) {
			solucion[4] = "Clase C";
			solucion[8] = "255.255.255.0";
			solucion[9] = "/24";
		} else if (ip[0] >= 224 && ip[0] <= 239) {
			solucion[4] = "Clase D (Multicast)";
			solucion[8] = "No aplica";
			solucion[9] = "";
		} else if (ip[0] >= 240 && ip[0] <= 255) {
			solucion[4] = "Clase E (Experimental)";
			solucion[8] = "No aplica";
			solucion[9] = "No aplica";
		} else {
			solucion[4] = "IP inválida";
			solucion[8] = "No aplica";
			solucion[9] = "No aplica";
		}
	}

	public void calcularIpPublicaOPrivada() {
		if ((ip[0] == 10) || (ip[0] == 172 && ip[1] >= 16 && ip[1] <= 31) || (ip[0] == 192 && ip[1] == 168)) {
			solucion[5] = "IP privada";
		} else {
			solucion[5] = "IP pública";
		}
	}

	public void calcularPorcionDeRedHosts() {

		String ipBin = "";
		String maskBin = "";
		String porcionRed = "";
		String porcionHost = "";
		boolean conf = true;

		for (int i = 0; i < 4; i++) {
			ipBin += convertidor.decimalToBinary(ip[i]) + ".";
			maskBin += convertidor.decimalToBinary(mascara[i]) + ".";
		}
		ipBin = ipBin.substring(0, ipBin.length() - 1);
		maskBin = maskBin.substring(0, maskBin.length() - 1);
		for (int i = 0; i < ipBin.length(); i++) {
			if (ipBin.charAt(i) == '.' && conf) {
				porcionRed += ".";
			} else if (ipBin.charAt(i) == '.') {
				porcionHost += ".";
			} else if (maskBin.charAt(i) == '1') {
				porcionRed += ipBin.charAt(i);
			} else {
				conf = false;
				porcionHost += ipBin.charAt(i);
			}
		}

		solucion[6] = porcionRed;
		solucion[7] = porcionHost;

	}

	public int[] getIp() {
		return ip;
	}

	public void setIp(int[] ip) {
		this.ip = ip;
	}

	public int[] getMascara() {
		return mascara;
	}

	public void setMascara(int[] mascara) {
		this.mascara = mascara;
	}

	public String[] getSolucion() {
		return solucion;
	}

	public void setSolucion(String[] solucion) {
		this.solucion = solucion;
	}

	public int getNumMascara() {
		return numMascara;
	}

	public void setNumMascara(int numMascara) {
		this.numMascara = numMascara;
	}

}
