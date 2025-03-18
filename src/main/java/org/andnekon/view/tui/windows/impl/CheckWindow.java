package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.view.tui.windows.PopupWindow;

import java.util.List;

public class CheckWindow extends PopupWindow {

    private GameSession session;

    private Label shotCards;
    private Label armorCards;

    public CheckWindow(GameSession session) {
        super();
        // overrides size from PopupWindow
        // TerminalSize size = gui.getScreen().getTerminalSize();
        // setFixedSize(new TerminalSize(size.getColumns() / 3, size.getRows()));
        this.session = session;
    }

    @Override
    public void prepare() {
        shotCards.setText(deckToText(session.getPlayer().getShotDeck().getTotal()));
        armorCards.setText(deckToText(session.getPlayer().getArmorDeck().getTotal()));
    }

    @Override
    public void setup() {
        Panel content = new Panel(new GridLayout(2));

        shotCards = new Label("");
        armorCards = new Label("");
        content.addComponent(shotCards);
        content.addComponent(armorCards);
        this.setComponent(content);
    }

    private String deckToText(List<Card> deck) {
        return deck.stream().map(Card::toString).reduce((a, b) -> a + "\n" + b).orElse("");
    }
}
