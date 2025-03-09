package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private int currentStep = 0;
    private boolean finished = false;
    private Map<Integer, String> steps = new HashMap<>();
    private Map<Integer, List<String>> answers = new HashMap<>();
    private Map<Integer, String> results = new HashMap<>();
    private final Map<Integer, Map<String, Integer>> transitions = new HashMap<>();

    public Quest() {
        // Ініціалізація кроків, відповідей та переходів
        steps.put(0, "You woke up in the forest. what will you do?");
        answers.put(0, List.of("Go North", "Go South"));
        transitions.put(0, Map.of("Go North", 1, "Go South", 4));

        steps.put(1, "You are walking through the forest and you see a cave. Go inside?");
        answers.put(1, List.of("Yes", "No"));
        transitions.put(1, Map.of("Yes", 2, "No", 0));

        steps.put(2, "You are in a cave. There are two tunnels in front of you. Where will you go?");
        answers.put(2, List.of("Left Tunnel", "Right Tunnel"));
        transitions.put(2, Map.of("Left Tunnel", 3, "Right Tunnel", 5));

        steps.put(3, "You found a treasure! Congratulations!");
        results.put(3, "You win!");

        steps.put(4, "You fell into a trap! Game Over!");
        results.put(4, "You lose!");

        steps.put(5, "You found a monster! What will you do?");
        answers.put(5, List.of("Attack", "Run"));
        transitions.put(5, Map.of("Attack", 6, "Run", 4));

        steps.put(6, "You defeated the monster! Continue?");
        answers.put(6, List.of("Yes", "No"));
        transitions.put(6, Map.of("Yes", 3, "No", 4));
    }

    public void nextStep(String answer) {
        transitions.getOrDefault(currentStep, Map.of())
                .forEach((key, nextStep) -> {
                    if (key.equals(answer)) {
                        currentStep = nextStep;
                        if (results.containsKey(currentStep)) {
                            finished = true;
                        }
                    }
                });
    }

    public String getCurrentQuestion() {
        return steps.get(currentStep);
    }

    public boolean isFinished() {
        return finished;
    }

    public List<String> getAnswers() {
        return answers.get(currentStep);
    }

    public String getCurrentResult() {
        return results.get(currentStep);
    }
}