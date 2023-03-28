package com.github.danrog303.quizserver.server;

import com.github.danrog303.quizserver.server.workers.QuizAnswerConsumerWorker;
import com.github.danrog303.quizserver.server.workers.QuizAnswerSupplierWorker;
import com.github.danrog303.quizserver.server.workers.QuizQuestionAnnouncerWorker;
import com.github.danrog303.quizserver.utils.Logger;

import java.util.ArrayList;
import java.util.List;

public class QuizServer implements Runnable {
    private final List<Thread> workers;

    public QuizServer(Logger gameStateLogger) {
        this.workers = new ArrayList<>();
        QuizGameState quizGame = new QuizGameState(gameStateLogger);

        workers.add(new Thread(new QuizAnswerSupplierWorker(quizGame)));
        workers.add(new Thread(new QuizAnswerConsumerWorker(quizGame)));
        workers.add(new Thread(new QuizQuestionAnnouncerWorker(quizGame)));
    }

    @Override
    public void run() {
        this.workers.forEach(Thread::start);
    }
}
