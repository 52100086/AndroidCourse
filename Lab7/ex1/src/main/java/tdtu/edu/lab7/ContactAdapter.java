package tdtu.edu.lab7;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.MyViewHolder> {
    private Cursor mCursor;
    private Context context;

    public ContactAdapter(Cursor cursor, Context context) {

        mCursor = cursor;
        this.context = context;
    }

    private void callPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        context.startActivity(intent);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        int nameColumnIndex = mCursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME);
        String name = mCursor.getString(nameColumnIndex);
        holder.name.setText(name);


        int phoneColumnIndex = mCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
        String phoneNumber = mCursor.getString(phoneColumnIndex);
        holder.contact_phone.setText(phoneNumber);
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }



    Cursor getContacts(String query) {
        return context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " LIKE ?",
                new String[]{"%" + query + "%"},
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView contact_phone;
        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.contact_name);
            contact_phone = (TextView) view.findViewById(R.id.contact_phone);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mCursor.moveToPosition(position);
                    int phoneColumnIndex = mCursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER);
                    String phoneNumber = mCursor.getString(phoneColumnIndex);
                    callPhoneNumber(phoneNumber);
                }
            });

        }


    }

}

