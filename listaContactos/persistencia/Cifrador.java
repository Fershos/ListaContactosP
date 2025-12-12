package listaContactos.persistencia;

import java.util.Base64;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Cifrador {
  public static byte[] hashear(String cadena) {
    byte[] res;
    try {
      MessageDigest ms = MessageDigest.getInstance("SHA-256");
      ms.update(cadena.getBytes());
      res = ms.digest();
    } catch(NoSuchAlgorithmException e) {
      res = Integer.toString(cadena.hashCode()).getBytes();
    }

    return res;
  }

  public static boolean sonMismoHash(byte[] uno, byte[] dos) {
    return MessageDigest.isEqual(uno, dos);
  }

  private static SecretKeySpec crearKey(byte[] password) {
    SecretKeySpec sks = new SecretKeySpec(password, "AES");
    return sks;
  }

  public static String cifrar(byte[] password, String dato) {
    SecretKeySpec sks = crearKey(password);
    String datosCifrados;
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.ENCRYPT_MODE, sks);
      datosCifrados = Base64.getEncoder().encodeToString(cipher.doFinal(dato.getBytes()));
    } catch(Exception e) {
      datosCifrados = "error";
    }
    return datosCifrados;
  }

  public static String decifrar(byte[] password, String datoCifrado) {
    SecretKeySpec sks = crearKey(password);
    String datosDec;
    try {
      Cipher cipher = Cipher.getInstance("AES");
      cipher.init(Cipher.DECRYPT_MODE, sks);
      datosDec = new String(cipher.doFinal(Base64.getDecoder().decode(datoCifrado)));
    } catch(Exception e) {
      datosDec = "error";
    }
    return datosDec;
  }
}
