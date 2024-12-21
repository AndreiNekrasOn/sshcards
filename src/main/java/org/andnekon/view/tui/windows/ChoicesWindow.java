package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;

import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** ChoicesWindow */
public abstract class ChoicesWindow extends AbstractTuiWindow {

    private static final Logger logger = LoggerFactory.getLogger(ChoicesWindow.class);

    // For some reason ActionListBox wasn't highlighting properly. Instrad of
    // figuring it out I did a workaround with Panel of Labels
    protected Panel menu; // the reason we have this class

    protected String[] options;

    private int currentIndex = 0;

    public ChoicesWindow(StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    public void setup() {
        this.menu = new Panel();
    }

    @Override
    public void prepare() {
        menu.removeAllComponents();
        setMenuOptions();
        for (int i = 0; i < options.length; i++) {
            String name = String.format("%d. %s", i + 1, options[i]);
            menu.addComponent(new Label(name));
        }
        focusMenuElement();
    }

    private void focusMenuElement() {
        for (int i = 0; i < menu.getChildCount(); i++) {
            Label option = (Label) menu.getChildrenList().get(i);
            option.removeStyle(SGR.BOLD);
            if (i == currentIndex) {
                option.addStyle(SGR.BOLD);
            }
        }
    }

    /** Captures input for hjkl+wasd+arrows. */
    @Override
    protected void postRead() {
        if (buffer.size() != 1) {
            System.out.println("buffer size not zero?!");
            return;
        }
        KeyStroke key = buffer.get(0);
        if (KeyStrokeUtil.isRightMotion(key)) {
            setBufferToChoice();
            return;
        }
        if (KeyStrokeUtil.isUpMotion(key)) {
            currentIndex--;
            buffer.clear();
        } else if (KeyStrokeUtil.isDownMotion(key)) {
            currentIndex++;
            buffer.clear();
        }
        currentIndex = menu.getChildCount() == 0 ? 0 : currentIndex % menu.getChildCount();
        focusMenuElement();
    }

    protected abstract void setMenuOptions();

    private synchronized void setBufferToChoice() {
        buffer.clear();
        buffer.add(KeyStroke.fromString(String.valueOf(currentIndex + 1)));
    }
}
