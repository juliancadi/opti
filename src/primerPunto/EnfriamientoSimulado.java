package primerPunto;

import java.util.Random;
import java.util.Vector;

/**
 *
 * @author julian.caicedo
 */
public class EnfriamientoSimulado {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Mapa mapa = Mapa.generarMapaAleatorio();
        Vector<Integer> i = Mapa.generarBinariosAntenas(mapa);
        mapa.setAntenas(i);
        System.out.println(mapa.imprimirMapa());
        System.out.println("Puntos para antena: "+mapa.getnAntenas());
        System.out.println("Antenas instaladas: "+mapa.getAntenasInstaladas());
        System.out.println("Potencia total: "+mapa.getPotenciaTotal());
        System.out.println("Cobertura total: "+mapa.evaluarZ());
        System.out.println();
        double T = 100;
        double K = 10000;
        double A = 100;
        double k = 0;
        double a = 0;
        System.out.println("pablo pirobo");
        while(T>0){
            while(k < K && a < A){
            	Vector<Integer> j = Mapa.generarBinariosAntenas(mapa);
            	mapa.setAntenas(i);
            	int fi = mapa.evaluarZ()*-1;
            	mapa.setAntenas(j);
            	int fj = mapa.evaluarZ()*-1;
                if(fi-fj < 0){
                	i = j;
                	mapa.setAntenas(i);
                	a = a + 1;
                }
                else{
                	Random r = new Random();
                	if (r.nextDouble() < Math.exp((fi - fj)/T)){
						i = j;
						mapa.setAntenas(i);
						a = a + 1;
	                }
	            }
                k = k + 1;
            }
	        T = 0.10*T;
	        K = 0.10*K;
	        k = 0;
	        a = 0;
        }
        mapa.setAntenas(i);
        System.out.println(mapa.imprimirMapa());
        System.out.println("Puntos para antena: "+mapa.getnAntenas());
        System.out.println("Antenas instaladas: "+mapa.getAntenasInstaladas());
        System.out.println("Potencia total: "+mapa.getPotenciaTotal());
        System.out.println("Cobertura total: "+mapa.evaluarZ());
        System.out.println();
    }
}

