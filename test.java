import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

//import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        // LPMapEntry<Integer, String> entry = new LPMapEntry<>(7, "seven");
        // entry.makeTombstone();

        Integer[] karray = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
        String[] varray = { "zro", "one", "two", "tre", "for", "fyv", "six", "svn", "ate", "nyn", "ten" };
        float maxlf = 1.0f;
        // System.out.println(maxlf);
        // while (++j < 10);
        // j = i;
        // System.out.println(i);
        // int size = 0;
        // LPHashMap<Integer, String> e7 = null; // empty map, max load .7
        // LPHashMap<Integer, String> e6; // empty map, max load .6
        LPHashMap<Integer, String> hashmap = new LPHashMap<Integer, String>(maxlf);
        HashSet<LPMapEntry<Integer, String>> pairs = new HashSet<LPMapEntry<Integer, String>>();
        LPMapEntry<Integer, String>[] table = new LPMapEntry[5];

        // for (int i = 0; i < karray.length; i++) {
        // hashmap.put(karray[i], varray[i]);
        // pairs.add(new LPMapEntry<Integer,String>(karray[i], varray[i]));
        // // // System.out.println(hm);
        // // // System.out.println(hm.getLoad());
        // //// size++;
        // //// System.out.println(hashmap.size());
        // }
        hashmap.put(0, "0"); // slot 0
        table[0] = new LPMapEntry<Integer, String>(0, "0");
        hashmap.put(10, "1"); // slot 0, probe to 1
        table[1] = new LPMapEntry<Integer, String>(10, "1");
        hashmap.put(4, "4"); // slot 4, no probe
        table[4] = new LPMapEntry<Integer, String>(4, "4");
        hashmap.put(20, "2"); // slot 0, probe to 2
        table[2] = new LPMapEntry<Integer, String>(20, "2");
        hashmap.put(14, "3"); // slot 4, probe to 0, 1, 2, 3
        table[3] = new LPMapEntry<Integer, String>(14, "3");
        Iterator<LPMapEntry<Integer, String>> itr = hashmap.iterator();
        int i = 0;
        while (itr.hasNext()) {
            System.out.print(itr.next() + " ");
            i++;
        }
        System.out.println();
        hashmap.remove(0);
        // System.out.println(hashmap.entries());
        System.out.println(
                "cap = " + hashmap.getCapacity() + "; size = " + hashmap.size() + "; tomb = " + hashmap.ghosts());
        // LPMapEntry<Integer, String> e;
        // assertEquals(hashmap.remove(0), "0");
        // itr = hashmap.iterator();
        // e = itr.next(); // should be tombstone
        // System.out.println(e.isTombstone());
        // System.out.println(hashmap.ghosts() == 1);

        Iterator<LPMapEntry<Integer, String>> itr2 = hashmap.iterator();
        // System.out.print("itr2 ");
        while (itr2.hasNext()) {
            System.out.print(itr2.next() + " ");
        }
        System.out.println();
        // System.out.println("size = " + hashmap.size() + "; tomb = " +
        // hashmap.ghosts());
        //
        hashmap.remove(10);
        // System.out.println("size = " + hashmap.size() + "; tomb = " +
        // hashmap.ghosts());
        //
        hashmap.remove(4);
        Iterator<LPMapEntry<Integer, String>> itr3 = hashmap.iterator();

        while (itr3.hasNext()) {
            System.out.print(itr3.next() + " ");
        }
        System.out.println();
        System.out.println(
                "cap = " + hashmap.getCapacity() + "; size = " + hashmap.size() + "; tomb = " + hashmap.ghosts());

        //
        //// Iterator<LPMapEntry<Integer, String>> itr2 = hashmap.iterator();
        // while (itr2.hasNext()) {
        // System.out.print(itr2.next() + " ");
        // }
        // System.out.println();

        // System.out.print(hashmap.ghosts());

        // System.out.println();

        // System.out.println(hashmap.values());
        // System.out.println(hashmap.entries());
        // System.out.println(pairs);
        // System.out.println(hashmap.hasValue("ten"));
        // keys.remove(10);
        // System.out.println(keys);
        // // System.out.println(keys.getClass().getName());
        // System.out.println(hashmap.keys());
        // System.out.println(hashmap.keys().getClass().getName());
        // System.out.println(keys.equals(hashmap.keys()));

        //

    }
}