package tdtu.edu.lab8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tdtu.edu.lab8.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements EditStudent.OnStudentDataChangedListener  {

    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_add){
            Intent intent = new Intent(MainActivity.this, AddStudent.class);
            startActivity(intent);
            return true;
        }

        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerView = binding.recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ApiResponse> call = api.getStudents();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<Student> students = response.body().getData();
                    adapter = new StudentAdapter(students);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, "Failed to connect", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onStudentDataChanged() {
        fetchData();
    }


    public void fetchData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<ApiResponse> call = api.getStudents();
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    List<Student> students = response.body().getData();
                    adapter = new StudentAdapter(students);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Handle the error
            }
        });
    }


}
