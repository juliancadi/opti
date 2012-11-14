package primerPunto;

import java.util.Random;
import java.util.Vector;

public class EnfriamientoSimulado {

    public static void main(String[] args) {
    	
    	// Soluci�n Inicial
        Mapa mapa = Mapa.generarMapaAleatorio();
        Vector<Integer> i = Mapa.generarBinariosAntenas(mapa);
        mapa.setAntenas(i);
        
        /**************Impresi�n de datos por consola********************/
        System.out.println("----------Soluci�n Inicial----------");
		System.out.println();
        System.out.println(mapa.imprimirMapa());
        System.out.println();
        /****************************************************************/
        
        // Inicializaci�n de parametros
        double T = 100;
        double K = 10;
        double A = 100;
        double k = 0;
        double a = 0;
        int iteracionesTotales = 0;
        
        // El ciclo se detiene cuando la temperatura T alcanzada es mejor a 0.00001, aproximadamente cero.
        while(T>=0.00001){
        	// Ciclo para cada etapa. Se detiene cuando se alcanzan las iteraciones definidas para cada etapa (K)
        	// o cuando se alcance el n�mero definido de aceptaciones por etapa (A)
            while(k < K && a < A){
            	// Se genera una nueva posible soluci�n j dentro del conjunto de posibles soluciones del mapa.
            	Vector<Integer> j = Mapa.generarBinariosAntenas(mapa);
            	mapa.setAntenas(i);
            	int fi = mapa.evaluarZ()*-1;
            	mapa.setAntenas(j);
            	int fj = mapa.evaluarZ()*-1;
            	// Si la nueva posible soluci�n evaluada es mayor a la soluci�n actual, entonces pasa a ser la soluci�n actual.
                if(fi > fj){
                	i = j;
                	a = a + 1; // Como se acepta j, se aumenta el n�mero de aceptaciones en esta etapa
                }
                else{
                	// Si no, se asume que los estados del sistema tienen la funcion de distribucion 
                	// de probabilidad de Boltzman, por lo tanto se evalua la probabilidad de que el sistema se encuentre en el estado j
                	Random r = new Random();
                	if (r.nextDouble() < Math.exp((fi - fj)/T)){
						i = j;
						a = a + 1; // Como se acepta j, se aumenta el n�mero de aceptaciones en esta etapa
	                }
	            }
                k = k + 1; // Se aumentan las iteraciones en esta etapa
                iteracionesTotales++;
            }
            // Se disminuye la temperatura y se aumenta la cantidad de aceptaciones por etapa
	        T = 0.10*T;
	        K = 1.50*K;
	        k = 0;
	        a = 0;
        }
        mapa.setAntenas(i);
        /**************Impresi�n de datos por consola********************/
        System.out.println("----------Soluci�n �ptima----------");
		System.out.println();
        System.out.println(mapa.imprimirMapa());
        System.out.println("N�mero total de iteraciones: "+iteracionesTotales);
        /****************************************************************/
    }
}

