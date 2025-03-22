package quest.unit;

import lombok.Getter;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Setter
@Getter
public class GameStatistics {
    private static final String STATS_FILE_PATH = "data/game_stats.txt";
    private int gamesPlayed;

    public GameStatistics(){
        ensureDataDirectoryExists();
        loadStats();
    }
    public void incrementGamesPlayed(){
        gamesPlayed++;
        saveStats();

    }
    private void ensureDataDirectoryExists() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            boolean created = dataDir.mkdirs();
            if(created){
                System.out.println("Data directory created");
            }else {
                System.out.println("Failed to create data directory");
            }

        }
    }
    private void loadStats(){
        try {
            if(Files.exists(Paths.get(STATS_FILE_PATH))){
                String content = new String(Files.readAllBytes
                        (Paths.get(STATS_FILE_PATH)));
                gamesPlayed = Integer.parseInt(content.trim());
            }else{
                gamesPlayed = 0;
            }

        }catch (IOException e){
            System.err.println("Failed to load statistics file"+e.getMessage());
            gamesPlayed = 0;
        }
    }
    private void saveStats() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_FILE_PATH))) {
            writer.write(String.valueOf(gamesPlayed));
            System.out.println("Game statistics saved");
        } catch (IOException e) {
            System.err.println("Failed to save game statistics: " + e.getMessage());
        }
    }
}
