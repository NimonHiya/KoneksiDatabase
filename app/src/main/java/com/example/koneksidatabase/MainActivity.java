package com.example.koneksidatabase;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity; // Updated to AndroidX
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.koneksidatabase.DataHelper;
import com.example.koneksidatabase.R;

public class MainActivity extends AppCompatActivity {

    String[] daftar;
    ListView ListView01;
    Menu menu;
    protected Cursor cursor;
    DataHelper dbcenter;
    public static MainActivity ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button to navigate to "Buat" activity
        Button btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent inte = new Intent(MainActivity.this, Buat.class);
                startActivity(inte);
            }
        });

        // Initialize the database helper
        ma = this;
        dbcenter = new DataHelper(this);
        RefreshList(); // Populate the list
    }

    // Method to refresh the ListView
    public void RefreshList() {
        SQLiteDatabase db = dbcenter.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata", null);
        daftar = new String[cursor.getCount()]; // Array to store list items

        cursor.moveToFirst();
        for (int cc = 0; cc < cursor.getCount(); cc++) {
            cursor.moveToPosition(cc);
            daftar[cc] = cursor.getString(1); // Populate 'daftar' with 'nama'
        }

        ListView01 = findViewById(R.id.listView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, daftar);
        ListView01.setAdapter(adapter); // Set adapter for the ListView
        ListView01.setSelected(true);

        // Set item click listener for the ListView
        ListView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                final String selection = daftar[arg2]; // Selected item

                final CharSequence[] dialogitem = {"Lihat Biodata", "Update Biodata", "Hapus Biodata"};

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Pilihan");
                builder.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        switch (item) {
                            case 0: // View Biodata
                                Intent i = new Intent(getApplicationContext(), Lihat_Bio.class);
                                i.putExtra("nama", selection);
                                startActivity(i);
                                break;
                            case 1: // Update Biodata
                                Intent in = new Intent(getApplicationContext(), Update.class);
                                in.putExtra("nama", selection);
                                startActivity(in);
                                break;
                            case 2: // Delete Biodata
                                SQLiteDatabase db = dbcenter.getWritableDatabase();
                                db.execSQL("DELETE FROM biodata WHERE nama = ?", new String[]{selection});
                                RefreshList(); // Refresh after deletion
                                break;
                        }
                    }
                });
                builder.create().show();
            }
        });

        // Notify the adapter to update the ListView
        ((ArrayAdapter) ListView01.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
