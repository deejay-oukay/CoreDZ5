import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

// Написать функцию, создающую резервную копию всех файлов в директории
// (без поддиректорий) во вновь созданную папку ./backup
public class Backup {
    private static final String sourceDirectory = ".";
    private static final String destinationDirectory = "./backup";
    public static void main(String[] args) {

        try {
            create();
            System.out.println("Резервная копия успешно создана");
        } catch (IOException e) {
            System.out.println("Ошибка при создании резервной копии: "+e.getMessage());
        }
    }

    public static void create() throws IOException {
        File source = new File(sourceDirectory);
        File destination = new File(destinationDirectory);

        if (!source.isDirectory()) {
            throw new IllegalArgumentException("Исходный путь не является директорией...");
        }

        if (!destination.exists()) {
            if (!destination.mkdirs())
                throw new IOException("Не удалось создать директорию назначения...");
        }

        File[] files = source.listFiles();
        if (files == null) {
            throw new IOException("Список файлов для копирования - пуст...");
        }
        else
        {
            for (File file : files) {
                if (file.isFile()) {
                    File copyFile = new File(destinationDirectory + "/" + file.getName());
                    copyFile(file, copyFile);
                }
            }
        }
    }

    public static void copyFile(File sourceFile, File destinationFile) throws IOException {
        try (FileInputStream input = new FileInputStream(sourceFile);
             FileOutputStream output = new FileOutputStream(destinationFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = input.read(buffer)) > 0) {
                output.write(buffer, 0, length);
            }
        }
    }
}