package ua.kiev.prog.Entity;
import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "menu")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer menu_id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "weight", nullable = false)
    private Integer weight;

    @Column(name = "discount")
    private BigDecimal discount;

    public Dish(String name, BigDecimal price, Integer weight) {
        this.name = name;
        this.price = price;
        this.weight = weight;
    }

    public Dish(String name, BigDecimal price, Integer weight, BigDecimal discount) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.discount = discount;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getWeight() {
        return weight;
    }

    public BigDecimal getDiscount() {
        return discount;
    }
}
