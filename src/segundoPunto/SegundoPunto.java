package segundoPunto;

import java.util.Random;

public class SegundoPunto {
	
	private static final String CROMOSOMA_IDEAL[] = {"A","A","A","B"};

	public static void main(String[] args) {
		
		// Población Inicial
		String individuo1[] = {"B","A","A","A"};
		String individuo2[] = {"B","B","B","B"};
		String individuo3[] = {"A","B","B","A"};
		String individuo4[] = {"B","A","B","A"};
		String poblacion[][] = {individuo1, individuo2, individuo3, individuo4};
		
		/**************Impresión de datos por consola********************/
		System.out.println("----------Población Inicial----------");
		System.out.println();
		System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
		/****************************************************************/
		
		// El ciclo de creación de nuevas generaciones se realizará hasta que se cumpla el criterio de convergencia
		int generacion = 1;
		while(converge(poblacion)==false){
			// Obtención de los nuevos integrantes de la siguiente generación
			String individuosPorSeleccion[][] = seleccionar(poblacion); // Se obtienen 2 por selección
			String individuoPorCruce[] = cruzar(poblacion); // Se obtiene 1 hijo del cruce de 2 individuos
			String individuoPorMutacion[] = mutar(poblacion); // Se obtiene 1 individuo mutado
			// Reemplazo de los nuevos individuos en la población
			poblacion[0] = individuosPorSeleccion[0];
			poblacion[1] = individuosPorSeleccion[1];
			poblacion[2] = individuoPorCruce;
			poblacion[3] = individuoPorMutacion;
			/**************Impresión de datos por consola********************/
			System.out.println("----------Generación # "+generacion+"----------");
			System.out.println();
			System.out.println(imprimirPoblacion(poblacion));
			System.out.println();
			/****************************************************************/
			generacion++;
		}
		generacion--;
		/**************Impresión de datos por consola********************/
		System.out.println();
		System.out.println();
		System.out.println("**********Convergencia****************");
		System.out.println();
		System.out.println(" -> Generación # "+generacion);
		System.out.println(imprimirPoblacion(poblacion));
		System.out.println();
		/****************************************************************/
	}
	
	// Método encargado de seleccionar 2 individuos aleatoriamente de la población actual
	private static String[][] seleccionar(String poblacion[][]){
		
		String individuosSeleccionados[][] = new String[2][4];
		
		// Se obtiene el individuo con mejor aptitud
		int iMejor = 0;
		int mejorApt = 0;
		for(int i = 0; i<=3; i++){
			if(medirAptitud(poblacion[i])>mejorApt){
				iMejor = i;
				mejorApt = medirAptitud(poblacion[i]); 
			}
		}
		// Se obtiene el segundo individuo con mejor aptitud que sea diferente del primero
		int i2Mejor = 0;
		int mejor2Apt = 0;
		for(int i = 0; i<=3; i++){
			if(medirAptitud(poblacion[i])>mejor2Apt && i!=iMejor){
				i2Mejor = i;
				mejor2Apt = medirAptitud(poblacion[i]); 
			}
		}		
		
		individuosSeleccionados[0] = poblacion[iMejor]; 
		individuosSeleccionados[1] = poblacion[i2Mejor];
		
		/**************Impresión de datos por consola********************/
		System.out.println(" 1. Selección");
		System.out.println("       o Individuo "+(iMejor+1)+": "+imprimirIndividuo(individuosSeleccionados[0]));
		System.out.println("       o Individuo "+(i2Mejor+1)+": "+imprimirIndividuo(individuosSeleccionados[1]));
		System.out.println();	
		/****************************************************************/
		
		return individuosSeleccionados;
	}
	
	// Método encargado de seleccionar dos individuos aleatoriamente para realizar un cruce entre ellos
	private static String[] cruzar(String poblacion[][]){
		
		String padreDominante[] = new String[4];
		String padreRecesivo[] = new String[4];
		String hijoCruce[] = new String[4];
		
		// Se seleccionan 2 individuos diferentes de la población de manera aleatoria
		Random r = new Random();
		int aleatorio1, aleatorio2;
		aleatorio1 =  r.nextInt(4);
		do{
			aleatorio2 = r.nextInt(4);
		}while(aleatorio2==aleatorio1);
		
		// Al tener los 2 padres, se elige el que tenga mejor aptitud como el padre dominante, siendo el otro
		// el padre recesivo.
		if(medirAptitud(poblacion[aleatorio1])>=medirAptitud(poblacion[aleatorio2])){
			padreDominante = poblacion[aleatorio1];
			padreRecesivo = poblacion[aleatorio2];
		}
		else{
			padreDominante = poblacion[aleatorio2];
			padreRecesivo = poblacion[aleatorio1];
		}
		
		// El hijo resultante de este cruce tendrá como primeros genes los mismos del padre dominante, y
		// desde el punto de cruce en adelante tendrá los genes del padre recesivo
		int puntoCruce =  r.nextInt(3)+1;
		for(int i = 0; i<=3;i++){
			if(i<puntoCruce){
				hijoCruce[i] = padreDominante[i];
			}
			else{
				hijoCruce[i] = padreRecesivo[i];
			}
		}
		
		/**************Impresión de datos por consola********************/
		System.out.println(" 2. Cruce");
		if(medirAptitud(poblacion[aleatorio1])>=medirAptitud(poblacion[aleatorio2])){
			padreDominante = poblacion[aleatorio1];
			padreRecesivo = poblacion[aleatorio2];
			System.out.println("       - Individuo "+(aleatorio1+1)+": "+imprimirIndividuo(padreDominante)+" (Padre Dominante)");	
			System.out.println("       - Individuo "+(aleatorio2+1)+": "+imprimirIndividuo(padreRecesivo)+" (Padre Recesivo)");
		}
		else{
			padreDominante = poblacion[aleatorio2];
			padreRecesivo = poblacion[aleatorio1];
			System.out.println("       - Individuo "+(aleatorio2+1)+": "+imprimirIndividuo(padreDominante)+" (Padre Dominante)");	
			System.out.println("       - Individuo "+(aleatorio1+1)+": "+imprimirIndividuo(padreRecesivo)+" (Padre Recesivo)");
		}
		System.out.println("       o Nuevo Hijo : "+imprimirIndividuo(hijoCruce)+" (Punto de Cruce = "+(puntoCruce+1)+")");
		System.out.println();
		/****************************************************************/
		
		return hijoCruce;
	}
	
