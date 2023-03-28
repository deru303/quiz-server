package com.github.danrog303.quizserver.server.workers;

import com.github.danrog303.quizserver.questions.QuizAnswer;
import com.github.danrog303.quizserver.questions.QuizAnswerValidator;
import com.github.danrog303.quizserver.questions.QuizQuestion;
import com.github.danrog303.quizserver.server.QuizGameState;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class QuizAnswerConsumerWorker implements Runnable {
    private final QuizGameState quizGame;

    @Override
    @SneakyThrows(InterruptedException.class)
    public void run() {
        while (!quizGame.isQuizOver()) {
            QuizAnswer answer = quizGame.getAnswers().take();
            QuizAnswerValidator answerValidator = new QuizAnswerValidator();
            QuizQuestion currentQuestion = quizGame.getCurrentQuestion();

            System.out.println(answer);

            if (answerValidator.isAnswerCorrect(currentQuestion, answer)) {
                quizGame.getCurrentQuestionIndex().incrementAndGet();

                String message = "Player %s answer was correct!\n".formatted(answer.getUserName());
                quizGame.getGameStateLogger().log(message);
            } else {
                String message = "Player %s answer was incorrect!".formatted(answer.getUserName());
                quizGame.getGameStateLogger().log(message);
            }
        }
    }
}
