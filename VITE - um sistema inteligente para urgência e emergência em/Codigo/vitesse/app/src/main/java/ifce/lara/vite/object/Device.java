package ifce.lara.vite.object;

import com.orm.SugarRecord;

/**
 07/09/16.
 */
public class Device extends SugarRecord {
    public String name;
    public String mac;

    public Device (){}

    public Device (String name, String mac) {
        this.name = name;
        this.mac = mac;
    }
}
