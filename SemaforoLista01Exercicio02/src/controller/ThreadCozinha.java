package controller;

import java.util.Random;
import java.util.concurrent.Semaphore;

public class ThreadCozinha extends Thread{
	private final int TID = (int) getId();
	private Semaphore semaforo;
	
	public ThreadCozinha(Semaphore semaforo) {
		this.semaforo = semaforo;
	}
	
	public void run() {
		cozinhar();
	}
	
	private void cozinhar() {
		Random aleat = new Random();
		String prato;
		int tempo = 0;
		if(TID % 2 != 0) {
			prato = "Sopa de Cebola";
			tempo = aleat.nextInt(500, 800);
		}else {
			prato = "Lasanha a Bolonhesa";
			tempo = aleat.nextInt(600, 1200);
		}
		
		double percentual = tempo / 100, porcentagem = 0.0;
		
		System.out.println(TID+ "# " + prato + " iníciou o cozimento.");
		
		while(porcentagem <= 100) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				System.err.println(e.getMessage());
			}
			tempo = tempo - 100;
			porcentagem = 100 - (tempo / percentual);
			if(porcentagem <= 100) {
				System.out.println(TID + "# " + prato + " está " + porcentagem + "% cozido.");
			}
		}
		System.out.println(TID + "# " + prato + " está pronto para ser entregue.");
		try {
			semaforo.acquire();
			entregar(prato);
		}catch (InterruptedException e) {
			System.err.println(e.getMessage());
		}finally {
			semaforo.release();
		}
	}
	
	private void entregar(String prato) {
		try {
			Thread.sleep(500);
			System.out.println(TID + "# " + prato + " foi entregue.");
		} catch (InterruptedException e){
			System.err.println(e.getMessage());
		}
	}
}
