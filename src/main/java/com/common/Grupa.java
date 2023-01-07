package com.common;

import jakarta.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "grupy")
public class Grupa {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "termin", nullable = true)
    private Date termin;
    @Basic
    @Column(name = "nazwa", nullable = true, length = 45)
    private String nazwa;

    public Grupa(){};

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getTermin() {
        return termin;
    }

    public void setTermin(Date termin) {
        this.termin = termin;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Grupa grupa = (Grupa) o;
        return id == grupa.id && Objects.equals(termin, grupa.termin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, termin);
    }
}
