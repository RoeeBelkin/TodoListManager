package belkin.roee.com.todolist;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Date;


public class AddNewTodoItemActivity extends Activity {
    private Button okBtn;
    private Button cancelBtn;
    private DatePicker date;
    private EditText text;
//    Dialog addDialog;
//    private ListViewItems itemsToList;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_dialog);
//        openAddDialog();
//            showDialog(1);

        //init dialog
        okBtn = (Button)findViewById(R.id.btnOK);
        cancelBtn = (Button)findViewById(R.id.btnCancel);
        date = (DatePicker)findViewById(R.id.datePicker);
        text = (EditText)findViewById(R.id.addDialog_text_editText);


        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().length()>0) {
                    String itemTxt = (text.getText()).toString();
                    Intent data = new Intent();
                    if (date.getContext() == null){
                        data.putExtra("dueDate",-1);
                    }else{
                        Date dueDate = new Date(date.getYear()-1900,date.getMonth(),date.getDayOfMonth());
                        data.putExtra("dueDate",dueDate.getTime());
                    }


                    data.putExtra("title",itemTxt);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });

    }

//    @Override
//    protected Dialog onCreateDialog(int id) {
//       if(id == 1){
//           addDialog = new Dialog(AddNewTodoItemActivity.this);
//           addDialog.setContentView(R.layout.add_dialog);
//       }
//
//        return addDialog;
//    }

//    public void openAddDialog() {
//        Dialog addDialog = new Dialog(AddNewTodoItemActivity.this);
//        addDialog.setTitle("ADD ITEM");
//        addDialog.setContentView(R.layout.add_dialog);
//        addDialog.show();
//
//
//    }


}
