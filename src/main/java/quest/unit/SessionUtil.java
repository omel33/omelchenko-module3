package quest.unit;

import quest.model.Quest;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final String QUEST_ATTRIBUTE = "quest";

    public static void storeQuestInSession(HttpSession session, Quest quest) {
        session.setAttribute(QUEST_ATTRIBUTE, quest);
    }

    public static Quest getQuestFromSession(HttpSession session) {
        return (Quest) session.getAttribute(QUEST_ATTRIBUTE);
    }
}