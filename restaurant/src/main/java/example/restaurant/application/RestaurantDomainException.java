package example.restaurant.application;

public class RestaurantDomainException extends Exception{
    private final String message;
    public RestaurantDomainException(String message){
        this.message = message;
    }

    public String toString(){
        return message;
    }
}