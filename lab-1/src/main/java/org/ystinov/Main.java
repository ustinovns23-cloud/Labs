package org.ystinov;
import java.io.*;
import java.util.*;

public class Main {
    enum Status {
        ALIVE,
        DEAD,
        UNKNOWN
    }

    public static void main(String[] args) {
        EnumMap<Status, List<String>> characters =
                new EnumMap<>(Status.class);
        for (Status status : Status.values()) {
            characters.put(status, new ArrayList<>());
        }

        try (BufferedReader reader =
                     new BufferedReader(new FileReader("characters.csv"))) {
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
            return;
        }

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