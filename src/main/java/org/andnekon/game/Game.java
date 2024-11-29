package org.andnekon.game;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;

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

    Scanner scanner;

    public Game() {
        this.scanner = new Scanner( new BufferedReader( new InputStreamReader( System.in ) ) );
        this.session = new GameSession();
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
        while (battleState != BattleState.COMPLETE) {
            switch (battleState) {
                case START -> {
                    session.initBattle();
                    battleState = BattleState.PLAYER_TURN_START;
                }
                case PLAYER_TURN_START -> {
                    player.setEnergy(3);
                    player.setDefense(0);
                    enemy.fillIntents(player);
                    battleState = BattleState.PLAYER_TURN;
                }
                case PLAYER_TURN -> {
                    String input = readInput("What do you do?");
                    battleState = processBattleInput(player, enemy, input);
                }
                case PLAYER_TURN_END -> {}
                case ENEMY_TURN_START -> {
                    enemy.setDefense(0);
                    for (Intent intent : enemy.getCurrentIntents()) {
                        intent.execute();
                        System.out.printf("%s: %s\n", enemy.display(), intent);
                    }
                    System.out.println();
                    battleState = BattleState.ENEMY_TURN_END;
                }
                case ENEMY_TURN_END -> {
                    enemy.clearIntents();
                    session.incTurn();
                    battleState = setIfBattleEnd(player, enemy);
                }
                case PLAYER_TURN_HELP -> {
                    battleState = BattleState.PLAYER_TURN;
                }
                case COMPLETE -> {}
            }
        }
    }

    private BattleState setIfBattleEnd(Player player, Enemy enemy) {
        if (player.getHp() <= 0) {
            setGameState(GameState.GAME_OVER);
            return BattleState.COMPLETE;
        } else if (enemy.getHp() <= 0) {
            setGameState(GameState.REWARD);
            return BattleState.COMPLETE;
        }
        return BattleState.PLAYER_TURN_START;
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
            System.out.println("Not enough energy");
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
            case 'e' -> BattleState.ENEMY_TURN_START;
            case 'h' -> {
                printBattleHelp();
                yield BattleState.PLAYER_TURN_HELP;
            }
            case 'c' -> {
                player.printBattleDeck();
                System.out.println(String.format("""
                            Player: [energy: %d, hp: %d, defence: %d]
                            %s: [hp: %d, def %d]
                            %s intends to %s.""",
                            player.getEnergy(), player.getHp(), player.getDefense(),
                            enemy.display(), enemy.getHp(), enemy.getDefense(),
                            enemy.display(), enemy.displayIntents()));
                yield BattleState.PLAYER_TURN_HELP;
            }
            default -> BattleState.PLAYER_TURN;
        };
    }

    private void printBattleHelp() {
        System.out.println("""
                [q] - quit
                [h] - print this help
                [e] - end your turn
                [c] - check your state
                [1-4] - play selected card""");
    }

    private void selectNavigation() {
        if (!session.isNavigationInit()) {
            session.initNavigation();
        }
        Enemy firstEnemy = session.getEnemyNavLeft();
        Enemy secondEnemy = session.getEnemyNavRight();
        System.out.println(String.format(" Choose: [ 1. %s | 2. %s ] ",
                    firstEnemy.display(), secondEnemy.display()));
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
        System.out.println("Help yourself, jerk.");
    }

    private void displayMenu() {
        System.out.println("""
                1. Start game
                2. Continue
                3. About
                4. Quit
                """);
    }

    private void showEndScreen() {
        System.out.println("Game over");
        setGameState(GameState.MENU);
    }

    private String readInput(String prompt) {
        System.out.printf("%s ", prompt);
        return scanner.nextLine();

    }

}
