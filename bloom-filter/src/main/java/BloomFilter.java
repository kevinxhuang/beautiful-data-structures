import java.util.BitSet;

/**
 * User: Killua
 * Date: 2013-11-2
 * E-Mail: killua_hzl@163.com
 * Description: A BloomFilter implement.
 */
public class BloomFilter<E> {

    private final IHashFunction hashFunction;
    private final BitSet bitSet;
    private final int hashNum;
    private final int bitSetSize;

    public BloomFilter(int bitsPerElement, int ElementNum, IHashFunction function) {

        this.bitSetSize = (int) Math.ceil(bitsPerElement * ElementNum);
        this.bitSet = new BitSet(bitSetSize);
        this.hashNum = (int) (bitSetSize / ElementNum * Math.log(2.0));
        this.hashFunction = function;
    }

    public BloomFilter(double falsePositiveProbability, int ElementNum, IHashFunction function) {

        this((int) (-Math.log(falsePositiveProbability) / Math.log(2.0) / Math.log(2.0)), ElementNum, function);
    }

    public void add(E element) {
        int[] hashes = hashFunction.hash(element, hashNum);
        for (int hash : hashes) {
            bitSet.set(Math.abs(hash % bitSetSize), true);
        }
    }

    public boolean contains(E element) {
        int[] hashes = hashFunction.hash(element, hashNum);
        for (int hash : hashes) {
            if (!bitSet.get(Math.abs(hash % bitSetSize))) {
                return false;
            }
        }
        return true;
    }

    public void clear() {
        bitSet.clear();
    }

    public int getHashNum() {
        return hashNum;
    }

    public int getBitSetSize() {
        return bitSetSize;
    }
}
