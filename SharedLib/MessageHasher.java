package SharedLib;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class MessageHasher {

    private MessageDigest messageDigest;

    public MessageHasher() throws NoSuchAlgorithmException {
        messageDigest = MessageDigest.getInstance("SHA-256");
    }

    private byte[] stringToByteArray(String message) {
        return message.getBytes(StandardCharsets.UTF_8);
    }

    private String hashedMessageToString(byte[] hashedMessageBytes) {
        return Base64.getEncoder().encodeToString(hashedMessageBytes);
    }

    public String hashMessage(String message) {
        messageDigest.reset();
        byte[] hashedMessageBytes = messageDigest.digest(stringToByteArray(message));
        return hashedMessageToString(hashedMessageBytes);
    }

    public String saltHashedMessage(String hashedMessage, String salt) {
        messageDigest.reset();
        messageDigest.update(stringToByteArray(hashedMessage));
        byte[] saltedHashedMessageBytes = messageDigest.digest(stringToByteArray(salt));
        return hashedMessageToString(saltedHashedMessageBytes);
    }

    public String generateSalt(Integer stringSize) {
        final String characterSetString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(stringSize);
        for (int i = 0; i < stringSize; i++) {
            int index = (int)(characterSetString.length() * Math.random());
            sb.append(characterSetString.charAt(index));
        }
        return sb.toString();
    }
}