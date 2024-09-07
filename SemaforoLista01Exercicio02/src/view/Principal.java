package view;

import java.util.concurrent.Semaphore;

import controller.ThreadCozinha;

public class Principal {
	public static void main(String[] args) {
		Semaphore semaforo = new Semaphore(1);
		for(int i = 0; i < 5; i++) {
			ThreadCozinha t = new ThreadCozinha(semaforo);
			t.start();
		}
	}
}
