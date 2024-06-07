package testAlgoritmos;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ThreadLocalRandom;

// 												--- QuickSort CONCURRENTE ---

public class QuickSortConcurrente extends RecursiveTask<Integer> {

	private static final long serialVersionUID = 1L;

	// Declaración del inicio, fin y array
	int start, end;
	int[] arr;

	// Ajuste del umbral de recursión
	static final int UMBRAL = 1000;

	// Función de partición
	private int partition(int start, int end, int[] arr) {

		int i = start, j = end;

		// Elige un pivot aleatorio
		int pivoted = ThreadLocalRandom.current().nextInt(j - i) + i;

		// Intercambia el pivot con el elemento final del array
		int temp = arr[j];
		arr[j] = arr[pivoted];
		arr[pivoted] = temp;
		j--;

		// Inicia la partición del array
		while (i <= j) {

			// Si el elemento es menor o igual al pivot, incrementa i
			if (arr[i] <= arr[end]) {
				i++;
				continue;
			}

			// Si el elemento es mayor o igual al pivot, decrementa j
			if (arr[j] >= arr[end]) {
				j--;
				continue;
			}

			// Intercambia los elementos en i y j
			temp = arr[j];
			arr[j] = arr[i];
			arr[i] = temp;
			j--;
			i++;
		}

		// Intercambia el pivot a su posición correcta
		temp = arr[j + 1];
		arr[j + 1] = arr[end];
		arr[end] = temp;
		return j + 1;
	}

	// Función para implementar el método QuickSort
	public QuickSortConcurrente(int start, int end, int[] arr) {
		this.arr = arr;
		this.start = start;
		this.end = end;
	}

	// Función para computar
	@Override
	protected Integer compute() {

		// Verifica el caso base: si el segmento de array es válido
		if (start < end) {

			// Si el tamaño del segmento es menor que el umbral, usa QuickSort secuencial
			if (end - start < UMBRAL) {
				quickSortSecuencial(arr, start, end);
				
			} else {

				// Y si no, realiza la partición y obtiene el índice del pivot
				int p = partition(start, end, arr);

				// Se divide el array creando tareas para ordenar las dos subparticiones (la de la izquierda y la de la derecha)
				QuickSortConcurrente left = new QuickSortConcurrente(start, p - 1, arr);
				QuickSortConcurrente right = new QuickSortConcurrente(p + 1, end, arr);

				// Se ejecutan las dos tareas en paralelo
				invokeAll(left, right);
			}
		}

		// No queremos nada como retorno
		return null;
	}

	// Función para implementar el método QuickSort Secuencial
	private void quickSortSecuencial(int[] arr, int low, int high) {
		if (low < high) {
			int pi = partition(low, high, arr);
			quickSortSecuencial(arr, low, pi - 1);
			quickSortSecuencial(arr, pi + 1, high);
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Random random = new Random();

		// Diferentes tamaños del array para diferentes pruebas
		// int n = 10;
		// int n = 1000;
		// int n = 100000;
		// int n = 500000;
		 int n = 1000000;

		// Declaración del array
		int[] arr = new int[n];

		// Se carga al array con números randoms de 0 a 100.000
		for (int i = 0; i < n; i++) {
			arr[i] = random.nextInt(100000);
		}

		// ForkJoinPool para mantener la creación de hilos según los recursos
		ForkJoinPool pool = ForkJoinPool.commonPool();

		// Definición del tiempo inicial y tiempo final para calcular la duración de
		// ejecución
		// Inicia el primer hilo en el pool fork/join para el rango 0, n-1
		long tiempoInicial = System.nanoTime();
		pool.invoke(new QuickSortConcurrente(0, n - 1, arr));
		long tiempoFinal = System.nanoTime();

		// Duración en milisegundos
		long duracion = (tiempoFinal - tiempoInicial) / 1000000;

		// Muestra del tiempo de ejecución
		System.out.println("Tiempo de ejecucion (concurrente): " + duracion + " milisegundos.");
	}

}
