import edu.duke.FileResource;

import java.util.HashMap;
import java.util.Map;

public class CodonCount {
    private static final int CODON_LENGTH = 3;

    private HashMap<String, Integer> map;

    private CodonCount() {
        map = new HashMap<String, Integer>();
    }

    private FileResource openFile() {
        FileResource fr = new FileResource();
        return fr;
    }

    private String preparationString() {
        return openFile().asString().toUpperCase().trim();
    }

    private void buildCodonMap(int start, String dna) {
        for (int i = start; i <= dna.length() - CODON_LENGTH; i += CODON_LENGTH) {
            String codon = dna.substring(i, i + CODON_LENGTH);
            Integer codonHere = map.get(codon);
            if (codonHere != null) {
                map.put(codon, codonHere + 1);
            } else {
                map.put(codon, 1);
            }
        }
    }

    private void printCodonCounts(int start, int end) {
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int occurrences = entry.getValue();
            if (occurrences >= start && occurrences <= end) {
                System.out.println(entry.getKey() + "\t" + occurrences);
            }
        }
    }

    private String getMostCommonCodon() {
        int max = 0;
        String codon = null;
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int curr = entry.getValue();
            if (curr > max) {
                max = curr;
                codon = entry.getKey();
            }
        }
        return codon;
    }

    private void codonCountTest() {
        String dna = preparationString();
        int start = 1;
        int end = 5;

        for (int i = 0; i < CODON_LENGTH; ++i) {
            buildCodonMap(i, dna);
            String codon = getMostCommonCodon();
            System.out.println("Reading frame starting with " + i + " results in " + map.size() + " unique codons");
            System.out.println("  and most common codon is " +
                    codon + " with count " + map.get(codon));
            System.out.println("Counts of codons between " + start + " and " + end + " inclusive are:");
            printCodonCounts(start, end);
            System.out.println();
            map.clear();
        }
    }

    public static void main(String[] args) {
        CodonCount codonCount = new CodonCount();
        codonCount.codonCountTest();
    }
}
