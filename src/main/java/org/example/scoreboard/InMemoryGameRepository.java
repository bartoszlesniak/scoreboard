package org.example.scoreboard;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryGameRepository implements GameRepository {
    protected final Map<String, Game> games = new HashMap<>();

    @Override
    public Optional<Game> findByTeamNames(String homeTeamName, String awayTeamName) {
        return Optional.ofNullable(games.get(getKey(homeTeamName, awayTeamName)));
    }

    @Override
    public void save(Game game) {
        games.put(getKeyForGame(game), game);
    }

    private String getKeyForGame(Game game) {
        return getKey(game.getHomeTeamName(), game.getAwayTeamName());
    }

    private String getKey(String homeTeamName, String awayTeamName) {
        return String.format("%s#%s", homeTeamName, awayTeamName);
    }
}
