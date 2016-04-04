package belkin.roee.com.todolist;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.view.View;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;

public class ToDoListManagerActivity extends Activity {
   private static final int REQUEST_CODE_ADD_DIALOG = 1;
    private Button addButton;
    private ListView lv;
//    private TextView tv;
    private ArrayList<ListViewItems> strArr;
    int itemPosition;
    ArrayAdapter<ListViewItems> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_list_manager);

        //init stuff
        addButton = (Button)findViewById(R.id.Add_button);
//        tv = (TextView)findViewById(R.id.Input_txt);
        strArr = new ArrayList<ListViewItems>();
        adapter = new myAdapter();
        lv = (ListView)findViewById(R.id.listView);

        registerForContextMenu(lv);
        lv.setAdapter(adapter);


        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (tv.getText().length() > 0) {
//                    strArr.add(new ListViewItems(tv.getText().toString()));
//                    tv.setText("");
//                    adapter.notifyDataSetChanged();
//                }
                openAddDialog(v);
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                //open the dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(ToDoListManagerActivity.this);
                builder.setMessage(strArr.get(position).getItem());

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    //pressed cancel
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.setPositiveButton("Delete Item", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //remove item
                        strArr.remove(position);
                        adapter.notifyDataSetChanged();
                        dialog.cancel();
                    }
                });
                //add button if the title start with Call
                if (strArr.get(position).getItem().startsWith("Call ")) {
                    builder.setNeutralButton(strArr.get(position).getItem(), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //call
                            Intent dial = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse("tel:" + strArr.get(position).getItem().substring(5)));
                            startActivity(dial);
                            dialog.cancel();
                        }
                    });
                }

                AlertDialog alert = builder.create();
                alert.show();
                return true;
            }
        });

    }

    public void openAddDialog(View v){
        Intent intent = new Intent(ToDoListManagerActivity.this,AddNewTodoItemActivity.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_DIALOG);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_ADD_DIALOG){
            if (resultCode == RESULT_OK){

                String txt = data.getStringExtra("title");
                long dueDate = data.getLongExtra("dueDate",-1);
                ListViewItems newItem = new ListViewItems(txt, dueDate);

                strArr.add(newItem);
                adapter.sort(new Comparator<ListViewItems>() {
                    @Override
                    public int compare(ListViewItems lhs, ListViewItems rhs) {
                        if (lhs.getDueDate()-rhs.getDueDate() <0) {
                            return -1;
                        }
                        return 1;
                    }
                });

                adapter.notifyDataSetChanged();
            }

        }

//        super.onActivityResult(requestCode, resultCode, data);
    }

//    @Override
//    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//
//        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
//        itemPosition = info.position;
//       TextView txt = (TextView)info.targetView.findViewById(R.id.itemText_txtView);
//       String headline = txt.getText().toString();
//
//        menu.setHeaderTitle("" + headline);
//        menu.add(menu.NONE, 0, menu.NONE, "DELETE");
//
//
//
//
//    }
//
//    @Override
//    public boolean onContextItemSelected(MenuItem item) {
//
//        strArr.remove(itemPosition);
//        adapter.notifyDataSetChanged();
//        return super.onContextItemSelected(item);
//    }

    private class myAdapter extends ArrayAdapter<ListViewItems> {
       public myAdapter(){
           super(ToDoListManagerActivity.this, R.layout.view_items, strArr);

       }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null){
                itemView = (getLayoutInflater()).inflate(R.layout.view_items,parent,false);

            }
            TextView txt = (TextView)itemView.findViewById(R.id.itemText_txtView);
            TextView dueDate = (TextView)itemView.findViewById(R.id.itemDueDate);
            String currentStr = strArr.get(position).getItem();
            long currentDueDate = strArr.get(position).getDueDate();
            if (currentDueDate == -1){
                dueDate.setText("No Due Date");
            }else{
                Date d = new Date(getItem(position).getDueDate());
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                dueDate.setText(dateFormat.format(d));
            }

            txt.setText(currentStr);

            if (isEarlier(currentDueDate)){
                txt.setTextColor(getResources().getColor(R.color.colorRed));
                dueDate.setTextColor(getResources().getColor(R.color.colorRed));
            }else {
                txt.setTextColor(getResources().getColor(R.color.colorBlue));
                dueDate.setTextColor(getResources().getColor(R.color.colorBlue));
            }



            return itemView;
        }

        private boolean isEarlier(long currentDueDate) {
            if (currentDueDate<Calendar.getInstance().getTime().getTime()) {
                return true;
            }
            return  false;
        }


    }



}

