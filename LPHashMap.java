import java.util.Map;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
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
public class LPHashMap<K, V> implements MapJHU<K, V>, 
                                                Iterable<LPMapEntry<K, V>> {

    /* Custom methods --------------------------------- */
    /** Max load factor. */
    private float maxlf;
    /** Table for storing the entries. */
    private LPMapEntry<K, V>[] table;
    /** Table capacity. */
    private int capacity;
    /** Number of entries in table. */
    private int n = 0; // size
    /** Number of tombs. */
    private int tomb = 0;
    /** Initial capacity. */
    private final int initcap = 5;

    /**
     * Create an empty open addressing hash map implementation with capacity 5.
     * 
     * @param max
     *            the maximum load factor, 0 < maxLoad <= 1
     */
    public LPHashMap(float max) {
        this.maxlf = max;
        this.capacity = this.initcap;
        this.table = new LPMapEntry[this.capacity];
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
        return ((float) this.n / this.capacity);
    }

    /**
     * Get the table capacity (total # of slots).
     * 
     * @return the capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Rehash the entries to a new table size.
     * 
     * @param cap
     *            the capacity of the table after rehashing, cap > size()
     */
    public void rehash(int cap) {
        /**
         * Use two arraylists for adding keys and values from table. Not using
         * the values() and keys() methods because the sets result in out of
         * order key-value pairs.
         */
        ArrayList<K> keys = new ArrayList<>();
        ArrayList<V> values = new ArrayList<>();
        for (LPMapEntry<K, V> e : this.table) {
            if (e != null && !e.isTombstone()) {
                keys.add(e.getKey());
                values.add(e.getValue());
            }
        }
        this.capacity = cap;
        this.table = new LPMapEntry[this.capacity];
        this.n = 0;
        this.tomb = 0;
        for (int i = 0; i < keys.size(); i++) {
            this.put(keys.get(i), values.get(i));
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
    public void clear() throws ConcurrentModificationException {
        for (int i = 0; i < this.capacity; i++) {
            this.table[i] = null;
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
        // if (value == null) {
        // return false;
        // } else {
        // return true;
        // }
        return (value != null);
    }

    @Override
    public boolean hasValue(V value) {
        for (Map.Entry<K, V> e : this.entries()) {
            if (e.getValue() == value) {
                return true;
            }
        }
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
        // get index from hashcode
        int i = key.hashCode() % this.capacity;
        // if the hashmap is empty or if current element is null
        // return null
        if (this.n == 0 || this.table[i] == null) {
            return null;
        }
        int j = i;
        // if the current index has the key and if it is not a tombstone
        // return value
        if (this.table[j].getKey().equals(key) 
                && !this.table[i].isTombstone()) {
            return this.table[j].getValue();
        } else {
            // go through array until the right key is found
            j = (j + 1) % this.capacity;
            while (j != i) {
                if (this.table[j] == null) {
                    return null;
                } else if (this.table[j].getKey().equals(key) 
                        && !this.table[j].isTombstone()) {
                    // if isTombstone(), then doesn't return value
                    return this.table[j].getValue();
                }
                j = (j + 1) % this.capacity;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) throws ConcurrentModificationException {
        // check if the load factor is about to be bigger than maxlf
        float futureLoad = this.getLoad() + (float) 1 / this.capacity;
        if (futureLoad > this.maxlf) {
            this.rehash(this.capacity * 2 + 1);
        }
        int i = key.hashCode() % this.capacity;
        int j = i;
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
            j = (j + 1) % this.capacity;
            /**
             * Use i to find the next available slot, either a tombstone or an
             * empty slot.
             */
            while (j != i) {
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
                j = (j + 1) % this.capacity;
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
    public V remove(K key) throws ConcurrentModificationException {
        V value = this.get(key);
        if (value == null) {
            // if the value is null, then no key is found
            return null;
        } else {
            this.tomb++;
            // make tombstone.
            // check if the current index i has the right key.
            int i = key.hashCode() % this.capacity;
            if (this.table[i].getKey().equals(key)) {
                this.table[i].makeTombstone();
                this.n--;
                this.tooManyTombstones();
                return value;
            } else {
                // if the current index i doesn't have the right key, keep
                // checking by linear probing until the right key is found.
                // return the value when found.
                int j = i; // create new index j
                j = (j + 1) % this.capacity; // cycle through index j
                while (j != i) { // compare index j to index i
                    if (this.table[j].getKey().equals(key)) {
                        this.table[j].makeTombstone();
                        this.n--;
                        this.tooManyTombstones();
                        return value;
                    }
                    j = (j + 1) % this.capacity;
                }
            }

        }
        // if index j = i and after going through the table,
        // no key is found, nothing is removed and return null
        return null;
    }
    /** When num of tombstones > num of entries,
     *  the hashmap is rehashed with the same capcity.
     */
    private void tooManyTombstones() {
        if (this.tomb > (this.n)) {
            this.rehash(this.capacity);
        }
    }

    // You may use HashSet within this method.
    @Override
    public Set<Map.Entry<K, V>> entries() {
        HashSet<Map.Entry<K, V>> temp = new HashSet<Map.Entry<K, V>>(this.n);
        for (LPMapEntry<K, V> e : this.table) {
            if (e != null && !e.isTombstone()) {
                temp.add(e);
            }
        }
        return temp;
    }

    /**
     * Get a set of all the keys in the map.
     * 
     * @return the set
     */
    // You may use HashSet within this method.
    @Override
    public Set<K> keys() {
        HashSet<K> temp = new HashSet<K>(this.n);
        for (LPMapEntry<K, V> e : this.table) {
            if (e != null && !e.isTombstone()) {
                temp.add(e.getKey());
            }
        }
        return temp;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> temp = new ArrayList<V>();
        for (LPMapEntry<K, V> e : this.table) {
            if (e != null && !e.isTombstone()) {
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
        return new HashMapIterator();
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

    // public String toString() {
    // String temp = "{ ";
    // for (Map.Entry<K, V> e : this.entries()) {
    // String key = e.getKey().toString();
    // String value = e.getValue().toString();
    // temp = temp + "{" + key + ", " + value + "} ";
    // }
    // temp = temp + "}";
    // return temp;
    // }

    /* ----- insert the HashMapIterator inner class here ----- */
    /** Inner Iterator class for iterating through hashmap.
     * 
     */
    class HashMapIterator implements Iterator<LPMapEntry<K, V>> {
        /** Index for iterator.
         * 
         */
        private int nextIndex = -1;
        
        @Override
        public boolean hasNext() {
            return this.nextIndex < (LPHashMap.this.capacity - 1);
        }

        @Override
        public LPMapEntry<K, V> next() {
            int index = ++this.nextIndex;
            // if (table[index] == null) {
            // return null;
            // } else {
            // return table[index];
            // }
            return LPHashMap.this.table[index];
        }

        @Override
        public void remove() {
            if (LPHashMap.this.table[this.nextIndex] != null 
                    && !LPHashMap.this.table[this.nextIndex].isTombstone()) {
                LPHashMap.this.n--;
                LPHashMap.this.table[this.nextIndex].makeTombstone();
            }
        }

    }
}
