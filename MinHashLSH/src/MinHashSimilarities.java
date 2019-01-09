
public class MinHashSimilarities {
	private MinHash minHash;
	private int [][]termDocumentMatrix;
	private int [][]minHashMatrix;
	public MinHashSimilarities(String folder, int numPermutations) {
		minHash = new MinHash(folder, numPermutations);
		termDocumentMatrix = minHash.termDocumentMatrix();
		minHashMatrix = minHash.minHashMatrix();
	}
	public double exactJaccard(String file1, String file2) {
		int f1 = minHash.getFileIndex(file1);
		int f2 = minHash.getFileIndex(file2);
		int commen = 0;
		int all = 0;
        for (int i = 0; i < termDocumentMatrix.length; i++) {
	            commen = commen + Math.min(termDocumentMatrix[i][f1], termDocumentMatrix[i][f2]);
	            all = all + Math.max(termDocumentMatrix[i][f1], termDocumentMatrix[i][f2]);
        }
        return (double)commen/all; 
	}
	public double approximateJaccard(String file1, String file2) {
		int fileIndex1 = minHash.getFileIndex(file1);
		int fileIndex2 = minHash.getFileIndex(file2);
		int count = 0;
		for(int i = 0; i < minHashMatrix.length; i++) {
			if(minHashMatrix[i][fileIndex1] == minHashMatrix[i][fileIndex2])
				count++;
		}
		return (double)count/minHashMatrix.length;
	}
	public int[] minHashSig(String fileName) {
		int index = minHash.getFileIndex(fileName);
		return minHash.signature(index);
	}
	public String[] allDocs(){
		return minHash.allDocs();
	}
}
