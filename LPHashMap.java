import java.util.Map;
import java.util.Set;
import java.util.Collection;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/** Base implementation of MapJHU using linear probe open addressing.
 *  @author CS226 Staff, Spring 2016
 *  @param <K> the base type of the keys
 *  @param <V> the base type of the values
 */
public class LPHashMap<K, V> implements MapJHU<K, V>,
                                        Iterable<LPMapEntry<K, V>> {

    /* Custom methods --------------------------------- */
    private float lf;
    /** Create an empty open addressing hash map implementation with capacity 5.
     *  @param max the maximum load factor, 0 < maxLoad <= 1
     */
    public LPHashMap(float max) {
        this.lf = max;
    }

    /** Get the maximum load factor.
     *  @return the load factor
     */
    public float getMaxLoad() {
        return 0;
    }

    /** Get the current load factor.
     *  @return the load factor, should be 0 < lf <= 1
     */
    public float getLoad() {
        return 0;
    }

    /** Get the table capacity (total # of slots).
     *  @return the capacity
     */
    public int getCapacity() {
        return 0;
    }

    /** Rehash the entries to a new table size.
     *  @param cap the capacity of the table after rehashing, cap > size()
     */
    public void rehash(int cap) {

    }

    /** Get the number of tombstones currently in the map (markers
     *  left behind when values were deleted, until the slot is reused).
     *  @return the number
     */
    public int ghosts() {
        return 0;
    }

    /* Methods from the MapJHU interface ----------------  */

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public boolean hasKey(K key) {
        return false;
    }

    @Override
    public boolean hasValue(V value) {
        return false;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    // You may use HashSet within this method.
    @Override
    public Set<Map.Entry<K, V>> entries() {
        return null;
    }

    // You may use HashSet within this method.
    @Override
    public Set<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    /*  --------------  from Object --------  YOU DON'T HAVE TO IMPLEMENT
    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
    */

    /* ---------- from Iterable ---------- */
    @Override
    public Iterator<LPMapEntry<K, V>> iterator() {
        return null;
    }

    @Override
    public void forEach(Consumer<? super LPMapEntry<K, V>> action) {
        // you do not have to implement this
    }

    @Override
    public Spliterator<LPMapEntry<K, V>> spliterator() {
        // you do not have to implement this
        return null;
    }

    /* -----  insert the HashMapIterator inner class here ----- */
}
