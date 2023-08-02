package org.example;

import java.util.List;

public class GradeCalculator {
    private final Courses courses;
//    private final List<Course> courses;
    public GradeCalculator(List<Course> courses) {
        this.courses = new Courses(courses);
    }

    public double calculateGrade() {
        // (학점수x교과목 평점)의 합계
//        double multipliedCreaditAndCourseGrade = 0;
//        for (Course course : courses) {
            // 이 부분은 Course에서 해주는 것이 옳다.
            // as-is
//            multipliedCreaditAndCourseGrade += course.getCredit() * course.getGradeToNumber();
            // to-be
//            multipliedCreaditAndCourseGrade += course.multiplyCreditAndCourseGrade();
//        }
        // to-be 2 courses 클래스 생성
        double multipliedCreaditAndCourseGrade = courses.multiplyCreditAndcCourseGrade();
        int totalCompletedCredit = courses.totalCompletedCredit();

        return multipliedCreaditAndCourseGrade / totalCompletedCredit;
    }
}
