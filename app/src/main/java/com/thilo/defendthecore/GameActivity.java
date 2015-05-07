package com.thilo.defendthecore;

import framework.Screen;
import impl.AndroidGame;

public class GameActivity extends AndroidGame {
    @Override
    public Screen getStartScreen() {
        return new GameScreen(this); 
    }
}
