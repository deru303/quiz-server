package com.github.danrog303.quizserver.server;

import com.github.danrog303.quizserver.questions.QuizAnswer;
import com.github.danrog303.quizserver.questions.QuizQuestion;
import com.github.danrog303.quizserver.questions.QuizQuestionFileReader;
import com.github.danrog303.quizserver.utils.Logger;
import lombok.Data;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

@Data
public class QuizGameState {
    private final Logger gameStateLogger;
    private final AtomicInteger currentQuestionIndex;
    private final List<QuizQuestion> questions;
    private final BlockingQueue<QuizAnswer> answers;

    public QuizQuestion getCurrentQuestion() {
        try {
            return questions.get(currentQuestionIndex.get());
        } catch(ArrayIndexOutOfBoundsException e) {
            return null;
        }
    }

    public boolean isQuizOver() {
        return currentQuestionIndex.get() >= questions.size();
    }

    public QuizGameState(Logger gameStateLogger) {
        this.gameStateLogger = gameStateLogger;
        this.currentQuestionIndex = new AtomicInteger(0);
        this.questions = Collections.unmodifiableList(new QuizQuestionFileReader().getQuestions());
        this.answers = new ArrayBlockingQueue<>(10);
    }
}

