package quest.unit;

import quest.model.Quest;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final String QUEST_ATTRIBUTE = "quest";
    private static final String PLAYER_NAME_ATTRIBUTE = "playerName";
    private static final String GAMES_PLAYED_ATTRIBUTE = "gamesPlayed";

    public static void storeQuestInSession(HttpSession session, Quest quest) {
        session.setAttribute(QUEST_ATTRIBUTE, quest);
    }

    public static Quest getQuestFromSession(HttpSession session) {
        return (Quest) session.getAttribute(QUEST_ATTRIBUTE);
    }

    public static void storePlayerName(HttpSession session, String playerName) {
        session.setAttribute(PLAYER_NAME_ATTRIBUTE, playerName);
    }

    public static String getPlayerName(HttpSession session) {
        return (String) session.getAttribute(PLAYER_NAME_ATTRIBUTE);
    }

    public static void incrementGamesPlayed(HttpSession session) {
        Integer gamesPlayed = (Integer) session.getAttribute(GAMES_PLAYED_ATTRIBUTE);
        if (gamesPlayed == null) {
            gamesPlayed = 0;
        }
        session.setAttribute(GAMES_PLAYED_ATTRIBUTE, gamesPlayed + 1);
    }
    public static int getGamesPlayed(HttpSession session) {
        return (Integer) session.getAttribute(GAMES_PLAYED_ATTRIBUTE);
    }

}
