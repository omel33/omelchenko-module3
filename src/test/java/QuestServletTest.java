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
import quest.unit.GameStatistics;
import quest.unit.ProgressManager;
import quest.unit.SessionUtil;

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
        PlayerProgress progress = new PlayerProgress("TestPlayer", 2, 3);

        try (var mockedProgressManager = mockStatic(ProgressManager.class);
             var mockedSessionUtil = mockStatic(SessionUtil.class)) {

            mockedProgressManager.when(ProgressManager::loadProgress).thenReturn(Optional.of(progress));
            mockedSessionUtil.when(() -> SessionUtil.getGamesPlayed(session)).thenReturn(3);
            mockedSessionUtil.when(() -> SessionUtil.getPlayerName(session)).thenReturn("TestPlayer");

            when(gameStatistics.getGamesPlayed()).thenReturn(10);

            servlet.doGet(request, response);

            mockedSessionUtil.verify(() -> SessionUtil.storePlayerName(session, "TestPlayer"));
            mockedSessionUtil.verify(() -> SessionUtil.setGamesPlayed(session, 3));
            verify(request).setAttribute(eq("totalGamesPlayed"), eq(10));
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPostNewGame() throws ServletException, IOException {
        when(request.getParameter("playerName")).thenReturn("NewPlayer");
        when(session.getAttribute("quest")).thenReturn(null);

        try (var mockedSessionUtil = mockStatic(SessionUtil.class)) {
            servlet.doPost(request, response);

            // Перевіряємо, що створюється новий квест і зберігається в сесії
            mockedSessionUtil.verify(() -> SessionUtil.storePlayerName(session, "NewPlayer"));
            mockedSessionUtil.verify(() -> SessionUtil.incrementGamesPlayed(session));
            verify(gameStatistics).incrementGamesPlayed();
            verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPostExistingGame() throws ServletException, IOException {
        Quest existingQuest = new Quest();
        when(session.getAttribute("quest")).thenReturn(existingQuest);
        when(request.getParameter("answer")).thenReturn("Go North");

        try (var mockedSessionUtil = mockStatic(SessionUtil.class)) {
            mockedSessionUtil.when(() -> SessionUtil.getQuestFromSession(session)).thenReturn(existingQuest);

            servlet.doPost(request, response);

            // Перевіряємо, що відповіді оновлюються
            verify(request).setAttribute(eq("quest"), eq(existingQuest));
            verify(request).setAttribute(eq("answers"), anyList());
            verify(requestDispatcher).forward(request, response);
        }
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
        when(quest.getCurrentResult()).thenReturn("Перемога!");

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("result"), eq("Перемога!"));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testSaveProgress() {
        when(session.getAttribute("playerName")).thenReturn("TestPlayer");
        when(session.getAttribute("gamesPlayed")).thenReturn(5);
        Quest quest = new Quest();
        quest.nextStep("Go North");

        try (var mockedSessionUtil = mockStatic(SessionUtil.class);
             var mockedProgressManager = mockStatic(ProgressManager.class)) {
            mockedSessionUtil.when(() -> SessionUtil.getPlayerName(session)).thenReturn("TestPlayer");
            mockedSessionUtil.when(() -> SessionUtil.getGamesPlayed(session)).thenReturn(5);

            servlet.saveProgress(request, quest);

            mockedProgressManager.verify(() -> ProgressManager.saveProgress(argThat(p ->
                    p.getPlayerName().equals("TestPlayer") &&
                            p.getCurrentStep() == 1 &&
                            p.getGamesPlayed() == 5
            )));
        }
    }
}
