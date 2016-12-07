package orderit.orderit.helpclasses;

/**
 * Created by Gert on 01.12.2016.
 */

public class Order {

    private String drink_name_;
    private String bottle_name_;
    private String customer_name_;
    private String description_;
    private String number_;
    private long id_;


    public Order(long id, String drink_name, String bottle_name, String customer_name, String number, String dest) {

        id_ = id;
        drink_name_ = drink_name;
        description_ = dest;
        bottle_name_ = bottle_name;
        customer_name_ = customer_name;
        number_ = number;
    }


    public String getDrinkName() {
        return drink_name_;
    }

    public void setDrinkName(String drink_name) {
        this.drink_name_ = drink_name;
    }

    public String getBottleName() {
        return bottle_name_;
    }

    public void setBottleName(String bottle_name) {
        this.bottle_name_ = bottle_name;
    }

    public String getCustomerName() {
        return customer_name_;
    }

    public void setCustomerName(String customer_name) {
        this.customer_name_ = customer_name;
    }

    public String getNumber() {
        return number_;
    }

    public void setNumber(String number) {
        this.number_ = number;
    }

    public String getId() {
        String id = "" + id_;
        return id;
    }

    public void setDescription(String dest) {
        description_ = dest;
    }

    public String getDescription() {
        return description_;
    }

    public void setId(long id) {
        this.id_ = id;
    }

    public String convertOrderToString() {
        String order;
        if(description_.isEmpty()) {
            order = drink_name_ + "  " + bottle_name_ + "  " + number_;
        }
        else {
            order = drink_name_ + "  " + bottle_name_ + "  " + number_ + "\n" + description_;
        }
        return order;
    }
}
