package com.motivator.model;

/**
 * Created by sunil on 20-09-2016.
 */
public class CarePlanPOJO {
    String doctor_visit;

    public String getMasala() {
        return masala;
    }

    public void setMasala(String masala) {
        this.masala = masala;
    }

    String masala;

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    String symptoms;

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    String exercise;

    public String getDiet() {
        return diet;
    }

    public void setDiet(String diet) {
        this.diet = diet;
    }

    String diet;

    public String getDoctor_visit() {
        return doctor_visit;
    }

    public void setDoctor_visit(String doctor_visit) {
        this.doctor_visit = doctor_visit;
    }


    @Override
    public String toString() {
        return "CarePlanPOJO{" +
                "doctor_visit='" + doctor_visit + '\'' +
                ", masala='" + masala + '\'' +
                ", symptoms='" + symptoms + '\'' +
                ", exercise='" + exercise + '\'' +
                ", diet='" + diet + '\'' +
                '}';
    }
}
