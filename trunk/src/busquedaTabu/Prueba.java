package busquedaTabu;

public class Prueba {
	
	public static void main(String arg[]){
		Mapa mapa = Mapa.generarMapaAleatorio(15, 15);
		System.out.println("Mapa vacio:");
		System.out.println(mapa.toString());
		mapa.ponerAntenasAleatorias();
		System.out.println("Con antenas:");
		System.out.println(mapa.toString());
		System.out.println(mapa.resultados());
		mapa.optimizar();
		System.out.println("Busqueda Tabu:");
		System.out.println(mapa.toString());
		System.out.println(mapa.resultados());
		for(int i=0; i <100; i++){
			System.out.print(mapa.getAntenas()[i] +" ");	
		}
		System.out.println();

		
	}

}
