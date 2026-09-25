package org.ystinov;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;

public class CharacterService {

    public EnumMap<Status, List<String>> readCharacters(String fileName) {
        EnumMap<Status, List<String>> characters =
                createCharactersMap();

        try (BufferedReader reader = new BufferedReader(
                new FileReader(fileName))) {

            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {
                addCharacter(line, characters);
            }

        } catch (IOException e) {
            System.err.println(
                    "Ошибка при чтении файла: " + e.getMessage()
            );
            throw new RuntimeException(e);
        }

        return characters;
    }

    public void writeResult(
            String fileName,
            EnumMap<Status, List<String>> characters) {

        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileName))) {

            for (Status status : Status.values()) {
                writer.write(status + " → ");

                List<String> names = characters.get(status);

                writer.write(String.join(", ", names));
                writer.newLine();
            }

        } catch (IOException e) {
            System.err.println(
                    "Ошибка при записи файла: " + e.getMessage()
            );
            throw new RuntimeException(e);
        }
    }

    private EnumMap<Status, List<String>> createCharactersMap() {
        EnumMap<Status, List<String>> characters =
                new EnumMap<>(Status.class);

        for (Status status : Status.values()) {
            characters.put(status, new ArrayList<>());
        }

        return characters;
    }

    private void addCharacter(
            String line,
            EnumMap<Status, List<String>> characters) {

        String[] data = line.split(",");

        String name = data[1];
        String statusText = data[2];

        Status status = getStatus(statusText);

        characters.get(status).add(name);
    }

    private Status getStatus(String statusText) {

        if ("Alive".equalsIgnoreCase(statusText)) {
            return Status.ALIVE;
        }

        if ("Dead".equalsIgnoreCase(statusText)) {
            return Status.DEAD;
        }

        return Status.UNKNOWN;
    }
}