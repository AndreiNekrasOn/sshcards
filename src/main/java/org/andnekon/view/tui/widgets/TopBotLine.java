package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.view.tui.TerminalRegion;

/** TopBotLine decorator */
public class TopBotLine implements Widget {

    private TerminalRegion region;

    private Widget widget;

    private GameSession session;

    private String help;

    public TopBotLine(Widget widget, TerminalRegion whole, GameSession session, String help) {
        this.widget = widget;
        this.help = help;
        this.session = session;
        this.region = whole;
    }

    @Override
    public void draw(Screen screen) {
        widget.draw(screen);
        Widget shortHelp =
                new SingleLine(help, new TerminalPosition(region.leftCol(), region.botRow() + 1));
        shortHelp = new Border(shortHelp);
        shortHelp.draw(screen);


        StringBuilder sb = new StringBuilder();
        sb.append("HP: ")
            .append(session.getPlayer().getHp())
            .append("("  + session.getPlayer().getMaxHp() + ") ")
            .append("#Cards: ")
            .append(session.getPlayer().getShotDeck().getTotal().size() +
                    session.getPlayer().getArmorDeck().getTotal().size());
        Widget statsHelp =
                new SingleLine(sb.toString(), new TerminalPosition(region.leftCol(), region.topRow() - 1));
        statsHelp = new Border(statsHelp);
        statsHelp.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }
}
