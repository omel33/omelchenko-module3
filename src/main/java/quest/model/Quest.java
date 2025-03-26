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

        steps.put(0, "You woke up in the forest. What will you do?");
        answers.put(0, List.of("Go North", "Go South", "Climb a tree"));
        transitions.put(0, Map.of(
                "Go North", 1,
                "Go South", 2,
                "Climb a tree", 3
        ));

        steps.put(1, "You are walking through the forest and you see a cave. Go inside?");
        answers.put(1, List.of("Yes", "No", "Look around"));
        transitions.put(1, Map.of(
                "Yes", 4,
                "No", 0,
                "Look around", 7
        ));

        steps.put(2, "You see a river. What will you do?");
        answers.put(2, List.of("Swim across", "Follow the river", "Go back"));
        transitions.put(2, Map.of(
                "Swim across", 7,
                "Follow the river", 8,
                "Go back", 0
        ));

        steps.put(3, "You climbed a tree and see a village in the distance. What will you do?");
        answers.put(3, List.of("Go to the village", "Stay in the tree", "Climb down"));
        transitions.put(3, Map.of(
                "Go to the village", 9,
                "Stay in the tree", 10,
                "Climb down", 0
        ));

        steps.put(4, "You are in a cave. There are two tunnels in front of you. Where will you go?");
        answers.put(4, List.of("Left Tunnel", "Right Tunnel", "Go back"));
        transitions.put(4, Map.of(
                "Left Tunnel", 5,
                "Right Tunnel", 6,
                "Go back", 1
        ));

        steps.put(5, "You found a treasure chest! What will you do?");
        answers.put(5, List.of("Open it", "Leave it", "Take it with you"));
        transitions.put(5, Map.of(
                "Open it", 9,
                "Leave it", 10,
                "Take it with you", 11
        ));

        steps.put(6, "You found a sleeping dragon! What will you do?");
        answers.put(6, List.of("Attack", "Sneak past", "Run away"));
        transitions.put(6, Map.of(
                "Attack", 11,
                "Sneak past", 9,
                "Run away", 10
        ));

        steps.put(7, "You swam across the river and found a hidden path. What will you do?");
        answers.put(7, List.of("Follow the path", "Rest", "Go back"));
        transitions.put(7, Map.of(
                "Follow the path", 9,
                "Rest", 10,
                "Go back", 2
        ));

        steps.put(8, "You followed the river and found a bridge. What will you do?");
        answers.put(8, List.of("Cross the bridge", "Jump into the river", "Go back"));
        transitions.put(8, Map.of(
                "Cross the bridge", 9,
                "Jump into the river", 10,
                "Go back", 2
        ));

        steps.put(9, "You found a treasure! Congratulations!");
        results.put(9, "You win!");


        steps.put(10, "You fell into a trap! Game Over!");
        results.put(10, "You lose!");

        steps.put(11, "You defeated the dragon and found its treasure! Congratulations!");
        results.put(11, "You win!");

        steps.put(12, "The dragon woke up and ate you! Game Over!");
        results.put(12, "You lose!");
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