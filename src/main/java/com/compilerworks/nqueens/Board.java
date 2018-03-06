package com.compilerworks.nqueens;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 2d Board representation for the NQueens problem.
 */
public class Board {

    final List<int[]> queens;
    final int[][] board;

    /**
     * Builds a pre-populated board.
     *
     * @param b the 2d board with queens already placed.
     */
    public Board(int[][] b) {
        board = b;
        queens = new ArrayList<>(b.length); //default size

        //let's determine the positions where we have queens
        for (int r = 0; r < b.length; r++) {
            for (int c = 0; c < b.length; c++) {
                if (b[r][c] == 1) {
                    queens.add(new int[]{r, c});
                }
            }
        }
    }

    /**
     * Creates an empty NxN board.
     *
     * @param size the board size
     */
    public Board(int size) {
        this(new int[size][size]);
    }

    /**
     * Takes a literal board representation, where row 0 is at bottom.
     *
     * @param b the row configuration
     * @return the board
     */
    public static Board ofReverse(int[][] b) {
        return new Board(reverse(b));
    }

    /**
     * Reverses an array of rows. To be used with Board#ofReverse in order to supply literal arrays (ie, WYSIWYG).
     *
     * @param array the array
     */
    private static int[][] reverse(int[][] array) {
        int start = 0;
        int end = array.length - 1;
        while (start < end) {
            int[] temp = array[start];
            array[start] = array[end];
            array[end] = temp;
            start++;
            end--;
        }

        return array;
    }

    /**
     * Tries to find the solution for the following:
     * <ul>
     * <li>NQueens problem</li>
     * <li>Additionally, no 3 queens can form a straight line</li>
     * </ul>
     *
     * @return <tt>true</tt> if a solution was found
     */
    public boolean findSolution() {
        return find(0);
    }

    /**
     * Tries to recursively find solutions putting additional queens starting at <tt>startCol</tt>.
     * Stops when N queens are placed, for a board of NxN.
     *
     * @param startCol the starting column
     * @return <tt>true</tt>if a solution was found
     */
    private boolean find(int startCol) {
        if (startCol >= board.length) {
            return true;
        }
        for (int i = 0; i < board.length; i++) {
            if (canPlaceQueenAt(i, startCol)) {
                placeQueenAt(i, startCol);

                if (find(startCol + 1)) {
                    return true;
                }

                removeQueenAt(i, startCol);
            }
        }
        return false;
    }

    void placeQueenAt(int row, int col) {
        board[row][col] = 1;

        int[] pos = {row, col};
        queens.add(pos);
    }

    void removeQueenAt(int row, int col) {
        board[row][col] = 0;
        queens.removeIf(p -> p[0] == row && p[1] == col);
    }

    public boolean canPlaceQueenAt(int row, int col) {
        return !isPosUnderAttack(row, col) && !isPosCollinear3(row, col);
    }

    /**
     * Check for collinearity between two queens and additional supplied position.
     * We can do this quickly if we compute the slope of the supplied point against all other points and save each slope.
     * If a slope is repeated then it means there are two pairs of points with same slope, ie the three are collinear.
     *
     * @param row
     * @param col
     * @return <tt>true</tt> if the position is collinear with two other queens.
     */
    boolean isPosCollinear3(int row, int col) {
        if (queens.size() < 2) {
            return false;
        }

        Set<Double> slopes = new HashSet<>();
        for (int[] p1 : queens) {

            //take care of division by zero by using a special value
            Double slope = (p1[0] - row) == 0
                    ? Double.MAX_VALUE
                    : Double.valueOf((double) (p1[1] - col) / (double) (p1[0] - row));

            if (slopes.contains(slope)) {
                return true;
            }
            slopes.add(slope);
        }
        return false;
    }

    boolean isPosUnderAttack(int row, int col) {
        //check same row
        for (int c = 0; c < col; c++) {
            if (board[row][c] == 1) {
                return true;
            }
        }

        //check lower diag
        for (int r = row, c = col; r >= 0 && c >= 0; c--, r--) {
            if (board[r][c] == 1) {
                return true;
            }
        }

        //check upper diag
        for (int r = row, c = col; r < board.length && c >= 0; r++, c--) {
            if (board[r][c] == 1) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String toString() {
        StringWriter sw = new StringWriter();

        for (int r = board.length - 1; r >= 0; r--) {
            for (int c = 0; c < board.length; c++) {
                sw.append(String.format("%d ", Integer.valueOf(board[r][c])));
            }
            if (r > 0) {
                sw.append("\n");
            }
        }

        return sw.toString();
    }

}
