package org.example.scoreboard;

import org.example.scoreboard.exception.ScoreBoardServiceException;

class Game {
    private final String homeTeamName;
    private final String awayTeamName;
    private int homeTeamScore = 0;
    private int awayTeamScore = 0;

    Game(String homeTeamName, String awayTeamName) {
        if (homeTeamName == null || homeTeamName.isEmpty() || awayTeamName == null || awayTeamName.isEmpty()) {
            throw new ScoreBoardServiceException("Team name cannot be empty");
        }

        this.homeTeamName = homeTeamName;
        this.awayTeamName = awayTeamName;
    }

    Game(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
        this(homeTeamName, awayTeamName);
        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }

    public String getHomeTeamName() {
        return homeTeamName;
    }

    public String getAwayTeamName() {
        return awayTeamName;
    }

    public int getHomeTeamScore() {
        return homeTeamScore;
    }

    public int getAwayTeamScore() {
        return awayTeamScore;
    }

    public int getTotalScore() {
        return homeTeamScore + awayTeamScore;
    }

    public void setScores(int homeTeamScore, int awayTeamScore) {
        if (homeTeamScore < 0 || awayTeamScore < 0) {
            throw new ScoreBoardServiceException("Score cannot be lower than 0");
        }

        this.homeTeamScore = homeTeamScore;
        this.awayTeamScore = awayTeamScore;
    }
}
