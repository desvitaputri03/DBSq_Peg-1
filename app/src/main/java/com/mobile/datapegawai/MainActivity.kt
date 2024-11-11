package com.mobile.datapegawai

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobile.datapegawai.helper.DbHelper

class MainActivity : AppCompatActivity() {

    private lateinit var etnama: EditText
    private lateinit var etnamakampus: EditText
    private lateinit var etemail: EditText
    private lateinit var etphone: EditText
    private lateinit var submit: Button
    private lateinit var showData: Button
    private lateinit var txtnama: TextView
    private lateinit var txtkampus: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etnama = findViewById(R.id.etnama)
        etnamakampus = findViewById(R.id.etnamakampus)
        etemail = findViewById(R.id.etemail)
        etphone = findViewById(R.id.etphone)
        submit = findViewById(R.id.btnSubmit)
        showData = findViewById(R.id.btnShowData)
        txtnama = findViewById(R.id.txtnama)
        txtkampus = findViewById(R.id.txtkampus)

        submit.setOnClickListener {
            val db = DbHelper(this, null)

            // Mengambil data dari EditText
            val name = etnama.text.toString()
            val kampus = etnamakampus.text.toString()
            val email = etemail.text.toString()
            val phone = etphone.text.toString()

            db.addName(name, kampus, email, phone)

            Toast.makeText(this, "$name berhasil ditambahkan ke database", Toast.LENGTH_LONG).show()

            // Mengosongkan EditText
            etnama.text.clear()
            etnamakampus.text.clear()
            etemail.text.clear()
            etphone.text.clear()
        }

        showData.setOnClickListener {
            val db = DbHelper(this, null)
            val cursor = db.getName()

            cursor?.let {
                txtnama.text = ""
                txtkampus.text = ""

                if (cursor.moveToFirst()) {
                    do {
                        txtnama.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_LENGKAP)) + "\n")
                        txtkampus.append(cursor.getString(cursor.getColumnIndexOrThrow(DbHelper.NAMA_KAMPUS)) + "\n")
                    } while (cursor.moveToNext())
                }

                cursor.close()
            } ?: run {
                Toast.makeText(this, "Tidak ada data yang ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }
}