import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

public class MinHash {
	private String folder;
	private int numPermutations;

	private HashMap<String, Integer> Terms;//String word -> number term
	private List<String> Docs;
	
	private List<HashMap<Integer, Integer>> termMatrix;
	private int maxFreq;
	
	private int[][] valueAB;
	private int P;
	private int[][] minHash;

	public MinHash(String folder, int numPermutations) {
		this.folder = folder;
		this.numPermutations = numPermutations;
		Terms = new HashMap<>();
		Docs = new LinkedList<String>();
		termMatrix = new LinkedList<>();

		maxFreq = Integer.MIN_VALUE;
		
		preprocess();
		generateAB();
		generateMinHashMatrix();
    }

	private void preprocess() {
        File file = null;
        file = new File(folder);
        File[] files = file.listFiles();
        
        int index = 0;
        for (File f : files) {
            if(!f.getName().contains("txt"))
            	continue;
            Docs.add(f.getName());
            termMatrix.add(new HashMap<>());
        	HashMap<Integer, Integer> doc = termMatrix.get(index);
            try {
                Scanner wordPerFile = new Scanner(f);

                while (wordPerFile.hasNextLine()) {

                    String eachLine = wordPerFile.nextLine().toLowerCase();

                    eachLine = eachLine.replaceAll(","," ");
                    eachLine = eachLine.replaceAll("\\."," ");
                    eachLine = eachLine.replaceAll("'"," ");
                    eachLine = eachLine.replaceAll(";"," ");
                    eachLine = eachLine.replaceAll(":"," ");


                    StringTokenizer st = new StringTokenizer(eachLine);
                    while (st.hasMoreTokens()) {
                        String word = st.nextToken();
                        if (!(word.isEmpty() || word.equals("the") || word.length() < 3)) {
                        	putKey(Terms, word);
                        	int term = Terms.get(word);
                        	putFreq(doc, term);
                        	maxFreq = maxFreq > doc.get(term)? maxFreq : doc.get(term);
                        }
                    }
                }
                wordPerFile.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            index++;
        }
    }
	private void putKey(HashMap<String, Integer> map, String termName) {
		if(!map.containsKey(termName))
			map.put(termName, map.size());
	}
	private void putFreq(HashMap<Integer, Integer> map, int term) {
		if(!map.containsKey(term))
			map.put(term, 1);
		else
			map.put(term, map.get(term)+1);
	}

	private void generateMinHashMatrix() {
		int numRow = numPermutations;
		int numCol = Docs.size();
        minHash = new int[numRow][numCol];
        int[][] temp = new int[numCol][numRow];

        for (int i = 0; i < numCol; i++) {
            temp[i] = signature(i);
        }
        for (int i = 0; i < numRow; i++) {
            for (int j = 0; j < numCol; j++) {
                minHash[i][j] = temp[j][i];
            }
        }
    }
	public int[] signature (int docIndex) {
        int[] signature = new int[numPermutations];
        HashMap<Integer, Integer> doc = termMatrix.get(docIndex);

        for (int k = 0; k < numPermutations; k++) {
            int minHashNum = Integer.MAX_VALUE;
            for (int term : doc.keySet()) {
            	int freq = doc.get(term);
            	for(int t = 0; t < freq; t++) {
            		int virtualTerm = virtualTerm(term, t);
            		int value = permutation(k, virtualTerm);
                    minHashNum = value < minHashNum? value : minHashNum;
            	}
            }
            signature[k] = minHashNum;
        }
        return signature;
    }
	private int virtualTerm(int term, int t) {		//intput a number output its virtualTerm
		return maxFreq * term + t;			
	}
	private int permutation(int k, int number) {//intput a number output its permutation
		int key = ((number * valueAB[k][0] + valueAB[k][1]) & 0x7fffffff) % P;
		return key;
	}
    private void generateAB(){
		P = nextPrime(Terms.size() * (maxFreq));
        valueAB = new int[numPermutations][2];
        for(int i=0; i<numPermutations; i++){
        	valueAB[i][0] = (int)(Math.random() * (double)(P-1));
        	valueAB[i][1] = (int)(Math.random() * (double)(P-1));
        }
    }

  	private int nextPrime(int n) { 
	    BigInteger b = new BigInteger(Integer.toUnsignedString(n)); 
	    return Integer.parseInt(b.nextProbablePrime().toString()); 
	} 
  	
	
	private Boolean DocHasTerm(int Doc, int term) {
		return termMatrix.get(Doc).containsKey(term);
	}
	private int termAt(int row, int col) {
		return termMatrix.get(row).get(col);
	}
	 	
  	
  	
  	
	public String[] allDocs(){
		String[] arr = new String[Docs.size()];
		int i = 0;
		for(Iterator<String> iter = Docs.iterator(); iter.hasNext();) {
			arr[i] = iter.next();;
			i++;
		}
		return arr;
    }
	
	public int[][] termDocumentMatrix() {
		int numRows = Terms.size();
		int numCols = Docs.size();
		System.out.println("Rows: " + numRows + " Cols: " + numCols);
		int[][] ret = new int[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				if(DocHasTerm(j,i))
					ret[i][j] = termAt(j,i);
			}
		}
		return ret;
    }
	
	public int numTerms(){
        return Terms.size();
    }
	public int[][] minHashMatrix() {
        return minHash;
    }
	
	public int getFileIndex(String file) {
		return Docs.indexOf(file);
	}
	
	public int numPermutations() {
		return numPermutations;
	}
	

}
