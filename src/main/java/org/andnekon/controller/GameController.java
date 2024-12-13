package org.andnekon.controller;

// TODO: controller should be responsible for translating input into
// sensible action? GameLogic - observer
public interface GameController {

    public void run(String line);

    public void run();
}
