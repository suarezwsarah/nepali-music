package com.apps.constants;

public class PlayerConstant {

    private static boolean shuffle = false;

    public static boolean isShuffle() {
        return shuffle;
    }

    public static void setShuffle(boolean shuffle) {
        PlayerConstant.shuffle = shuffle;
    }
}
