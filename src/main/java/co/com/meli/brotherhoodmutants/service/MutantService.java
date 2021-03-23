package co.com.meli.brotherhoodmutants.service;

import co.com.meli.brotherhoodmutants.model.entity.Stats;
import co.com.meli.brotherhoodmutants.model.response.StatsResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@AllArgsConstructor
public class MutantService {

    public static int N = 6;
    private static final List<char[]> sequences = Arrays.asList(new char[]{'A', 'A', 'A', 'A'}, new char[]{'C', 'C', 'C', 'C'}, new char[]{'G', 'G', 'G', 'G'}, new char[]{'T', 'T', 'T', 'T'});

    @Autowired
    private final StatsRepository statsRepository;

    public boolean isMutant(String[] dna) {
        return findOrientation(dna);
    }

    public StatsResponse getStats() {
        return statsRepository.findStatsRatio();
    }

    private boolean findOrientation(String[] dna) {

        List<Integer> matches = new ArrayList<>();

        char[][] dnaSequence = new char[dna.length][dna.length];

        for (int i = 0; i < dna.length; i++) {
            String s = dna[i];
            char[] chars = s.toCharArray();
            System.arraycopy(chars, 0, dnaSequence[i], 0, chars.length);
        }

        // allocate memory for string containing cols
        char[] col = new char[N];

        for (char[] sequence : sequences) {

            if (KMPSearch(sequence, diagonalOrderStraight(dnaSequence)) == 1) {
                matches.add(1);
            }

            for (int i = 0; i < N; i++) {

                // search in row i
                if (KMPSearch(sequence, dnaSequence[i]) == 1) {
                    matches.add(1);
                }

                // Construct an array to store i'th column
                for (int j = 0; j < N; j++) {
                    col[j] = dnaSequence[j][i];
                }

                // Search in column i
                if (KMPSearch(sequence, col) == 1) {
                    matches.add(1);
                }
            }
        }

        if (matches.size() > 1) {
            saveStats(Boolean.TRUE);
            return Boolean.TRUE;
        } else {
            saveStats(Boolean.FALSE);
            return Boolean.FALSE;
        }
    }

    private void saveStats(Boolean mutant) {
        Stats stats = new Stats();
        stats.setDateMatch(LocalDate.now());
        stats.setMutant(mutant ? true : null);
        stats.setHuman(!mutant ? true : null);
        statsRepository.save(stats);
    }

    private static int KMPSearch(char[] sequence, char[] dnaSequence) {

        int M = sequence.length;
        // create lps[] that will hold the longest prefix suffix values for pattern
        int[] lps = new int[M];
        int j = 0; // index for sequence[]

        // Preprocess the pattern (calculate lps[] array)
        computeLPSArray(sequence, M, lps);

        int i = 0; // index for dnaSequence[]

        while (i < N) {

            if (sequence[j] == dnaSequence[i]) {

                j++;
                i++;
            }
            if (j == M) {
                // return 1 is pattern is found
                return 1;
            }
            // mismatch after j matches
            else if (i < N && sequence[j] != dnaSequence[i]) {
                // Do not match lps[0..lps[j-1]] characters, they will match anyway
                if (j != 0) {
                    j = lps[j - 1];

                } else {
                    i = i + 1;
                }
            }
        }
        // return 0 is pattern is not found
        return 0;
    }

    private static void computeLPSArray(char[] sequence, int M, int[] lps) {

        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0

        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {

            if (sequence[i] == sequence[len]) {

                len++;
                lps[i++] = len;
            } else {
                // (sequence[i] != sequence[len])
                if (len != 0) {
                    // This is tricky. Consider the example AAACAAAA and i = 7.
                    len = lps[len - 1];
                    // Also, note that we do not increment i here
                } else {
                    // if (len == 0)
                    lps[i++] = 0;
                }
            }
        }
    }

    // The main function that prints given
    // matrix in diagonal order
    static char[] diagonalOrderStraight(char matrix[][]) {
        int ROW = matrix.length;
        int COL = matrix.length;
        char[] col = new char[0];
        // There will be ROW+COL-1 lines in the output
        for (int line = 1; line <= (ROW + COL - 1); line++) {

            // Get column index of the first
            // element in this line of output.
            // The index is 0 for first ROW
            // lines and line - ROW for remaining lines
            int start_col = max(0, line - ROW);

            // Get count of elements in this line.
            // The count of elements is equal to
            // minimum of line number, COL-start_col and ROW
            int count = min(line, (COL - start_col),
                    ROW);

            col = new char[line];
            for (int j = 0; j < count; j++) {
                col[j] = matrix[min(ROW, line) - j - 1][start_col + j];
            }
            if (col.length == matrix.length)
                break;
        }
        return col;
    }

    // A utility function to find min of two integers
    static int min(int a, int b) {
        return (a < b) ? a : b;
    }

    // A utility function to find min of three integers
    static int min(int a, int b, int c) {
        return min(min(a, b), c);
    }

    // A utility function to find max of two integers
    static int max(int a, int b) {
        return (a > b) ? a : b;
    }
}
