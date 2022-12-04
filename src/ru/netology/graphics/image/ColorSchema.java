package ru.netology.graphics.image;

public class ColorSchema implements TextColorSchema {

    private final char[] symbols = {'#', '$', '@', '%', '*', '+', '-', '.'};

    @Override
    public char convert(int color) {
        return symbols[color / 32];
    }
}
