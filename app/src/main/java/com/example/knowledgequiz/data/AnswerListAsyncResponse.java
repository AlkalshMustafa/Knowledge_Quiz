package com.example.knowledgequiz.data;

import com.example.knowledgequiz.model.Question;

import java.util.ArrayList;

public interface AnswerListAsyncResponse {
    void processFinished(ArrayList<Question> questionArrayList);
}
