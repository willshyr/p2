import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * Base implementation of MapJHU using linear probe open addressing.
 * 
 * @author CS226 Staff, Spring 2016
 * @param <K>
 *            the base type of the keys
 * @param <V>
 *            the base type of the values
 */
public class LPHashMap<K, V> implements MapJHU<K, V>, Iterable<LPMapEntry<K, V>> {

    /* Custom methods --------------------------------- */
    private float maxlf;
    // private float currlf;
    private LPMapEntry<K, V>[] table;
    private int cap;
    private int n = 0; // size
    private int tomb = 0;

    /**
     * Create an empty open addressing hash map implementation with capacity 5.
     * 
     * @param max
     *            the maximum load factor, 0 < maxLoad <= 1
     */
    public LPHashMap(float max) {
        this.maxlf = max;
        this.cap = 5;
        table = new LPMapEntry[this.cap];
    }

    /**
     * Get the maximum load factor.
     * 
     * @return the load factor
     */
    public float getMaxLoad() {
        return this.maxlf;
    }

    /**
     * Get the current load factor.
     * 
     * @return the load factor, should be 0 < lf <= 1
     */
    public float getLoad() {
        return ((float) this.n / this.cap);
    }

    /**
     * Get the table capacity (total # of slots).
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return this.cap;
    }

    /**
     * Rehash the entries to a new table size.
     * 
     * @param cap
     *            the capacity of the table after rehashing, cap > size()
     */
    public void rehash(int cap) {
        ArrayList<Map.Entry<K, V>> temp = new ArrayList<>(n);
        for (Map.Entry<K, V> e : this.entries()) {
            temp.add(e);
        }
        // System.out.println(temp.get(0));
        this.cap = cap;
        this.table = new LPMapEntry[cap];
        this.n = 0;
        for (Map.Entry<K, V> e : temp) {
            this.put(e.getKey(), e.getValue());
        }
    }

    /**
     * Get the number of tombstones currently in the map (markers left behind
     * when values were deleted, until the slot is reused).
     * 
     * @return the number
     */
    public int ghosts() {
        return this.tomb;
    }

    /* Methods from the MapJHU interface ---------------- */
    /**
     * Get the number of (actual) entries in the Map.
     * 
     * @return the size
     */
    @Override
    public int size() {
        return this.n;
    }

    @Override
    public void clear() {
        for (int i = 0; i < this.cap; i++) {
            table[i] = null;
        }
        this.n = 0;
    }

    @Override
    public boolean isEmpty() {
        return this.n == 0;
    }

