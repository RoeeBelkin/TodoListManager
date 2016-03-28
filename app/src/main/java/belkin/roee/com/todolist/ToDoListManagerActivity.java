package belkin.roee.com.todolist;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class ToDoListManagerActivity extends Activity {
    private Button addButton;
    private ListView lv;
    private TextView tv;
    private ArrayList<ListViewItems> strArr;
    int itemPosition;
    ArrayAdapter<ListViewItems> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);

        //init stuff
        addButton = (Button)findViewById(R.id.Add_button);
        tv = (TextView)findViewById(R.id.Input_txt);
        strArr = new ArrayList<ListViewItems>();
        adapter = new myAdapter();
        lv = (ListView)findViewById(R.id.listView);

        registerForContextMenu(lv);
        lv.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getText().length() > 0) {
                    strArr.add(new ListViewItems(tv.getText().toString()));
                    tv.setText("");
                    adapter.notifyDataSetChanged();
                }
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                return false;
            }
        });

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
        itemPosition = info.position;
       TextView txt = (TextView)info.targetView.findViewById(R.id.itemText_txtView);
       String headline = txt.getText().toString();

        menu.setHeaderTitle(""+headline);
        menu.add(menu.NONE, 0, menu.NONE, "DELETE");



    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        strArr.remove(itemPosition);
        adapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    private class myAdapter extends ArrayAdapter<ListViewItems> {
       public myAdapter(){
           super(ToDoListManagerActivity.this,R.layout.view_items,strArr);
       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                itemView = (getLayoutInflater()).inflate(R.layout.view_items,parent,false);
            }



            TextView txt = (TextView)itemView.findViewById(R.id.itemText_txtView);
            String currentStr = strArr.get(position).getItem();
            txt.setText(currentStr);

            if (position%2 == 0){
                txt.setTextColor(getResources().getColor(R.color.colorRed));
            }else {
                txt.setTextColor(getResources().getColor(R.color.colorBlue));
            }

            return itemView;
        }
    }



}

