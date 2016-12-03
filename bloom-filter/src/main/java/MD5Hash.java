import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: Killua
 * Date: 2013-11-2
 * E-Mail: killua_hzl@163.com
 * Description: A IHash implement by using MD5.
 */
public class MD5Hash implements IHashFunction<byte[]> {

    public static final String HASH_ALGORITHM = "MD5";

    @Override
    public int[] hash(byte[] element, int hashNum) {

        int hashes[] = new int[hashNum];

        //Reference: https://github.com/MagnusS/Java-BloomFilter/blob/master/src/com/skjegstad/utils/BloomFilter.java
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(HASH_ALGORITHM);

            int k = 0;
            byte salt = 0;
            while (k < hashNum) {

                messageDigest.update(salt++);
                byte[] md5 = messageDigest.digest(element);

                for (int i = 0; i < md5.length / 4 && k < hashNum; i++) {
                    int h = 0;
                    for (int j = (i * 4); j < (i * 4) + 4; j++) {
                        h <<= 8;
                        h |= ((int) md5[j]) & 0xFF;
                    }
                    hashes[k] = h;
                    k++;
                }
            }
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException(
                    "No Such Algorithm " + HASH_ALGORITHM);
        }

        return hashes;
    }
}
