package ru.vitaly.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.Cascade;
import ru.vitaly.utils.YearMonthDaySerializer;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.time.LocalDate;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;

/**
 * @author Vitaly Vasilyev, date: 27.02.2020, e-mail: rav.energ@rambler.ru
 * @version 1.0
 */
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @Cascade(SAVE_UPDATE)
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    @Column(name = "image_name")
    private String imgName;

    @ManyToOne
    @JoinColumn(name = "offeruser_id", nullable = false)
    private User user;

    @JsonSerialize(using = YearMonthDaySerializer.class)
    @Column(name = "create_date")
    private LocalDate createDate;

    @Column
    private boolean sold;

    public Offer() { }

    public Offer(String imgName) {
        this.imgName = imgName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String image) {
        this.imgName = image;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Offer)) {
            return false;
        }
        Offer offer = (Offer) o;
        return id != 0 && id == offer.id;
    }

    @Override
    public int hashCode() {
        return 33;
    }

    @Override
    public String toString() {
        return String.format("Offer: car: {%s}, image: %s, sold: %b, user: {%s}, createDate: %s", car, imgName, sold, user, createDate);
    }

    public static Builder newBuilder() {
        return new Offer().new Builder();
    }

    public class Builder {
        private Builder() { }

        public Builder setId(int id) {
            Offer.this.id = id;
            return this;
        }

        public Builder setCar(Car car) {
            Offer.this.car = car;
            return this;
        }

        public Builder setImgName(String imgName) {
            Offer.this.imgName = imgName;
            return this;
        }

        public Builder setUser(User user) {
            Offer.this.user = user;
            return this;
        }

        public Builder setCreateDate(LocalDate createDate) {
            Offer.this.createDate = createDate;
            return this;
        }

        public Offer build() {
            return Offer.this;
        }
    }
}