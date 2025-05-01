package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/** Represents a rectangle, that hold 2 terminal positions */
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

    public void setTopLeft(TerminalPosition topLeft) {
        this.topLeft = topLeft;
    }

    public TerminalPosition getBottomRight() {
        return bottomRight;
    }

    public void setBottomRight(TerminalPosition bottomRight) {
        this.bottomRight = bottomRight;
    }

    public int leftCol() {
        return topLeft.getColumn();
    }

    public int topRow() {
        return topLeft.getRow();
    }

    public int rightCol() {
        return bottomRight.getColumn();
    }

    public int botRow() {
        return bottomRight.getRow();
    }

    @Override
    public String toString() {
        return "TerminalRegion [topLeft=" + topLeft + ", bottomRight=" + bottomRight + "]";
    }
}
