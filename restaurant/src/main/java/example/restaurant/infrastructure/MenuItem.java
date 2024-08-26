package example.restaurant.infrastructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import example.restaurant.api.BaseMenuItemValueObject;
import jakarta.persistence.*;
import lombok.*;

@Entity(name="menu_item")
@Table(name="menu_item")
@Setter
@Getter
@ToString
@NoArgsConstructor //needed for the convertor
@AllArgsConstructor //needed for the convertor
//interface enables GetOrderItemsResponse to avoid coupling to interface layer via MenuItemValueObject
public class MenuItem implements BaseMenuItemValueObject {
    @Id
    @SequenceGenerator(name= "menu_item_sequence",
            sequenceName = "menu_item_sequence_id",
            allocationSize = 1)
    @GeneratedValue(strategy=GenerationType.IDENTITY,
            generator="menu_item_sequence")
    @Column(name="menu_item_id")
    private long id;

    @Column(name="menu_name")
    private String name;

    @Column(name="menu_price")
    private double price;

    @JsonIgnore //not displayed in list when JSONing menu items
    @Column(name="restaurant_id")
    private String restaurant_id;
}
