import org.junit.jupiter.api.Test;
import quest.model.Quest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

 class QuestTest {

    @Test
    void testInitialState() {
        Quest quest = new Quest();

        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());

        List<String> expectedAnswers1 = List.of("Go North", "Go South");
        assertEquals(expectedAnswers1, quest.getAnswers());

        assertFalse(quest.isFinished());

        assertNull(quest.getCurrentResult());
    }

    @Test
    void testGoNorthPath() {
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
     void testGoSouthPath() {
        Quest quest = new Quest();

        quest.nextStep("Go South");
        assertEquals("You fell into a trap! Game Over!", quest.getCurrentQuestion());

        assertTrue(quest.isFinished());

        assertEquals("You lose!", quest.getCurrentResult());
    }

    @Test
     void testMonsterPath() {
        Quest quest = new Quest();

        quest.nextStep("Go North");

        quest.nextStep("Yes");

        quest.nextStep("Right Tunnel");
        assertEquals("You found a monster! What will you do?", quest.getCurrentQuestion());

        List<String> expectedAnswers5 = List.of("Attack", "Run");
        assertEquals(expectedAnswers5, quest.getAnswers());

        quest.nextStep("Attack");
        assertEquals("You defeated the monster! Continue?", quest.getCurrentQuestion());

        List<String> expectedAnswers6 = List.of("Yes", "No");
        assertEquals(expectedAnswers6, quest.getAnswers());

        quest.nextStep("Yes");

        assertTrue(quest.isFinished());

        assertEquals("You win!", quest.getCurrentResult());
    }

    @Test
     void testInvalidAnswer() {
        Quest quest = new Quest();

        quest.nextStep("Invalid Answer");

        assertFalse(quest.isFinished());

        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());
    }
     @Test
     void testAllInitialOptions() {
         Quest quest = new Quest();

         quest.nextStep("Go North");
         assertEquals(1, quest.getCurrentStep());

         quest = new Quest();
         quest.nextStep("Go South");
         assertEquals(2, quest.getCurrentStep());

         quest = new Quest();
         quest.nextStep("Climb a tree");
         assertEquals(3, quest.getCurrentStep());
     }
     @Test
     void testGetAnswersForAllSteps() {
         Quest quest = new Quest();

         for (int i = 0; i < 13; i++) {
             quest.setCurrentStep(i);
             if (quest.getAnswers() != null) {
                 assertFalse(quest.getAnswers().isEmpty());
             }
         }
     }
}