package com.compilerworks.nqueens;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    public void testToString() {
        int[][] b = {
                {1, 1, 1}, // row 0
                {0, 1, 0}, // row 1
                {1, 0, 1}};// row 2

        Board board = new Board(b);

        assertEquals("1 0 1 \n0 1 0 \n1 1 1 ", board.toString());
    }

    @Test
    public void testOfReverse() {
        int[][] b = {
                {1, 0, 1}, // row 2
                {0, 1, 0}, // row 1
                {1, 1, 1}};// row 0

        Board board = Board.ofReverse(b);

        assertEquals("1 0 1 \n0 1 0 \n1 1 1 ", board.toString());
    }

    @Test
    public void testCannotPlaceQueenInSameRow() {
        int[][] b = {
                {0, 0, 0}, // row 2
                {1, 0, 0}, // row 1
                {0, 0, 0}};// row 0

        Board board = Board.ofReverse(b);

        assertFalse("Shouldn't be able to place queen under attack by same row", board.canPlaceQueenAt(1, 1));
    }

    @Test
    public void testCannotPlaceQueenInUpperDiag() {
        int[][] b = {
                {0, 0, 0}, // row 2
                {1, 0, 0}, // row 1
                {0, 0, 0}};// row 0
        Board board = Board.ofReverse(b);

        assertFalse("Shouldn't be able to place queen under attack by upper diagonal", board.canPlaceQueenAt(2, 1));
    }

    @Test
    public void testCannotPlaceQueenInLowerDiag() {
        int[][] b = {
                {0, 0, 0}, // row 2
                {1, 0, 0}, // row 1
                {0, 0, 0}};// row 0

        Board board = Board.ofReverse(b);

        assertFalse("Shouldn't be able to place queen under attack by lower diagonal", board.canPlaceQueenAt(0, 1));
    }

    @Test
    public void testCannotPlaceQueenBecauseThreeInLine() {
        int[][] b = {
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0}, //pos 2,4 should't be allowed
                {0, 0, 1, 0, 0},
                {1, 0, 0, 0, 0}};

        Board board = Board.ofReverse(b);

        assertFalse("Shouldn't be able place queen at [2,4] because of line [[0,0],[1,2],[2,4]]",
                board.canPlaceQueenAt(2, 4));
    }

    @Test
    public void testBoardApi() {
        Board b = new Board(5);

        assertTrue(b.canPlaceQueenAt(0,0));
        b.placeQueenAt(0,0);

        assertTrue(b.canPlaceQueenAt(1,2));
        b.placeQueenAt(1,2);

        assertFalse(b.canPlaceQueenAt(2,4));

        b.removeQueenAt(1,2);
        assertTrue(b.canPlaceQueenAt(2,4));
    }

    @Test
    public void testSolutionDoesntExistFor3x3() {
        Board b = new Board(3);
        assertFalse("3x3 board shouldn't have a solution", b.findSolution());
    }

    @Test
    public void testSolutionExistsFor4x4() {
        Board b = new Board(4);
        assertTrue("4x4 board should have a solution", b.findSolution());
    }
}
