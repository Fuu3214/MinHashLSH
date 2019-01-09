
public class MinHashAccuracy {
	
	long generateTime, exactTime, approxTime;
	
    public MinHashAccuracy() {}

    public void accuracy(String folder, int numPermutations, double epsilon) {

        System.out.println("Test on folder " + folder);

        MinHashSimilarities mhs = new MinHashSimilarities(folder, numPermutations);
        String[] allDocs = mhs.allDocs();
        int n = allDocs.length;
        int pairs = 0;
        for (int i = 0; i < n; i ++) {
            for (int j = i + 1; j < n; j ++) {
                double exact = mhs.exactJaccard(allDocs[i], allDocs[j]);
                double approx = mhs.approximateJaccard(allDocs[i], allDocs[j]);
//                System.out.println(allDocs[i] + " & " + allDocs[j] + " : "+ " exact: " + exact + " approx: " + approx);
                if (Math.abs(exact - approx) > epsilon) {
                    pairs ++;
                }
            }
        }
        System.out.println("There are " + pairs + " pairs differ more than epsilon of "+ epsilon +
                " with numPermutations of "+ numPermutations);
    }

}
