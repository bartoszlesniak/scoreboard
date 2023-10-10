package org.example.scoreboard;

class InMemoryGameTestRepository extends InMemoryGameRepository implements TestRepository {
    @Override
    public void reset() {
        games.clear();
    }

    @Override
    public void applyFixtures() {
        save(new Game("Mexico", "Canada", 0, 5));
        save(new Game("Spain", "Brazil", 10, 2));
        save(new Game("Germany", "France", 2, 2));
        save(new Game("Uruguay", "Italy", 6, 6));
        save(new Game("Argentina", "Australia", 3, 1));
    }
}
