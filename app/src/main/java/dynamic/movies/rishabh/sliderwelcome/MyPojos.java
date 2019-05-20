package dynamic.movies.rishabh.sliderwelcome;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

/**
 * Created by RISHABH on 5/14/2016.
 */
public class MyPojos extends RealmObject {

    @PrimaryKey @Required
    private String id;

    private String data;
    private String date;
    private String time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public String getDate() {
        return date;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        setDate(df.format(c.getTime()));
        df = new SimpleDateFormat("HH:mm");
        setTime(df.format(c.getTime()));
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
