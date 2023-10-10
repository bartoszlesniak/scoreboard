package org.example.scoreboard;

import java.util.Optional;

interface GameRepository {
    Optional<Game> findByTeamNames(String homeTeamName, String awayTeamName);
    void save(Game game);
}
