package org.example.scoreboard;

import java.util.List;
import java.util.stream.Collectors;

class GamesSummaryVMMapper {
    public static GamesSummaryVM map(List<Game> games) {
        return new GamesSummaryVM(games.stream().map(GamesSummaryVMMapper::map).collect(Collectors.toList()));
    }

    private static GamesSummaryVM.Game map(Game game) {
        return new GamesSummaryVM.Game(
                game.getHomeTeamName(),
                game.getAwayTeamName(),
                game.getHomeTeamScore(),
                game.getAwayTeamScore()
        );
    }
}
