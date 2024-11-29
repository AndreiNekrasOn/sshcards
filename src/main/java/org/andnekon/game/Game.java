package org.andnekon.game;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.ui.console.ConsoleDisplayer;
import org.andnekon.ui.console.ConsoleReader;
import org.andnekon.ui.DisplayOptions;
import org.andnekon.ui.Displayer;
import org.andnekon.ui.HelpType;
import org.andnekon.ui.Messages;
import org.andnekon.ui.Reader;

/**
 * Turn-based game that takes user input, provides a Slay-the-spire like interaction
 * in the terminal.
 */
public class Game {
    public static void main( String[] args ) {

        Game game = new Game();
        game.run();
    }

    GameState gameState;

    GameSession session;

    Reader reader;

    Displayer ui;

    public Game() {
        this.reader = new ConsoleReader();
        this.session = new GameSession();
        this.ui = new ConsoleDisplayer(this.session);
        this.session.setPlayer(new Player());
    }


    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    /** Game description:
     * Menu: Start game, Continue, About, Quit
     * After start game: navigation->battle->reward->navigation untill game over
     * Navigation: choose one of 3 avaliable fights
     * Battle: is turn-based
     * during players turn, the player has several cards that cost energy;
     * computer displays its intent
     * during computer turn, computer makes the intended
     * when the computer HP reaches 0, the battle is over
     * if the player HP reaches 0, the game is over
     * Reward: choose one of 3 rewards, randomly chosen cards
     * When the game ends: show end screen with score
     * After the end screen, show the menu
     */
    private void run() {
        session.initializeDefaultDeck();
        gameState = GameState.MENU;
        while (gameState != GameState.QUIT) {
            switch (gameState) {
                case QUIT -> {}
                case MENU -> processMenu();
                case START_GAME -> setGameState(GameState.NAVIGATION);
                case NAVIGATION -> selectNavigation();
                case BATTLE -> processBattle();
                case REWARD -> setGameState(GameState.NAVIGATION);
                case GAME_OVER -> showEndScreen();
            }
        }
    }

    private void processBattle() {
        Player player = session.getPlayer();
        Enemy enemy = session.getEnemy();

        BattleState battleState = BattleState.START;
        session.initBattle();
        while (battleState != BattleState.COMPLETE) {
            switch (battleState) {
                case START -> {
                    battleState = BattleState.PLAYER_TURN_START;
                }
                case PLAYER_TURN_START -> {
                    session.initTurn();
                    ui.help(HelpType.BATTLE_INFO);
                    ui.choice(player.getBattleDeck().toArray());
                    battleState = BattleState.PLAYER_TURN;
                }
                case PLAYER_TURN -> {
                    String input = readInput("What do you do?");
                    battleState = processBattleInput(player, enemy, input);
                }
                case PLAYER_TURN_END -> {
                    battleState = checkBattleEnd(BattleState.ENEMY_TURN_START, player, enemy);
                }
                case ENEMY_TURN_START -> {
                    enemy.setDefense(0);
                    for (Intent intent : enemy.getCurrentIntents()) {
                        intent.execute();
                    }
                    battleState = BattleState.ENEMY_TURN_END;
                }
                case ENEMY_TURN_END -> {
                    enemy.clearIntents();
                    session.incTurn();
                    battleState = checkBattleEnd(BattleState.PLAYER_TURN_START, player, enemy);
                }
                case PLAYER_TURN_HELP -> {
                    battleState = BattleState.PLAYER_TURN;
                }
                case COMPLETE -> {}
            }
        }
    }

    private BattleState checkBattleEnd(BattleState nextState, Player player, Enemy enemy) {
        if (player.getHp() <= 0) {
            setGameState(GameState.GAME_OVER);
            return BattleState.COMPLETE;
        } else if (enemy.getHp() <= 0) {
            setGameState(GameState.REWARD);
            return BattleState.COMPLETE;
        }
        return nextState;
    }

    private BattleState processBattleInput(Player player, Enemy enemy, String input) {
        BattleState state = processBattleInputHelpers(player, enemy, input);
        if (state != BattleState.PLAYER_TURN) {
            return state;
        }
        // play card
        Card card;
        try {
            int i = Integer.parseInt(input);
            if (i < 1 || i > player.getBattleDeck().size()) {
                displayHelp();
                return BattleState.PLAYER_TURN_HELP;
            }
            card = player.getBattleDeck().get(i - 1);
        } catch (Exception e) {
            displayHelp();
            return BattleState.PLAYER_TURN_HELP;
        }
        if (card.getCost() > player.getEnergy()) {
            ui.warning("Not enough energy");
            return BattleState.PLAYER_TURN;
        }
        player.useCard(card, enemy);
        return BattleState.PLAYER_TURN;
    }

    private BattleState processBattleInputHelpers(Player player, Enemy enemy, String input) {
        if (input.length() == 0) {
            displayHelp();
            return BattleState.PLAYER_TURN_HELP;
        }
        return switch (input.charAt(0)) {
            case 'q' -> {
                setGameState(GameState.QUIT);
                yield BattleState.COMPLETE;
            }
            case 'e' -> BattleState.PLAYER_TURN_END;
            case 'h' -> {
                printBattleHelp();
                yield BattleState.PLAYER_TURN_HELP;
            }
            case 'c' -> {
                ui.choice(player.getBattleDeck().toArray());
                ui.help(HelpType.BATTLE_INFO);
                yield BattleState.PLAYER_TURN_HELP;
            }
            default -> BattleState.PLAYER_TURN;
        };
    }

    private void printBattleHelp() {
        ui.withSettings(DisplayOptions.MENU.id())
            .choice((Object[]) Messages.BATTLE_OPTIONS);
    }

    private void selectNavigation() {
        if (!session.isNavigationInit()) {
            session.initNavigation();
        }
        Enemy firstEnemy = session.getEnemyNavLeft();
        Enemy secondEnemy = session.getEnemyNavRight();
        ui.choice(firstEnemy.display(), secondEnemy.display());
        switch (readInput("Where do you go?")) {
            case "1":
                session.setEnemy(firstEnemy);
                break;
            case "2":
                session.setEnemy(secondEnemy);
                break;
            default:
                displayHelp();
                return;
        }
        session.setNavigationInit(false);
        setGameState(GameState.BATTLE);
    }

    private void processMenu() {
        displayMenu();

        String input = readInput("Select your option");
        switch (input) {
            case "1":
                setGameState(GameState.START_GAME);
                break;
            case "2":
                break;
            case "3":
                break;
            case "4":
                setGameState(GameState.QUIT);
                break;
            default:
                displayHelp();
        }
    }

    private void displayHelp() {
        ui.help(HelpType.WRONG_INPUT);;
    }

    private void displayMenu() {
        ui.withSettings(DisplayOptions.MENU.id())
            .choice((Object[]) Messages.MENU_OPTIONS);
    }

    private void showEndScreen() {
        ui.warning("Game over");
        setGameState(GameState.MENU);
    }

    private String readInput(String prompt) {
        ui.prompt(prompt);
        reader.consume();
        return reader.flush();

    }

}
