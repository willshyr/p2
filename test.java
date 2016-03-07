//import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        // LPMapEntry<Integer, String> entry = new LPMapEntry<>(7, "seven");
        // entry.makeTombstone();
        // System.out.println(entry == null);
        Integer[] karray = { 1, 3, 4, 10, 12 };
        String[] varray = { "one", "three", "four", "ten", "twelve" };
        float maxlf = 0.5f;
        System.out.println(maxlf);
        // while (++j < 10);
        // j = i;
        // System.out.println(i);
        LPHashMap<Integer, String> hm = new LPHashMap<Integer, String>(maxlf);


        // for (int i = 0; i < karray.length; i++) {
        hm.put(karray[0], varray[0]);
        hm.put(karray[2], varray[2]);
        hm.put(karray[4], varray[4]);
        System.out.println(hm.getLoad());
        System.out.println(hm.getMaxLoad());
        // }
        System.out.println(hm.get(11));
        // System.out.println(hm.getClass().getName());
        // System.out.println(hm.isEmpty());

    }
}