package com.github.danrog303.quizserver.server.workers;

import com.github.danrog303.quizserver.questions.QuizAnswer;
import com.github.danrog303.quizserver.questions.QuizQuestion;
import com.github.danrog303.quizserver.server.QuizGameState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;

@RequiredArgsConstructor
public class QuizAnswerSupplierWorker implements Runnable {
    private final int SERVER_PORT = 2138;
    private final QuizGameState quizGame;

    @Override
    @SneakyThrows(IOException.class)
    public void run() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_PORT)) {
            quizGame.getGameStateLogger().log("Server started at port %s".formatted(SERVER_PORT));
            while (!quizGame.isQuizOver()) {
                try (Socket clientSocket = serverSocket.accept()) {
                    handleClientAnswer(clientSocket);
                }
            }
            quizGame.getGameStateLogger().log("Server is stopping");
        }
    }

    private void handleClientAnswer(Socket clientSocket) throws IOException {
        byte[] clientInput = clientSocket.getInputStream().readAllBytes();
        String clientInputString = new String(clientInput, Charset.defaultCharset()).trim();
        quizGame.getGameStateLogger().log("Client accepted! (%s)".formatted(clientInputString));

        QuizQuestion currentQuestion = quizGame.getCurrentQuestion();
        if (currentQuestion == null) {
            return;
        }

        String[] quizAnswerParts = clientInputString.split("\\|");
        if (quizAnswerParts.length == 2) {
            quizGame.getGameStateLogger().log("Added to answer queue");
            quizGame.getAnswers().add(new QuizAnswer(quizAnswerParts[0], currentQuestion, quizAnswerParts[1]));
        } else {
            quizGame.getGameStateLogger().log("Answer malformed, parsing aborted");
        }
    }
}
