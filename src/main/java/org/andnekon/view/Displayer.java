package org.andnekon.view;

public interface Displayer {

    void help(HelpType type);

    void message(String format, Object... args);

    void prompt(String message);

    void choice(Object... options);

    // TODO: good case for decorator pattern
    Displayer withSettings(int options);
}
