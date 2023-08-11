package com.example.tictactoe;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
public class Mcst {
    private static final Random random = new Random();

    public int search(TreeNode root, int searchNumbers) {
        for (int i = 0; i < searchNumbers; i++) {
            TreeNode node = select(root);
            int rollout = rollout(node);
            backPropagate(node, rollout);
        }

//        JgraphtDemo.display(root);
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
                current = selectBest(current, 2);
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

            double exploitation = 0;
            if (child.getVisits() != 0) {
                exploitation = (currentPlayer * (double)child.getScores() / child.getVisits());
            }

            double exploration = explorationConstant * Math.sqrt(Math.log((float) current.getVisits() / child.getVisits()));

            double moveScore = exploitation + exploration;

//            log.info("{} : move score: {}",  child.getBoard().encode(), moveScore);

            if (moveScore > bestScore) {
                bestMoves = new ArrayList<>(Collections.singletonList(child));
                bestScore = moveScore;
            } else if (Math.abs(moveScore - bestScore) < 0.0001) {
                bestMoves.add(child);
            }
        }

        if (bestMoves.isEmpty()) {
            throw new RuntimeException("best move is empty.");
        }
        TreeNode treeNode = bestMoves.get(random.nextInt(bestMoves.size()));
//        log.info("select {}, score: {}", treeNode.getBoard().encode(), bestScore);
        return treeNode;
    }

    private TreeNode expand(TreeNode current) {
        return current.expand();
    }
}
