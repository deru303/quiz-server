package com.github.danrog303.quizserver;

import com.github.danrog303.quizserver.server.QuizServer;
import com.github.danrog303.quizserver.utils.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class QuizServerViewController {
    public TextArea loggingTextArea;

    @FXML
    public void initialize() {
        Logger gameStateLogger = (message) -> {
            System.out.printf("Log: %s%n", message);
            loggingTextArea.appendText("%s\n".formatted(message));
        };

        QuizServer server = new QuizServer(gameStateLogger);
        server.run();
    }
}