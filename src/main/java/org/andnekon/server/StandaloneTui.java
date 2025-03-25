package org.andnekon.server;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.andnekon.controller.GameController;
import org.andnekon.controller.GameControllerFactory;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class StandaloneTui {

    public static void main(String[] args) throws IOException {
        Logger root = (Logger) LoggerFactory.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
        root.setLevel(Level.ERROR);
        GameController controller =
                GameControllerFactory.createController(true, System.in, System.out);
        controller.run();
    }
}
