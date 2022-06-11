package ua.griddynamics.menu;

import ua.griddynamics.ProductType;

public enum ShowListMenu {

    FOOD(1, "Food", ProductType.FOOD),
    CLOTHES(2, "Clothes", ProductType.CLOTHES),
    ENTERTAINMENT(3, "Entertainment", ProductType.ENTERTAINMENT),
    OTHER(4, "Other", ProductType.OTHER),
    ALL(5, "All", ProductType.UNDEFINED),
    BACK(6, "Back", ProductType.UNDEFINED),
    UNDEFINED(-1, "", ProductType.UNDEFINED);

    private final int command;
    private final String description;
    private final ProductType productType;

    ShowListMenu(int command, String description, ProductType productType) {
        this.command = command;
        this.description = description;
        this.productType = productType;
    }

    public int getCommand() {
        return command;
    }

    public String getDescription() {
        return description;
    }

    public static ShowListMenu getInstance(int command) {
        for (ShowListMenu v : values()) {
            if (v.getCommand() == command) {
                return v;
            }
        }
        return UNDEFINED;
    }

    public static String getMenuStr() {
        StringBuilder str = new StringBuilder("\nChoose the type of purchase: ");
        for (ShowListMenu item : ShowListMenu.values()) {
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

    public ProductType getProductType() {
        return productType;
    }
}
