package org.andnekon.view.tui;

import org.andnekon.view.Displayer;
import org.andnekon.view.HelpType;

import com.googlecode.lanterna.screen.Screen;

public class TuiDisplayer implements Displayer {

    private Screen screen;

    public TuiDisplayer(Screen screen) {
        this.screen = screen;
    }

    @Override
    public void help(HelpType type) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'help'");
    }

    @Override
    public void message(String format, Object... args) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'message'");
    }

    @Override
    public void prompt(String message) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'prompt'");
    }

    @Override
    public void choice(Object... options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'choice'");
    }

    @Override
    public Displayer withSettings(int options) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'withSettings'");
    }

}

