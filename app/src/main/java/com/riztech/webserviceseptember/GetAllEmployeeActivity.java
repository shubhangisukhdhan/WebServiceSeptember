package com.riztech.webserviceseptember;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.riztech.webserviceseptember.adapter.EmployeeClientListener;
import com.riztech.webserviceseptember.adapter.EmployeeListAdapter;
import com.riztech.webserviceseptember.model.BaseResponse;
import com.riztech.webserviceseptember.model.Employee;
import com.riztech.webserviceseptember.service.ApiClient;
import com.riztech.webserviceseptember.service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllEmployeeActivity extends AppCompatActivity implements EmployeeClientListener {

RecyclerView rvEmployees;
EmployeeListAdapter employeeListAdapter;
ProgressBar progress;
ApiInterface apiInterface;

List<Employee>employees;


public static final String DATA = "data";
public static final int REQUEST_UPDATE=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_employee);

     rvEmployees = findViewById(R.id.rvEmployees);
     progress=findViewById(R.id.progress);
     rvEmployees.setLayoutManager(new LinearLayoutManager(this));

       apiInterface = ApiClient.getClient().create(ApiInterface.class);
       fetchAllEmployee();

    }

    private void fetchAllEmployee() {
        Call<List<Employee>> callEmployee = apiInterface.getAllEmployees();
        progress.setVisibility(View.VISIBLE);

        callEmployee.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                progress.setVisibility(View.GONE);

                employees = response.body();
                employeeListAdapter = new
                        EmployeeListAdapter(employees,GetAllEmployeeActivity.this);
                rvEmployees.setAdapter(employeeListAdapter);
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {

                progress.setVisibility(View.GONE);

            }
        });

    }

    @Override
    public void onDeleteClick(int position) {
       final Employee employee = employeeListAdapter.getItemAtPosition(position);

       int employeeId = employee.getId();
       Call<BaseResponse> callDelete = apiInterface.deleteEmployees(employeeId);

       progress.setVisibility(View.VISIBLE);

         callDelete.enqueue(new Callback<BaseResponse>() {
           @Override

           public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
               progress.setVisibility(View.GONE);

               BaseResponse baseResponse = response.body();
               employees.remove(employee);
               employeeListAdapter.notifyDataSetChanged();
               Toast.makeText(GetAllEmployeeActivity.this,baseResponse.getMessage(),Toast.LENGTH_SHORT).show();

           }

           @Override
           public void onFailure(Call<BaseResponse> call, Throwable t) {
               progress.setVisibility(View.GONE);

           }
       });

    }

    @Override
    public void onItemClick(int position) {

        Employee employee = employeeListAdapter.getItemAtPosition(position);
        Intent intent = new Intent(this,UpdateActivity.class);
        intent.putExtra(DATA,employee);
        startActivityForResult(intent,REQUEST_UPDATE);
       // Toast.makeText(this,"Click position",Toast.LENGTH_SHORT).show();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_UPDATE && requestCode == RESULT_OK){
            fetchAllEmployee();
        }
    }
}
