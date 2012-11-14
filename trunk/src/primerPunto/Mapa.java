package primerPunto;

import java.util.Random;
import java.util.Vector;

public class Mapa {
	
	// Campos de la estructura Mapa
	private int cobertura[][];
	private int nAntenas;
	private Vector<Integer> tipoAntena;
	private Vector<Integer> xAntena;
	private Vector<Integer> yAntena;
	
	// Posibles valores en la matriz de cobertura
	public static final int SIN_COBERTURA = -1;
	public static final int SIN_ANTENA = 0;
	public static final int A_1MW = 1;
	public static final int A_3MW = 2;
	public static final int A_4MW = 3;
	public static final double PROBABILIDAD_ANTENA = 0.50;
	
	//Constructores, setters y getters
	
	public Mapa() {
		super();
	}

	public int[][] getCobertura() {
		return cobertura;
	}

	public void setCobertura(int[][] cobertura) {
		this.cobertura = cobertura;
	}

	public int getnAntenas() {
		return nAntenas;
	}

	public void setnAntenas(int nAntenas) {
		this.nAntenas = nAntenas;
	}

	public Vector<Integer> getTipoAntena() {
		return tipoAntena;
	}

	public void setTipoAntena(Vector<Integer> tipoAntena) {
		this.tipoAntena = tipoAntena;
	}

	public Vector<Integer> getxAntena() {
		return xAntena;
	}

	public void setxAntena(Vector<Integer> xAntena) {
		this.xAntena = xAntena;
	}

	public Vector<Integer> getyAntena() {
		return yAntena;
	}

	public void setyAntena(Vector<Integer> yAntena) {
		this.yAntena = yAntena;
	}
	
	// Generacion de mapas de distribución aleatorios y soluciones aleatorias

	public static Mapa generarMapaAleatorio(){
		Mapa mapa = new Mapa();
		// Matriz de cobertura
		int puntos[][] = new int[15][15];
		// Vectores para almacenar la ubicación de los puntos donde es posible
		// ubicar una antena
		Vector<Integer> tiposA = new Vector<Integer>();
		Vector<Integer> xA = new Vector<Integer>();
		Vector<Integer> yA = new Vector<Integer>();
		
		Random r = new Random();
		// Se recorre toda la matriz, creando un mapa de distribución aleatorio
		for(int i=0; i<15; i++){
			for(int j=0; j<15; j++){
				// Se genera un número aleatorio entre 0 y 1, y se evalua la probabilidad
				// de que haya una posible antena o no en dicho punto
				double p = r.nextDouble();
				if(p<=PROBABILIDAD_ANTENA){
					// Si cumple la probabilidad se establece un punto de posible antena
					// sin embargo no se ubica ninguna antena
					tiposA.add(SIN_ANTENA);
					xA.add(i);
					yA.add(j);
					puntos[i][j] = SIN_ANTENA;
				}
				else{
					puntos[i][j] = SIN_COBERTURA;
				}
			}
		}
		// Se establece la cobertura, la cantidad de puntos de posibles antenas,
		// los tipos de antena y las coordenadas. A este punto la cobertura es 0
		// y no hay ninguna antena instalada
		mapa.setCobertura(puntos);
		mapa.setnAntenas(tiposA.size());
		mapa.setTipoAntena(tiposA);
		mapa.setxAntena(xA);
		mapa.setyAntena(yA);
		return mapa;
	}
	
