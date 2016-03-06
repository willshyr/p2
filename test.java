import java.util.Scanner;
public class test{
	public static void main(String[] args) { 
		String input = "1 fish 2 fish red fish blue fish";
		Scanner s = new Scanner(input).useDelimiter("\\s*fish\\s*");
		System.out.println(s.nextInt()); // prints: 1
		System.out.println(s.nextInt()); // prints: 2
		System.out.println(s.next()); // prints: red
		System.out.println(s.next()); // prints: blue
		s.close();
	}
}