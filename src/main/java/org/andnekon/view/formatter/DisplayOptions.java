package org.andnekon.view.formatter;

public enum DisplayOptions {
    MENU(1),
    UNNUMBERED(2),
    COLORED(4);

    private int id;

    DisplayOptions(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

}
