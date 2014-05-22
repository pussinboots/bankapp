package tools

import javax.crypto.SecretKey
import javax.crypto.KeyGenerator
import sun.misc.BASE64Encoder
import sun.misc.BASE64Decoder
import javax.crypto.spec.SecretKeySpec

case class Encryption(key: String, value: String, encrypted: String) 
object EasyCryptUtil {
	def encrypt(value: String): Encryption = {
		require (sys.env.get("aes_key") != None, "system property aes_key is missing")
	  	val storedKey =  sys.env.get("aes_key").get //"16rdKQfqN3L4TY7YktgxBw=="

		val myDecoder = new BASE64Decoder();
		val cryptedKey = myDecoder.decodeBuffer(storedKey);      
		val aesKey = new SecretKeySpec(cryptedKey, "AES"); 
		    
		// Klasse erzeugen
		val ec = new EasyCrypt(aesKey, "AES");
		val myEncoder = new BASE64Encoder();
		val key = myEncoder.encode(aesKey.getEncoded());
		val geheim = ec.encrypt(value);
		val erg = ec.decrypt(geheim);
		Encryption(key, value, geheim)
	}
	def encrypt(value: Double): Encryption = encrypt(value.toString)
}
