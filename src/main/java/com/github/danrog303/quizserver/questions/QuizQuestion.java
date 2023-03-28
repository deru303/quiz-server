package com.github.danrog303.quizserver.questions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data @AllArgsConstructor
public class QuizQuestion {
    private @NonNull String question;
    private @NonNull String correctAnswer;
}
