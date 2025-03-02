import org.junit.jupiter.api.Test;
import quest.model.Quest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuestTest {

    @Test
    public void testQuest() {
        Quest quest = new Quest();

        // Перевірка початкового питання
        assertEquals("You woke up in the forest. what will you do?", quest.getCurrentQuestion());

        // Перевірка варіантів відповідей для першого питання
        List<String> expectedAnswers1 = List.of("Go North", "Go South");
        assertEquals(expectedAnswers1, quest.getAnswers());

        // Перехід до наступного кроку (вибір "Go North")
        quest.nextStep("Go North");
        assertEquals("You are walking through the forest and you see a cave. Go inside?", quest.getCurrentQuestion());

        // Перевірка варіантів відповідей для другого питання
        List<String> expectedAnswers2 = List.of("Yes", "No");
        assertEquals(expectedAnswers2, quest.getAnswers());

        // Перехід до наступного кроку (вибір "Yes")
        quest.nextStep("Yes");
        assertEquals("You are in a cave. There are two tunnels in front of you. Where will you go?", quest.getCurrentQuestion());

        // Перевірка варіантів відповідей для третього питання
        List<String> expectedAnswers3 = List.of("Left Tunnel", "Right Tunnel");
        assertEquals(expectedAnswers3, quest.getAnswers());

        // Перехід до наступного кроку (вибір "Left Tunnel")
        quest.nextStep("Left Tunnel");

        // Перевірка, чи гра завершена
        assertTrue(quest.isFinished());
    }
}