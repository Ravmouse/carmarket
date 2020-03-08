package ru.vitaly.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ru.vitaly.utils.LocalDateSerializer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @JsonSerialize(using = LocalDateSerializer.class)
    @Column(name = "year")
    private LocalDate year;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "transmission_id")
    private Transmission transmission;

    @ManyToOne
    @JoinColumn(name = "carbody_id")
    private CarBody carBody;

    @ManyToOne
    @JoinColumn(name = "engine_id")
    private Engine engine;

    public Car() {
    }

    public Car(LocalDate year) {
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getYear() {
        return year;
    }

    public void setYear(LocalDate year) {
        this.year = year;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Transmission transmission) {
        this.transmission = transmission;
    }

    public CarBody getCarBody() {
        return carBody;
    }

    public void setCarBody(CarBody body) {
        this.carBody = body;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Car)) {
            return false;
        }
        Car car = (Car) o;
        return id != 0 && id == car.id;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return String.format("year: %s, brand: {%s}, model: %s, transmission: %s, body: %s, engine: %s", year, brand, model,
                transmission, carBody, engine);
    }

    public static Builder newBuilder() {
        return new Car().new Builder();
    }

    public class Builder {
        private Builder() { }

        public Builder setId(int id) {
            Car.this.id = id;
            return this;
        }

        public Builder setYear(LocalDate year) {
            Car.this.year = year;
            return this;
        }

        public Builder setBrand(Brand brand) {
            Car.this.brand = brand;
            return this;
        }

        public Builder setModel(Model model) {
            Car.this.model = model;
            return this;
        }

        public Builder setTransmission(Transmission transmission) {
            Car.this.transmission = transmission;
            return this;
        }

        public Builder setCarbody(CarBody carBody) {
            Car.this.carBody = carBody;
            return this;
        }

        public Builder setEngine(Engine engine) {
            Car.this.engine = engine;
            return this;
        }

        public Car build() {
            return Car.this;
        }
    }
}