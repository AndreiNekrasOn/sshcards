package org.andnekon.game;

public class GameAction {
    public enum Type {
        // GLOBAL ACTIONS
        QUIT,
        HELP,
        PASS,

        // Main Menu, Navigation, Popup-choices
        NAVIGATION,

        // CONFIRM ACTIONS
        ACCEPT,
        REFUSE,

        // BATTLE ACTIONS
        BATTLE_CARD,
        BATTLE_END_TURN,
        BATTLE_HELP,
        BATTLE_SELECT,

        // QUIT ACTIONS
        QUIT_REFUSE,

        // DRAFT MODE
        DRAFT_NEXT,
        DRAFT_ADD,
        DRAFT_SKIP;
    }

    private int id;
    private String payload;

    private Type action;

    public GameAction(Type action, int id, String payload) {
        this.id = id;
        this.payload = payload;
        this.action = action;
    }

    public GameAction(Type action) {
        this(action, 0, null);
    }

    public GameAction(Type action, int id) {
        this(action, id, null);
    }

    public GameAction(Type action, String payload) {
        this(action, 0, payload);
    }

    public Type action() {
        return action;
    }

    public int id() {
        return id;
    }

    public String payload() {
        return payload;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(action.toString());
        if (payload != null) {
            builder.append(", with payload ").append(payload);
        }
        builder.append(", with value ").append(id);
        return builder.toString();
    }
}
