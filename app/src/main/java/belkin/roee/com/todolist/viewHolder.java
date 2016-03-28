package belkin.roee.com.todolist;

import android.view.View;
import android.widget.TextView;


/**
 * Created by Roee on 05/05/2016.
 */
public class viewHolder {
    TextView itemText;

    public viewHolder(View v) {
       itemText =(TextView) v.findViewById(R.id.Input_txt);
    }
}
