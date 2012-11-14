package primerPunto;

import java.util.Vector;

public class BusquedaTabu {

	public static void main(String[] args) {
		
		// Solución Inicial
		Mapa mapa = Mapa.generarMapaAleatorio();
        Vector<Integer> solucionActual = Mapa.generarBinariosAntenas(mapa);
        mapa.setAntenas(solucionActual);
        
        // Parametros iniciales
        // Solucion inicial = solucion actual
        // Lista Tabu vacia
        Vector<Integer> mejorSolucion = new Vector<Integer>(solucionActual);
        int mejorSolucionZ = mapa.evaluarZ();
        Vector<Vector<Integer>> listaTabu = new Vector<Vector<Integer>>();

        /**************Impresión de datos por consola********************/
        System.out.println("----------Solución Inicial----------");
		System.out.println();
        System.out.println(mapa.imprimirMapa());
        System.out.println();
        /****************************************************************/
        
        // El criterio de parada son 50 iteraciones
        for(int i=0; i<50; i++){
        	Vector<Integer> mejorVecino = new Vector<Integer>(); 
        	int mejorVecinoZ = 0;
        	// Se crean los nuevos vecinos, variando de a un bit en la solucion actual
        	for(int j=0; j<solucionActual.size();j++){
        		Vector<Integer> vecino = new Vector<Integer>(solucionActual);
        		int bit = vecino.elementAt(j);
        		if(bit==0) {
        			vecino.setElementAt(1, j);
        		}
        		else{
        			vecino.setElementAt(0, j);        		
        		}
        		mapa.setAntenas(vecino);
        		// A medida que avanza el ciclo se obtiene el mejor vecino, evaluando
        		// que tenga el mayor valor en la función objetivo y comparado que no se encuentre en la lista Tabú
        		int z = mapa.evaluarZ();
        		if(z>mejorVecinoZ && listaTabu.indexOf(vecino)==-1){
        			mejorVecino = vecino;
        			mejorVecinoZ = z;
        		}
        	}
            // Se agrega la solución actual a la lista Tabú
        	listaTabu.addElement(solucionActual);
        	// La siguiente solución actual será el mejor vecino encontrado en la iteración
        	solucionActual = mejorVecino;
        	// Se compara si hay una nueva mejor solución en esta iteración
        	if(mejorVecinoZ>mejorSolucionZ){
        		mejorSolucion = mejorVecino;
        		mejorSolucionZ = mejorVecinoZ;
        	}
        }
        
        /**************Impresión de datos por consola********************/
        mapa.setAntenas(mejorSolucion);
        System.out.println("----------Solución Óptima----------");
		System.out.println();
        System.out.println(mapa.imprimirMapa());
        System.out.println();
        /****************************************************************/
	}

}