	// Este método elige un individuo aleatoriamente y muta uno de sus genes tambien elegido de forma aleatoria
	private static String[] mutar(String poblacion[][]){
		Random r = new Random();
		// Se obtiene un individuo aleatoriamente y se copia todo su código genético en un nuevo individuo
		int individuo = r.nextInt(4); 
		String individuoMutado[] = {new String(poblacion[individuo][0]),new String(poblacion[individuo][1]),new String(poblacion[individuo][2]),new String(poblacion[individuo][3])};
		// Generamos un indice aleatorio, que corresponderá al gen que será mutado, despues se invierte en el nuevo individuo
		int puntoMutacion = r.nextInt(4);
		if(individuoMutado[puntoMutacion].equals("A")){
			individuoMutado[puntoMutacion] = "B";	
		}
		else{
			individuoMutado[puntoMutacion] = "A";
		}
		
		/**************Impresión de datos por consola********************/
		System.out.println(" 3. Mutación");
		System.out.println("       - Individuo "+(individuo+1)+": "+imprimirIndividuo(poblacion[individuo]));
		System.out.println("       o Mutado     : "+imprimirIndividuo(individuoMutado)+" (Punto de Mutación = "+(puntoMutacion+1)+")");
		System.out.println();
		/****************************************************************/
		
		return individuoMutado;
	}
	
	// Este método mide la aptitud de un individuo, comparando cada gen con los genes del cromosa referencia (AAAB)
	private static int medirAptitud(String individuo[]){
		int aptitud = 0; // 0 puntos si no hay coincidencia entre ningún gen
		if(individuo[0].equals(CROMOSOMA_IDEAL[0])) aptitud = aptitud + 2; // 2 puntos para coincidencias con el gen referente a la resistencia a la humedad
		if(individuo[1].equals(CROMOSOMA_IDEAL[1])) aptitud = aptitud + 2; // 2 puntos para coincidencias con el gen referente a la resistencia al calor
		if(individuo[2].equals(CROMOSOMA_IDEAL[2])) aptitud = aptitud + 3; // 3 puntos para coincidencias con el gen del número de hojas
		if(individuo[3].equals(CROMOSOMA_IDEAL[3])) aptitud = aptitud + 1; // 1 punto  para coincidencias con el gen de la altura de tallo
		return aptitud;
	}
	
	// Este método compara los genes de cada uno de los individuos de la población,
	// dando como verdadera la convergencia en el momento en que todos son iguales
	private static boolean converge(String poblacion[][]){
		boolean convergencia = true;
		for(int i=1; i<=3; i++){
			for(int j=0; j<=3; j++){
				// Por un solo gen que sea diferente entre 2 individuos se dice que NO converge
				if(!poblacion[0][j].equals(poblacion[i][j])){
					convergencia = false;
					return convergencia;
				}
			}
		}
		// Si se recorren todos los elementos correctamente y ningun gen es diferente de otro entre individuos se dice que converge
		return convergencia;
	}
	
	// Los siguiente métodos se encargan de representar la información de los individuos y sus genes por consola
	
	private static String imprimirIndividuo(String individuo[]){
		String resultado = individuo[0]+individuo[1]+individuo[2]+individuo[3];
		return resultado;
	}
	
	private static String imprimirPoblacion(String poblacion[][]){
		String resultado = "+++ Individuo 1: "+imprimirIndividuo(poblacion[0])+" = "+medirAptitud(poblacion[0])+" (Aptitud) +++\n";
		resultado = resultado + "+++ Individuo 2: "+imprimirIndividuo(poblacion[1])+" = "+medirAptitud(poblacion[1])+" (Aptitud) +++\n";
		resultado = resultado + "+++ Individuo 3: "+imprimirIndividuo(poblacion[2])+" = "+medirAptitud(poblacion[2])+" (Aptitud) +++\n";
		resultado = resultado + "+++ Individuo 4: "+imprimirIndividuo(poblacion[3])+" = "+medirAptitud(poblacion[3])+" (Aptitud) +++";
		return resultado;
	}
}
