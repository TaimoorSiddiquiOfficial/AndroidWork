package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Students extends AppCompatActivity {

    MySqliteDb db;
    ArrayList<Student> lst=new ArrayList<Student>();
    ListView listView;
    StudentAdapter  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students);
        db=new MySqliteDb(this);
        listView=findViewById(R.id.listview);
        adapter=new StudentAdapter(lst,this);
        listView.setAdapter(adapter);
        fetchStudents();

    }
    void fetchStudents()
    {
        lst.clear();
        Cursor cursor=db.getStudents();
        while (cursor.moveToNext())
        {
            String id=cursor.getString(0);
            String name=cursor.getString(1);
            String phone=cursor.getString(2);
            String email=cursor.getString(3);
            String password=cursor.getString(4);
            Student std=new Student(id,name,phone,email,password);
            lst.add(std);
        }
        adapter.notifyDataSetChanged();
    }

    class StudentAdapter extends BaseAdapter
    {
        ArrayList<Student> lst;
        Context context;
        LayoutInflater inflater;

        public StudentAdapter(ArrayList<Student> lst, Context context) {
            this.lst = lst;
            this.context = context;
            inflater= (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return lst.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v=inflater.inflate(R.layout.stdlayout,null);
            TextView txtId=v.findViewById(R.id.stdId);
            TextView txtName=v.findViewById(R.id.stdName);
            TextView txtPhone=v.findViewById(R.id.stdPhone);
            TextView txtEmail=v.findViewById(R.id.stdEmail);
            ImageView stdImg=v.findViewById(R.id.stdImg);
            ImageView btnDelete=v.findViewById(R.id.img_btn_delete);
            ImageView btnEdit=v.findViewById(R.id.img_btn_edit);

            Student obj=lst.get(position);
            txtId.setText(obj.Id);
            txtName.setText(obj.Name);
            txtPhone.setText(obj.Phone);
            txtEmail.setText(obj.Email);

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   boolean isDeleted=db.deleteStudent(obj.Id);
                   if(isDeleted)
                   {
                       Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                       fetchStudents();
                   }

                }
            });

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }
    }

}







