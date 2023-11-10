package tdtu.edu.lab5;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.ViewHolder> {

    private List<Phone> phones;
    private Context context;

    private OnItemCheckListener onItemCheckListener;
    public void setOnItemCheckListener(OnItemCheckListener listener) {
        this.onItemCheckListener = listener;
    }


    public PhoneAdapter(List<Phone> phones, Context context) {
        this.phones = phones;
        this.context = context;
    }


    @NonNull
    @Override
    public PhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneAdapter.ViewHolder holder, int position) {
        Phone phone = phones.get(position);
        holder.textView.setText(phone.getName());


        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    onItemCheckListener.onItemCheck(phone);
                } else {
                    onItemCheckListener.onItemUncheck(phone);
                }
            }
        });

        holder.checkBox.setChecked(phone.isChecked());

    }


    @Override
    public int getItemCount() {
        return phones.size();
    }



    public interface OnItemCheckListener {
        void onItemCheck(Phone phone);
        void onItemUncheck(Phone phone);
    }
    public void removeAt(int position) {
        phones.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, phones.size());
    }

    public void removeAll() {
        phones.clear();
        notifyDataSetChanged();
    }

    public void setAllChecked() {
        for (Phone phone : phones) {
            if(phone.isChecked()){
                phone.setChecked(false);
            }else{
                phone.setChecked(true);
            }
        }
        notifyDataSetChanged();
    }






    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        CheckBox checkBox;
        public ViewHolder(@NonNull View itemView){
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            checkBox = itemView.findViewById(R.id.checkbox_phone);

        }
    }

}


