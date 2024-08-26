package example.restaurant.api;

import example.common.domain.Money;

//Used in AddNewMenuCommand to avoid coupling of domain MenuItem to api
public interface BaseMenuItem {
     long id();

     String name() ;

     Money price();
}