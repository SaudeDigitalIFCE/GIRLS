package ifce.lara.vite.object;

import android.widget.EditText;

import com.orm.SugarRecord;

/**
 * Created by otonbraga on 07/09/16.
 */
public class UserInfo extends SugarRecord {
    public String name;
    public String disiase;
    public String allergy;
    public String medicines;
    public String blood;
    public String phone;

    public UserInfo () {}

    public UserInfo(String name, String disiase, String allergy, String medicines, String blood, String phone) {
        this.name = name;
        this.disiase = disiase;
        this.allergy = allergy;
        this.medicines = medicines;
        this.blood = blood;
        this.phone = phone;
    }
}
