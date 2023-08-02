package org.example;

import java.util.List;

public class Courses {
    // 일급 컬랙션 (First Class Collection)
    //Collection을 Wrapping하면서, 그 외 다른 멤버 변수가 없는 상태
    // 장점
    // 1. 비즈니스에 종속적인 자료구조
    // 2. Collection의 불변성 보장
    // 3. 상태와 행위를 한 곳에서 관리
    // 4. 이름이 있는 컬랙션

    private final List<Course> courses; // 내가 내부나 외부에 List.add 메서드를 사용하지 않아야 불변성이 보장된다.

    public Courses(List<Course> courses) {
        this.courses = courses;
    }


    public double multiplyCreditAndcCourseGrade() {
        double multipliedCreaditAndCourseGrade = 0;
        for (Course course : courses) {
            multipliedCreaditAndCourseGrade += course.multiplyCreditAndCourseGrade();
        }
        return multipliedCreaditAndCourseGrade;
    }

    public int totalCompletedCredit() {
        return courses.stream()
                .mapToInt(Course::getCredit)
                .sum();
    }
}
