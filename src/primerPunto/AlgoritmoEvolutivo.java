package primerPunto;

import java.util.Vector;

public class AlgoritmoEvolutivo {
	
	public static void main(String arg[]){

		/*mapa.setAntenas(Mapa.generarBinariosAntenas(mapa.getnAntenas()));
		System.out.println(mapa.imprimirMapa());
		System.out.println("Potencia total: "+mapa.getPotenciaTotal());
		System.out.println("Puntos para antenas: "+mapa.getnAntenas());
		System.out.println("Antenas Instaladas: "+mapa.getAntenasInstaladas());
		System.out.println("Z: "+mapa.evaluarZ());*/
		//
		// Poblaci�n Inicial
		/*Mapa mapa = Mapa.generarMapaAleatorio();
		Vector<Integer> individuo1 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo2 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo3 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo4 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector poblacion[] = {individuo1,individuo2,individuo3,individuo4};*/
				
		System.out.println("----------Poblaci�n Inicial----------");
		System.out.println();
		//System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
				
		// El ciclo de creaci�n de nuevas generaciones se realizar� hasta que se cumpla el criterio de convergencia
		int generacion = 1;
		//while(converge(poblacion)==false){
			// Obtenci�n de los nuevos integrantes de la siguiente generaci�n
			//Vector<Integer> individuosPorSeleccion[] = seleccionar(poblacion); // Se obtienen 2 por selecci�n
			//Vector<Integer> individuoPorCruce[] = cruzar(poblacion); // Se obtiene 1 hijo del cruce de 2 individuos
			//Vector<Integer> individuoPorMutacion[] = mutar(poblacion); // Se obtiene 1 individuo mutado
			// Reemplazo de los nuevos individuos en la poblaci�n
			//poblacion[0] = individuosPorSeleccion[0];
			//poblacion[1] = individuosPorSeleccion[1];
			//poblacion[2] = individuoPorCruce;
			//poblacion[3] = individuoPorMutacion;
			/**************Impresi�n de datos por consola********************/
			System.out.println("----------Generaci�n # "+generacion+"----------");
			System.out.println();
			//System.out.println(imprimirPoblacion(poblacion));
			System.out.println();
			/****************************************************************/
			generacion++;
		//}
		generacion--;
		/**************Impresi�n de datos por consola********************/
		System.out.println();
		System.out.println();
		System.out.println("**********Convergencia****************");
		System.out.println();
		System.out.println(" -> Generaci�n # "+generacion);
		//System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
		/****************************************************************/
	}

}
