package com.riztech.webserviceseptember;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.riztech.webserviceseptember.model.Employee;

public class SearchUpdateActivity extends AppCompatActivity {
    EditText edtId,edtName, edtAddress, edtPhoneNumber, edtSalary, edtDesignation;
    Button btnSearch,btnUpdate;
    ProgressBar progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_update);

        edtId=findViewById(R.id.edtId);
        edtAddress = findViewById(R.id.edtAddress);
        edtName = findViewById(R.id.edtName);
        edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtSalary = findViewById(R.id.edtSalary);
        edtDesignation = findViewById(R.id.edtDesignation);
        btnSearch=findViewById(R.id.btnSearch);
        btnUpdate=findViewById(R.id.btnUpdate);
        progress = findViewById(R.id.progress);


    }

    public void searchEmployee(View view) {

        String id = edtId.getText().toString().trim();



    }

    public void updateEmployee(View view) {

        String id = edtId.getText().toString().trim();

        String name = edtName.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        String phoneNumber = edtPhoneNumber.getText().toString().trim();
        String salString = edtSalary.getText().toString().trim();
        String designation = edtDesignation.getText().toString().trim();

        long salary = Long.parseLong(salString);

        Employee employee = new Employee(name, address, phoneNumber, salary, designation);


    }
}
