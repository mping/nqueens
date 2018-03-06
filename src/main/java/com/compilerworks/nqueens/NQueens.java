package com.compilerworks.nqueens;

public class NQueens {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid invocation. To run via gradle: gradle run -PappArgs=\"[4]\"");
            System.exit(1);
        }

        Integer size = Integer.valueOf(args[0]);
        Board b = new Board(size);

        if (b.findSolution()) {
            System.out.println(b);
        } else {
            System.err.printf("Couldn't find solution for board of %dx%d!", size, size);
        }
    }
}
