package org.andnekon.view.tui.windows;

public interface TuiWindow {

    public void setup();

    public void prepare();

    public void show();

    public String read();
}
