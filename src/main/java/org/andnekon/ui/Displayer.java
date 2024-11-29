package org.andnekon.ui;

public interface Displayer {

    void help(HelpType type);

    void warning(String message);

    void prompt(String message);

    void choice(Object... options);

    Displayer withSettings(int options);
}
