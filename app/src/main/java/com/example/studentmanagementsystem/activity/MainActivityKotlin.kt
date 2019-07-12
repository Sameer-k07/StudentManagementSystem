package com.example.studentmanagementsystem.activity

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.*
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.example.studentmanagementsystem.R
import com.example.studentmanagementsystem.adapter.StudentAdapterKotlin
import com.example.studentmanagementsystem.comparator.SortByName
import com.example.studentmanagementsystem.comparator.SortByNameKotlin
import com.example.studentmanagementsystem.comparator.SortByRollNo
import com.example.studentmanagementsystem.constant.Constant
import com.example.studentmanagementsystem.listener.TouchListener
import com.example.studentmanagementsystem.model.StudentKotlin
import java.util.*
import kotlin.collections.ArrayList

class MainActivityKotlin : AppCompatActivity() {
    private var addButton: Button? = null
    private var rlNoStudent: RelativeLayout? = null
    private lateinit var mStudentList: ArrayList<StudentKotlin>
    private var mRecyclerView: RecyclerView? = null
    private lateinit var mAdapter: StudentAdapterKotlin
    private var mDialogItems: Array<String>? = null
    private var pos: Int = 0
    private val VIEW = 0
    private val EDIT = 1
    private val DELETE = 2
    private val RC_VIEW = 1
    private val RC_EDIT = 2

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        recyclerViewOperations()
    }

    private fun init() {
        mStudentList = ArrayList()
        mDialogItems = resources.getStringArray(R.array.Dialog_Operations)
        mRecyclerView = findViewById(R.id.recycler_view)
        mAdapter = StudentAdapterKotlin(mStudentList)
        mRecyclerView!!.setLayoutManager(LinearLayoutManager(this@MainActivityKotlin))
        mRecyclerView!!.addItemDecoration(DividerItemDecoration(this@MainActivityKotlin, LinearLayoutManager.VERTICAL))
        mRecyclerView!!.setItemAnimator(DefaultItemAnimator())
        mRecyclerView!!.setAdapter(mAdapter)
        addButton = findViewById(R.id.btn_addStudent)
        addButton!!.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@MainActivityKotlin, AddStudentActivityKotlin::class.java)
            intent.putParcelableArrayListExtra("mStudentList", mStudentList)
            intent.putExtra(Constant.MODE, Constant.NORMAL)
            startActivityForResult(intent, RC_VIEW)
        })
    }

  /*  private fun recyclerViewOperations() {
        mRecyclerView!!.addOnItemTouchListener(TouchListener(this@MainActivityKotlin, mRecyclerView, object : TouchListener.ClickListener {
            override fun onClick(view: View?, position: Int) {
                val student = mStudentList[position]
                val mBuilder = AlertDialog.Builder(this@MainActivityKotlin)
                mBuilder.setTitle("Choose your operation")
                mBuilder.setSingleChoiceItems(mDialogItems, -1)
                { dialog: DialogInterface?, which: Int ->
                    val intent = Intent(this@MainActivityKotlin, AddStudentActivityKotlin::class.java)
                    when (which) {
                        VIEW -> viewDetails(intent, student)
                        EDIT -> {
                            setposition(position)
                            editDetails(intent, student)
                        }
                        DELETE -> {
                            setposition(position)
                            deleteDetails()
                        }
                    }
                    dialog!!.dismiss()
                }
                mBuilder.setNeutralButton(R.string.cancel) { dialog, which -> }
                val mDialog = mBuilder.create()
                mDialog.show()
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))
    }*/

    private fun recyclerViewOperations(){
        mRecyclerView!!.addOnItemTouchListener(TouchListener(this@MainActivityKotlin ,mRecyclerView,object : TouchListener.ClickListener{

            override fun onClick(view: View?, position: Int) {
                val student = mStudentList[position]
                val mBuilder = AlertDialog.Builder(this@MainActivityKotlin)
                mBuilder.setTitle("Choose your operation")
                mBuilder.setSingleChoiceItems(mDialogItems,-1){
                    dialog, which ->
                    val intent = Intent(this@MainActivityKotlin,AddStudentActivityKotlin::class.java)
                    when(which){
                        VIEW -> viewDetails(intent,student)
                        EDIT -> {
                            setposition(position)
                            editDetails(intent,student)
                        }
                        DELETE ->{
                            setposition(position)
                            deleteDetails()
                        }
                    }
                    dialog!!.dismiss()
                }
                mBuilder.setNegativeButton("Cancel"){
                    dialog, which ->
                }
                val mDialog =mBuilder.create()
                mDialog.show()
            }
            override fun onLongClick(view: View?, position: Int) {

            }
        }))
    }





    private fun viewDetails(intent: Intent, student: StudentKotlin) {
        intent.putExtra(Constant.MODE, Constant.VIEW)
        Toast.makeText(this@MainActivityKotlin, getString(R.string.your_choice_view), Toast.LENGTH_LONG).show()
        intent.putExtra(Constant.VIEW_NAME, student.mName)
        intent.putExtra(Constant.VIEW_ROLL, student.mRollNo)
        startActivity(intent)
    }

    private fun editDetails(intent: Intent, student: StudentKotlin) {
        intent.putExtra(Constant.MODE, Constant.EDIT)
        intent.putExtra("mStudentList", mStudentList)
        intent.putExtra(Constant.VIEW_NAME, student.mName)
        intent.putExtra(Constant.VIEW_ROLL, student.mRollNo)
        intent.putExtra(Constant.POSITION, Integer.toString(getposition()))
        Toast.makeText(this@MainActivityKotlin, getString(R.string.your_choice_edit), Toast.LENGTH_LONG).show()
        startActivityForResult(intent, RC_EDIT)
    }

    private fun deleteDetails() {
        val alertDialogBuilder = AlertDialog.Builder(this@MainActivityKotlin)
        alertDialogBuilder.setMessage(R.string.confirmation)
        alertDialogBuilder.setPositiveButton(R.string.yes
        ) { arg0, arg1 ->
            mStudentList.removeAt(getposition())
            if (mStudentList.isEmpty()) {
                val intent = Intent(this@MainActivityKotlin, MainActivity::class.java)
                startActivity(intent)
            }
            mAdapter.notifyDataSetChanged()
        }
        alertDialogBuilder.setNegativeButton(R.string.no) { dialog, which -> dialog.cancel() }

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        Toast.makeText(this@MainActivityKotlin, getString(R.string.your_choice_delete), Toast.LENGTH_LONG).show()
    }

    private fun setposition(position: Int) {
        pos = position
    }

    private fun getposition(): Int {
        return pos
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        rlNoStudent = findViewById(R.id.noStudentView)
        if (mAdapter.getItemCount() == -1) {
            rlNoStudent!!.setVisibility(View.VISIBLE)
        } else {
            rlNoStudent!!.setVisibility(View.INVISIBLE)
        }
        if (requestCode == RC_VIEW && resultCode == Activity.RESULT_OK) {
            val name = data!!.getStringExtra(Constant.NAME)
            val roll = data.getStringExtra(Constant.ROLL_NO)
            mStudentList.add(StudentKotlin(name, roll))
            mAdapter.notifyDataSetChanged()
        }
        if (requestCode == RC_EDIT && resultCode == Activity.RESULT_OK) {
            val updatedName = data!!.getStringExtra(Constant.NAME)
            val updatedRoll = data.getStringExtra(Constant.ROLL_NO)
            //updating information at same index
            val updatedInfo = mStudentList.get(getposition())
            updatedInfo.mName = updatedName
            updatedInfo.mRollNo = updatedRoll
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.menu_sort_by_name -> {
                Toast.makeText(this@MainActivityKotlin, getString(R.string.sortByName), Toast.LENGTH_LONG).show()
                //Collections.sort(mStudentList, SortByName())
                //Collections.sort(mStudentList,SortByNameKotlin)
                //mStudentList.sortedWith(compareBy({ it.mName }))
                //mStudentList.sortedWith(SortByNameKotlin())
                Log.e("old list", mStudentList.toString())
                mStudentList.sortedWith(compareBy { it.mName })
                Log.e("new list", mStudentList.toString())
                mAdapter.updateData(mStudentList)
                return true
            }
            R.id.menu_sort_by_roll -> {
                Toast.makeText(this@MainActivityKotlin, getString(R.string.sortByRoll), Toast.LENGTH_LONG).show()
                mStudentList.sortedWith(compareBy({ it.mRollNo }))
                mAdapter.notifyDataSetChanged()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        val item = menu.findItem(R.id.menu_switchId) as MenuItem
        item.setActionView(R.layout.switch_layout)
        val switch_toggle = menu.findItem(R.id.menu_switchId).actionView.findViewById<Switch>(R.id.switch_toggle)
        switch_toggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mRecyclerView!!.setLayoutManager(GridLayoutManager(this@MainActivityKotlin, 2))
                mRecyclerView!!.addItemDecoration(DividerItemDecoration(this@MainActivityKotlin, GridLayoutManager.VERTICAL))
                mRecyclerView!!.addItemDecoration(DividerItemDecoration(this@MainActivityKotlin, GridLayoutManager.HORIZONTAL))
                Toast.makeText(this@MainActivityKotlin, getString(R.string.grid_layout), Toast.LENGTH_LONG).show()
            } else {
                //setting the recycler view for linear layout
                mRecyclerView!!.setLayoutManager(LinearLayoutManager(this@MainActivityKotlin))
                mRecyclerView!!.addItemDecoration(DividerItemDecoration(this@MainActivityKotlin, LinearLayoutManager.VERTICAL))
                Toast.makeText(this@MainActivityKotlin, getString(R.string.linear_layout), Toast.LENGTH_LONG).show()
            }
        }
        return super.onCreateOptionsMenu(menu)

    }
}