	public static Vector<Integer> generarBinariosAntenas(Mapa m){
		Vector<Integer> binarios = new Vector<Integer>();
		Random r = new Random();
		int totalPotencia = 0;
		int totalAntenas = 0;
		boolean limite = false;
		// Se generan dos bits aleatorios por cada punto donde es posible
		// ubicar una antena. Esto se realiza hasta que se haya sobrepasado
		// la cantidad limite de antenas y de potencia total.
		for(int i=0; i<m.getnAntenas(); i++){
			int b1 = 0;
			int b0 = 0;
			if(!limite){
				b1 = r.nextInt(2);
				b0 = r.nextInt(2);
				if(b1!=0 || b0 != 0) // Si el par binario NO es 00, significa que es una nueva antena
					totalAntenas++;
				// Según la antena que represente este par binario, se suma la potencia total acumulada
				switch(b1*2+b0){
					case A_1MW:
						totalPotencia = totalPotencia + 1;
						break;
					case A_3MW:
						totalPotencia = totalPotencia + 3;
						break;
					case A_4MW:
						totalPotencia = totalPotencia + 4;
						break;
				}
				// Se evaluan las restricciones
				if(totalAntenas > 10 || totalPotencia > 20) {
					limite = true;
					b1 = 0;
					b0 = 0;
				}
			}
			binarios.add(b1);
			binarios.add(b0);
		}
		// Por último se les da un nuevo orden de manera aleatoria a los pares obtenidos
		// con el fin de que las antenas generadas aleatoriamente no se acumulen hacia una parte del mapa.
		// Como cada par tiene un posicion en x y y almacenadas en otros vectores, basta con intercambiar
		// su orden en el vector de binarios para que su respectiva antena cambie de posición en el mapa.
		int mix1,mix2,aux1,aux2;
		for(int i=0;i<20;i++){
			// Se eligen 2 pares aleatoriamente y se intercambian sus posiciones entre si
			mix1 = r.nextInt(binarios.size());
			if((mix1%2)!=0) mix1--;
			mix2 = r.nextInt(binarios.size());
			if((mix2%2)!=0) mix2--;
			aux1 = binarios.elementAt(mix2);
			aux2 = binarios.elementAt(mix2+1);
			binarios.setElementAt(binarios.elementAt(mix1), mix2);
			binarios.setElementAt(binarios.elementAt(mix1+1), mix2+1);
			binarios.setElementAt(aux1, mix1);
			binarios.setElementAt(aux2, mix1+1);
		}
		return binarios;
	}
	
	public void setAntenas(Vector<Integer> binariosAntenas){
		// Gracias a esta función es posible evaluar diferentes soluciones posibles
		// en un mismo mapa
		Vector<Integer> enterosAntenas = new Vector<Integer>();
		for(int i = 0; i<binariosAntenas.size(); i=i+2){
			// b1 b0
			int b1 = binariosAntenas.elementAt(i);
			int b0 = binariosAntenas.elementAt(i+1);
			// Se pasa del sistema binario al sistema decimal
			enterosAntenas.add(b1*2+b0*1);
		}
		// Se desactiva la cobertura antigua
		this.quitarCobertura();
		// Se ingresan los nuevos valores para los tipos de antena, ahora en base decimal
		this.setTipoAntena(enterosAntenas);
		// Por ultimo se activan las antenas recién ingresadas
		this.activarAntenas();
	}
	
	
	private void quitarCobertura() {
		// Se quita la cobertura tanto en los puntos de posible antena como en el resto de los puntos
		for(int i = 0; i<15; i++){
			for(int j = 0; j<15; j++){
				if(this.esPuntoAntena(i, j)){
					this.getCobertura()[i][j] = SIN_ANTENA;
				}
				else{
					this.getCobertura()[i][j] = SIN_COBERTURA;
				}
			}
		}
	}

	private void activarAntenas() {
		// Este método se encarga de fijar la cobertura de cada antena
		// estableciendo la señal en cada uno de los puntos que la rodea
		// según su potencia
		for(int k = 0; k<this.getnAntenas(); k++){
			
			int tipo = this.getTipoAntena().elementAt(k);
			int x = this.getxAntena().elementAt(k);
			int y = this.getyAntena().elementAt(k);
			
			switch(tipo){
			
				// Si la antena es de 1Mw de potencia, se cubre un area de 3x3
				case A_1MW:
					for(int i = -1; i<2; i++){
						for(int j = -1; j<2; j++){
							if((x+i)>=0 && (x+i)<15 && (j+y)>=0 && (j+y)<15)
								this.getCobertura()[x+i][y+j] = A_1MW;
						}
					}
	
					break;
				
				// Si la antena es de 3Mw de potencia, se cubre un area de 5x5
				case A_3MW:
					
					for(int i = -2; i<3; i++){
						for(int j = -2; j<3; j++){
							if((x+i)>=0 && (x+i)<15 && (j+y)>=0 && (j+y)<15)
								this.getCobertura()[x+i][y+j] = A_3MW;
						}
					}
					
					break;
					
				// Si la antena es de 4Mw de potencia, se cubre un area de 7x7
				case A_4MW:
					for(int i = -3; i<4; i++){
						for(int j = -3; j<4; j++){
							if((x+i)>=0 && (x+i)<15 && (j+y)>=0 && (j+y)<15)
								this.getCobertura()[x+i][y+j] = A_4MW;
						}
					}
					break;
			}
		}
	}
	
