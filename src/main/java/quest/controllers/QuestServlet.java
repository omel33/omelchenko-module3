package quest.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import quest.model.Quest;
import quest.unit.SessionUtil;

import java.io.IOException;

@Slf4j
@WebServlet("/quest")
public class QuestServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String playerName = req.getParameter("playerName");
        if(playerName == null){
            SessionUtil.storePlayerName(req.getSession(), playerName);
            SessionUtil.incrementGamesPlayed(req.getSession());
        }
        String answer = req.getParameter("answer");
        Quest quest = SessionUtil.getQuestFromSession(req.getSession());

        if (quest == null) {
            quest = new Quest();
            SessionUtil.storeQuestInSession(req.getSession(), quest);
            log.info("New quest started for session: {}", req.getSession().getId());
        }

        quest.nextStep(answer);
        req.setAttribute("quest", quest);
        req.setAttribute("anwers",quest.getCurrentStep());

        if (quest.isFinished()) {
            log.info("Quest finished for session: {}", req.getSession().getId());
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/question.jsp").forward(req, resp);
        }
    }
}