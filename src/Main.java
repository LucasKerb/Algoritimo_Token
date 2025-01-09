import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Capture the value of num
            System.out.print("Enter the serial (0 to 10000): ");
            int num = sc.nextInt();

            // Validate if the number is within the allowed range
            if (num < 0 || num > 10000) {
                throw new IllegalArgumentException("The number must be between 0 and 10000.");
            }

            // Capture the token value in the format 0xADF015
            System.out.print("Enter the hexadecimal token (0x000000 to 0xFFFFFF): ");
            String tokenInput = sc.next();

            // Validate and extract the token bytes
            if (!tokenInput.startsWith("0x") || tokenInput.length() != 8) {
                throw new IllegalArgumentException(
                        "The token must be in the format 0x followed by 6 hexadecimal characters.");
            }

            int tokenValue = Integer.parseInt(tokenInput.substring(2), 16);

            // Validate if the token is within the allowed range
            if (tokenValue < 0x000000 || tokenValue > 0xFFFFFF) {
                throw new IllegalArgumentException("The token must be between 0x000000 and 0xFFFFFF.");
            }

            byte[] token = convertHexToBytes(tokenInput.substring(2)); // Remove the "0x" and process
            byte[] magicWord = { (byte) 0x99, (byte) 0xFF, (byte) 0xAA };
            byte[] numChar = new byte[3];
            byte[] result = new byte[3];

            // Copy the first 3 bytes of 'num' to 'numChar'
            numChar = Arrays.copyOfRange(intToBytes(num), 0, 3);

            // Display the XOR operation result
            System.out.print("\nXOR Result (Access Key): 0x");
            for (int i = 0; i < 3; i++) {
                result[i] = (byte) ((token[i] ^ magicWord[i]) ^ numChar[i]);
                System.out.printf("%02X", result[i]);
            }
            System.out.println();

            // Calculate SHA1 of the result and display the first 3 bytes
            try {
                byte[] sha1Hash = calculateSHA1(result);
                System.out.print("SHA1 (output): 0x");
                for (int i = 0; i < 3; i++) {
                    System.out.printf("%02X", sha1Hash[i]);
                }
                System.out.println();

                // Display the "final" SHA1 in the format ######
                String finalSha1Hex = String.format("%02X%02X%02X", sha1Hash[0], sha1Hash[1], sha1Hash[2]);
                System.out.printf("Final: \"%s\"%n", finalSha1Hex);
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Error calculating SHA1: " + e.getMessage());
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected error:" + e.getMessage());
        } finally {
            sc.close();
        }
    }

    /**
     * Converts a hexadecimal string to a byte array.
     */
    public static byte[] convertHexToBytes(String hex) {
        byte[] bytes = new byte[3];
        for (int i = 0; i < 3; i++) {
            String byteHex = hex.substring(i * 2, (i + 1) * 2); // Get each pair of characters
            bytes[i] = (byte) Integer.parseInt(byteHex, 16); // Convert from hexadecimal to byte
        }
        return bytes;
    }

    /**
     * Converts an integer to a little-endian byte array (4 bytes).
     */
    public static byte[] intToBytes(int value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) ((value >> (8 * i)) & 0xFF); // Shift and get the least significant 8 bits
        }
        return bytes;
    }

    /**
     * Calculates the SHA1 hash of a byte array.
     */
    public static byte[] calculateSHA1(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        return sha1.digest(input);
    }
}
