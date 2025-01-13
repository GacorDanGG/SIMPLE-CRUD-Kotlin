package com.example.P9_2741

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pertemuan9_12.DataModelClass
import com.example.pertemuan9_12.DatabaseHandler
import com.example.pertemuan9_12.ItemAdapter
import com.example.pertemuan9_12.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_simpan).setOnClickListener {
            tambahData()
            aturDataRecyclerView()
        }
    }

    private fun tambahData() {
        val etNama = findViewById<EditText>(R.id.et_nama)
        val etNim = findViewById<EditText>(R.id.et_nim)

        val nama = etNama.text.toString()
        val nim = etNim.text.toString()

        val databaseHandler = DatabaseHandler(this)

        if (nama.isNotEmpty() && nim.isNotEmpty()) {
            val status = databaseHandler.tambahMahasiswa(DataModelClass(0, nama, nim))

            if (status > -1) {
                Toast.makeText(applicationContext, "Data disimpan", Toast.LENGTH_LONG).show()

                etNama.text.clear()
                etNim.text.clear()
            }
        } else {
            Toast.makeText(applicationContext, "Nama atau NIM tidak boleh kosong", Toast.LENGTH_LONG).show()
        }
    }

    private fun aksesItemData(): ArrayList<DataModelClass> {
        val databaseHandler = DatabaseHandler(this)
        return databaseHandler.tampilMahasiswa()
    }

    private fun aturDataRecyclerView() {
        val rv_ItemData = findViewById<RecyclerView>(R.id.rv_ItemData)
        val tvNoRecordsAvailable = findViewById<TextView>(R.id.tvNoRecordsAvailable)
        if (aksesItemData().size > 0) {
            rv_ItemData.visibility = View.VISIBLE
            tvNoRecordsAvailable.visibility = View.GONE
            rv_ItemData.layoutManager = LinearLayoutManager(this)
            val itemAdapter = ItemAdapter(this, aksesItemData())
            rv_ItemData.adapter = itemAdapter
        } else {
            rv_ItemData.visibility = View.GONE
            tvNoRecordsAvailable.visibility = View.VISIBLE
        }
    }

    fun updateRecordDialog(empModelClass: DataModelClass) {
        val updateDialog = Dialog(this, R.style.Theme_Dialog)
        updateDialog.setCancelable(false)
        updateDialog.setContentView(R.layout.dialog_update)

        val etUpdateName = updateDialog.findViewById<EditText>(R.id.etUpdateName)
        val etUpdateEmailId = updateDialog.findViewById<EditText>(R.id.etUpdateEmailId)
        val tvUpdate = updateDialog.findViewById<TextView>(R.id.tvUpdate)
        val tvCancel = updateDialog.findViewById<TextView>(R.id.tvCancel)

        etUpdateName.setText(empModelClass.nama)
        etUpdateEmailId.setText(empModelClass.nim)

        tvUpdate.setOnClickListener {
            val name = etUpdateName.text.toString()
            val email = etUpdateEmailId.text.toString()

            val databaseHandler = DatabaseHandler(this)

            if (name.isNotEmpty() && email.isNotEmpty()) {
                val status = databaseHandler.updateMahasiswa(DataModelClass(empModelClass.id, name, email))
                if (status > -1) {
                    Toast.makeText(applicationContext, "Record Updated.", Toast.LENGTH_LONG).show()

                    aturDataRecyclerView()
                    updateDialog.dismiss()
                }
            } else {
                Toast.makeText(applicationContext, "Name or Email cannot be blank", Toast.LENGTH_LONG).show()
            }
        }
        tvCancel.setOnClickListener {
            updateDialog.dismiss()
        }
        updateDialog.show()
    }

    fun deleteRecordAlertDialog(empModelClass: DataModelClass) {
        val builder = android.app.AlertDialog.Builder(this)
        builder.setTitle("Hapus Data")
        builder.setMessage("Apakah Anda yakin ingin menghapus ${empModelClass.nama}?")
        builder.setIcon(android.R.drawable.ic_dialog_alert)

        builder.setPositiveButton("Ya") { dialogInterface, _ ->
            val databaseHandler = DatabaseHandler(this)
            val status = databaseHandler.hapusMahasiswa(empModelClass.id)
            if (status > -1) {
                Toast.makeText(
                    applicationContext,
                    "Data berhasil dihapus.",
                    Toast.LENGTH_LONG
                ).show()
                aturDataRecyclerView()
            } else {
                Toast.makeText(
                    applicationContext,
                    "Gagal menghapus data.",
                    Toast.LENGTH_LONG
                ).show()
            }
            dialogInterface.dismiss()
        }

        builder.setNegativeButton("Tidak") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        val alertDialog: android.app.AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

}
