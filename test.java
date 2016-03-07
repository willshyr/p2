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
        System.out.println(hm.getLoad());
        System.out.println(hm.getMaxLoad());

        for (int i = 0; i < karray.length; i++) {
            hm.put(karray[i], varray[i]);
        }
        System.out.println(hm.get(3));
        // System.out.println(hm.getClass().getName());
        // System.out.println(hm.isEmpty());

    }
}