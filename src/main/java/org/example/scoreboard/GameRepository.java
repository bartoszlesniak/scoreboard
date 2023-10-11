package org.example.scoreboard;

import java.util.List;
import java.util.Optional;

interface GameRepository {
    Optional<Game> findByTeamNames(String homeTeamName, String awayTeamName);
    List<Game> findAllOrderedByTotalScore();
    void save(Game game);
    void delete(Game game);
}
