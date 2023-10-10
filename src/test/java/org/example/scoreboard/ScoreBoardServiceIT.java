package org.example.scoreboard;

import org.example.scoreboard.exception.ScoreBoardServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ScoreBoardServiceIT {
    private final InMemoryGameTestRepository repository = new InMemoryGameTestRepository();
    private final ScoreBoardService service = new ScoreBoardServiceImpl(repository);

    @BeforeEach
    void setUp() {
        repository.reset();
        repository.applyFixtures();
    }

    @Test
    void startGame_whenThereAreNoGames_createsNewGameWithEmptyScores() {
        // Given
        final String homeTeamName = "Poland";
        final String awayTeamName = "England";

        // When
        service.startGame(homeTeamName, awayTeamName);

        // Then
        Optional<Game> optionalGame = repository.findByTeamNames(homeTeamName, awayTeamName);
        assertFalse(optionalGame.isEmpty());

        final Game actualGame = optionalGame.get();
        assertEquals(homeTeamName, actualGame.getHomeTeamName());
        assertEquals(awayTeamName, actualGame.getAwayTeamName());
        assertEquals(0, actualGame.getHomeTeamScore());
        assertEquals(0, actualGame.getAwayTeamScore());
    }

    @ParameterizedTest
    @CsvSource({"Home,''", "'',Away", "'',''", "Home,", ",Away", ","})
    void startGame_whenEmptyTeamNameProvided_throwsException(String homeTeamName, String awayTeamName) {
        // When
        final Executable executable = () -> service.startGame(homeTeamName, awayTeamName);

        // Then
        final Throwable exception = assertThrows(ScoreBoardServiceException.class, executable);
        assertEquals("Team name cannot be empty", exception.getMessage());
    }

    @Test
    void startGame_whenGameAlreadyExists_throwsException() {
        // Given
        final String homeTeamName = "Mexico";
        final String awayTeamName = "Canada";

        // When
        final Executable executable = () -> service.startGame(homeTeamName, awayTeamName);

        // Then
        final Throwable exception = assertThrows(ScoreBoardServiceException.class, executable);
        assertEquals("Game has already started", exception.getMessage());
    }

    @Test
    void finishGame_whenGameExists_removesGame() {
        // Given
        final String homeTeamName = "Mexico";
        final String awayTeamName = "Canada";

        // When
        service.finishGame(homeTeamName, awayTeamName);

        // Then
        Optional<Game> optionalGame = repository.findByTeamNames(homeTeamName, awayTeamName);
        assertTrue(optionalGame.isEmpty());
    }

    @Test
    void finishGame_whenGameDoesNotExists_throwsAnException() {
        // Given
        final String homeTeamName = "Poland";
        final String awayTeamName = "England";

        // When
        final Executable executable = () -> service.finishGame(homeTeamName, awayTeamName);

        // Then
        final Throwable exception = assertThrows(ScoreBoardServiceException.class, executable);
        assertEquals("Game does not exists", exception.getMessage());
    }

    @Test
    void updateScore_whenGameExists_updatesScore() {
        // Given
        final String homeTeamName = "Mexico";
        final String awayTeamName = "Canada";

        // When
        service.updateScore(homeTeamName, awayTeamName, 1, 6);

        // Then
        Optional<Game> optionalGame = repository.findByTeamNames(homeTeamName, awayTeamName);
        assertFalse(optionalGame.isEmpty());

        final Game actualGame = optionalGame.get();
        assertEquals(1, actualGame.getHomeTeamScore());
        assertEquals(6, actualGame.getAwayTeamScore());
    }

    @Test
    void updateScore_whenGameExists_andScoreIsLowerThanPrevious_updatesScore() {
        // Given
        final String homeTeamName = "Mexico";
        final String awayTeamName = "Canada";

        // When
        service.updateScore(homeTeamName, awayTeamName, 0, 4);

        // Then
        Optional<Game> optionalGame = repository.findByTeamNames(homeTeamName, awayTeamName);
        assertFalse(optionalGame.isEmpty());

        final Game actualGame = optionalGame.get();
        assertEquals(0, actualGame.getHomeTeamScore());
        assertEquals(4, actualGame.getAwayTeamScore());
    }

    @Test
    void updateScore_whenGameDoesNotExists_throwsAnException() {
        // Given
        final String homeTeamName = "Poland";
        final String awayTeamName = "England";

        // When
        final Executable executable = () -> service.updateScore(homeTeamName, awayTeamName, 5, 2);

        // Then
        final Throwable exception = assertThrows(ScoreBoardServiceException.class, executable);
        assertEquals("Game does not exists", exception.getMessage());
    }

    @ParameterizedTest
    @CsvSource({"-1, 0", "2, -5"})
    void updateScore_whenScoreIsInvalid_throwsAnException(int homeTeamScore, int awayTeamScore) {
        // Given
        final String homeTeamName = "Mexico";
        final String awayTeamName = "Canada";

        // When
        final Executable executable = () -> service
                .updateScore(homeTeamName, awayTeamName, homeTeamScore, awayTeamScore);

        // Then
        final Throwable exception = assertThrows(ScoreBoardServiceException.class, executable);
        assertEquals("Score cannot be lower than 0", exception.getMessage());
    }

    @Test
    void getGamesSummaryByTotalScore_returnsGamesSummary() {
        // When
        final GamesSummaryVM summary = service.getGamesSummaryByTotalScore();

        // Then
        assertNotNull(summary);
        assertEquals(5, summary.getGames().size());
        assertEquals(new GamesSummaryVM.Game("Uruguay", "Italy", 6, 6), summary.getGames().get(0));
        assertEquals(new GamesSummaryVM.Game("Spain", "Brazil", 10, 2), summary.getGames().get(1));
        assertEquals(new GamesSummaryVM.Game("Mexico", "Canada", 0, 5), summary.getGames().get(2));
        assertEquals(new GamesSummaryVM.Game("Argentina", "Australia", 3, 1), summary.getGames().get(3));
        assertEquals(new GamesSummaryVM.Game("Germany", "France", 2, 2), summary.getGames().get(4));
    }

    @Test
    void getGamesSummaryByTotalScore_whenNoGames_returnsEmptyList() {
        // Given
        repository.reset();

        // When
        final GamesSummaryVM summary = service.getGamesSummaryByTotalScore();

        // Then
        assertNotNull(summary);
        assertEquals(0, summary.getGames().size());
    }
}
