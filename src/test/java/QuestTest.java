import org.junit.jupiter.api.Test;
import quest.model.Quest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class QuestTest {

    @Test
    public void testInitialState() {
        Quest quest = new Quest();

        // Перевірка початкового питання
        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());

        List<String> expectedAnswers1 = List.of("Go North", "Go South");
        assertEquals(expectedAnswers1, quest.getAnswers());

        assertFalse(quest.isFinished());

        assertNull(quest.getCurrentResult());
    }

    @Test
    public void testGoNorthPath() {
        Quest quest = new Quest();

        quest.nextStep("Go North");
        assertEquals("You are walking through the forest and you see a cave. Go inside?", quest.getCurrentQuestion());

        List<String> expectedAnswers2 = List.of("Yes", "No");
        assertEquals(expectedAnswers2, quest.getAnswers());

        quest.nextStep("Yes");
        assertEquals("You are in a cave. There are two tunnels in front of you. Where will you go?", quest.getCurrentQuestion());

        List<String> expectedAnswers3 = List.of("Left Tunnel", "Right Tunnel");
        assertEquals(expectedAnswers3, quest.getAnswers());

        quest.nextStep("Left Tunnel");

        assertTrue(quest.isFinished());

        assertEquals("You win!", quest.getCurrentResult());
    }

    @Test
    public void testGoSouthPath() {
        Quest quest = new Quest();

        quest.nextStep("Go South");
        assertEquals("You fill in the trap! Game Over!", quest.getCurrentQuestion());

        assertTrue(quest.isFinished());

        assertEquals("You lose!", quest.getCurrentResult());
    }

    @Test
    public void testMonsterPath() {
        Quest quest = new Quest();

        quest.nextStep("Go North");

        quest.nextStep("Yes");

        quest.nextStep("Right Tunnel");
        assertEquals("You found a monster! What will you do?", quest.getCurrentQuestion());

        List<String> expectedAnswers5 = List.of("Attack", "Run");
        assertEquals(expectedAnswers5, quest.getAnswers());

        quest.nextStep("Attack");
        assertEquals("You defeat the monster! Continue?", quest.getCurrentQuestion());

        List<String> expectedAnswers6 = List.of("Yes", "No");
        assertEquals(expectedAnswers6, quest.getAnswers());

        quest.nextStep("Yes");

        assertTrue(quest.isFinished());

        assertEquals("You win!", quest.getCurrentResult());
    }

    @Test
    public void testInvalidAnswer() {
        Quest quest = new Quest();

        quest.nextStep("Invalid Answer");

        assertFalse(quest.isFinished());

        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());
    }
}