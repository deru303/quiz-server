package com.github.danrog303.quizserver.questions;

public class QuizAnswerValidator {
    public boolean isAnswerCorrect(QuizAnswer answer) {
        String userAnswer = answer.getAnswer().toLowerCase().trim();
        String correctAnswer = answer.getQuestion().getCorrectAnswer().toLowerCase();
        return userAnswer.equals(correctAnswer);
    }

    public boolean isAnswerCorrect(QuizQuestion question, QuizAnswer answer) {
        return answer.getQuestion().equals(question) && isAnswerCorrect(answer);
    }
}
