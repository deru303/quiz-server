package com.github.danrog303.quizserver.questions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data @AllArgsConstructor
public class QuizAnswer {
    private @NonNull String userName;
    private @NonNull QuizQuestion question;
    private @NonNull String answer;
}
