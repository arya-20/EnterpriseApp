package example.restaurant.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import example.common.domain.Money;
import example.common.domain.ValueObject;
import example.restaurant.api.BaseMenuItem;
import example.restaurant.application.events.MenuItemCustomSerializer;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = false)
@Setter
@NoArgsConstructor
@JsonSerialize(using = MenuItemCustomSerializer.class)
public class MenuItem extends ValueObject implements BaseMenuItem { //BaseMenuItem (lives in api to avoid coupling to domain
    private long id;
    private String name;
    private Money price;

    public MenuItem(long id, String name, Money price) {
        setId(id);
        setName(name);
        setPrice(price);
    }

    public long id(){return id;}

    public String name() {
        return name;
    }

    public Money price() {
        return price;
    }

    private void setId(long id) {
        assertArgumentNotEmpty(id, "Id cannot be empty");
        this.id = id;
    }

    private void setName(String name) {
        assertArgumentNotEmpty(name, "Name cannot be empty");
        this.name = name;
    }

    private void setPrice(Money price) {
        assertArgumentNotEmpty(price, "Price cannot be empty");
        this.price = price;
    }

    public String toString(){
        return String.format("id=%s, name=%s, price=%s", id(), name, price);
    }

}