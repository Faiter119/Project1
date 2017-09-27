package test;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

public class SimpleRestClient {

    private final String baseURL;
    private final WebTarget baseResource;
    private static final MediaType responseType = MediaType.APPLICATION_JSON_TYPE;
    private final String keyFile;

    public SimpleRestClient(String nExtBaseURL, String keyFilePath) {

        baseURL = nExtBaseURL;
        keyFile = keyFilePath;
        baseResource = ClientBuilder.newClient().target(baseURL);
    }

    private String encryptAuthParameter(String user, String password) throws NoSuchAlgorithmException, InvalidKeySpecException, IOException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {

        // construct the base for the auth parameter

        String login = DatatypeConverter.printBase64Binary(user.getBytes()) + ":" + DatatypeConverter.printBase64Binary(password.getBytes()) + ":" + DatatypeConverter.printBase64Binary(String.valueOf(System.currentTimeMillis()).getBytes());

        // RSA encrypt it using the nExt public key
        PublicKey pubKey = getKeyFromPEM(keyFile);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);

        byte[] encryptedBytes = cipher.doFinal(login.getBytes("UTF-8"));

        // Convert it to plain ASCII with base64
        String authParam = DatatypeConverter.printBase64Binary(encryptedBytes);

        // URL encode to get rid of uncomfortable characters
        return URLEncoder.encode(authParam, "UTF-8");
    }

    public String login(String user, String password) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, IOException {

        String authParam = encryptAuthParameter(user, password);

        // Fetch the resource.
        Response response = baseResource.path("login").queryParam("service", "NEXTAPI").queryParam("auth", authParam).request(responseType).post(null);

        ObjectNode json = response.readEntity(ObjectNode.class);

        String sessionKey = json.get("session_key").asText();

        // add the session key to basic auth for all calls
        baseResource.register(HttpAuthenticationFeature.basic(sessionKey, sessionKey));

        return sessionKey;
    }

    public void getAccounts() {

        String resp = baseResource.path("accounts").request(responseType).get(String.class);
        System.out.println(resp);
    }

    public void getAccountSummary(int accno) {

        String resp = baseResource.path("accounts").path(String.valueOf(accno)).request(responseType).get(String.class);
        System.out.println(resp);
    }

    public void getAccountPositions(int accno) {

        String resp = baseResource.path("accounts").path(String.valueOf(accno)).path("positions").request(responseType).get(String.class);
        System.out.println(resp);
    }

    public void logout(String sessionKey) {

        String resp = baseResource.path("login").path(sessionKey).request(responseType).delete(String.class);
        System.out.println(resp);
    }

    private static PublicKey getKeyFromPEM(String filename) throws NoSuchAlgorithmException, InvalidKeySpecException, FileNotFoundException, IOException {

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = null;
            String key = "";
            while (true) {
                line = reader.readLine();
                if (line == null) break;
                else if (line.startsWith("-----BEGIN PUBLIC KEY-----")) continue;
                else if (line.startsWith("-----END PUBLIC KEY-----")) continue;
                else key += line.trim();
            }
            byte[] binary = DatatypeConverter.parseBase64Binary(key);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(binary);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            return kf.generatePublic(spec);
        }
    }

    public static void main(String[] args) throws Exception {

        SimpleRestClient client = new SimpleRestClient("https://api.test.nordnet.se/next/2", "NEXTAPI_TEST_public.pem");
        String sessionKey = client.login("Faiter", "admin");
        client.getAccounts();
        client.getAccountPositions(Integer.valueOf("ACCOUNT ID"));
        client.getAccountSummary(Integer.valueOf("ACCOUNT ID"));
        client.logout(sessionKey);
    }
}
