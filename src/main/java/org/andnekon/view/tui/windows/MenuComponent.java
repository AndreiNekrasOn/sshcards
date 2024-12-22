package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;

import org.andnekon.utils.KeyStrokeUtil;

import java.util.List;

/**
 * Custom implementation of {@code ActionListBox}, that also handles input key strokes in a specific
 * way
 */
public class MenuComponent extends Panel {

    private int currentIndex = 0;

    public void prepare(String... options) {
        removeAllComponents();
        for (int i = 0; i < options.length; i++) {
            String name = String.format("%d. %s", i + 1, options[i]);
            addComponent(new Label(name));
        }
        // update if number of children decreased
        currentIndex = Math.max(0, Math.min(currentIndex, getChildCount() - 1));
        focusMenuElement();
    }

    public void unfocus() {
        for (int i = 0; i < getChildCount(); i++) {
            Label option = (Label) getChildrenList().get(i);
            option.removeStyle(SGR.BOLD);
        }
    }

    private void focusMenuElement() {
        unfocus();
        if (getChildCount() > 0) {
            ((Label) getChildrenList().get(currentIndex)).addStyle(SGR.BOLD);
        }
    }

    /**
     * up/down motion change selected item, right motion updates buffer with the current selected
     * item index value
     *
     * @param buffer input key strokes to transform with menu navigation logic
     * @return {@code true} if buffer was transformed
     */
    public boolean processInput(List<KeyStroke> buffer) {
        if (buffer.size() != 1) {
            System.out.println("buffer size not zero?!");
            return false;
        }
        KeyStroke key = buffer.get(0); // clear only on successfull motion
        if (KeyStrokeUtil.isRightMotion(key)) {
            buffer.clear();
            buffer.add(KeyStroke.fromString(String.valueOf(currentIndex + 1)));
            return true;
        }
        if (isKeyDigit(key)) {
            // no need to reset buffer here
            return true;
        }
        if (KeyStrokeUtil.isUpMotion(key)) {
            currentIndex--;
            buffer.clear();
        } else if (KeyStrokeUtil.isDownMotion(key)) {
            currentIndex++;
            buffer.clear();
        }

        int n = getChildCount();
        if (n > 1) {
            currentIndex = ((currentIndex % n) + n) % n;
        } else {
            currentIndex = 0;
        }
        return buffer.isEmpty();
    }

    private boolean isKeyDigit(KeyStroke key) {
        for (int i = 0; i <= 9; i++) {
            if (KeyStrokeUtil.compareChar(key, (char) ('0' + i))) {
                return true;
            }
        }
        return false;
    }
}
