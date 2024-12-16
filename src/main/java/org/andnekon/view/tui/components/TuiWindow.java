package org.andnekon.view.tui.components;

public interface TuiWindow {

    public void setup();

    public void prepare();

    public void show();

    public String read();
}
