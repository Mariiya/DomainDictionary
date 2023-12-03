package com.domaindictionary.service;

import org.apache.commons.compress.utils.IOUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    public void removeFile(String path) {
        File file = new File(path);

        // Check if the file exists
        if (file.exists()) {
            // Attempt to delete the file
            boolean deleted = file.delete();

            // Check if the deletion was successful
            if (deleted) {
                System.out.println("File deleted successfully: " + path);
            } else {
                System.err.println("Failed to delete the file: " + path);
            }
        } else {
            System.err.println("File not found: " + path);
        }
    }

    public String createAndSaveFile(ByteArrayInputStream document, String filePath) {
        File fileToSave = new File("DictionaryMain\\src\\main\\resources\\dictionaries\\" + file.getOriginalFilename());

        try (OutputStream os = new FileOutputStream(fileToSave)) {
            os.write(document.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(Paths.get(fileToSave.getAbsolutePath()))) {
            return fileToSave.getAbsolutePath();
        }
        return "";
    }

    public String saveToFile(ByteArrayInputStream stream, String name) {
        File fileToSave = new File("DomainDictionaryMain\\src\\main\\resources\\dictionaries\\"
                + name + System.currentTimeMillis() + ".txt");

        try {
            IOUtils.copy(stream, new FileOutputStream(fileToSave));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Files.exists(Paths.get(fileToSave.getAbsolutePath()))) {
            return fileToSave.getAbsolutePath();
        }
        return "";
    }

    public String readFile(File file) throws IOException {
        return new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
    }

    public File extractFile(String filePath) throws IOException {
        Path path = Path.of(filePath);

        // Check if the file exists
        if (!Files.exists(path)) {
            throw new IOException("File not found: " + filePath);
        }

        return path.toFile();
    }
}
