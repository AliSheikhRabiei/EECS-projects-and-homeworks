   
/* returns first index where pattern occurs in text, or -1 if not found
 * 
 */

public class DivideConquerSubstringSearch {


    public static int find(String text, String pattern) {
        if (pattern == null || text == null) throw new IllegalArgumentException("null input");
        int n = text.length();
        int m = pattern.length();
        
        // empty pattern occurs at 0
        if (m == 0) return 0;
        if (m > n) return -1;

        return findRec(text, pattern, 0, n - 1);
    }

    // Search pattern inside text L to R
    private static int findRec(String text, String pattern, int L, int R) {
        int m = pattern.length();
        int len = R - L + 1;

        // Base case is when pattern cannot fit
        if (len < m) return -1;

        // if segment is small enough we can brute force
        if (len <= m) {
            return bruteForce(text, pattern, L, R);
        }

        int mid = (L + R) / 2;

        // Conquer part, search L then R
        int leftAns = findRec(text, pattern, L, mid);
        if (leftAns != -1) return leftAns;

        int rightAns = findRec(text, pattern, mid + 1, R);
        if (rightAns != -1) return rightAns;

        // Combine part, check matches that cross the boundary between mid and mid+1
        // Any crossing match must start in mid - m + 1 and mid
        int start = Math.max(L, mid - m + 1);
        int end = Math.min(mid, R - m + 1);

        for (int i = start; i <= end; i++) {
            if (matchesAt(text, pattern, i)) {
                return i;
            }
        }

        return -1;
    }

    // Brute force check within L to R
    private static int bruteForce(String text, String pattern, int L, int R) {
        int m = pattern.length();
        int lastStart = R - m + 1;
        for (int i = L; i <= lastStart; i++) {
            if (matchesAt(text, pattern, i)) return i;
        }
        return -1;
    }

    // Compare pattern with text starting at index i
    private static boolean matchesAt(String text, String pattern, int i) {
        int m = pattern.length();
        for (int j = 0; j < m; j++) {
            if (text.charAt(i + j) != pattern.charAt(j)) return false;
        }
        return true;
    }

}
