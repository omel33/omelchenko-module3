package quest.model;

import org.junit.jupiter.api.Test;

import quest.unit.ProgressManager;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProgressManagerTest {

    @Test
    public void testSaveAndLoadProgress() {
        PlayerProgress progress = new PlayerProgress("John", 2, 3);

        ProgressManager.saveProgress(progress);

        Optional<PlayerProgress> loadedProgressOptional = ProgressManager.loadProgress();

        assertTrue(loadedProgressOptional.isPresent(), "Progress should be loaded");

        PlayerProgress loadedProgress = loadedProgressOptional.get();

        assertEquals("John", loadedProgress.getPlayerName());
        assertEquals(2, loadedProgress.getCurrentStep());
        assertEquals(3, loadedProgress.getGamesPlayed());

        new File(Paths.get("player_progress.txt").toUri()).delete();
    }

    @Test
    public void testLoadProgressWhenFileDoesNotExist() {
        new File(Paths.get("player_progress.txt").toUri()).delete();

        Optional<PlayerProgress> loadedProgressOptional = ProgressManager.loadProgress();

        assertTrue(loadedProgressOptional.isEmpty(), "Progress should not be loaded when file does not exist");
    }
}