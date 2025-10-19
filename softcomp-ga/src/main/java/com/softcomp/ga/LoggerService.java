package com.softcomp.ga;

import java.io.FileWriter;
import java.io.IOException;

public class LoggerService {

    private static LoggerService instance;
    private static final String LOG_FILE = "GARun.log";

    private LoggerService() {
        initialize();
    }

    public static LoggerService getInstance() {
        if (instance == null) {
            instance = new LoggerService();
        }
        return instance;
    }

    private void initialize() {
        try (FileWriter writer = new FileWriter(LOG_FILE, false)) {

        } catch (IOException e) {
            System.err.println("Error initializing log file: " + e.getMessage());
        }
    }

    public void log(String message) {
        try (FileWriter writer = new FileWriter(LOG_FILE, true)) {
            writer.write(message + "\n");
        } catch (IOException e) {
            System.err.println("Error writing to log file: " + e.getMessage());
        }
    }
}