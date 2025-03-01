import org.junit.jupiter.api.Test;
import quest.model.Quest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestTest {

    @Test
    public void testQuest() {
        Quest quest = new Quest();

        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());

        String[] expectedAnswers1 = {"Go North", "Go South"};
        assertEquals(expectedAnswers1, quest.getAnswers().get(0));

        quest.nextStep("Go North");
        assertEquals("You are walking through the forest and you see a cave. Go inside?", quest.getCurrentQuestion());

        String[] expectedAnswers2 = {"Yes", "No"};
        assertEquals(expectedAnswers2, quest.getAnswers().get(1));
        quest.nextStep("Yes");
        assertEquals("You are in a cave. There are two tunnels in front of you. Where will you go?", quest.getCurrentQuestion());

        String[] expectedAnswers3 = {"Left Tunnel", "Right Tunnel"};
        assertEquals(expectedAnswers3, quest.getAnswers().get(2));

        quest.nextStep("Left Tunnel");

        assertTrue(quest.isFinished());
    }
}