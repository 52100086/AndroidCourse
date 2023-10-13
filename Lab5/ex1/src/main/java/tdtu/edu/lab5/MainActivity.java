package tdtu.edu.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import tdtu.edu.lab5.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    private RecyclerView recyclerView;
    private List<Phone> phones;

    PhoneAdapter adapter;

    private List<Phone> selectedPhones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        phones = new ArrayList<Phone>();
        selectedPhones = new ArrayList<Phone>();
        recyclerView = binding.recyclerView;
        phones.add(new Phone("Apple"));
        phones.add(new Phone("Samsung"));
        phones.add(new Phone("Nokia"));
        phones.add(new Phone("Oppo"));

        adapter = new PhoneAdapter(phones, this);

        adapter.setOnItemCheckListener(new PhoneAdapter.OnItemCheckListener() {
            @Override
            public void onItemCheck(Phone phone) {
                selectedPhones.add(phone);
            }
            @Override
            public void onItemUncheck(Phone phone) {
                selectedPhones.remove(phone);
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.check_all) {
            adapter.setAllChecked();
            return true;
        } else if (item.getItemId() == R.id.delete_selected) {
            for (Phone phone : selectedPhones) {
                int position = phones.indexOf(phone);
                adapter.removeAt(position);
            }
            selectedPhones.clear();
            return true;
        } else if (item.getItemId() == R.id.delete_all) {
            adapter.removeAll();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

}