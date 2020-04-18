package com.bowling.tenpinbowling.Assert;

import com.bowling.tenpinbowling.models.Frame;
import com.bowling.tenpinbowling.models.Player;

public class Assertions {

    public static PlayerAssert assertThat(Player actual) {
        return new PlayerAssert(actual);
    }

    public static FrameAssert assertThat(Frame actual) {
        return new FrameAssert(actual);
    }
}
