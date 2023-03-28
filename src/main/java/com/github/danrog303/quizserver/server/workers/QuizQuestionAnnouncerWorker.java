package com.github.danrog303.quizserver.server.workers;

import com.github.danrog303.quizserver.server.QuizGameState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Set;

@RequiredArgsConstructor
public class QuizQuestionAnnouncerWorker implements Runnable {
    private final QuizGameState quizGame;
    private final Set<Integer> announcedQuestions = new HashSet<>();

    @Override
    @SneakyThrows(InterruptedException.class)
    public synchronized void run() {
        while (!quizGame.isQuizOver()) {
            int currentQuestionIndex = quizGame.getCurrentQuestionIndex().get();

            if (!announcedQuestions.contains(currentQuestionIndex)) {
                quizGame.getGameStateLogger().log("Question: " + quizGame.getCurrentQuestion().getQuestion());
                announcedQuestions.add(currentQuestionIndex);
            } else {
                wait(200);
            }
        }
    }
}
