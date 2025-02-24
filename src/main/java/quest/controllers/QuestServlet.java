package quest.controllers;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import quest.model.Quest;
import quest.unit.SessionUtil;

@WebServlet("/quest")
public class QuestServlet extends HttpServlet {
    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String answer = req.getParameter("answer");
        Quest quest = SessionUtil.getQuestFromSession(req.getSession());
        if (quest == null) {
            quest = new Quest();
            SessionUtil.storeQuestInSession(req.getSession(), quest);
        }
        quest.nextStep(answer);
        req.setAttribute("quest", quest);

        if(quest.isFinished()) {
            req.getRequestDispatcher("/result.jsp").forward(req, resp);
        }else {
            req.getRequestDispatcher("/question.jsp").forward(req, resp);
        }

    }

}
