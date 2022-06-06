package ua.griddynamics.menu;

public enum SortMenu {

    SORT_ALL(1, "Sort all purchases"),
    SORT_TYPES(2, "Sort by type"),
    SORT_IN_TYPE(3, "Sort certain type"),
    BACK(4, "Back"),
    UNDEFINED(-1, "");

    private final int command;
    private final String description;

    SortMenu(int command, String description) {
        this.command = command;
        this.description = description;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static SortMenu getInstance(int command) {
        for (SortMenu v : values()) {
            if (v.getCommand() == command) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static String getMenuStr() {
        StringBuilder str = new StringBuilder("\nHow do you want to sort?");
        for (SortMenu item : SortMenu.values()) {
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
