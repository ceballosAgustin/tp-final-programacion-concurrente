package testAlgoritmos;

import java.util.Random;

// 												--- QuickSort SECUENCIAL ---

public class QuickSortSecuencial {

	// Función para intercambiar dos elementos
	static void swap(int[] arr, int i, int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}

	// Esta función agarra el último elemento como pivot,
	// coloca el elemento pivot en su posición correcta en el array ordenado,
	// y coloca todos los elementos menores a la izquierda del pivot y los elementos
	// mayores a la derecha.
	static int partition(int[] arr, int low, int high) {

		// Elección del pivot
		int pivot = arr[high];

		// 'i' sería el elemento más pequeño e indica
		// la posición correcta del pivot encontrada hasta ahora
		int i = (low - 1);

		for (int j = low; j <= high - 1; j++) {

			// Si el elemento actual de 'j' es menor que el pivot
			if (arr[j] < pivot) {

				// Entonces que aumente el índice del elemento más pequeño
				i++;
				swap(arr, i, j);
			}
		}

		swap(arr, i + 1, high);
		return (i + 1);
	}

	// --- FUNCIÓN PRINCIPAL QUE IMPLEMENTA QUICKSORT ---
	// arr[] = array a ordenar,
	// low = indice inicial,
	// high = indice final
	static void quickSort(int[] arr, int low, int high) {
		if (low < high) {

			// pi es el índice de partición, arr[pi]
			// está ahora en el lugar correcto
			int pi = partition(arr, low, high);

			// Ordenar por separado los elementos antes
			// de la partición y después de la partición
			quickSort(arr, low, pi - 1);
			quickSort(arr, pi + 1, high);
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

		// Definición del tiempo inicial y tiempo final para calcular la duración de ejecución
		long tiempoInicial = System.nanoTime();
		quickSort(arr, 0, n - 1);
		long tiempoFinal = System.nanoTime();

		// Duración en milisegundos
		long duracion = (tiempoFinal - tiempoInicial) / 1000000;

		// Muestra del tiempo de ejecución
		System.out.println("Tiempo de ejecucion (secuencial): " + duracion + " milisegundos.");
	}

}
