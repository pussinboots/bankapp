package tools

import org.specs2.mutable.Specification
import play.api.test.PlaySpecification
import javax.crypto.SecretKey
import javax.crypto.KeyGenerator
import sun.misc.BASE64Encoder
import sun.misc.BASE64Decoder
import javax.crypto.spec.SecretKeySpec
import tools.EasyCryptUtil._

class EasyCryptUtilSpec extends PlaySpecification {
  
  "encryption" should {
    "aes encryption with specific key" in {
	sys.props.+=("aes_key" -> "16rdKQfqN3L4TY7YktgxBw==")
	val encrypted =	"Hallo AxxG-Leser".encrypt
        encrypted.key must beEqualTo("16rdKQfqN3L4TY7YktgxBw==")
	encrypted.value must beEqualTo("Hallo AxxG-Leser")
	encrypted.encrypted must beEqualTo("BGqqWc8RB0z5AqGjgAoWyWwrvhIHY1vJCR/MNy3a2Os=")  
    }
  }
}
