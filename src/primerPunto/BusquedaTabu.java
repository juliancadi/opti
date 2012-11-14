package primerPunto;

import java.util.Vector;

public class BusquedaTabu {

	public static void main(String[] args) {
		
		// Soluci�n Inicial
		Mapa mapa = Mapa.generarMapaAleatorio();
        Vector<Integer> solucionActual = Mapa.generarBinariosAntenas(mapa);
        mapa.setAntenas(solucionActual);
        
        // Parametros iniciales
        // Solucion inicial = solucion actual
        // Lista Tabu vacia
        Vector<Integer> mejorSolucion = new Vector<Integer>(solucionActual);
        int mejorSolucionZ = mapa.evaluarZ();
        Vector<Vector<Integer>> listaTabu = new Vector<Vector<Integer>>();

        /**************Impresi�n de datos por consola********************/
        System.out.println("----------Soluci�n Inicial----------");
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
        		// que tenga el mayor valor en la funci�n objetivo y comparado que no se encuentre en la lista Tab�
        		int z = mapa.evaluarZ();
        		if(z>mejorVecinoZ && listaTabu.indexOf(vecino)==-1){
        			mejorVecino = vecino;
        			mejorVecinoZ = z;
        		}
        	}
            // Se agrega la soluci�n actual a la lista Tab�
        	listaTabu.addElement(solucionActual);
        	// La siguiente soluci�n actual ser� el mejor vecino encontrado en la iteraci�n
        	solucionActual = mejorVecino;
        	// Se compara si hay una nueva mejor soluci�n en esta iteraci�n
        	if(mejorVecinoZ>mejorSolucionZ){
        		mejorSolucion = mejorVecino;
        		mejorSolucionZ = mejorVecinoZ;
        	}
        }
        
        /**************Impresi�n de datos por consola********************/
        mapa.setAntenas(mejorSolucion);
        System.out.println("----------Soluci�n �ptima----------");
		System.out.println();
        System.out.println(mapa.imprimirMapa());
        System.out.println();
        /****************************************************************/
	}

}
