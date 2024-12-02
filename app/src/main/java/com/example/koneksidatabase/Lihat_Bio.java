package com.example.koneksidatabase;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class Lihat_Bio extends AppCompatActivity {
    protected Cursor cursor;
    DataHelper dbHelper;
    Button ton2;
    TextView text1, text2, text3, text4, text5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_bio); // Menghubungkan ke layout activity_lihat_bio.xml

        dbHelper = new DataHelper(this); // Inisialisasi DataHelper
        text1 = findViewById(R.id.textViewliat1); // Menghubungkan TextView untuk No
        text2 = findViewById(R.id.textViewliat2); // Menghubungkan TextView untuk Nama
        text3 = findViewById(R.id.textViewliat3); // Menghubungkan TextView untuk Tanggal Lahir
        text4 = findViewById(R.id.textViewliat4); // Menghubungkan TextView untuk Jenis Kelamin
        text5 = findViewById(R.id.textViewliat5); // Menghubungkan TextView untuk Alamat
        ton2 = findViewById(R.id.button1); // Menghubungkan Button Kembali

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // Query untuk mengambil data berdasarkan nama yang dikirimkan melalui Intent
        cursor = db.rawQuery("SELECT * FROM biodata WHERE nama = ?", new String[]{getIntent().getStringExtra("nama")});
        cursor.moveToFirst(); // Pindah ke baris pertama hasil query

        if (cursor.getCount() > 0) {
            // Mengatur data dari cursor ke TextView
            text1.setText(cursor.getString(0)); // Menampilkan No
            text2.setText(cursor.getString(1)); // Menampilkan Nama
            text3.setText(cursor.getString(2)); // Menampilkan Tanggal Lahir
            text4.setText(cursor.getString(3)); // Menampilkan Jenis Kelamin
            text5.setText(cursor.getString(4)); // Menampilkan Alamat
        }

        // Set event listener untuk tombol kembali
        ton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                finish(); // Menutup activity dan kembali ke activity sebelumnya
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; ini menambahkan item ke action bar jika ada
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
}