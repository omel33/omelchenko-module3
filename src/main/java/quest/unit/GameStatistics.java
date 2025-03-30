    package quest.unit;

    import lombok.Getter;
    import lombok.Setter;

    import java.io.BufferedWriter;
    import java.io.File;
    import java.io.FileWriter;
    import java.io.IOException;
    import java.nio.file.Files;
    import java.nio.file.Path;
    import java.nio.file.Paths;


    @Setter
    @Getter
    public class GameStatistics {
        private static final Path STATS_FILE_PATH =  Paths.get(System.getProperty("user.dir"), "data", "game_stats.txt");
        private int gamesPlayed;

        public GameStatistics() {
            ensureDataDirectoryExists();
            loadStats();
        }

        public void incrementGamesPlayed() {
            gamesPlayed++;
            saveStats();

        }

        void ensureDataDirectoryExists() {
            File dataDir = new File("data");
            System.out.println(dataDir.getAbsolutePath());
            if (!dataDir.exists()) {
                boolean created = dataDir.mkdirs();
                if (created) {
                    System.out.println("Data directory created");
                } else {
                    System.out.println("Failed to create data directory");
                }

            } else {
                System.out.println("Data directory already exists: " + dataDir.getAbsolutePath());
            }
        }
            public void loadStats() {
                try {
                    if (Files.exists(STATS_FILE_PATH)) {
                        String content = Files.readString(STATS_FILE_PATH).trim();
                        gamesPlayed = Integer.parseInt(content.trim());
                    } else {
                        gamesPlayed = 0;
                    }

                } catch (IOException e) {
                    System.err.println("Failed to load statistics file" + e.getMessage());
                    gamesPlayed = 0;
                }
            }
            void saveStats() {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(STATS_FILE_PATH.toFile()))) {
                    writer.write(String.valueOf(gamesPlayed));
                    System.out.println("Game statistics saved");
                } catch (IOException e) {
                    System.err.println("Failed to save game statistics: " + e.getMessage());
                }
            }
        }
