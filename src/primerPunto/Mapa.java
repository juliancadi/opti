package primerPunto;

import java.util.Random;
import java.util.Vector;

public class Mapa {
	
	private int cobertura[][];
	private int nAntenas;
	private Vector<Integer> tipoAntena;
	private Vector<Integer> xAntena;
	private Vector<Integer> yAntena;
	
	public static final int SIN_COBERTURA = -1;
	public static final int SIN_ANTENA = 0;
	public static final int A_1MW = 1;
	public static final int A_3MW = 2;
	public static final int A_4MW = 3;
	public static final double PROBABILIDAD_ANTENA = 0.20;
	
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

	public static Mapa generarMapaAleatorio(){
		Mapa mapa = new Mapa();
		int puntos[][] = new int[15][15];
		Vector<Integer> tiposA = new Vector<Integer>();
		Vector<Integer> xA = new Vector<Integer>();
		Vector<Integer> yA = new Vector<Integer>();
		Random r = new Random();
		for(int i=0; i<15; i++){
			for(int j=0; j<15; j++){
				double p = r.nextDouble();
				if(p<=PROBABILIDAD_ANTENA){
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
		//do{
			for(int i=0; i<m.getnAntenas(); i++){
				int b1 = 0;
				int b0 = 0;
				if(!limite){
					b1 = r.nextInt(2);
					b0 = r.nextInt(2);
					if(b1!=0 || b0 != 0)
						totalAntenas++;
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
					if(totalAntenas > 10 || totalPotencia > 20) {
						limite = true;
						b1 = 0;
						b0 = 0;
					}
				}
				binarios.add(b1);
				binarios.add(b0);
			}
			int mix1,mix2,aux1,aux2;
			for(int i=0;i<20;i++){
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
		Vector<Integer> enterosAntenas = new Vector<Integer>();
		for(int i = 0; i<binariosAntenas.size(); i=i+2){
			// b1 b0
			int b1 = binariosAntenas.elementAt(i);
			int b0 = binariosAntenas.elementAt(i+1);
			enterosAntenas.add(b1*2+b0*1);
		}
		this.setTipoAntena(enterosAntenas);
		this.activarAntenas();
	}

	private void activarAntenas() {
		for(int k = 0; k<this.getnAntenas(); k++){
			
			int tipo = this.getTipoAntena().elementAt(k);
			int x = this.getxAntena().elementAt(k);
			int y = this.getyAntena().elementAt(k);
			switch(tipo){
			
				case A_1MW:
					for(int i = -1; i<2; i++){
						for(int j = -1; j<2; j++){
							if((x+i)>=0 && (x+i)<15 && (j+y)>=0 && (j+y)<15)
								this.getCobertura()[x+i][y+j] = A_1MW;
						}
					}
	
					break;
					
				case A_3MW:
					
					for(int i = -2; i<3; i++){
						for(int j = -2; j<3; j++){
							if((x+i)>=0 && (x+i)<15 && (j+y)>=0 && (j+y)<15)
								this.getCobertura()[x+i][y+j] = A_3MW;
						}
					}
					
					break;
					
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
							//resultado = resultado + "O1O|";
							resultado = resultado + " O |";
						}
						break;
					case A_3MW:
						if(esAntena(i,j)){
							resultado = resultado + ">3<|";
						}
						else{
							//resultado = resultado + "O3O|";
							resultado = resultado + " O |";
						}
						break;
					case A_4MW:
						if(esAntena(i,j)){
							resultado = resultado + ">4<|";
						}
						else{
							//resultado = resultado + "O4O|";
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
		resultado = resultado + "Cobertura total: "+this.evaluarZ()+"\n";
		
		return resultado;
	}

	private boolean esAntena(int i, int j) {
		for(int k=0; k<this.getnAntenas(); k++){
			if(this.getxAntena().elementAt(k)==i && this.getyAntena().elementAt(k)==j && this.getTipoAntena().elementAt(k)!=SIN_ANTENA)
				return true;
		}
		return false;
	}
	
	public int getAntenasInstaladas(){
		int resultado = 0;
		for(int i=0; i< this.getTipoAntena().size(); i++){
			if(this.getTipoAntena().elementAt(i)!=SIN_ANTENA)
				resultado++;
		}
		return resultado;
	}
	
	public int getPotenciaTotal(){
		int resultado = 0;
		for(int i=0; i< this.getTipoAntena().size(); i++){
				resultado = resultado + this.getTipoAntena().elementAt(i);
		}
		return resultado;
	}
	
	public int evaluarZ(){
		int resultado = 0;
		for(int i=0; i< 15; i++){
			for(int j=0; j< 15; j++){
				if(this.getCobertura()[i][j]!= SIN_COBERTURA && this.getCobertura()[i][j]!= SIN_ANTENA){
					resultado++;
				}
			}
		}
		// penalizacion
		if(this.getPotenciaTotal()>20){
			resultado = resultado - (this.getPotenciaTotal()-20);
		}
		if(this.getAntenasInstaladas()>10){
			resultado = resultado - (this.getAntenasInstaladas()-10);
		}
		return resultado;
	}

	public static String imprimirPoblacion(Mapa m,Vector<Integer>[] poblacion) {
		String resultado = "";
		for(int i = 0; i<poblacion.length;i++){
			m.setAntenas(poblacion[i]);
			resultado = resultado + m.imprimirMapa();
		}
		return resultado;
	}

}
