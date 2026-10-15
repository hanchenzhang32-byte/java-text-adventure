package org.uob.a1;

/**
 * Tracks the player's score based on rooms visited and puzzles solved.
 * Starts from an initial score and then updates as the player explores.
 */
public class Score {
    private int startingScore;
    private int roomsVisited;
    private int puzzlesSolved;
    private final int PUZZLE_VALUE = 10; // how much each puzzle is worth

    public Score(int startingScore) {
        this.startingScore = startingScore;
        this.roomsVisited = 0;
        this.puzzlesSolved = 0;
    }

    // called whenever the player enters a new room
    public void visitRoom() {
        roomsVisited++;
    }

    // called whenever the player solves a puzzle (e.g. keycard, fuse)
    public void solvePuzzle() {
        puzzlesSolved++;
    }

    // final score: start - roomsVisited + puzzlesSolved * 10
    public double getScore() {
        return startingScore - roomsVisited + (puzzlesSolved * PUZZLE_VALUE);
    }
}
