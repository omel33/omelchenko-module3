package quest.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import quest.model.PlayerProgress;
import quest.model.Quest;
import quest.unit.*;

import java.io.IOException;
import java.util.Optional;

import static org.mockito.Mockito.*;


class QuestServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private ProgressManager progressManager;
    @Mock
    private GameStatistics gameStatistics;

    private QuestServlet servlet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new QuestServlet();

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void testDoGetWithSavedProgress() throws ServletException, IOException {
        // Підготовка тестових даних
        PlayerProgress progress = new PlayerProgress("TestPlayer", 2, 3);
        when(progressManager.loadProgress()).thenReturn(Optional.of(progress));
        when(gameStatistics.getGamesPlayed()).thenReturn(10);

        // Виклик методу
        servlet.doGet(request, response);

        // Перевірки
        verify(session).setAttribute(eq("playerName"), eq("TestPlayer"));
        verify(session).setAttribute(eq("gamesPlayed"), eq(3));
        verify(request).setAttribute(eq("totalGamesPlayed"), eq(10));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostNewGame() throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn("NewPlayer");
        when(session.getAttribute("quest")).thenReturn(null);

        servlet.doPost(request, response);

        verify(session).setAttribute(eq("playerName"), eq("NewPlayer"));
        verify(session).setAttribute(eq("gamesPlayed"), anyInt());
        verify(gameStatistics).incrementGamesPlayed();
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostExistingGame() throws ServletException, IOException {
        Quest existingQuest = new Quest();
        when(session.getAttribute("quest")).thenReturn(existingQuest);
        when(request.getParameter("answer")).thenReturn("Go North");

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("quest"), any(Quest.class));
        verify(request).setAttribute(eq("answers"), anyList());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostPlayAgain() throws ServletException, IOException {
        when(request.getParameter("playAgain")).thenReturn("playAgain");

        servlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDoPostFinishedQuest() throws ServletException, IOException {
        Quest quest = mock(Quest.class);
        when(quest.isFinished()).thenReturn(true);
        when(session.getAttribute("quest")).thenReturn(quest);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("result"), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testSaveProgress() {
        when(session.getAttribute("playerName")).thenReturn("TestPlayer");
        when(session.getAttribute("gamesPlayed")).thenReturn(5);
        Quest quest = new Quest();
        quest.nextStep("Go North");

        servlet.saveProgress(request, quest);

        verify(progressManager).saveProgress(argThat(p ->
                p.getPlayerName().equals("TestPlayer") &&
                        p.getCurrentStep() == 1 &&
                        p.getGamesPlayed() == 5
        ));
    }
}