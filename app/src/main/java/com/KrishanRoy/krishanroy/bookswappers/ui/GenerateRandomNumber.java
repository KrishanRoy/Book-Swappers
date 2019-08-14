package com.KrishanRoy.krishanroy.bookswappers.ui;

import java.util.Random;

public class GenerateRandomNumber {
    public int randomNumber() {
        Random random = new Random();
        return random.nextInt(4000) + 2000;
    }
}
