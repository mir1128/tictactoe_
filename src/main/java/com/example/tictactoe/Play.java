package com.example.tictactoe;

import java.util.Scanner;

public class Play {
    public static void main(String[] args) {
        System.out.println("tic toc tae: ");
        Board nextState = new Board();
        print(nextState);

        Scanner scanner = new Scanner(System.in);
        Mcst mcst = new Mcst();

        System.out.print("your turn: ");

        String input = scanner.next();
        while (!input.equals("quit")) {
            int move = Integer.parseInt(input);
            nextState = nextState.move(move);

            print(nextState);

            if (ifGameOver(nextState)) {
                nextState = new Board();
                System.out.print("your turn: ");
                input = scanner.next();
                continue;
            }

            TreeNode treeNode = TreeNode.startNode(nextState);
            int aiMove = mcst.search(treeNode, 1000);
            nextState = nextState.move(aiMove);

            print(nextState);
            if (ifGameOver(nextState)) {
                nextState = new Board();
            }
            System.out.print("your turn: ");
            input = scanner.next();
        }
    }

    private static boolean ifGameOver(Board board) {
        if (board.isGameOver()) {
            if (board.isOWin()) System.out.println("o win.");
            if (board.isXWin()) System.out.println("x win.");
            if (board.isDraw()) System.out.println("draw.");
            return true;
        }
        return false;
    }

    private static void print(Board board) {
        System.out.println(board.dump());
    }
}
