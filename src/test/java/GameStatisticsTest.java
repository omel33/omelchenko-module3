package quest.unit;

import org.junit.jupiter.api.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.*;

class GameStatisticsTest {
    private static final Path TEST_STATS_PATH = Paths.get("data", "test_game_stats.txt");
    private GameStatistics gameStatistics;

    @BeforeEach
    void setUp() throws IOException {
        Files.createDirectories(TEST_STATS_PATH.getParent());
        Files.writeString(TEST_STATS_PATH, "0");
        gameStatistics = new GameStatistics();
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(TEST_STATS_PATH);

    }

    @Test
    void testIncrementGamesPlayed() {
        int initialGames = gameStatistics.getGamesPlayed();
        gameStatistics.incrementGamesPlayed();
        assertEquals(initialGames + 1, gameStatistics.getGamesPlayed());
    }

    @Test
    void testLoadStatsWhenFileExists() throws IOException {
        Files.writeString(TEST_STATS_PATH, "42");
        gameStatistics.loadStats();
        assertEquals(42, gameStatistics.getGamesPlayed());
    }

    @Test
    void testLoadStatsWhenFileNotExists() throws IOException {
        Files.deleteIfExists(TEST_STATS_PATH);
        gameStatistics.loadStats();
        assertEquals(0, gameStatistics.getGamesPlayed());
    }

    @Test
    void testSaveStats() throws IOException {
        gameStatistics.setGamesPlayed(10);
        gameStatistics.saveStats();
        String content = Files.readString(TEST_STATS_PATH);
        assertEquals("10", content.trim());
    }

    @Test
    void testEnsureDataDirectoryExists() {
        assertTrue(Files.exists(TEST_STATS_PATH.getParent()));
    }
}