package tdtu.edu.lab8;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tdtu.edu.lab8.Student;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {
    private static List<Student> students;

    public StudentAdapter(List<Student> students ) {
        this.students = students;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Student student = students.get(position);
        holder.studentId.setText(Integer.toString(student.getId()));
        holder.studentName.setText(student.getName());
        holder.studentEmail.setText(student.getEmail());

    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView studentId;
        public TextView studentName;
        public TextView studentEmail;

        public ViewHolder(View itemView) {
            super(itemView);
            studentId = itemView.findViewById(R.id.student_id);
            studentName = itemView.findViewById(R.id.student_name);
            studentEmail = itemView.findViewById(R.id.student_email);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showMenu(v, getAdapterPosition());
                    return true;
                }
            });
            final GestureDetector gestureDetector = new GestureDetector(itemView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    // Handle the double tap event
                    Intent intent = new Intent(itemView.getContext(), EditStudent.class);
                    intent.putExtra("student", students.get(getAdapterPosition()));
                    itemView.getContext().startActivity(intent);
                    return true;
                }
            });

            itemView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return gestureDetector.onTouchEvent(event);
                }
            });
        }



    }


    private static void showMenu(View view, int position) {
        PopupMenu popupMenu = new PopupMenu(view.getContext(), view);
        popupMenu.getMenuInflater().inflate(R.menu.context_menu, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.edit){
                    Intent intent = new Intent(view.getContext(), EditStudent.class);
                    intent.putExtra("student", students.get(position));
                    view.getContext().startActivity(intent);
                    return true;
                } else if(item.getItemId() == R.id.delete){
                    new AlertDialog.Builder(view.getContext())
                            .setTitle("Confirm Delete")
                            .setMessage("Are you sure you want to delete this student?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Delete the student
                                    deleteStudent(students.get(position));
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                    return true;
                }
                return false;
            }


            private void deleteStudent(Student student) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Call<ApiResponse> call = api.deleteStudent(student.getId());
                call.enqueue(new Callback<ApiResponse>() {
                    @Override
                    public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(view.getContext(), "Student deleted successfully", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponse> call, Throwable t) {
                        // Handle the error
                        Toast.makeText(view.getContext(), "Failed to delete student", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        popupMenu.show();
    }






}
