package org.andnekon.view.tui.widgets.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.utils.StringUtil;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;

/** PlayerPositionRow */
public class PlayerPositionRow implements Widget {

    private TerminalRegion region;

    private Widget padding;

    private Widget playerArt;

    /**
     * @param region region of the widget ! IMPORTANT ! GETS UPDATED to fit
     */
    public PlayerPositionRow(
            AsciiReaderService arService, TerminalRegion region, TerminalPosition artTopLeft) {
        this.region = region;
        String art = StringUtil.wrap("[ERROR LOADING IMAGE]", 12);
        try {
            art = arService.readFile("tui/ships/Default");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // verify here that there's no overlap?
        playerArt = new MultiLine(artTopLeft.getColumn(), artTopLeft.getRow(), art);
        // +2 adjusts for border here
        region.setBottomRight(
                new TerminalPosition(
                        region.leftCol() + (EnemyCard.WIDTH + 2) * 3,
                        playerArt.getRegion().botRow()));
    }

    @Override
    public void draw(Screen screen) {
        playerArt.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }
}