	// Funciones de evaluación de funciones, actualización de valores y utileria
	
	// Evalua si un punto determinado tiene instalada una antena o no
	private boolean esAntena(int i, int j) {
		for(int k=0; k<this.getnAntenas(); k++){
			if(this.getxAntena().elementAt(k)==i && this.getyAntena().elementAt(k)==j && this.getTipoAntena().elementAt(k)!=SIN_ANTENA)
				return true;
		}
		return false;
	}
	
	// Evalua si un punto es un punto donde es posible instalar una antena
	private boolean esPuntoAntena(int i, int j) {
		for(int k=0; k<this.getnAntenas(); k++){
			if(this.getxAntena().elementAt(k)==i && this.getyAntena().elementAt(k)==j)
				return true;
		}
		return false;
	}
	
	// Cuenta el número de antenas que hay instaladas
	public int getAntenasInstaladas(){
		int resultado = 0;
		for(int i=0; i< this.getTipoAntena().size(); i++){
			if(this.getTipoAntena().elementAt(i)!=SIN_ANTENA)
				resultado++;
		}
		return resultado;
	}
	
	// Suma la potencia total en Mw de las antenas que hay instaladas
	public int getPotenciaTotal(){
		int resultado = 0;
		for(int i=0; i< this.getTipoAntena().size(); i++){
				resultado = resultado + this.getTipoAntena().elementAt(i);
		}
		return resultado;
	}
	
	// Evalua la función objetivo, es decir, suma las coberturas de cada punto, recorriendo toda la matriz de cobertura
	public int evaluarZ(){
		int resultado = 0;
		for(int i=0; i< 15; i++){
			for(int j=0; j< 15; j++){
				if(this.getCobertura()[i][j]!= SIN_COBERTURA && this.getCobertura()[i][j]!= SIN_ANTENA){
					resultado++;
				}
			}
		}
		// Penalizacion en restricciones
		if(this.getPotenciaTotal()>20 || this.getAntenasInstaladas()>10){
			resultado = 0;
		}
		return resultado;
	}
	
	// Según lo que se encuentre en la matriz de cobertura, representa este valor
	// graficamente en la consola
	public String imprimirMapa(){

		String resultado = "";
		for(int i=0; i< 15; i++){
			resultado = resultado + "|";
			for(int j=0; j< 15; j++){
				switch(this.getCobertura()[i][j]){
					case A_1MW:
						if(esAntena(i,j)){
							resultado = resultado + ">1<|";
						}
						else{
							resultado = resultado + " O |";
						}
						break;
					case A_3MW:
						if(esAntena(i,j)){
							resultado = resultado + ">3<|";
						}
						else{
							resultado = resultado + " O |";
						}
						break;
					case A_4MW:
						if(esAntena(i,j)){
							resultado = resultado + ">4<|";
						}
						else{
							resultado = resultado + " O |";
						}
						break;
					case SIN_ANTENA:
						resultado = resultado + "###|";
						break;
					case SIN_COBERTURA:
						resultado = resultado + "   |";
						break;
				}
			}
			resultado = resultado + "\n";
			resultado = resultado + "--------------------------------------------------------------";
			resultado = resultado + "\n";
		}
		resultado = resultado + "Antenas instaladas: "+this.getAntenasInstaladas()+"/"+this.getnAntenas()+"\n";
		resultado = resultado + "Potencia total: "+this.getPotenciaTotal()+" Mw"+"\n";
		resultado = resultado + "Cobertura total: "+this.evaluarZ()+"/255\n";
		
		return resultado;
	}

	// Imprime un conjunto entero de mapas con diferentes soluciones
	public static String imprimirPoblacion(Mapa m,Vector<Integer>[] poblacion) {
		String resultado = "";
		for(int i = 0; i<poblacion.length;i++){
			m.setAntenas(poblacion[i]);
			resultado = resultado + m.imprimirMapa();
		}
		return resultado;
	}

}
