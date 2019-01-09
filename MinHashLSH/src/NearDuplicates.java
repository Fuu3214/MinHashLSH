import java.util.ArrayList;

public class NearDuplicates {
    private int bands;
    private LSH lsh;
    public NearDuplicates(String folder, int numPermutations, double s){
        MinHash mh = new MinHash(folder, numPermutations);
        int k = mh.minHashMatrix().length;
        int b = 1;
        while (true) {
            double temp = Math.pow(1.0/b, 1.0/(k/b));
            if(temp > s) {
                b++;
            } else {
                bands = b;
                break;
            }
        }
//        System.out.println("B: " + b + ", R: " + k/b + ", s: " + Math.pow(1.0/b, 1.0/(k/b)));
        this.lsh = new LSH(mh.minHashMatrix(), mh.allDocs(), bands);
    }

    public ArrayList<String> nearDuplciateDetector(String docName) {
        //Then this method returns a list of documents that
        // are at least s-similar to docName, by calling the method nearDuplicates from LSH

        //Check the document
        ArrayList<String> arr = lsh.nearDuplicatesOf(docName);
        arr.remove(docName);
        return arr;
    }

}
