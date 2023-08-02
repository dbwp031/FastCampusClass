package org.example;

public class Course {
    private final String subject; // 과목명
    private final int credit; // 학점
    private final String grade;
    public Course(String subject, int credit, String grade) {
        this.subject = subject;
        this.credit = credit;
        this.grade = grade;
    }

    public int getCredit() {
        return this.credit;
    }

    public double getGradeToNumber() {
        double grade = 0;
        switch (this.grade) {
            case "A+":
                grade = 4.5;
                break;
            case "A":
                grade = 4.0;
                break;
            case "B+":
                grade = 3.5;
                break;
            case "B":
                grade = 3.0;
                break;
            case "C+":
                grade = 2.5;
                break;
            case "C":
                grade = 2.0;
                break;
        }
        return grade;
    }
    // 응집도가 높아 수정이 필요할 때 노동량을 최소화할 수 있다.
    public double multiplyCreditAndCourseGrade() {
        return this.credit * this.getGradeToNumber();
    }
}
