package segundoPunto;

public class Prueba {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int i[] = {0,0};
		int j[] = cosa(i);
		System.out.println("i = "+i[0]+i[1]+" - j = "+j[0]+j[1]);
	}

	private static int[] cosa(int i[]){
		int x[] = {new Integer(i[0]),new Integer(i[1])};
		x[0]=22;
		x[1]=55;
		return x;
	}
}
