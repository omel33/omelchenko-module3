package quest.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import quest.model.PlayerProgress;
import quest.model.Quest;
import quest.unit.GameStatistics;
import quest.unit.ProgressManager;
import quest.unit.SessionUtil;

import java.io.IOException;
import java.util.Optional;



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

        Mockito.when(request.getSession()).thenReturn(session);
        Mockito.when(request.getRequestDispatcher(ArgumentMatchers.anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void testDoGetWithSavedProgress() throws ServletException, IOException {
        PlayerProgress progress = new PlayerProgress("TestPlayer", 2, 3);

        try (var mockedProgressManager = Mockito.mockStatic(ProgressManager.class);
             var mockedSessionUtil = Mockito.mockStatic(SessionUtil.class)) {

            mockedProgressManager.when(ProgressManager::loadProgress).thenReturn(Optional.of(progress));
            mockedSessionUtil.when(() -> SessionUtil.getGamesPlayed(session)).thenReturn(3);
            mockedSessionUtil.when(() -> SessionUtil.getPlayerName(session)).thenReturn("TestPlayer");

            Mockito.when(gameStatistics.getGamesPlayed()).thenReturn(10);

            servlet.doGet(request, response);

            mockedSessionUtil.verify(() -> SessionUtil.storePlayerName(session, "TestPlayer"));
            mockedSessionUtil.verify(() -> SessionUtil.setGamesPlayed(session, 3));
            Mockito.verify(request).setAttribute(ArgumentMatchers.eq("totalGamesPlayed"), (ArgumentMatchers.anyInt()));
            Mockito.verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPostNewGame() throws ServletException, IOException {
        Mockito.when(request.getParameter("playerName")).thenReturn("NewPlayer");
        Mockito.when(session.getAttribute("quest")).thenReturn(null);

        try (var mockedSessionUtil = Mockito.mockStatic(SessionUtil.class)) {
            servlet.doPost(request, response);

            // Перевіряємо, що створюється новий квест і зберігається в сесії
            mockedSessionUtil.verify(() -> SessionUtil.storePlayerName(session, "NewPlayer"));
            mockedSessionUtil.verify(() -> SessionUtil.incrementGamesPlayed(session));
            Mockito.verify(requestDispatcher).forward(request, response);
            Mockito.verifyNoMoreInteractions(requestDispatcher);
        }
    }

    @Test
    void testDoPostExistingGame() throws ServletException, IOException {
        Quest existingQuest = new Quest();
        Mockito.when(session.getAttribute("quest")).thenReturn(existingQuest);
        Mockito.when(request.getParameter("answer")).thenReturn("Go North");

        try (var mockedSessionUtil = Mockito.mockStatic(SessionUtil.class)) {
            mockedSessionUtil.when(() -> SessionUtil.getQuestFromSession(session)).thenReturn(existingQuest);

            servlet.doPost(request, response);

            // Перевіряємо, що відповіді оновлюються
            Mockito.verify(request).setAttribute(ArgumentMatchers.eq("quest"), ArgumentMatchers.eq(existingQuest));
            Mockito.verify(request).setAttribute(ArgumentMatchers.eq("answers"), ArgumentMatchers.anyList());
            Mockito.verify(requestDispatcher).forward(request, response);
        }
    }

    @Test
    void testDoPostPlayAgain() throws ServletException, IOException {
        Mockito.when(request.getParameter("playAgain")).thenReturn("playAgain");

        servlet.doPost(request, response);

        Mockito.verify(session).invalidate();
        Mockito.verify(response).sendRedirect(ArgumentMatchers.anyString());
    }

    @Test
    void testDoPostFinishedQuest() throws ServletException, IOException {
        Quest quest = Mockito.mock(Quest.class);
        Mockito.when(quest.isFinished()).thenReturn(true);
        Mockito.when(session.getAttribute("gamesPlayed")).thenReturn(5);
        Mockito.when(session.getAttribute("quest")).thenReturn(quest);
        Mockito.when(quest.getCurrentResult()).thenReturn("Перемога!");

        servlet.doPost(request, response);

        Mockito.verify(request).setAttribute(ArgumentMatchers.eq("result"), ArgumentMatchers.eq("Перемога!"));
        Mockito.verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testSaveProgress() {
        Mockito.when(session.getAttribute("playerName")).thenReturn("TestPlayer");
        Mockito.when(session.getAttribute("gamesPlayed")).thenReturn(5);
        Quest quest = new Quest();
        quest.nextStep("Go North");

        try (var mockedSessionUtil = Mockito.mockStatic(SessionUtil.class);
             var mockedProgressManager = Mockito.mockStatic(ProgressManager.class)) {
            mockedSessionUtil.when(() -> SessionUtil.getPlayerName(session)).thenReturn("TestPlayer");
            mockedSessionUtil.when(() -> SessionUtil.getGamesPlayed(session)).thenReturn(5);

            servlet.saveProgress(request, quest);

            mockedProgressManager.verify(() -> ProgressManager.saveProgress(ArgumentMatchers.argThat(p ->
                    p.getPlayerName().equals("TestPlayer") &&
                            p.getCurrentStep() == 1 &&
                            p.getGamesPlayed() == 5
            )));
        }
    }
}
