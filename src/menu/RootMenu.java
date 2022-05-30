package menu;

public enum RootMenu {
    ADD_INCOME(1, "Add income"),
    ADD_PURCHASE(2, "Add purchase"),
    SHOW_LIST_OF_PURCHASES(3, "Show list of purchases"),
    BALANCE(4, "Balance"),
    SAVE(5, "Save"),
    LOAD(6, "Load"),
    ANALYZE(7, "Analyze (Sort)"),
    EXIT(0, "Exit"),
    UNDEFINED(-1, "");

    private final int command;
    private final String description;

    RootMenu(int command, String description) {
        this.command = command;
        this.description = description;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static RootMenu getInstance(int command) {
        for (RootMenu v : values()) {
            if (v.getCommand() == command) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static String getMenuStr() {
        StringBuilder str = new StringBuilder("Choose your action:");
        for (RootMenu item : RootMenu.values()) {
            if (item.isUndefined()) {
                continue;
            }
            str.append(System.lineSeparator());
            str.append(item.getCommand()).append(") ").append(item.getDescription());
        }
        return str.toString();
    }

    public boolean isUndefined() {
        return this == UNDEFINED;
    }
}
