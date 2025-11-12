import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Encoders;
import java.security.Key;

public class KeyGenerator {
    public static void main(String[] args) {
        // Generate a cryptographically secure key suitable for HS256
        Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

        // Base64 encode the key for safe storage in properties file
        String base64Key = Encoders.BASE64.encode(key.getEncoded());

        System.out.println("Generated JWT Secret Key (put this in application.properties):");
        System.out.println(base64Key);
}
}