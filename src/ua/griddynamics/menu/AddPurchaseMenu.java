package ua.griddynamics.menu;

import ua.griddynamics.ProductType;

public enum AddPurchaseMenu {

    FOOD(1, "Food", ProductType.FOOD),
    CLOTHES(2, "Clothes", ProductType.CLOTHES),
    ENTERTAINMENT(3, "Entertainment", ProductType.ENTERTAINMENT),
    OTHER(4, "Other", ProductType.OTHER),
    BACK(5, "Back", ProductType.UNDEFINED),
    UNDEFINED(-1, "", ProductType.UNDEFINED);

    private final int command;
    private final String description;
    private final ProductType productType;

    AddPurchaseMenu(int command, String description, ProductType productType) {
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

    public ProductType getProductType() {
        return productType;
    }
}
