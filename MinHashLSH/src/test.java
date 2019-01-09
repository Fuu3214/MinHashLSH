import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class test {
	private static final String folder = "data\\space";
	
	public static void printMatrix(int[][] matrix) {
		for(int i =0; i < matrix.length; i++) {
			for(int j =0; j < matrix[0].length; j++) {
				System.out.print(matrix[i][j] + "\t"); 
			}
			System.out.print("\n"); 
		}
	}
    private static int[][] tranpose(int[][] arrs) {
        for (int i = 0; i < arrs.length; i++) {
            for (int j = 0; j < arrs[0].length; j++) {
            	int tmp = arrs[i][j];
                arrs[i][j] = arrs[j][i];
                arrs[j][i] = tmp;
            }
        }
        return arrs;
    }
	public static void main(String[] argc) {
		File file = null;
        file = new File(folder);
        File[] files = file.listFiles();
        
        String[] fName = new String[10];
		for(int i = 0; i < 10; i++) {
			int rand = (int) (Math.random() * files.length);
			fName[i] = files[rand].getName();
		}
		double[] s = new double[]{0.3, 0.5, 0.9};
		for(int i = 0; i < s.length; i++) {
			NearDuplicates ndp = new NearDuplicates(folder, 400, s[i]);
			for(int j = 0; j < fName.length; j++) {
				System.out.println(s[i] + "-similiar to file " + fName[j] + ": " + ndp.nearDuplciateDetector(fName[j]).toString());
			}
			System.out.print("\n");
		}
		
		
//		MinHash mh = new MinHash(folder, 400);	
//		int[][] matrix = mh.minHashMatrix();
////		printMatrix(matrix);
//		String file1 = "baseball557.txt";
//		String file2 = "baseball558.txt";
//		String file3 = "baseball563.txt";
//		NearDuplicates ndp = new NearDuplicates(folder, 800, 0.9);
//		ArrayList<String> arr = ndp.nearDuplciateDetector(file1);
//		System.out.println("0.9-similiar:" + arr.toString()); 
//		MinHashSimilarities mhs = new MinHashSimilarities(folder, 800);	
//        System.out.println(file1 + " & " + file2 + " exact: " + mhs.exactJaccard(file1, file2)); 
//        System.out.println(file1 + " & " + file2 + " approx: " + mhs.approximateJaccard(file1, file2)); 
//        
//        System.out.println(file1 + " & " + file3 + " exact: " + mhs.exactJaccard(file1, file3)); 
//        System.out.println(file1 + " & " + file3 + " approx: " + mhs.approximateJaccard(file1, file3)); 
//        

        
        
//        MinHashAccuracy mha = new MinHashAccuracy();
//		int[] permutations = new int[]{400,600,800};
//		double[] epsilons = new double[]{0.04,0.07,0.09};
//		for (int i = 0; i < permutations.length; i++) {
//			for(int j = 0; j <epsilons.length; j++) {
//				mha.accuracy(folder,permutations[i],epsilons[j]);
//			}
//		}
		
//        MinHashTime mht = new MinHashTime();
//        mht.timer(folder, 600);
		
//		MinHash mh = new MinHash(folder, 10);
//		System.out.println(Arrays.toString(mh.allDocs()));
//		printMatrix(mh.termDocumentMatrix());
//		System.out.print("\n");
//		printMatrix(mh.minHashMatrix());
//		System.out.println(mh.numPermutations());
	}
}
