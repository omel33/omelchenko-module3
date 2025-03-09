package quest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import quest.model.PlayerProgress;
import quest.model.Quest;
import quest.unit.ProgressManager;
import quest.unit.SessionUtil;

import java.io.IOException;
import java.util.Optional;

import static quest.unit.ProgressManager.saveProgress;

@Slf4j
@WebServlet("/quest")
public class QuestServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Завантажуємо прогрес гравця
        ProgressManager.loadProgress().ifPresent(progress -> {
            // Якщо прогрес існує, зберігаємо дані в сесії
            SessionUtil.storePlayerName(req.getSession(), progress.getPlayerName());
            SessionUtil.setGamesPlayed(req.getSession(), progress.getGamesPlayed());
        });

        // Перенаправляємо на сторінку welcome.jsp
        req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Optional.ofNullable(req.getParameter("playerName"))
                .ifPresent(name -> {
                    SessionUtil.storePlayerName(req.getSession(), name);
                    SessionUtil.incrementGamesPlayed(req.getSession());
                });

        Quest quest = Optional.ofNullable(SessionUtil.getQuestFromSession(req.getSession()))
                .orElseGet(() -> {
                    Quest newQuest = new Quest();
                    SessionUtil.storeQuestInSession(req.getSession(), newQuest);
                    log.info("New quest started for session: {}", req.getSession().getId());
                    return newQuest;
                });

        quest.nextStep(req.getParameter("answer"));
        req.setAttribute("quest", quest);
        req.setAttribute("answers", quest.getAnswers());

        if (quest.isFinished()) {
            req.setAttribute("result", quest.getCurrentResult());
            log.info("Quest finished for session: {}", req.getSession().getId());
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/question.jsp").forward(req, resp);
        }

        saveProgress(req, quest);
    }
    private void saveProgress(HttpServletRequest req, Quest quest) {
        PlayerProgress progress = new PlayerProgress(
                SessionUtil.getPlayerName(req.getSession()),
                quest.getCurrentStep(),
                SessionUtil.getGamesPlayed(req.getSession())
        );
        ProgressManager.saveProgress(progress);
    }
}