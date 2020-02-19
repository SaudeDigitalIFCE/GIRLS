package ifce.lara.vite.object;

import com.orm.SugarRecord;

/**
  on 29/08/16.
 */
public class Contact extends SugarRecord {

    public String name;
    public String phone;

    public Contact(){}

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}