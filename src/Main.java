import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Main {

    private static final String INPUT_FILE_PATH = "characters.csv";
    private static final String OUTPUT_FILE_PATH = "result.txt";

    public static void main(String[] args) {
        // Создаем EnumMap и сразу кладем пустые списки для каждого статуса
        Map<Status, List<String>> characterStatusMap = createEmptyStatusMap();

        // Step 1 и 2: Читаем CSV и заполняем карту
        readAndGroupCharacters(INPUT_FILE_PATH, characterStatusMap);

        // Step 3: Записываем результат в файл
        writeResultToFile(OUTPUT_FILE_PATH, characterStatusMap);

        System.out.println("Обработка завершена! Результат сохранен в " + OUTPUT_FILE_PATH);
    }

    private static Map<Status, List<String>> createEmptyStatusMap() {
        Map<Status, List<String>> statusMap = new EnumMap<>(Status.class);
        statusMap.put(Status.ALIVE, new ArrayList<>());
        statusMap.put(Status.DEAD, new ArrayList<>());
        statusMap.put(Status.UNKNOWN, new ArrayList<>());
        return statusMap;
    }

    private static void readAndGroupCharacters(String filePath, Map<Status, List<String>> characterStatusMap) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            // Пропускаем первую строчку (заголовок CSV)
            String line = bufferedReader.readLine();

            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] columns = line.split(",");
                if (columns.length >= 3) {
                    String characterName = columns[1].trim();
                    String statusText = columns[2].trim();

                    Status status = parseStatus(statusText);
                    characterStatusMap.get(status).add(characterName);
                }
            }
        } catch (IOException ioException) {
            System.out.println("Ошибка при чтении файла: " + ioException.getMessage());
        }
    }

    private static Status parseStatus(String statusText) {
        if ("Alive".equalsIgnoreCase(statusText)) {
            return Status.ALIVE;
        } else if ("Dead".equalsIgnoreCase(statusText)) {
            return Status.DEAD;
        } else {
            return Status.UNKNOWN;
        }
    }

    private static void writeResultToFile(String filePath, Map<Status, List<String>> characterStatusMap) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<Status, List<String>> entry : characterStatusMap.entrySet()) {
                Status status = entry.getKey();
                List<String> namesList = entry.getValue();

                // Объединяем список имен через запятую: "Rick Sanchez, Morty Smith"
                String namesString = String.join(", ", namesList);

                // Формируем строку: "ALIVE → Rick Sanchez, Morty Smith"
                bufferedWriter.write(status.name() + " → " + namesString);
                bufferedWriter.newLine();
            }
        } catch (IOException ioException) {
            System.out.println("Ошибка при записи файла: " + ioException.getMessage());
        }
    }
}