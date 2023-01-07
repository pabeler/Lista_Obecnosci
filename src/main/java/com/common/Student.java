package com.common;


import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "studenci")
public class Student implements java.io.Serializable {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "imie", nullable = true, length = 30)
    private String imie;
    @Basic
    @Column(name = "nazwisko", nullable = true, length = 30)
    private String nazwisko;
    @Basic
    @Column(name = "grupa", nullable = true)
    private Integer grupa;
    @Basic
    @Column(name = "obecnosc", nullable = true, length = 30)
    private String obecnosc;

    public Student(){};



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Integer getGrupa() {
        return grupa;
    }

    public void setGrupa(Integer grupa) {
        this.grupa = grupa;
    }

    public String getObecnosc() {
        return obecnosc;
    }

    public void setObecnosc(String obecnosc) {
        this.obecnosc = obecnosc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id == student.id && Objects.equals(imie, student.imie) && Objects.equals(nazwisko, student.nazwisko) && Objects.equals(grupa, student.grupa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, imie, nazwisko, grupa);
    }



}
