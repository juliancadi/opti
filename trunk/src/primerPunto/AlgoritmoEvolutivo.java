package primerPunto;

import java.util.Random;
import java.util.Vector;

public class AlgoritmoEvolutivo {
	
	public static void main(String arg[]){

		// Poblaci�n Inicial
		Mapa mapa = Mapa.generarMapaAleatorio();
		@SuppressWarnings("unchecked")
		Vector<Integer> poblacion[] = new Vector[4];
		poblacion[0] = Mapa.generarBinariosAntenas(mapa);
		poblacion[1] = Mapa.generarBinariosAntenas(mapa);
		poblacion[2] = Mapa.generarBinariosAntenas(mapa);
		poblacion[3] = Mapa.generarBinariosAntenas(mapa);
				
		/**************Impresi�n de datos por consola********************/
		System.out.println("----------Poblaci�n Inicial----------");
        System.out.println();
		System.out.println(Mapa.imprimirPoblacion(mapa, poblacion));
		System.out.println();
        /****************************************************************/
				
		// El ciclo de creaci�n de nuevas generaciones se realizar� hasta que se cumpla el criterio de convergencia
		int generacion = 1;
		while(converge(poblacion)==false){
			// Obtenci�n de los nuevos integrantes de la siguiente generaci�n
			Vector<Integer> individuosPorSeleccion[] = seleccionar(mapa, poblacion); // Se obtienen los 2 mejores por selecci�n
			Vector<Integer> individuoPorCruce = cruzar(mapa,poblacion); // Se obtiene 1 hijo del cruce de 2 individuos
			Vector<Integer> individuoPorMutacion = mutar(mapa,poblacion); // Se obtiene 1 individuo por mutaci�n
			// Reemplazo de los nuevos individuos en la poblaci�n
			poblacion[0] = individuosPorSeleccion[0];
			poblacion[1] = individuosPorSeleccion[1];
			poblacion[2] = individuoPorCruce;
			poblacion[3] = individuoPorMutacion;

			generacion++;
		}
		generacion--;
		/**************Impresi�n de datos por consola********************/
		System.out.println();
		System.out.println();
		System.out.println("**********Convergencia****************");
		System.out.println();
		System.out.println(" -> Generaci�n # "+generacion);
		mapa.setAntenas(poblacion[0]);
		System.out.println(mapa.imprimirMapa());
		System.out.println();
		/****************************************************************/
	}

	private static Vector<Integer> mutar(Mapa mapa, Vector<Integer>[] poblacion) {
		Random r = new Random();
		// Se obtiene un individuo aleatoriamente y se copia todo su c�digo gen�tico en un nuevo individuo
		int individuo = r.nextInt(4); 
		Vector<Integer> individuoMutado = poblacion[individuo];
		// Generamos un indice aleatorio, que corresponder� al gen que ser� mutado, despues se invierte en el nuevo individuo
		int puntoMutacion = r.nextInt(4);
		if(individuoMutado.elementAt(puntoMutacion)==0){
			individuoMutado.setElementAt(1, puntoMutacion);	
		}
		else{
			individuoMutado.setElementAt(0, puntoMutacion);
		}
		
		return individuoMutado;
	}

	private static Vector<Integer> cruzar(Mapa m,Vector<Integer>[] poblacion) {
		Vector<Integer> padreDominante = new Vector<Integer>();
		Vector<Integer> padreRecesivo = new Vector<Integer>();
		Vector<Integer> hijoCruce = new Vector<Integer>();
		
		// Se seleccionan 2 individuos diferentes de la poblaci�n de manera aleatoria
		Random r = new Random();
		int aleatorio1, aleatorio2;
		aleatorio1 =  r.nextInt(4);
		do{
			aleatorio2 = r.nextInt(4);
		}while(aleatorio2==aleatorio1);
		
		// Al tener los 2 padres, se elige el que tenga mejor aptitud como el padre dominante, siendo el otro
		// el padre recesivo.
		m.setAntenas(poblacion[aleatorio1]);
		int a1 =  m.evaluarZ();
		m.setAntenas(poblacion[aleatorio2]);
		int a2 =  m.evaluarZ();
		if(a1>=a2){
			padreDominante = poblacion[aleatorio1];
			padreRecesivo = poblacion[aleatorio2];
		}
		else{
			padreDominante = poblacion[aleatorio2];
			padreRecesivo = poblacion[aleatorio1];
		}
		
		// El hijo resultante de este cruce tendr� como primeros genes los mismos del padre dominante, y
		// desde el punto de cruce en adelante tendr� los genes del padre recesivo
		int puntoCruce =  r.nextInt(padreDominante.size())+1;
		for(int i = 0; i<padreDominante.size();i++){
			if(i<puntoCruce){
				hijoCruce.addElement(padreDominante.elementAt(i));
			}
			else{
				hijoCruce.addElement(padreRecesivo.elementAt(i));
			}
		}
		return hijoCruce;
	}

	private static Vector<Integer>[] seleccionar(Mapa m,Vector<Integer> poblacion[]) {
		
		@SuppressWarnings("unchecked")
		Vector<Integer> individuosSeleccionados[] = new Vector[2];
		
		// Se obtiene el individuo con mejor aptitud
		int iMejor = 0;
		int mejorApt = 0;
		for(int i = 0; i<poblacion.length; i++){
			m.setAntenas(poblacion[i]);
			if(m.evaluarZ()>mejorApt){
				iMejor = i;
				mejorApt = m.evaluarZ(); 
			}
		}
		// Se obtiene el segundo individuo con mejor aptitud que sea diferente del primero
		int i2Mejor = 0;
		int mejor2Apt = 0;
		for(int i = 0; i<poblacion.length; i++){
			m.setAntenas(poblacion[i]);
			if(m.evaluarZ()>mejor2Apt && i!=iMejor){
				i2Mejor = i;
				mejor2Apt =m.evaluarZ(); 
			}
		}		
		
		individuosSeleccionados[0] = poblacion[iMejor]; 
		individuosSeleccionados[1] = poblacion[i2Mejor];
		
		return individuosSeleccionados;
	}

	private static boolean converge(Vector<Integer> poblacion[]) {
		boolean convergencia = false;
		// El criterio de convergencia es que todos los individuos de la poblaci�n sean iguales
		if(poblacion[0].equals(poblacion[1])&&poblacion[1].equals(poblacion[2])&&poblacion[2].equals(poblacion[3])) convergencia = true;
		return convergencia;
	}

}
