package com.example.tictactoe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Mcst {
    private static final Random random = new Random();

    public int search(TreeNode root, int searchNumbers) {
        for (int i = 0; i < searchNumbers; i++) {
            TreeNode node = select(root);
            int rollout = rollout(node);
            backPropagate(node, rollout);
        }

        TreeNode bestMove = selectBest(root, 0);
        return bestMove.getMove();
    }

    private void backPropagate(TreeNode node, int simulateScore) {
        node.accumulate(1, simulateScore);
        TreeNode parent = node.getParent();
        while (parent != null) {
            parent.accumulate(1, simulateScore);
            parent = parent.getParent();
        }
    }

    private int rollout(TreeNode node) {
        TreeNode current = node.replica();
        return current.rollout();
    }

    private TreeNode select(TreeNode root) {
        TreeNode current = root;
        while (current != null && !current.isTerminal()) {
            if (current.isFullyExpanded()) {
                current = selectBest(current, 1);
            } else {
                return expand(current);
            }
        }
        return current;
    }

    private TreeNode selectBest(TreeNode current, int explorationConstant) {
        double bestScore = Integer.MIN_VALUE;
        List<TreeNode> bestMoves = new ArrayList<>();

        for (TreeNode child : current.getChildren()) {
            int currentPlayer = child.getTurn() == 'x' ? 1 : -1;

            int exploitation = 0;
            if (child.getVisits() != 0) {
                exploitation = (currentPlayer * child.getScores() / child.getVisits());
            }

            double exploration = explorationConstant * Math.sqrt(current.getVisits()) / (1 + child.getVisits());

            double moveScore = exploitation + exploration;

            if (moveScore > bestScore) {
                bestMoves = new ArrayList<>(Collections.singletonList(child));
                bestScore = moveScore;
            } else if (moveScore == bestScore) {
                bestMoves.add(child);
            }
        }

        if (bestMoves.isEmpty()) {
            throw new RuntimeException("best move is empty.");
        }
        return bestMoves.get(random.nextInt(bestMoves.size()));
    }

    private TreeNode expand(TreeNode current) {
        return current.expand();
    }
}
