package belkin.roee.com.todolist;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.widget.DatePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Roee on 05/05/2016.
 */
public class ListViewItems  {
    private String item;

    private long dueDate;

    public ListViewItems(String item, long dueDate) {
        this.item = item;
        this.dueDate = dueDate;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getDueDate() {
        return dueDate;
    }

    public void setDueDate(long dueDate) {
        this.dueDate = dueDate;
    }
}
