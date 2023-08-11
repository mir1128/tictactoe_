package com.example.tictactoe;

import lombok.Getter;

import java.util.*;

public class TreeNode {
    private static final Random random = new Random();

    @Getter
    private Board board;

    @Getter
    private int visits;

    @Getter
    private int scores;

    @Getter
    private final boolean terminal;

    private final Map<String, TreeNode> childMap = new HashMap<>();

    @Getter
    private final TreeNode parent;

    @Getter
    private int move = -1;

    public static TreeNode startNode(Board board) {
        return new TreeNode(board, null, -1);
    }

    public static TreeNode childNode(Board board, TreeNode parent, int move) {
        return new TreeNode(board, parent, move);
    }

    public TreeNode replica() {
        return new TreeNode(this.board.clone(), null, -1);
    }

    private TreeNode() {
        this.board = new Board();
        this.parent = null;
        this.terminal = board.isGameOver();
    }

    private TreeNode(Board board, TreeNode parent, int move) {
        this.board = board;
        this.parent = parent;
        this.move = move;
        this.terminal = board.isGameOver();
    }

    public void accumulate(int visits, int scores) {
        this.visits += visits;
        this.scores += scores;
    }

    public byte getTurn() {
        return this.board.getCurrentPlayer();
    }

    public int terminalScore() {
        if (!board.isGameOver()) {
            throw new RuntimeException("game is on going.");
        }
        if (board.isDraw()) return 0;
        if (board.isXWin()) return 1;
        if (board.isOWin()) return -1;
        throw new RuntimeException("error state.");
    }

    public TreeNode expand() {
        List<Integer> moves = this.board.possibleMoves();

        for (Integer move : moves) {
            Board nextBoard = this.board.move(move);
            if (!childMap.containsKey(nextBoard.encode())) {
                TreeNode treeNode = new TreeNode(nextBoard, this, move);
                childMap.put(nextBoard.encode(), treeNode);
                return treeNode;
            }
        }

        return null;
    }

    public boolean isFullyExpanded() {
        List<Integer> integers = this.board.possibleMoves();
        return childMap.size() == integers.size();
    }

    public int rollout() {
        while (!this.board.isGameOver()) {
            List<Integer> moves = this.board.possibleMoves();
            this.board = this.board.move(moves.get(random.nextInt(moves.size())));
        }

        return terminalScore();
    }

    public Collection<TreeNode> getChildren() {
        return childMap.values();
    }
}
