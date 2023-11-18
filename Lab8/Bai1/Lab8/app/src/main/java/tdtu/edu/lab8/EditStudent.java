package tdtu.edu.lab8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tdtu.edu.lab8.databinding.ActivityEditStudentBinding;

public class EditStudent extends AppCompatActivity {

    ActivityEditStudentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditStudentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Student student = getIntent().getParcelableExtra("student");

        binding.studentId.setText(Integer.toString(student.getId()));
        binding.editName.setText(student.getName());
        binding.editEmail.setText(student.getEmail());
        binding.editPhone.setText(student.getPhone());

        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<ApiResponse> call = api.updateStudent(student.getId(), binding.editName.getText().toString(), binding.editEmail.getText().toString(), binding.editPhone.getText().toString());
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(EditStudent.this, "Data updated successfully", Toast.LENGTH_SHORT).show();
                            if (listener != null) {
                                listener.onStudentDataChanged();
                            }
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        Toast.makeText(EditStudent.this, "Failed to update data", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public interface OnStudentDataChangedListener {
        void onStudentDataChanged();
    }

    private OnStudentDataChangedListener listener;

    public void setOnStudentDataChangedListener(OnStudentDataChangedListener listener) {
        this.listener = listener;
    }


}
