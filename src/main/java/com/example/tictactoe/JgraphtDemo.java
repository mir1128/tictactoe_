package com.example.tictactoe;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;

public class JgraphtDemo {

    public static void display(TreeNode root) {
        DefaultMutableTreeNode node = draw(root);
        JFrame frame = new JFrame("Demo");
        JTree tree = new JTree(node);
        frame.add(tree);
        frame.setSize(550,400);
        frame.setVisible(true);
    }

    private static DefaultMutableTreeNode draw(TreeNode root) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(name(root));
        for (TreeNode child : root.getChildren()) {
            node.add(draw(child));
        }
        return node;
    }

    private static String name(TreeNode root) {
        return root.getBoard().encode() + " " +
                root.getScores() +
                "/" +
                root.getVisits();
    }

    public static void main(String[] args) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode("Project");
        DefaultMutableTreeNode node1 = new DefaultMutableTreeNode("App");
        DefaultMutableTreeNode node2 = new DefaultMutableTreeNode("Website");
        DefaultMutableTreeNode node3 = new DefaultMutableTreeNode("WebApp");
        node.add(node1);
        node.add(node2);
        node.add(node3);

        DefaultMutableTreeNode one = new DefaultMutableTreeNode("Learning website");
        DefaultMutableTreeNode two = new DefaultMutableTreeNode("Business website");
        DefaultMutableTreeNode three = new DefaultMutableTreeNode("News publishing website");
        DefaultMutableTreeNode four = new DefaultMutableTreeNode("Android app");
        DefaultMutableTreeNode five = new DefaultMutableTreeNode("iOS app");
        DefaultMutableTreeNode six = new DefaultMutableTreeNode("Editor WebApp");
        node1.add(one);
        node1.add(two);
        node1.add(three);
        node2.add(four);
        node2.add(five);
        node3.add(six);
        JFrame frame = new JFrame("Demo");
        JTree tree = new JTree(node);
        frame.add(tree);
        frame.setSize(550,400);
        frame.setVisible(true);
    }

}
