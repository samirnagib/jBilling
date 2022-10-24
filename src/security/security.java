package security;


import org.apache.commons.codec.binary.Base64;

public class security {
	
	
	

	public static String securityHide(Integer keyType, String objToBeProtected) {
		String origem = objToBeProtected;
		String objeto;
		
		keys k = new keys(); 
		switch(keyType) {
		case 1: //USR
			objeto = Base64.encodeBase64String((origem+"|"+k.getKeyUsr()).getBytes());
			break;
		case 2:
			objeto = Base64.encodeBase64String((origem+"|"+k.getKeyPwd()).getBytes());
			break;
		case 3:
			objeto = Base64.encodeBase64String((origem+"|"+k.getKeyOth()).getBytes());
			break;
		default:
			objeto = Base64.encodeBase64String((origem+"|").getBytes());
		}
		return objeto;
	}

	public static String securityShow(String objToBeShowed) {
		String origem = objToBeShowed;
		byte[] objeto;
		objeto = Base64.decodeBase64(origem.getBytes());
		String decodeObjwto = new String(objeto);
		String resultado[] = decodeObjwto.split("\\|");
		return resultado[0];
	}


}
