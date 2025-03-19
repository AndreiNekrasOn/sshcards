package org.andnekon.view.tui.widgets;

/**
 * Widget, that can be either active or not (like checkbox)
 */
public interface ActiveWidget extends Widget {

    boolean isActive();

    void setActive(boolean state);
}