    @Override
    public boolean hasKey(K key) {
        V value = this.get(key);
        if (value == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean hasValue(V value) {
        return false;
    }

    /**
     * Get the value associated with a key if there.
     * 
     * @param key
     *            the key being searched for
     * @return the value associated with key, or null if not found
     */
    @Override
    public V get(K key) {
        int j = key.hashCode() % this.cap;
        if (this.n == 0 || this.table[j] == null) {
            return null;
        }
        int i = j;
        if (this.table[i].getKey().equals(key)) {
            return table[i].getValue();
        } else {
            // go through array until the right key is found
            i = (i + 1) % this.cap;
            while (i != j) {
                System.out.println(i);
                if (this.table[i] == null) {
                    return null;
                } else if (this.table[i].getKey().equals(key)) {
                    return table[i].getValue();
                }
                i = (i + 1) % this.cap;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        // check if the load factor is about to be bigger than maxlf
        float futureLoad = this.getLoad() + (float) 1 / this.cap;
        // System.out.println(futureLoad);
        // System.out.println(futureLoad > this.maxlf);
        if (futureLoad > this.maxlf) {
            this.rehash(this.cap * 2);
        }
        int j = key.hashCode() % this.cap;
        int i = j;
        if (this.hasKey(key)) {
            V old = this.table[j].getValue();
            this.table[j].setValue(value);
            return old;
        } else {
            if (this.table[j] == null) {
                this.table[j] = new LPMapEntry<>(key, value);
                this.n++;
                return null;
            } else if (this.table[j].isTombstone()) {
                this.table[j] = new LPMapEntry<>(key, value);
                this.tomb--;
                this.n++;
                return null;
            }
            i = (i + 1) % this.cap;
            /**
             * Use i to find the next available slot, either a tombstone or an
             * empty slot.
             */
            while (i != j) {
                if (this.table[j] == null) {
                    this.table[j] = new LPMapEntry<>(key, value);
                    this.n++;
                    return null;
                } else if (this.table[j].isTombstone()) {
                    this.tomb--;
                    this.table[j] = new LPMapEntry<>(key, value);
                    this.n++;
                    return null;
                }
                i = (i + 1) % this.cap;
            }
        }
        return null;
    }

    /**
     * Remove the entry associated with a key.
     * 
     * @param key
     *            the key for the entry being deleted
     * @return the value associated with the key, or null if key not there
     */
    @Override
    public V remove(K key) {
        V value = this.get(key);
        if (value == null) {
            // if the value is null, then no key is found
            return null;
        } else {
            this.tomb++;
            // make tombstone.
            // check if the current index j has the right key.
            int j = key.hashCode() % this.cap;
            if (this.table[j].getKey().equals(key)) {
                this.table[j].makeTombstone();
            } else {
                /**
                 * if the current index j doesn't have the right key, keep
                 * checking by linear probing until the right key is found.
                 */
                int i = j;
                i = (i + 1) % this.cap;
                while (++i != j) {
                    if (this.table[j].getKey().equals(key)) {
                        this.table[j].makeTombstone();
                    }
                }
            }
            /**
             * rehash if the number of tombstones exceeds number of elements.
             * 
             */
            if (this.tomb > this.n) {
                this.rehash(this.cap * 2);
            }
            return value;
        }
    }

    // You may use HashSet within this method.
    @Override
    public Set<Map.Entry<K, V>> entries() {
        HashSet<Map.Entry<K, V>> temp = new HashSet<Map.Entry<K, V>>(this.n);
        for (LPMapEntry<K, V> e : table) {
            if (e != null) {
                temp.add(e);
            }
        }
        // System.out.println(temp);
        return temp;
    }

    // You may use HashSet within this method.
    @Override
    public Set<K> keys() {
        HashSet<K> temp = new HashSet<K>(this.n);
        for (LPMapEntry<K, V> e : table) {
            if (e != null) {
                temp.add(e.getKey());
            }
        }
        return temp;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> temp = new ArrayList<V>();
        for (LPMapEntry<K, V> e : table) {
            if (e != null) {
                temp.add(e.getValue());
            }
        }
        return temp;
    }

    /*
     * -------------- from Object -------- YOU DON'T HAVE TO IMPLEMENT
     * 
     * @Override public boolean equals(Object o) { return false; }
     * 
     * @Override public int hashCode() { return 0; }
     */

    /* ---------- from Iterable ---------- */
    @Override
    public Iterator<LPMapEntry<K, V>> iterator() {
        // Iterator<LPMapEntry<K,V>> temp = new table.iterator();
        ArrayList<LPMapEntry<K, V>> lst = new ArrayList<LPMapEntry<K, V>>();
        for (int i = 0; i < this.n; i++) {
            lst.add(table[i]);
        }
        HashMapIterator<LPMapEntry<K,V>> itr = lst.iterator();
        
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

    public String toString() {
        String temp = "{ ";
        for (Map.Entry<K, V> e : this.entries()) {
            String key = e.getKey().toString();
            String value = e.getValue().toString();
            temp = temp + "{" + key + ", " + value + "} ";
        }
        temp = temp + "}";
        return temp;
    }

    /* ----- insert the HashMapIterator inner class here ----- */
    private class HashMapIterator implements Iterator<LPMapEntry<K, V>> {

        private int nextIndex = -1;

        @Override
        public boolean hasNext() {
            return this.nextIndex < (n - 1);
        }

        @Override
        public LPMapEntry<K, V> next() {
            int index = ++this.nextIndex;
            return table[index];
        }

        @Override
        public void remove() {
            table[this.nextIndex].makeTombstone();
        }

    }
}
