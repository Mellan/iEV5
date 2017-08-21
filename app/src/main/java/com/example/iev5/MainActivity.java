package com.example.iev5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public String phonenumber;
    private SQLiteDatabase database;
    private DBManager dbManager;
    public Button confirm;
    public Button button_chg;
    public Button button_stpchg;
    public Button button_ac;
    public Button button_stpac;
    public EditText editText;
    private Cursor cursor;
    private String VIN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText) findViewById(R.id.edittext);
        button_chg=(Button) findViewById(R.id.chg);
        button_stpchg=(Button) findViewById(R.id.stpchg);
        button_ac=(Button) findViewById(R.id.ac);
        button_stpac=(Button) findViewById(R.id.stpac);
        confirm=(Button) findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VIN=editText.getText().toString();
                if (TextUtils.isEmpty(VIN)||VIN.length()!=8){
                    Toast.makeText(getApplicationContext(), "请正确输入车架号后8位", Toast.LENGTH_SHORT).show();
                    button_chg.setEnabled(false);
                    button_ac.setEnabled(false);
                    button_stpac.setEnabled(false);
                    button_stpchg.setEnabled(false);
                }else{
                    dbManager = new DBManager(MainActivity.this);
                    database = dbManager.openDatabase(DBManager.DB_PATH + "/" + DBManager.DB_NAME);
                    VIN = editText.getText().toString();
                    cursor = database.rawQuery("select * from simcode where VIN=?", new String[]{VIN});
                    if (cursor.moveToFirst()) {
                        phonenumber = cursor.getString(cursor.getColumnIndex("simcode"));
                        Log.e("SIMCODE", phonenumber);
                        button_chg.setEnabled(true);
                        button_ac.setEnabled(true);
                        button_stpac.setEnabled(true);
                        button_stpchg.setEnabled(true);
                    }else{
                        Toast.makeText(getApplicationContext(),"未查询到该车架号对应信息,请核对车架号后8位是否正确",Toast.LENGTH_LONG).show();
                        button_chg.setEnabled(false);
                        button_ac.setEnabled(false);
                        button_stpac.setEnabled(false);
                        button_stpchg.setEnabled(false);
                    }
                    cursor.close();
                    database.close();
                }

            }
        });
        button_chg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Executor executor = new Executor();
                    Toast.makeText(getApplicationContext(), "立即开始充电", Toast.LENGTH_SHORT).show();
                    Log.d("Test", phonenumber);
                    executor.makeChargingSMS(phonenumber);
            }
        });
        button_stpchg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Executor executor = new Executor();
                    Toast.makeText(getApplicationContext(), "结束充电", Toast.LENGTH_SHORT).show();
                    executor.makeStopChargeSMS(phonenumber);
            }
        });
        button_stpac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Executor executor=new Executor();
                    Toast.makeText(getApplicationContext(),"关闭空调",Toast.LENGTH_SHORT).show();
                    executor.makeStopACSMS(phonenumber);}
        });
        button_ac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phonebumber1=phonenumber;
                final Executor executor=new Executor();
                String[] arraytemperature = new String[5];
                arraytemperature[0] = "取消操作";
                arraytemperature[1] = "20℃";
                arraytemperature[2] = "22℃";
                arraytemperature[3] = "24℃";
                arraytemperature[4] = "26℃";
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle("请选择启动空调温度");
                dialog.setItems(arraytemperature, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int temperature = 0;
                        switch (which) {
                            case 0:
                                Toast.makeText(getApplicationContext(), "取消操作", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                temperature = 20;
                                break;
                            case 2:
                                temperature = 22;
                                break;
                            case 3:
                                temperature = 24;
                                break;
                            case 4:
                                temperature = 26;
                                break;
                        }
                        if (temperature > 0 && phonebumber1.length()!=0) {
                            Toast.makeText(getApplicationContext(), String.format("开启空调中,设置空调温度:%d℃", Integer.valueOf(temperature)), Toast.LENGTH_SHORT).show();
                            executor.makeAutoAirConditionTemperatureSMS(phonebumber1, temperature);
                        } else {
                            Toast.makeText(getApplicationContext(), "请正确输入被控车辆SIM卡号", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialog.create().show();
            }
        });
    }
}
