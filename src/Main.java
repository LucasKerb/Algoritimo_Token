import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            // Capturar o valor de num
            System.out.print("Digite o serial (0 a 10000): ");
            int num = sc.nextInt();

            // Validar se o número está dentro do intervalo permitido
            if (num < 0 || num > 10000) {
                throw new IllegalArgumentException("O número deve estar entre 0 e 10000.");
            }

            // Capturar os valores de token no formato 0xADF015
            System.out.print("Digite o token hexadecimal (0x000000 a 0xFFFFFF): ");
            String tokenInput = sc.next();

            // Validar e extrair os bytes do token
            if (!tokenInput.startsWith("0x") || tokenInput.length() != 8) {
                throw new IllegalArgumentException("O token deve estar no formato 0x seguido de 6 caracteres hexadecimais.");
            }

            int tokenValue = Integer.parseInt(tokenInput.substring(2), 16);

            // Validar se o token está dentro do intervalo permitido
            if (tokenValue < 0x000000 || tokenValue > 0xFFFFFF) {
                throw new IllegalArgumentException("O token deve estar entre 0x000000 e 0xFFFFFF.");
            }

            byte[] token = parseHexToken(tokenInput.substring(2)); // Remove o "0x" e processa
            byte[] magicWord = {(byte) 0x99, (byte) 0xFF, (byte) 0xAA};
            byte[] numChar = new byte[3];
            byte[] result = new byte[3];

            // Copiar os 3 primeiros bytes de 'num' para 'numChar'
            numChar = Arrays.copyOfRange(toLittleEndianBytes(num), 0, 3);

            // Exibe o resultado da operação XOR
            System.out.print("\nResultado XOR (Access Key): 0x");
            for (int i = 0; i < 3; i++) {
                result[i] = (byte) ((token[i] ^ magicWord[i]) ^ numChar[i]);
                System.out.printf("%02X", result[i]);
            }
            System.out.println();

            // Calcular SHA1 do resultado e exibir os 3 primeiros bytes
            try {
                byte[] sha1Hash = calculateSHA1(result);
                System.out.print("SHA1 (output): 0x");
                for (int i = 0; i < 3; i++) {
                    System.out.printf("%02X", sha1Hash[i]);
                }
                System.out.println();

                // Apresentar SHA1 "final" no formato ######
                String finalSha1Hex = String.format("%02X%02X%02X", sha1Hash[0], sha1Hash[1], sha1Hash[2]);
                System.out.printf("Final: \"%s\"%n", finalSha1Hex);
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Erro ao calcular SHA1: " + e.getMessage());
            }

        } catch (IllegalArgumentException e) {
            System.err.println("Erro: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado:" + e.getMessage());
        } finally {
            sc.close();
        }
    }

    /**
     * Converte uma string hexadecimal em um array de bytes.
     */
    public static byte[] parseHexToken(String hex) {
        byte[] bytes = new byte[3];
        for (int i = 0; i < 3; i++) {
            String byteHex = hex.substring(i * 2, (i + 1) * 2); // Pega cada par de caracteres
            bytes[i] = (byte) Integer.parseInt(byteHex, 16); // Converte de hexadecimal para byte
        }
        return bytes;
    }

    /**
     * Converte um inteiro em uma representação little-endian (4 bytes) em um array de bytes.
     */
    public static byte[] toLittleEndianBytes(int value) {
        byte[] bytes = new byte[4];
        for (int i = 0; i < 4; i++) {
            bytes[i] = (byte) ((value >> (8 * i)) & 0xFF); // Desloca e pega os 8 bits menos significativos
        }
        return bytes;
    }

    /**
     * Calcula o hash SHA1 de um array de bytes.
     */
    public static byte[] calculateSHA1(byte[] input) throws NoSuchAlgorithmException {
        MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
        return sha1.digest(input);
    }
}
