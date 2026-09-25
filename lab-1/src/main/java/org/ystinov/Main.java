package org.ystinov;

import java.util.EnumMap;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        CharacterService characterService =
                new CharacterService();

        EnumMap<Status, List<String>> characters =
                characterService.readCharacters("characters.csv");

        characterService.writeResult(
                "result.txt",
                characters
        );

        System.out.println("Результат записан в result.txt");
    }
}