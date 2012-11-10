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
		// Población Inicial
		/*Mapa mapa = Mapa.generarMapaAleatorio();
		Vector<Integer> individuo1 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo2 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo3 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector<Integer> individuo4 = Mapa.generarBinariosAntenas(mapa.getnAntenas());
		Vector poblacion[] = {individuo1,individuo2,individuo3,individuo4};*/
				
		System.out.println("----------Población Inicial----------");
		System.out.println();
		//System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
				
		// El ciclo de creación de nuevas generaciones se realizará hasta que se cumpla el criterio de convergencia
		int generacion = 1;
		//while(converge(poblacion)==false){
			// Obtención de los nuevos integrantes de la siguiente generación
			//Vector<Integer> individuosPorSeleccion[] = seleccionar(poblacion); // Se obtienen 2 por selección
			//Vector<Integer> individuoPorCruce[] = cruzar(poblacion); // Se obtiene 1 hijo del cruce de 2 individuos
			//Vector<Integer> individuoPorMutacion[] = mutar(poblacion); // Se obtiene 1 individuo mutado
			// Reemplazo de los nuevos individuos en la población
			//poblacion[0] = individuosPorSeleccion[0];
			//poblacion[1] = individuosPorSeleccion[1];
			//poblacion[2] = individuoPorCruce;
			//poblacion[3] = individuoPorMutacion;
			/**************Impresión de datos por consola********************/
			System.out.println("----------Generación # "+generacion+"----------");
			System.out.println();
			//System.out.println(imprimirPoblacion(poblacion));
			System.out.println();
			/****************************************************************/
			generacion++;
		//}
		generacion--;
		/**************Impresión de datos por consola********************/
		System.out.println();
		System.out.println();
		System.out.println("**********Convergencia****************");
		System.out.println();
		System.out.println(" -> Generación # "+generacion);
		//System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
		/****************************************************************/
	}

}
