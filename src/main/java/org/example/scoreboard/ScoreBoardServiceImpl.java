package org.example.scoreboard;

import org.example.scoreboard.exception.ScoreBoardServiceException;

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
        if (gameRepository.findByTeamNames(homeTeam, awayTeam).isPresent()) {
            throw new ScoreBoardServiceException("Game has already started");
        }
        gameRepository.save(new Game(homeTeam, awayTeam));
    }

    @Override
    public void finishGame(String homeTeam, String awayTeam) {
        final Game game = getGameOrThrowException(homeTeam, awayTeam);
        gameRepository.delete(game);
    }

    @Override
    public void updateScore(String homeTeam, String awayTeam, int homeTeamScore, int awayTeamScore) {
        final Game game = getGameOrThrowException(homeTeam, awayTeam);
        game.setScores(homeTeamScore, awayTeamScore);
    }

    private Game getGameOrThrowException(String homeTeam, String awayTeam) {
        return gameRepository.findByTeamNames(homeTeam, awayTeam)
                .orElseThrow(() -> new ScoreBoardServiceException("Game does not exists"));
    }

    @Override
    public GamesSummaryVM getGamesSummaryByTotalScore() {
        return GamesSummaryVMMapper.map(gameRepository.findAllOrderedByTotalScore());
    }
}
