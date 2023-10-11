package org.example.scoreboard;

import java.util.List;

/**
 * Games summary view model to return summary of currently active games.
 */
public class GamesSummaryVM {
    private final List<Game> games;

    GamesSummaryVM(List<Game> games) {
        this.games = games;
    }

    public List<Game> getGames() {
        return games;
    }

    public static class Game {
        private final String homeTeamName;
        private final String awayTeamName;
        private final int homeTeamScore;
        private final int awayTeamScore;

        Game(String homeTeamName, String awayTeamName, int homeTeamScore, int awayTeamScore) {
            this.homeTeamName = homeTeamName;
            this.awayTeamName = awayTeamName;
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
    }
}
