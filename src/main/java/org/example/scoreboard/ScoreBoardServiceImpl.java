package org.example.scoreboard;

public class ScoreBoardServiceImpl implements ScoreBoardService {
    private final GameRepository gameRepository;

    public ScoreBoardServiceImpl() {
        gameRepository = new InMemoryGameRepository();
    }

    ScoreBoardServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public void startGame(String homeTeam, String awayTeam) {
        // TODO
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        // TODO
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        // TODO
    }

    @Override
    public GamesSummaryVM getGamesSummaryByTotalScore() {
        // TODO
        return null;
    }
}
