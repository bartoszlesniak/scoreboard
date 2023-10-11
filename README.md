# Football World Cup Score Board

This library is responsible for registering scores of World Cup matches and showing them in specific order.

## Prerequisites

Java version 8 or higher is required to run the code in this library. 

## Usage

Library comes with in memory repository implementation, so there's no need to use external databases to store the data.
Setup is very simple, we just need to create a new instance of `ScoreBoardServiceImpl`.

```java
final ScoreBoardService scoreBoard = new ScoreBoardServiceImpl(); 
```

Then we can use all the methods this library provides, described below.

### Starting a new game

To start a new game we should use the method `startGame` with two `String` parameters: home team name and away team name.

```java
scoreBoard.startGame("Mexico", "Canada");
```

This game will be created with score 0:0 and kept until game will be finished.

**Important notes:**
- When Team names will be `null` or empty strings the `ScoreBoardServiceException` will be thrown.
- System won't allow to create twice the same game until the first one won't finish.
  The `ScoreBoardServiceException` will be thrown.
- System will allow to create a game with the same team that is currently in other game (even the same teams with different order).
  It was not a requirement for the current version.
- Switching home team name and away team name when starting a game will result in different game which can be started in parallel.

### Finishing a game

To finish a game we should use the method `finishGame` with two `String` parameters: home team name and away team name.

```java
scoreBoard.finishGame("Mexico", "Canada");
```

Game will be finished and removed from the score board.

**Important notes:**
- Game with team names must be started before finishing. Order of home and away team names needs to be the same. 
  If game was not started before, the `ScoreBoardServiceException` will be thrown.

### Updating the score

To update the score of a game we should use the method `updateScores` with parameters:
- home team name,
- away team name,
- home team score,
- away team score.

```java
scoreBoard.updateScore("Mexico", "Canada", 0, 5);
```

**Important notes:**
- Scores need to be a positive numbers, when negative number will be provided, the `ScoreBoardServiceException` will be thrown.
- There's a possibility to change the score to lower than it was before (in case of changing wrongly inserted score).
  System doesn't check if the inserted score is lower than previous one.
- Game with team names must be started before updating the score. Order of home and away team names needs to be the same.
  If game was not started before, the `ScoreBoardServiceException` will be thrown.

### Getting a summary of games by total score

To get a summary of games by total score we should use the method `getGamesSummaryByTotalScore`.
It will return `GamesSummaryVM` object with all games and their scores sorted by total score. If games will have the same
total score, then they will be ordered by the most recently added to the system. If there will be no games at the moment,
the result will have empty list.


```java
final GamesSummaryVM summary = scoreBoard.getGamesSummaryByTotalScore();
```
