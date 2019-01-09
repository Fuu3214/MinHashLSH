
public class MinHashTime {
	
	public MinHashTime() {}

    public void timer(String folder, int numPermutations) {
    	
        System.out.println("Test on folder " + folder);

        long t0 = System.currentTimeMillis();
        MinHashSimilarities mhs = new MinHashSimilarities(folder, numPermutations);
        long generate = System.currentTimeMillis() - t0;

        System.out.println("Time to construct an instance of MinHashSimilarities: " + (double)generate/1000 + " s");

        String[] allDocs = mhs.allDocs();
        int n = allDocs.length;
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < n; i ++) {
            for (int j = i + 1; j < n; j ++) {

                mhs.exactJaccard(allDocs[i], allDocs[j]);
            }
        }
        long exactTime = System.currentTimeMillis() - t1;

        System.out.println("Time to compute the exactJaccard: " + (double)exactTime/1000 + " s");

        long t2 = System.currentTimeMillis();
        for (int i = 0; i < n; i ++) {
            for (int j = i + 1; j < n; j ++) {

                mhs.approximateJaccard(allDocs[i], allDocs[j]);
            }
        }
        long approxTime = System.currentTimeMillis() - t2;

        System.out.println("Time to compute the approximateJaccard: " + (double)approxTime/1000 + " s");
    }

}

