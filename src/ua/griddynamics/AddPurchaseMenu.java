package ua.griddynamics;

public enum AddPurchaseMenu {

    FOOD(1, "Food"),
    CLOTHES(2, "Clothes"),
    ENTERTAINMENT(3, "Entertainment"),
    OTHER(4, "Other"),
    BACK(5, "Back"),
    UNDEFINED(-1, "");

    private final int command;
    private final String description;

    AddPurchaseMenu(int command, String description) {
        this.command = command;
        this.description = description;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static AddPurchaseMenu getInstance(int command) {
        for (AddPurchaseMenu v : values()) {
            if (v.getCommand() == command) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static String getMenuStr() {
        StringBuilder str = new StringBuilder("\n Choose the type of purchase");
        for (AddPurchaseMenu item : AddPurchaseMenu.values()) {
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
