package ru.vitaly.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@Entity
public class Engine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    public Engine() {
    }

    public Engine(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("%s", name);
    }

    public static Builder newBuilder() {
        return new Engine().new Builder();
    }

    public class Builder {
        private Builder() { }

        public Builder setId(int id) {
            Engine.this.id = id;
            return this;
        }

        public Builder setName(String name) {
            Engine.this.name = name;
            return this;
        }

        public Engine build() {
            return Engine.this;
        }
    }
}