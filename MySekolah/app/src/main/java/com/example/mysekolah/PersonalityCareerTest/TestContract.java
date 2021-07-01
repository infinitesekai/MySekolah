package com.example.mysekolah.PersonalityCareerTest;

import android.provider.BaseColumns;

public final class TestContract {
    private TestContract() {
    }

    public static class QuestionsTable implements BaseColumns {
        public static final String TABLE_NAME = "Question_List";
        public static final String COLUMN_QUESTION_ID = "ques_ID";
        public static final String COLUMN_QUESTION = "question";
        public static final String COLUMN_OPTION1 = "option1";
        public static final String COLUMN_OPTION2 = "option2";
        public static String COLUMN_ANSWER_CATEGORY = "category";
        public static String COLUMN_ANSWER_OPTION = "answer";
    }

    public static class TestResultTable implements BaseColumns {
        public static final String TABLE_NAME = "Test_result";
        public static final String COLUMN_TESTID = "testID";
        public static final String COLUMN_ICNO = "ICNo";
        public static final String COLUMN_H_1 = "highestResult1";
        public static final String COLUMN_H_2 = "highestResult2";
        public static final String COLUMN_H_3 = "highestResult3";

    }

    public static class CareerSuggestionTable implements BaseColumns {
        public static final String TABLE_NAME = "Career_Suggestion";
        public static final String COLUMN_ALPHABET = "alphabet";
        public static final String COLUMN_ALPNAME = "alpName";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_EXPLANATION = "explanation";
        public static final String COLUMN_SUGGESTION = "suggestedField";
    }

    public static class AnswerTrackingTable implements BaseColumns{
        public static final String TABLE_NAME = "Answer_Tracking";
        public static final String COLUMN_QUESTION_ID = "quesID";
        public static final String COLUMN_ANSWER_OPTION = "answerOption";
    }
}
