package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.MenuComponent;
import org.andnekon.view.tui.windows.PopupWindow;

public class RewardWindow extends PopupWindow {

    private GameSession session;
    private MenuComponent menu;

    public RewardWindow(StatefulMultiWindowTextGui gui, GameSession session) {
        super(gui);
        this.session = session;
    }

    @Override
    public void setup() {
        Panel content = new Panel();
        Label congrats = new Label("Good job, choose a card to add");
        content.addComponent(congrats);
        menu = new MenuComponent();
        content.addComponent(menu);
        this.setComponent(content);
    }

    @Override
    public void prepare() {
        menu.prepare(
                session.getRewardManager().getRewardOptions().stream()
                        .map(Card::toString)
                        .toArray(String[]::new));
    }

    @Override
    public void postRead() {
        menu.processInput(gui.getReader().getBuffer());
    }
}
