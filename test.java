import java.util.ArrayList;
import java.util.Iterator;

//import java.util.Scanner;
public class test {
    public static void main(String[] args) {
        // LPMapEntry<Integer, String> entry = new LPMapEntry<>(7, "seven");
        // entry.makeTombstone();
        // System.out.println(entry == null);
        Integer[] karray = {1, 3, 4, 10, 12};
        String[] varray = {"one", "three", "four", "ten", "twelve"};
        float maxlf = 0.5f;
//        System.out.println(maxlf);
        // while (++j < 10);
        // j = i;
        // System.out.println(i);
        LPHashMap<Integer, String> hm = new LPHashMap<Integer, String>(maxlf);
//        Integer ten = 10;
//        System.out.println(ten.hashCode());
        ArrayList<Integer> lst = new ArrayList<Integer>();
        for (Integer i : karray) {
            lst.add(i);
        }
        System.out.println(lst);

        Iterator<Integer> itr = lst.iterator();
        while (itr.hasNext()) {
            Integer element = itr.next();
            System.out.println(element);
            itr.remove();
        }
        System.out.println(lst);
    // for (int i = 0; i < karray.length; i++) {
    // hm.put(karray[i], varray[i]);
    // System.out.println(hm);
    // System.out.println(hm.getLoad());
    // }
    // for (int i = 0; i < karray.length; i++) {
    // System.out.println(hm.hasKey(karray[i]));
    // }
    // System.out.println(hm.getLoad());
    // System.out.println(hm.getMaxLoad());
    // // }
    // System.out.println(hm);
    // System.out.println(hm.getClass().getName());
    // System.out.println(hm.isEmpty());

}}