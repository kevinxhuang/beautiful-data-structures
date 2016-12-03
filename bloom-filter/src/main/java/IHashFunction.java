/**
 * User: Killua
 * Date: 2013-11-2
 * E-Mail: killua_hzl@163.com
 * Description: Hash function interface in BloomFilter
 */
public interface IHashFunction<E> {

    public int[] hash(E element, int hashNum);
}
