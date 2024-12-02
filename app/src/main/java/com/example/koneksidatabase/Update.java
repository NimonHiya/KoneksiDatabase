package com.example.koneksidatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import androidx.appcompat.app.AppCompatActivity; // AndroidX import
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.koneksidatabase.DataHelper;
import com.example.koneksidatabase.R;

public class Update extends AppCompatActivity {

    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton1, ton2;
    EditText text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        dbHelper = new DataHelper(this);

        // Initialize the EditTexts and Buttons
        text1 = findViewById(R.id.editTextup1); // 'No' field
        text2 = findViewById(R.id.editTextup2); // 'Nama' field
        text3 = findViewById(R.id.editTextup3); // 'Tanggal Lahir' field
        text4 = findViewById(R.id.editTextup4); // 'Jenis Kelamin' field
        text5 = findViewById(R.id.editTextup5); // 'Alamat' field

        // Fetch existing data based on 'nama' received in intent
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = ?", new String[]{getIntent().getStringExtra("nama")});

        if (cursor.moveToFirst()) {
            // Set values to the EditTexts from the cursor
            text1.setText(cursor.getString(0)); // No
            text2.setText(cursor.getString(1)); // Nama
            text3.setText(cursor.getString(2)); // Tanggal Lahir
            text4.setText(cursor.getString(3)); // Jenis Kelamin
            text5.setText(cursor.getString(4)); // Alamat
        }

        // Button to update the data
        ton1 = findViewById(R.id.button1);
        ton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // Update data in the database
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL(getString(R.string.update_biodata_set_nama_tgl_jk_alamat_where_no),
                        new String[]{
                                text2.getText().toString(), // Nama
                                text3.getText().toString(), // Tanggal Lahir
                                text4.getText().toString(), // Jenis Kelamin
                                text5.getText().toString(), // Alamat
                                text1.getText().toString()  // No (primary key)
                        });
                Toast.makeText(getApplicationContext(), "Update successful", Toast.LENGTH_LONG).show();
                MainActivity.ma.RefreshList(); // Assuming MainActivity still has this method
                finish();
            }
        });

        // Button to cancel and finish the activity
        ton2 = findViewById(R.id.button2);
        ton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if present
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}
