package orderit.orderit.helpclasses;

/**
 * Created by Gert on 01.12.2016.
 */

public class Bottle {

    private String bottle_name_;
    private String bottle_size_;

    public Bottle(String name, String size) {
        bottle_name_ = name;
        bottle_size_ = size;
    }

    public void setBottleName(String bottle_name) {
        bottle_name_ = bottle_name;
    }

    public void setBottleSize(String size) {
        bottle_size_ = size;
    }

    public String getBottleName() {
        return bottle_name_;
    }

    public String getBottleSize() {
        return bottle_size_;
    }

    public String convertBottletoString() {
        String str = this.getBottleName() + " ....... " + this.getBottleSize();
        return str;
    }
}

