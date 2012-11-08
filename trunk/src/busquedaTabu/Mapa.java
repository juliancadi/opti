package busquedaTabu;

import java.util.Random;

public class Mapa {
	
	private int m;
	private int n;
	private Lugar puntos[][];
	
	public Mapa(int m, int n, Lugar[][] puntos) {
		super();
		this.m = m;
		this.n = n;
		this.puntos = puntos;
	}

	public Mapa(int m, int n) {
		super();
		this.m = m;
		this.n = n;
	}
	
	public Mapa() {
		super();
	}

	public int getM() {
		return m;
	}

	public void setM(int m) {
		this.m = m;
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public Lugar[][] getPuntos() {
		return puntos;
	}

	public void setPuntos(Lugar[][] puntos) {
		this.puntos = puntos;
	}
	
	public static Mapa generarMapaAleatorio(int m, int n){
		Mapa mapa = new Mapa(m,n);
		Lugar puntos[][] = new Lugar[15][15]; 
		Random r = new Random();
		for(int i=0; i<mapa.getM(); i++){
			for(int j=0; j<mapa.getN(); j++){
				double p = r.nextDouble();
				int antena = Lugar.NO_PERMITIDA;
				if(p<=0.20){
					antena = Lugar.PERMITIDA;
				}
				puntos[i][j] = new Lugar(i, j, antena, 0);
			}
		}
		mapa.setPuntos(puntos);
		return mapa;
	}
	
	public void ponerAntenasAleatorias(){
		Random r = new Random();
		int antenas = 0;
		int potencia = 0;
		for(int i=0; i<this.getM(); i++){
			for(int j=0; j<this.getN(); j++){
				Lugar punto = this.getPuntos()[i][j];
				if(punto.getAntena() == Lugar.PERMITIDA){
					double p = r.nextDouble();
					if(p>=0 && p<0.05){
						if((potencia+1)>20) break;
						this.ubicarAntena(i, j, Lugar.P_1MW);
						antenas++;
						potencia = potencia + Lugar.P_1MW;
					}
					if(p>=0.05 && p<0.10){ 
						if((potencia+3)>20) break;
						this.ubicarAntena(i, j, Lugar.P_3MW);
						antenas++;
						potencia = potencia + Lugar.P_3MW;
					}
					if(p>=0.10 && p<0.15){ 
						if((potencia+4)>20) break;
						this.ubicarAntena(i, j, Lugar.P_4MW);
						antenas++;
						potencia = potencia + Lugar.P_4MW;
					}
					if(antenas>=10) return;
				}
			}
		}
	}
	
	private void ubicarAntena(int x, int y, int antena){
		this.getPuntos()[x][y].setAntena(antena);
		switch(antena){
			case Lugar.P_1MW:
				
				for(int i = -1; i<2; i++){
					for(int j = -1; j<2; j++){
						if((x+i)>=0 && (x+i)<this.getM() && (j+y)>=0 && (j+y)<this.getN())
							this.getPuntos()[x+i][y+j].setCobertura(1);
					}
				}

				break;
				
			case Lugar.P_3MW:
				
				for(int i = -2; i<3; i++){
					for(int j = -2; j<3; j++){
						if((x+i)>=0 && (x+i)<this.getM() && (j+y)>=0 && (j+y)<this.getN())
							this.getPuntos()[x+i][y+j].setCobertura(1);
					}
				}
				
				break;
				
			case Lugar.P_4MW:
				for(int i = -3; i<4; i++){
					for(int j = -3; j<4; j++){
						if((x+i)>=0 && (x+i)<this.getM() && (j+y)>=0 && (j+y)<this.getN())
							this.getPuntos()[x+i][y+j].setCobertura(1);
					}
				}
				break;
		}
	}
	
	public int getCoberturaTotal(){
		int cobertura = 0;
		for(int i = 0; i<this.getM(); i++){
			for(int j = 0; j<this.getM(); j++){
				cobertura = cobertura + this.getPuntos()[i][j].getCobertura();
			}
		}
		return cobertura;
	}
	
	public String toString(){
		String resultado = "Sin puntos";
		Lugar puntos[][] = this.getPuntos();
		if(puntos==null) return resultado;

		resultado = "";
		for(int i=0; i<this.getM(); i++){
			resultado = resultado + "|";
			for(int j=0; j<this.getN(); j++){
				Lugar p = puntos[i][j];
				switch(p.getAntena()){
					case Lugar.NO_PERMITIDA:
						if(p.getCobertura()==0){
							resultado = resultado + "   |";
						}else{
							resultado = resultado + " O |";
						}
						break;
					case Lugar.PERMITIDA:
						if(p.getCobertura()==0){
							resultado = resultado + "###|";
						}else{
							resultado = resultado + "#O#|";
						}
						break;
						
					case Lugar.P_1MW:
						if(p.getCobertura()==0){
							resultado = resultado + " 1 |";
						}else{
							resultado = resultado + "O1O|";
						}
						break;
						
					case Lugar.P_3MW:
						if(p.getCobertura()==0){
							resultado = resultado + " 3 |";
						}else{
							resultado = resultado + "O3O|";
						}
						break;
						
					case Lugar.P_4MW:
						if(p.getCobertura()==0){
							resultado = resultado + " 4 |";
						}else{
							resultado = resultado + "O4O|";
						}
						break;
				}
			}
			resultado = resultado + "\n";
			resultado = resultado + "--------------------------------------------------------------";
			resultado = resultado + "\n";
		}
		
		return resultado;
	}
	
	public String resultados(){
		
		int cobertura = this.getCoberturaTotal();
		int total = this.getM()*this.getN();
		double dCobertura = cobertura;

		double porcentaje = (dCobertura/total)*100;
		String resultado ="Total antenas: "+ this.getTotalAntenas() + "\n";
		resultado = resultado +"Total Potencia: "+ this.getTotalPotencia()+ "\n";
		resultado = resultado +"   Antenas 1Mw: "+ this.getAntenas(Lugar.P_1MW)+ "\n";
		resultado = resultado +"   Antenas 3Mw: "+ this.getAntenas(Lugar.P_3MW)+ "\n";
		resultado = resultado +"   Antenas 4Mw: "+ this.getAntenas(Lugar.P_4MW)+ "\n";
		resultado = resultado +"Porcentaje CON cobertura: "+ cobertura+ "/"+total+" ("+porcentaje+"%)"+"\n";
		resultado = resultado +"Porcentaje SIN cobertura: "+ (total-cobertura)+ "/"+total+" ("+(100-porcentaje)+"%)"+"\n";
		return resultado;
	}
	
	public int getTotalAntenas(){
		int antenas = 0;
		for(int i = 0; i<this.getM(); i++){
			for(int j = 0; j<this.getM(); j++){
				if(this.getPuntos()[i][j].isAntena())
					antenas++;
			}
		}
		return antenas;
	}
	
	public int getTotalPotencia(){
		int potencia = 0;
		for(int i = 0; i<this.getM(); i++){
			for(int j = 0; j<this.getM(); j++){
				if(this.getPuntos()[i][j].isAntena())
					potencia = potencia + this.getPuntos()[i][j].getAntena();
			}
		}
		return potencia;
	}
	
	public int getAntenas(int tipo){
		int antenas = 0;
		for(int i = 0; i<this.getM(); i++){
			for(int j = 0; j<this.getM(); j++){
				if(this.getPuntos()[i][j].getAntena()==tipo)
					antenas++;
			}
		}
		return antenas;
	}
	
	public void setAntenas(int antenas[]){
		
	}
	
	public int[] getAntenas(){
		int antenas[] = new int[100];
		int cant = 0;
		int iAntenas = 0;
		for(int i = 0; i<this.getM(); i++){
			for(int j = 0; j<this.getM(); j++){
				Lugar punto = this.getPuntos()[i][j];
				if(punto.getAntena()==Lugar.P_1MW){
					antenas[iAntenas] = 0;
					iAntenas++;
					antenas[iAntenas] = 1;
					iAntenas++;
				}
				else if(punto.getAntena()==Lugar.P_3MW){
					antenas[iAntenas] = 1;
					iAntenas++;
					antenas[iAntenas] = 0;
					iAntenas++;
				}else if(punto.getAntena()==Lugar.P_4MW){
					antenas[iAntenas] = 1;
					iAntenas++;
					antenas[iAntenas] = 1;
					iAntenas++;
				}
				if(punto.isAntena()){
					int binario[] = getBinario(punto.getX());
					antenas[iAntenas] = binario[3];
					iAntenas++;
					antenas[iAntenas] = binario[2];
					iAntenas++;
					antenas[iAntenas] = binario[1];
					iAntenas++;
					antenas[iAntenas] = binario[0];
					iAntenas++;
					binario = getBinario(punto.getY());
					antenas[iAntenas] = binario[3];
					iAntenas++;
					antenas[iAntenas] = binario[2];
					iAntenas++;
					antenas[iAntenas] = binario[1];
					iAntenas++;
					antenas[iAntenas] = binario[0];
					iAntenas++;
					cant++;
				}
				if(cant>10){
					return antenas;
				}
			}
		}
		return antenas;
	}
	
	private int[] getBinario(int x) {
		int i[] = new int[4];
		i[3] = 0;
		i[2] = 0;
		i[1] = 0;
		i[0] = 0;
		if(x>7){ 
			i[3] = Integer.parseInt(Integer.toBinaryString(x).substring(0, 1));
			i[2] = Integer.parseInt(Integer.toBinaryString(x).substring(1, 2));
			i[1] = Integer.parseInt(Integer.toBinaryString(x).substring(2, 3));
			i[0] = Integer.parseInt(Integer.toBinaryString(x).substring(3, 4));
		}
		else if(x>3){ 
			i[2] = Integer.parseInt(Integer.toBinaryString(x).substring(0, 1));
			i[1] = Integer.parseInt(Integer.toBinaryString(x).substring(1, 2));
			i[0] = Integer.parseInt(Integer.toBinaryString(x).substring(2, 3));
		}
		else if(x>1){ 
			i[1] = Integer.parseInt(Integer.toBinaryString(x).substring(0, 1));
			i[0] = Integer.parseInt(Integer.toBinaryString(x).substring(1, 2));
		}
		else{ 
			i[0] = Integer.parseInt(Integer.toBinaryString(x).substring(0, 1));
		}
		
		return i;
	}

	public void optimizar(){
		
	}

}
