package co.edu.unbosque.services;

import co.edu.unbosque.daos.IpDAO;

public class Redes {

	private IpDAO ip;

	public String[] calcularIP(String[] ip, int numMascara) {
		try {
			if(ip.length>4) {
				new Exception();
			}
			int[] ipInteger = new int[4];
			for(int i = 0; i < ipInteger.length; i++) {
				int num = Integer.parseInt(ip[i]);
				if(num > 255 || num < 0) {
					new Exception();
				}
				ipInteger[i] = num;
			}
			this.ip = new IpDAO(ipInteger, numMascara);
			return this.ip.calcular();
		} catch (Exception e) {
			return null;
		}
	}
	
	public String[] calcularIP(String[] ip, String[] mask) {
		try {
			if(ip.length!=4 && mask.length!=4) {
				new Exception();
			}
			int[] ipInteger = new int[4];
			int[] maskInteger = new int[4];
			for(int i = 0; i < 4; i++) {
				int numIP = Integer.parseInt(ip[i]);
				int numMask = Integer.parseInt(mask[i]);
				if(numIP > 255 || numIP < 0 || numMask > 255 || numMask < 0) {
					new Exception();
				}
				ipInteger[i] = numIP;
				maskInteger[i] = numMask;
			}
			this.ip = new IpDAO(ipInteger, maskInteger);
			return this.ip.calcular();
		} catch (Exception e) {
			return null;
		}
	}

}
