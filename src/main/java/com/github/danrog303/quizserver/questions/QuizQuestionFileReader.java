package com.github.danrog303.quizserver.questions;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

public class QuizQuestionFileReader {

    @SneakyThrows(IOException.class)
    public List<QuizQuestion> getQuestions(InputStream questionsStream) {
        List<String> fileLines = IOUtils.readLines(questionsStream, Charset.defaultCharset());
        return fileLines.stream().map(QuizQuestionFileReader::parseLine).toList();
    }

    public List<QuizQuestion> getQuestions() {
        String defaultFileResourcePath = "questions.txt";
        return getQuestions(getClass().getResourceAsStream(defaultFileResourcePath));
    }

    private static QuizQuestion parseLine(String line) {
        String[] questionParts = line.split("\\|");
        return new QuizQuestion(questionParts[0], questionParts[1]);
    }
}
