package org.example.scoreboard;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

class InMemoryGameRepository implements GameRepository {
    protected final Map<String, Game> games = new LinkedHashMap<>();

    @Override
    public Optional<Game> findByTeamNames(String homeTeamName, String awayTeamName) {
        return Optional.ofNullable(games.get(getKey(homeTeamName, awayTeamName)));
    }

    @Override
    public void save(Game game) {
        games.put(getKeyForGame(game), game);
    }

    @Override
    public void delete(Game game) {
        games.remove(getKeyForGame(game));
    }

    @Override
    public List<Game> findAllOrderedByTotalScore() {
        return games.values().stream()
                .sorted(this::compareGamesByTotalScore)
                .collect(Collectors.toList());
    }

    private int compareGamesByTotalScore(Game game1, Game game2) {
        if (game1.getTotalScore() == game2.getTotalScore()) {
            return -1; // to reverse order of insertion
        }
        return game2.getTotalScore() - game1.getTotalScore();
    }

    private String getKeyForGame(Game game) {
        return getKey(game.getHomeTeamName(), game.getAwayTeamName());
    }

    private String getKey(String homeTeamName, String awayTeamName) {
        return String.format("%s#%s", homeTeamName, awayTeamName);
    }
}
