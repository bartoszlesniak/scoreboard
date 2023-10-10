package org.example.scoreboard;

public interface ScoreBoardService {
    void startGame(String homeTeam, String awayTeam);
    void finishGame(String homeTeam, String awayTeam);
    void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore);
    GamesSummaryVM getGamesSummaryByTotalScore();
}
