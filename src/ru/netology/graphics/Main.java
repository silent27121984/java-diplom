package ru.netology.graphics;

import ru.netology.graphics.image.*;
import ru.netology.graphics.server.*;

import java.io.File;
import java.io.PrintWriter;

public class Main {

    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new GraphicsConverter(); // Создайте тут объект вашего класса конвертера
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем


        // Или то же, но с выводом на экран:
        String url = "https://raw.githubusercontent.com/netology-code/java-diplom/main/pics/simple-test.png";
        converter.setMaxRatio(2);
        String imgTxt = converter.convert(url);
        System.out.println(imgTxt);
    }
}
