package org.example.scoreboard;

/**
 * Main facade to interact with library responsible for registering scores of
 * World Cup matches and showing them in specific order.
 */
public interface ScoreBoardService {
    /**
     * Starts a new game.
     *
     * @param homeTeam Home team name
     * @param awayTeam Away team name
     */
    void startGame(String homeTeam, String awayTeam);

    /**
     * Finishes a game for provided teams.
     *
     * @param homeTeam Home team name
     * @param awayTeam Away team name
     */
    void finishGame(String homeTeam, String awayTeam);

    /**
     * Updates the score of the game for provided teams.
     *
     * @param homeTeam Home team name
     * @param awayTeam Away team name
     * @param homeTeamScore Home team score (cannot be lower than 0)
     * @param awayTeamScore Away team score (cannot be lower than 0)
     */
    void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore);

    /**
     * Get active games summary ordered by total score.
     *
     * @return Games summary view model {@link GamesSummaryVM}
     */
    GamesSummaryVM getGamesSummaryByTotalScore();
}
