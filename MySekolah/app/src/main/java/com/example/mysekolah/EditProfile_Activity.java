package com.example.mysekolah;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mysekolah.util.MyApplication;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class EditProfile_Activity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private TextView icText;
    private TextView nameText;
    private TextView genderText;
    private TextView raceText;
//    private Spinner raceSpinner;
//    private Spinner nationSpinner;
    private DatePicker datePicker;
    private Button dateBtn;
    private EditText jobEdit;
    private EditText salaryEdit;
    private EditText addressEdit;
    private EditText phoneEdit;
    private Button cancelBtn;
    private Button saveBtn;
    private int lastfragment;

    private ArrayList<String> races = new ArrayList<String>();
    private ArrayList<String> nations = new ArrayList<String>();

    private User currentUser;
    //private DatabaseHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        lastfragment=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
       // currentUser = MyApplication.currentUser;
       currentUser = (User) getIntent().getSerializableExtra("user");
       // DB = new DatabaseHelper(this);


        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        initViews();

//        prepareData();
//        setAdapter();
    }

    private void initViews() {
        //初始化view
        icText = findViewById(R.id.tv_IC);
        nameText = findViewById(R.id.tv_Name);
        genderText = findViewById(R.id.tv_gender);
        raceText = findViewById(R.id.tv_races);
//        raceSpinner = findViewById(R.id.spin_races);
//        nationSpinner = findViewById(R.id.spin_nationality);

        dateBtn = findViewById(R.id.et_bDate);
        jobEdit = findViewById(R.id.et_job);
        salaryEdit = findViewById(R.id.et_salary);
        addressEdit = findViewById(R.id.et_address);
        phoneEdit = findViewById(R.id.et_phoneno);
        cancelBtn = findViewById(R.id.btncancel);
        saveBtn = findViewById(R.id.btnsave);

        currentUser = (User) getIntent().getSerializableExtra("user");

        icText.setText(currentUser.getICNo());
        nameText.setText(currentUser.getName());
        genderText.setText(currentUser.getGender());
        raceText.setText(currentUser.getRace());
        jobEdit.setText(currentUser.getJob());
        salaryEdit.setText(currentUser.getSalary());
        addressEdit.setText(currentUser.getAddress());
        phoneEdit.setText(currentUser.getPhoneNo());
        dateBtn.setText(currentUser.getBdate());

        DatabaseAccess DB = DatabaseAccess.getInstance(this);
        DB.open();

        //日期点击事件
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                //获取日历的一个实例，里面包含了当前的年月日
                Calendar calendar=Calendar.getInstance();
                //构建一个日期对话框，该对话框已经集成了日期选择器
                //DatePickerDialog的第二个构造参数指定了日期监听器
                DatePickerDialog dialog=new DatePickerDialog(EditProfile_Activity.this,EditProfile_Activity.this,
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //把日期对话框显示在界面上
                dialog.show();
            }
        });

        //取消
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //保存
        saveBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                String job = jobEdit.getText().toString();
                String salary = salaryEdit.getText().toString();
                String address = addressEdit.getText().toString();
                String phone = phoneEdit.getText().toString();
                currentUser.setJob(job == null ? "":job);
                currentUser.setSalary(salary == null ? "":salary);
                currentUser.setAddress(address == null ? "":address);
                currentUser.setPhoneNo(phone == null ? "":phone);
                Boolean result = DB.updateUser(currentUser);

                //MyApplication.currentUser.setJob(job == null ? "":job);
               // MyApplication.currentUser.setSalary(salary == null ? "":salary);
                //MyApplication.currentUser.setAddress(address == null ? "":address);
                //MyApplication.currentUser.setPhoneNo(phone == null ? "":phone);
               // currentUser.setJob(job == null ? "":job);
                //currentUser.setSalary(salary == null ? "":salary);
                //currentUser.setAddress(address == null ? "":address);
                //currentUser.setPhoneNo(phone == null ? "":phone);
                if (result) {



                    Toast.makeText(EditProfile_Activity.this,"save successfully",Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(getApplicationContext(),MainActivity.class);
//                    intent.putExtra("user",currentUser);
//                    startActivity(intent);
                    Fragment selectedFragment = null;
                    selectedFragment = new ProfilePage();
                    //currentUser = MyApplication.currentUser;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();


                } else {
                    Toast.makeText(EditProfile_Activity.this,"save failed",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

//    private void prepareData() {
//        //准备数据
//        races.add("Chinese");
//        races.add("Malay");
//        races.add("Indian");
//        races.add("Others");
//
//        nations.add("Afghanistan");
//        nations.add("Albania" );
//        nations.add("Algeria" );
//        nations.add("Argentina" );
//        nations.add("Australia" );
//        nations.add("Austria" );
//        nations.add("Cambodia" );
//        nations.add("Canada" );
//        nations.add("Chile" );
//        nations.add("China" );
//        nations.add("Colombia *" );
//        nations.add("Czech Republic" );
//        nations.add("Denmark" );
//        nations.add("Dominican Republic" );
//        nations.add("Ecuador" );
//        nations.add("Egypt" );
//        nations.add("El Salvador" );
//        nations.add("England" );
//    }
//
//    private void setAdapter() {
//        //设置适配器
//        ArrayAdapter<String> raceAdapter = new ArrayAdapter<String>(EditProfile_Activity.this,android.R.layout.simple_spinner_item, races);
//        raceSpinner.setAdapter(raceAdapter);
//        raceSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedRace = races.get(position);
//                currentUser.setRace(selectedRace);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//
//        ArrayAdapter<String> nationAdapter = new ArrayAdapter<String>(EditProfile_Activity.this,android.R.layout.simple_spinner_item, nations);
//        nationSpinner.setAdapter(nationAdapter);
//        nationSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String selectedNation = nations.get(position);
//                currentUser.setRace(selectedNation);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        //实现日期选择器的监听
        //获取日期对话框设定的年月份
        String bdate = String.format("%d-%d-%d",i,i1+1,i2);
        dateBtn.setText(bdate);
        currentUser.setBdate(bdate);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_home:
                    selectedFragment = new HomePage_Student();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_home;
                    break;
                case R.id.nav_notif:
                    selectedFragment = new NotificationPage();
                    lastfragment = R.id.nav_notif;
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfilePage();
                    selectedFragment = new ProfilePage();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    //lastfragment = R.id.nav_profile;
                    break;
                case R.id.nav_search:
                    selectedFragment = new SearchPage_Student();
                    bundle = new Bundle();
                    bundle.putSerializable("user",currentUser);//这里的values就是我们要传的值
                    selectedFragment.setArguments(bundle);
                    lastfragment = R.id.nav_search;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return false;
        }
    };
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
