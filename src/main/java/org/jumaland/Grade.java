package org.jumaland;

import java.util.Date;

public class Grade {

    private final String grade;
    private final int score;
    private final Date date;

    public String getGrade() {
        return grade;
    }

    public int getScore() {
        return score;
    }

    public Date getDate() {
        return date;
    }

    public Grade(String grade, int score, Date date) {
        this.grade = grade;
        this.score = score;
        this.date = date;
    }
}
