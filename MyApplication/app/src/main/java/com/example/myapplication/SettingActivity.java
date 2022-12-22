package com.example.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener {
    int Sex=0;
    Button Male;
    Button Fmale;
    Button Saving;
    Spinner spinner;
    String activities="Once a week";
    MainActivity mainAct;
    String malebotton="#0057ad";
    String fmalebutton="#0057ad";
    EditText etText;
    String weight;
    SharedPreferences prefs = null;
    SharedPreferences spref;
    final String SAVED_TEXT = "Weight";
    public boolean isFirstStart=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_setting);
        Male = findViewById(R.id.button7);
        Fmale= findViewById(R.id.button8);
        Saving=findViewById(R.id.savingButton);
        spinner = findViewById(R.id.spinner7);

        ArrayAdapter<?> adapter = ArrayAdapter.createFromResource(this, R.array.activity, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        AdapterView.OnItemSelectedListener itemSelectedListener = new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Получаем выбранный объект
                activities = (String)parent.getItemAtPosition(position);
               ArrayAdapter adapter=(ArrayAdapter) spinner.getAdapter();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };

        spinner.setOnItemSelectedListener(itemSelectedListener);
        Saving.setOnClickListener(this);
        Male.setOnClickListener(this);
        Fmale.setOnClickListener(this);
        spref= getSharedPreferences("Settings",0);
        etText = findViewById(R.id.editTextNumberSigned);

        etText = findViewById(R.id.editTextNumberSigned);
        etText.setFilters(new InputFilter[]{ new InputFilterMinMax("1", "200")});


    }

    protected void onStart() {
        super.onStart();
        getData();
        Male.setBackgroundColor(Color.parseColor(malebotton));
        Fmale.setBackgroundColor(Color.parseColor(fmalebutton));
        switch (activities){
            case "1 час":
                spinner.setSelection(0);
                break;
            case "3 часа":
                spinner.setSelection(2);
                break;
            case "2 часа":
                spinner.setSelection(1);
                break;
            case "4 часа":
                spinner.setSelection(3);
                break;
            case "5 часов":
                spinner.setSelection(4);
                break;
            case "6 часов":
                spinner.setSelection(5);
                break;
            case "7 часов":
                spinner.setSelection(6);
                break;
            default:
                spinner.setSelection(0);
                break;
        }
    }

    protected void onResume(){
        super.onResume();
        if(Sex==0){
            getData();
            Male.setBackgroundColor(Color.parseColor(malebotton));
            Fmale.setBackgroundColor(Color.parseColor(fmalebutton));
            switch (activities){
                case "1 час":
                    spinner.setSelection(0);
                    break;
                case "3 часа":
                    spinner.setSelection(2);
                    break;
                case "2 часа":
                    spinner.setSelection(1);
                    break;
                case "4 часа":
                    spinner.setSelection(3);
                    break;
                case "5 часов":
                    spinner.setSelection(4);
                    break;
                case "6 часов":
                    spinner.setSelection(5);
                    break;
                case "7 часов":
                    spinner.setSelection(6);
                    break;
                default:
                    spinner.setSelection(0);
                    break;
            }
        }
        if (spref.getBoolean("FirstRun", true)){
            AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
            builder.setTitle("Приветствуем вас!")
                    .setMessage("Это приложение для подсчета количества выпитых стаканов. Оно поможет вам следить за водным балансом. Перед тем как приступить, вам нужно ввести свои данные. Для продолжения нажмите кнопку (Ок)")
                    .setIcon(R.drawable.logo)
                    .setCancelable(false)
                    .setNegativeButton("ОК",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alert = builder.create();
            alert.show();
            spref.edit().putBoolean("FirstRun", false).commit();
        }
    }

    protected void onPause(){
        super.onPause();
        saveData();
    }

    protected void onStop(){
        super.onStop();
        saveData();
    }

    public void BackOn(View v){
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button7:
                    Male.setBackgroundColor(Color.parseColor("#002447"));
                    Fmale.setBackgroundColor(Color.parseColor("#0057ad"));
                    Sex=2;
                    malebotton="#002447";
                    fmalebutton="#0057ad";
                    break;
                case R.id.button8:
                    Male.setBackgroundColor(Color.parseColor("#0057ad"));
                    Fmale.setBackgroundColor(Color.parseColor("#002447"));
                    Sex=1;
                    fmalebutton="#002447";
                    malebotton="#0057ad";
                    break; 
                case R.id.savingButton:
                    saveData();
                    break;
                default:
                    break;

            }
        }

    private void saveData() {
        SharedPreferences.Editor editor= spref.edit();
        editor.clear();
        editor.putInt("Sex",Sex);
        editor.putString("malebutton",malebotton);
        editor.putString("fmalebutton",fmalebutton);
        editor.putString("Activity",activities);
        editor.putString("Weight", etText.getText().toString());
        weight =  etText.getText().toString();
        editor.putBoolean("FirstRun", false);
        editor.apply();

    }
    private void getData(){
        Sex=spref.getInt("Sex",0);
        malebotton=spref.getString("malebutton","#0057ad");
        fmalebutton= spref.getString("fmalebutton","#0057ad");
        activities = spref.getString("Activity","0");
        weight = spref.getString("Weight", "");
        etText.setText(weight);
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
