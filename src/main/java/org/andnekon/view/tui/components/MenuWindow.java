package org.andnekon.view.tui.components;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;

import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.util.ArrayList;
import java.util.List;

public class MenuWindow extends AbstractTuiWindow {

    private ActionListBox menu;

    public MenuWindow(final StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    public void prepare() {}

    @Override
    public void setup() {
        final Panel content = new Panel();
        menu = new ActionListBox();
        // TODO: resolve text -> action in GameLogic, fire events
        // to TuiView, which will pass them to the controller through `read`?
        // Or maybe controller itself listens to the events, TuiView just passes them through.
        menu.addItem("1. Start", () -> transformBufferToChoice(1));
        menu.addItem("2. Continue", () -> transformBufferToChoice(2));
        menu.addItem("3. About", () -> transformBufferToChoice(3));
        menu.addItem("4. Quit", () -> transformBufferToChoice(4));
        content.addComponent(menu);
        this.setComponent(content);
    }

    @Override
    protected void postRead() {
        int currentIndex = menu.getSelectedIndex();
        if (buffer.size() == 1 && KeyStrokeUtil.isRightMotion(buffer.get(0))) {
            menu.getItemAt(currentIndex).run();
            return;
        }
        for (var key : buffer) {
            if (KeyStrokeUtil.isUpMotion(key) || KeyStrokeUtil.isLeftMotion(key)) {
                currentIndex--;
            } else if (KeyStrokeUtil.isDownMotion(key) || KeyStrokeUtil.isRightMotion(key)) {
                currentIndex++;
            }
        }
        menu.setSelectedIndex(currentIndex % menu.getItemCount());
    }

    private synchronized void transformBufferToChoice(int choice) {
        buffer = new ArrayList<>(List.of(KeyStroke.fromString(String.valueOf(choice))));
    }
}
