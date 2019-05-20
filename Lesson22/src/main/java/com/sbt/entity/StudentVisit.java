package com.sbt.entity;

public class StudentVisit {
    private int id;

    private Student student;
    private Lesson lesson;

    public StudentVisit(int id, int idStudent, int idLesson) {
        this.id = id;

        student = new Student(idStudent);
        lesson = new Lesson(idLesson);
    }

    public StudentVisit(int idStudent, int idLesson) {
        student = new Student(idStudent);
        lesson = new Lesson(idLesson);
    }

    public StudentVisit(Student student) {
        this.student = student;
    }

    public StudentVisit(Lesson lesson) {
        this.lesson = lesson;
    }

    public StudentVisit(Student student, Lesson lesson) {
        this.student = student;
        this.lesson = lesson;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public String toString() {
        return "StudentVisit{" +
                "id=" + id +
                ", student=" + student +
                ", lesson=" + lesson +
                '}';
    }
}
