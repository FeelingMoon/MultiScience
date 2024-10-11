package co.edu.unbosque.daos;

public class ConvertidorDAO {
	public String decimalToBinary(int dec) {
        StringBuilder bin = new StringBuilder();
        bin.insert(0, Integer.toBinaryString(dec));
        while(bin.length() < 8) {
        	bin.insert(0, "0");
        }
        return bin.toString();
    }
	
	public int binaryToDecimal(String bin) {
        return Integer.parseInt(bin, 2);
    }
	
	public int MaskToNumMask(int[] mask) {
		int numMask = 0;
		String tmpMask = "";
		for(int i : mask) {
			tmpMask += decimalToBinary(i);
		}
		for(char i : tmpMask.toCharArray()) {
			if(i == '0') {
				break;
			}
			numMask += 1;
		}
		return numMask;
	}
	
	public int[] numMaskToMask(int numMascara) {
		int[] mascara = new int[4];
		for (int i = 0; i < mascara.length; i++) {
			if (numMascara >= 8) {
				mascara[i] = 255;
				numMascara -= 8;
			} else {
				StringBuilder tmp = new StringBuilder();
				while (tmp.length() < 8) {
					if (numMascara > 0) {
						tmp.append("1");
						numMascara -= 1;
					} else {
						tmp.append("0");
					}
				}
				mascara[i] = binaryToDecimal(tmp.toString());
			}
		}
		return mascara;
	}
	
}
