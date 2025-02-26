package quest.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class Quest {
    private int currentStep = 0;
    private boolean finished = false;
    private Map<Integer, String> steps = new HashMap<>();
    private Map<Integer, String[]> answers = new HashMap<>();

    public Quest() {
        steps.put(0, "You woke up in the forest. what will you do?");
        answers.put(0, new String[]{"Go North", "Go South"});

        steps.put(1, "You are walking through the forest and you see a cave. Go inside?");
        answers.put(1, new String[]{"Yes", "No"});

        steps.put(2, "You are in a cave. There are two tunnels in front of you. Where will you go?");
        answers.put(2, new String[]{"Left Tunnel", "Right Tunnel"});

        steps.put(3, "You found a treasure! Congratulations!");
    }

    public void nextStep(String answer) {
        if (currentStep < steps.size()) {
            currentStep++;
        } else {
            finished = true;
        }
    }

}
