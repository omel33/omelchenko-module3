package quest.unit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quest.model.PlayerProgress;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

public class ProgressManager {
    private static String progressFilePath;
    private static final Logger log = LoggerFactory.getLogger(ProgressManager.class);

    static {
        try (InputStream input = ProgressManager.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            progressFilePath = prop.getProperty("progress.file.path");
        } catch (IOException e) {
            e.printStackTrace();
            progressFilePath = "player_progress.txt";
        }
    }

    public static void saveProgress(PlayerProgress progress) {
        try (ObjectOutputStream oos=new ObjectOutputStream(
                new FileOutputStream(progressFilePath)))   {
            oos.writeObject(progress);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static Optional<PlayerProgress> loadProgress() {
        return Optional.ofNullable(progressFilePath)
                .map(Paths::get) //
                .filter(Files::exists)
                .map(path -> {
                    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
                        return (PlayerProgress) ois.readObject();
                    } catch (IOException | ClassNotFoundException e) {
                        log.error("Failed to load progress", e);
                        return null;
                    }
                });
    }

}
