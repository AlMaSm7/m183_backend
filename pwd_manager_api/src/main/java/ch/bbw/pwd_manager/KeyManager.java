import java.io.DataOutputStream;

import java.io.FileOutputStream;
import java.io.IOException;

import java.security.KeyPair;
import java.security.KeyPairGenerator;

import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyManager {
    public static void genRSAKeyPairAndSaveToFile() {
        genRSAKeyPairAndSaveToFile(2048, "");
    }

    public static void genRSAKeyPairAndSaveToFile(String dir) {
        genRSAKeyPairAndSaveToFile(2048, dir);
    }

    public static void genRSAKeyPairAndSaveToFile(int keyLength, String dir) {
        KeyPair keyPair = genRSAKeyPair(keyLength);
        PublicKey  publicKey  = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();

        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(new FileOutputStream(dir
                    + "rsaPublicKey"));
            dos.write(publicKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null) {
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

        try {
            dos = new DataOutputStream(new FileOutputStream(dir
                    + "rsaPrivateKey"));
            dos.write(privateKey.getEncoded());
            dos.flush();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (dos != null)
                try {
                    dos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
    }

    public static KeyPair genRSAKeyPair() {
        return genRSAKeyPair(2048);
    }

    public static KeyPair genRSAKeyPair(int keyLength) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator
                    .getInstance("RSA");
            keyPairGenerator.initialize(keyLength);
            return keyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Use the public Key to decrypt the data.
     *
     * @param data the data to be decrypted
     * @param key the key used to do decryption
     * @return the decrypted data
     * @throws IllegalStateException If Your JDK don't support RSA.
     * @throws IllegalArgumentException If failed to decrypt the data.
     */
    public static byte[] decryptByPublicKey(byte[] data, String key) {
        Key publicKey = getPublicKey(key);
        return decrypt(data, publicKey);
    }

    /**
     * Use RSA to decrypt the data.
     *
     * @param data the data need to be decrypted
     * @param key the key used to do decryption
     * @return the decrypted data
     * @throws IllegalArgumentException Failed to decrypt the data.
     */
    public static byte[] decrypt(byte[] data, Key key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return CipherUtil.process(cipher, 128, data);
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "Failed to decrypt the data.", e);
        }
    }
}