package modelo;

import java.util.Random;

public class FuenteAleatorioRandom implements FuenteAleatorio {

	private Random rnd;
	
	public FuenteAleatorioRandom() {
		rnd = new Random ();
	}
	
	public FuenteAleatorioRandom(long semilla) {
		rnd = new Random (semilla);
	}
	
	@Override
	public int nextInt() {
		return rnd.nextInt();
	}
	
	@Override
	public int nextInt(int limite) {
		return rnd.nextInt(limite);
	}

}
