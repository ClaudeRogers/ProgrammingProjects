package utilities;

public class BinarySearch {
	
	//Return the index of the key in the sorted array a[]; -1 if not found
	public static int search(String key, String[] a) {
		return search(key, a, 0, a.length);
	}
	
	public static int search(String key, String[] a, int low, int high) {
		if (high <= low) return -1;
		int mid = low + (high - low) / 2;
		int cmp = a[mid].compareTo(key);
		
		if (cmp > 0) return search(key, a, low, mid);
		else if (cmp < 0) return search(key, a, mid+1, high);
		else	return mid;
	}
}
