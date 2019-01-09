import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class LSH {
	private final long PRIME = 0x7C24A70E837B9C07L;
	private int R;
	private int B;
	private int[][] minHashMatrix;
	private String[] docNames;
	private int numDocs;
	
	List<HashMap<Long, ArrayList<Integer>>> hashtables;
	
	public LSH(int[][] minHashMatrix, String[] docNames, int bands) {
		B = bands;
//		R = (int) Math.ceil((double)minHashMatrix.length / B);
		R = minHashMatrix.length / B;
		
		this.minHashMatrix = minHashMatrix;
		this.docNames = docNames;
		numDocs = docNames.length;
		
		initHashTable();
		fillHasTable();
		
//		System.out.println(B + ", " + R + ", " + numDocs);
	}
	private void initHashTable() {
		hashtables = new LinkedList<>();
		for(int l = 0; l < B; l++) {
	        hashtables.add(new HashMap<>());
		}
	}
	private void fillHasTable() {
		for(int docIndex = 0; docIndex < numDocs; docIndex++) {
			int[] sig = signature(docIndex);
			placeInTable(sig, docIndex);
		}
	}
	private int[] signature(int docIndex) {
		int[] sig = new int[minHashMatrix.length];
		for(int i = 0; i < minHashMatrix.length; i++) {
			sig[i] = minHashMatrix[i][docIndex];
		}
		return sig;
	}
	private void placeInTable(int[] signature, int docIndex) {
		for(int l = 0; l < B; l++) {
			long compressed = compressBand(signature, l);
			HashMap<Long, ArrayList<Integer>> row = hashtables.get(l);
			placeAtTable(docIndex, row, compressed);
		}
	}
	private long compressBand(int[] signature, int band) {
		long compress = 0;
		int[] tmp = new int[R];
		for(int i = 0; i < R; i++) {
			tmp[i] = signature[band * R + i];
			compress = ((long) signature[band * R + i] + compress) * PRIME;
		}
//		System.out.println("band :" + (band+1) + Arrays.toString(tmp) + "->" + ((int)compress & 0x7fffffff));
		return compress & 0x7fffffffffffffffL;
	}
	private void placeAtTable(int docIndex, HashMap<Long, ArrayList<Integer>> row, long col) {
		if(row.get(col) == null)
			row.put(col, new ArrayList<Integer>());
		row.get(col).add(docIndex);
//		System.out.println("Add: " + docIndex + " to " + row);
	}
  	
  	public ArrayList<String> nearDuplicatesOf(String docName) {
  		int docIndex = Arrays.asList(docNames).indexOf(docName);
  		ArrayList<Integer> nodeList = new ArrayList<Integer>();
  		int[] signature = signature(docIndex);
  		
		for(int l = 0; l < B; l++) {
			long compressed = compressBand(signature, l);
			HashMap<Long, ArrayList<Integer>> row = hashtables.get(l);
			if(row.get(compressed) != null) {
//				System.out.println("band :" + (l+1) + hashTable[l][randomHash(compress)].toString());
				nodeList.addAll(row.get(compressed));
			}
		}
		
		ArrayList<String> arr = new ArrayList<String>();
		for(int index : nodeList) {
			String str = docNames[index];
			if(!arr.contains(str))
				arr.add(str);
		}
		
  		return arr;
  	}
  	


  	
}
