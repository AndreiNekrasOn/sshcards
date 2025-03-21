package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * Represents a rectangle, that hold 2 terminal positions
 */
public class TerminalRegion {

    private TerminalPosition topLeft;
    private TerminalPosition bottomRight;

    public TerminalRegion(TerminalSize size) {
        this.topLeft = new TerminalPosition(0, 0);
        this.bottomRight = new TerminalPosition(size.getColumns(), size.getRows());
    }

    public TerminalRegion(int topLeftCol, int topLeftRow, int bottomRightCol, int bottomRightRow) {
        this.topLeft = new TerminalPosition(topLeftCol, topLeftRow);
        this.bottomRight = new TerminalPosition(bottomRightCol, bottomRightRow);
    }

    public TerminalPosition getTopLeft() {
        return topLeft;
    }

    public TerminalPosition getBottomRight() {
        return bottomRight;
    }

    public int topLeftCol() {
        return topLeft.getColumn();
    }

    public int topLeftRow() {
        return topLeft.getRow();
    }

    public int botRightCol() {
        return bottomRight.getColumn();
    }

    public int botRightRow() {
        return bottomRight.getRow();
    }

    // TODO: isOverlapping here?
}
