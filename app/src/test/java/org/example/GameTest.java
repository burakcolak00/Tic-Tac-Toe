package org.example;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

public class GameTest {
    @Test
    void testPrintGameLog() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Game game = new Game();
        game.printStatistics();
        String output = outContent.toString();
        assertTrue(output.contains("Current Log:"));
    }

    @Test
    void testSaveGameLogToFile() {
        Game game = new Game();
        game.saveStatisticsToFile();
        File file = new File("game_log.txt");
        Path path = Paths.get("game_log.txt");
        assertTrue(Files.exists(path), "game_log.txt should exist");

        try {
            String content = Files.readString(path);
            assertTrue(content.contains("Game Log:"));
        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        file.delete();
    }

}
