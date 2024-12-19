package org.andnekon.game.entity;

import org.andnekon.game.action.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

// TODO: tests
public class Deck<T extends Card> {

    private List<T> total;

    private List<T> inBattle;

    private int inBattleSize;

    public Deck(int inBattleSize) {
        this.inBattleSize = inBattleSize;
        this.total = new ArrayList<>();
        this.inBattle = new ArrayList<>();
    }

    public List<T> getTotal() {
        return total;
    }

    public List<T> getInBattle() {
        return inBattle;
    }

    public void initInBattle() {
        if (inBattleSize >= total.size()) {
            inBattle = new ArrayList<>(total);
            return;
        }
        List<T> shuffled = new ArrayList<>(total); // all elements are unique
        Collections.shuffle(Arrays.asList(shuffled));
        inBattle = shuffled.stream().limit(inBattleSize).toList();
    }

    public void add(T card) {
        this.total.add(card);
    }
}
