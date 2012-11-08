package busquedaTabu;

public class Lugar {
	
	private int x;
	private int y;
	private int antena;
	private int cobertura;
	
	public static final int NO_PERMITIDA = 0;
	public static final int PERMITIDA = 2;
	public static final int P_1MW = 1;
	public static final int P_3MW = 3;
	public static final int P_4MW = 4;
	
	public Lugar(int x, int y, int antena, int cobertura) {
		super();
		this.x = x;
		this.y = y;
		this.antena = antena;
		this.cobertura = cobertura;
	}
	
	public Lugar(int x, int y, int antena) {
		super();
		this.x = x;
		this.y = y;
		this.antena = antena;
	}
	
	public Lugar(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Lugar() {
		super();
	}
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public int getAntena() {
		return antena;
	}
	public void setAntena(int antena) {
		this.antena = antena;
	}
	public int getCobertura() {
		return cobertura;
	}
	public void setCobertura(int cobertura) {
		this.cobertura = cobertura;
	}
	
	public boolean isAntena(){
		if(this.getAntena()==Lugar.P_1MW||this.getAntena()==Lugar.P_3MW||this.getAntena()==Lugar.P_4MW)
			return true;
		return false;
	}
	
}
