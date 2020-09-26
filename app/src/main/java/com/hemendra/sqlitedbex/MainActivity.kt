package com.hemendra.sqlitedbex

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CursorAdapter
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.list_item.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var Dbase:SQLiteDatabase = openOrCreateDatabase(Consts.DB_NAME,Context.MODE_PRIVATE,null)
        Dbase.execSQL("create table if not exists employee(_id integer primary key autoincrement,empId integer, empName varchar(50),empPhone varchar(50))")

        btn_insert.setOnClickListener {

            var cv:ContentValues = ContentValues()
            cv.put("empId",et_empID.text.toString().toInt())
            cv.put("empName",et_empname.text.toString())
            cv.put("empPhone",et_empPhone.text.toString())

         var status:Long = Dbase.insert(Consts.TABLE_EMPLOYEE,null,cv)


            if(status!=-1L)
            {
                Toast.makeText(this@MainActivity,"Insertion Successful",Toast.LENGTH_SHORT).show()
                et_empID.setText("")
                et_empPhone.setText("")
                et_empname.setText("")
            }
            else{
                Toast.makeText(this@MainActivity,"Insertion failed",Toast.LENGTH_SHORT).show()
            }
        }


        btn_update.setOnClickListener {
            var cv:ContentValues = ContentValues()
            cv.put("empName",et_empname.text.toString())
            cv.put("empPhone",et_empPhone.text.toString())
          var status =   Dbase.update(Consts.TABLE_EMPLOYEE,cv,"empId=?", arrayOf(et_empID.text.toString()))

            if(status!=0)
            {
                Toast.makeText(this@MainActivity,"Updation Successful",Toast.LENGTH_SHORT).show()
                et_empID.setText("")
                et_empPhone.setText("")
                et_empname.setText("")
            }
            else{
                Toast.makeText(this@MainActivity,"Updation Failed",Toast.LENGTH_SHORT).show()
            }
        }

        btn_delete.setOnClickListener {

        var status =     Dbase.delete(Consts.TABLE_EMPLOYEE,"empId=?", arrayOf(et_empID.text.toString()))

            if(status!=0)
            {
                Toast.makeText(this@MainActivity,"Deletion Successful",Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this@MainActivity,"Deletion Failed",Toast.LENGTH_SHORT).show()
            }
        }

        btn_read.setOnClickListener {

            var c:Cursor = Dbase.query(Consts.TABLE_EMPLOYEE,null,null,null,null,null,null)

            var from = arrayOf("empId","empName","empPhone")
            var to = intArrayOf(R.id.tv_id,R.id.tv_name,R.id.tv_phone)
            var myadapter = SimpleCursorAdapter(this@MainActivity,R.layout.list_item,c,from,to,0)

            lv.adapter = myadapter
        }
    }
}