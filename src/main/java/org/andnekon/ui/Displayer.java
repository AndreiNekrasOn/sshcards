package org.andnekon.ui;

public interface Displayer {

    void help(HelpType type);

    void message(String format, Object... args);

    void prompt(String message);

    void choice(Object... options);

    Displayer withSettings(int options);
}
