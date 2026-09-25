package org.ystinov;

import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        EnumMap<Status, List<String>> characters = readCharacters();

        writeResult(characters);
    }

    public static EnumMap<Status, List<String>> readCharacters() {

        EnumMap<Status, List<String>> characters =
                new EnumMap<>(Status.class);

        for (Status status : Status.values()) {
            characters.put(status, new ArrayList<>());
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("characters.csv"))) {

            // Пропускаем заголовок
            reader.readLine();

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                String name = data[1];
                String statusText = data[2];

                Status status;

                if (statusText.equalsIgnoreCase("Alive")) {
                    status = Status.ALIVE;
                } else if (statusText.equalsIgnoreCase("Dead")) {
                    status = Status.DEAD;
                } else {
                    status = Status.UNKNOWN;
                }

                characters.get(status).add(name);
            }

        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        return characters;
    }

    public static void writeResult(
            EnumMap<Status, List<String>> characters) {

        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter("result.txt"))) {

            for (Status status : Status.values()) {

                writer.write(status + " → ");

                List<String> names = characters.get(status);

                writer.write(String.join(", ", names));

                writer.newLine();
            }

            System.out.println("Результат записан в result.txt");

        } catch (IOException e) {
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }
}