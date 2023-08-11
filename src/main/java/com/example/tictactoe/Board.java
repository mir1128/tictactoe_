package com.example.tictactoe;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Board implements Cloneable {
    private byte[] board;

    @Getter
    private byte currentPlayer;

    public Board() {
        this.board = new byte[9];
        this.currentPlayer = 'o';
    }

    public Board move(int move) {
        Board clone = clone();
        if (clone.board[move] == 0) {
            clone.currentPlayer = (byte) ((this.currentPlayer == 'x') ? 'o' : 'x');
            clone.board[move] = clone.currentPlayer;
        }
        return clone;
    }

    public String encode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : this.board) {
            if (b == 120) stringBuilder.append("x");
            if (b == 111) stringBuilder.append("o");
            if (b == 0) stringBuilder.append(".");
        }
        return stringBuilder.toString();
    }

    public List<Integer> possibleMoves() {
        return IntStream.range(0, 9).filter(i -> this.board[i] == 0).boxed().collect(Collectors.toList());
    }

    public boolean isGameOver() {
        return !gameState().equals(GameState.ON_GOING);
    }

    public boolean isOWin() {
        return gameState().equals(GameState.O_WIN);
    }

    public boolean isXWin() {
        return gameState().equals(GameState.X_WIN);
    }

    public boolean isDraw() {
        return gameState().equals(GameState.DRAW);
    }

    public String dump() {

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            stringBuilder.append(this.board[i] == 0 ? "-" : (this.board[i] == 'x' ? "x" : "o")).append(" ");
            if ((i + 1) % 3 == 0) {
                stringBuilder.append("\n");
            }
        }
        return stringBuilder.toString();
    }

    private GameState gameState() {
        // 判断行是否连成一条线
        for (int i = 0; i < 9; i += 3) {
            if (board[i] != 0 && board[i] == board[i + 1] && board[i] == board[i + 2]) {
                return board[i] == 'x' ? GameState.X_WIN : GameState.O_WIN;
            }
        }

        // 判断列是否连成一条线
        for (int i = 0; i < 3; i++) {
            if (board[i] != 0 && board[i] == board[i + 3] && board[i] == board[i + 6]) {
                return board[i] == 'x' ? GameState.X_WIN : GameState.O_WIN;
            }
        }

        // 判断对角线是否连成一条线
        if (board[0] != 0 && board[0] == board[4] && board[0] == board[8]) {
            return board[0] == 'x' ? GameState.X_WIN : GameState.O_WIN;
        }
        if (board[2] != 0 && board[2] == board[4] && board[2] == board[6]) {
            return board[2] == 'x' ? GameState.X_WIN : GameState.O_WIN;
        }

        // 判断是否平局
        for (byte b : board) {
            if (b == 0) {
                return GameState.ON_GOING;
            }
        }

        return GameState.DRAW;
    }

    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            clone.board = Arrays.copyOf(board, 9);
            clone.currentPlayer = this.currentPlayer;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
