package com.bowling.tenpinbowling.Assert;

import com.bowling.tenpinbowling.models.Player;
import org.assertj.core.api.AbstractAssert;

public class PlayerAssert extends AbstractAssert<PlayerAssert, Player> {

    PlayerAssert(Player actual) {
        super(actual, PlayerAssert.class);
    }

    public PlayerAssert hasName(String name) {
        isNotNull();
        if (!actual.getName().equals(name)) {
            failWithMessage("Expected player to have name %s but was %s",
                    name, actual.getName());
        }
        return this;
    }

}
