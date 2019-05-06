package com.example.studentmanagementsystem.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.Intent.getIntent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.studentmanagementsystem.R
import com.example.studentmanagementsystem.constant.Constant
import com.example.studentmanagementsystem.model.Student
import com.example.studentmanagementsystem.model.StudentKotlin
import com.example.studentmanagementsystem.validator.Validate
import com.example.studentmanagementsystem.validator.ValidateKotlin
import java.util.ArrayList

class AddStudentActivityKotlin : AppCompatActivity() {
    private var mSaveButton: Button? = null
    private var mEtStudentName: EditText? = null
    private var mEtStudentRollNo:EditText? = null
    private var mPosition: String? = null

    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)
        initValues()
        setValuesFromIntent()
    }

    private fun initValues() {
        mSaveButton = findViewById(R.id.btn_save)
        mEtStudentName = findViewById(R.id.et_studentName)
        mEtStudentRollNo = findViewById(R.id.et_studentRoll)
    }

    private fun setValuesFromIntent() {
        val intent = getIntent()
        val studentList = intent.getParcelableArrayListExtra<StudentKotlin>("mStudentList")
        when (intent.getStringExtra(Constant.MODE)) {
            Constant.NORMAL -> save(intent, studentList)
            Constant.VIEW -> viewMode(intent)
            Constant.EDIT -> editMode(intent, studentList)
            else -> {
            }
        }
    }

    fun save(intent: Intent, studentList: ArrayList<StudentKotlin>) {
        setTitle(R.string.addStudent)
        mSaveButton!!.setOnClickListener { saveData(studentList) }
    }

    fun saveData(studentList: ArrayList<StudentKotlin>) {


        if (!ValidateKotlin.isValidName(mEtStudentName!!.text.toString().trim { it <= ' ' })) {
            Toast.makeText(this@AddStudentActivityKotlin, getString(R.string.invalidName),
                    Toast.LENGTH_LONG).show()
            return
        }
        if (!ValidateKotlin.isValidRollNo(mEtStudentRollNo!!.getText().toString().trim({ it <= ' ' }))) {
            Toast.makeText(this@AddStudentActivityKotlin, getString(R.string.invalidRoll),
                    Toast.LENGTH_LONG).show()
            return
        }
        if (!ValidateKotlin.isUniqueRollNo(mEtStudentRollNo!!.getText().toString().trim({ it <= ' ' }), studentList)) {
            Toast.makeText(this@AddStudentActivityKotlin, getString(R.string.rollNotUnique),
                    Toast.LENGTH_LONG).show()
            return

        }
        val returnIntent = getIntent()
        returnIntent.putExtra(Constant.NAME, mEtStudentName!!.text.toString())
        returnIntent.putExtra(Constant.ROLL_NO, mEtStudentRollNo!!.getText().toString())
        setResult(Activity.RESULT_OK, returnIntent)
        finish()
    }


    fun viewMode(intent: Intent) {
        setTitle(R.string.details)
        mSaveButton!!.visibility = View.INVISIBLE
        mEtStudentName!!.setText(intent.getStringExtra(Constant.VIEW_NAME))
        mEtStudentRollNo!!.setText(intent.getStringExtra(Constant.VIEW_ROLL))
        mEtStudentName!!.isEnabled = false
        mEtStudentRollNo!!.setEnabled(false)
    }

    @SuppressLint("SetTextI18n")
    fun editMode(intent: Intent, studentList: ArrayList<StudentKotlin>) {
        setTitle(R.string.edit)
        mEtStudentName!!.setText(intent.getStringExtra(Constant.VIEW_NAME))
        mEtStudentRollNo!!.setText(intent.getStringExtra(Constant.VIEW_ROLL))
        mPosition = getIntent().getStringExtra(Constant.POSITION)
        mSaveButton!!.setText(getString(R.string.update))
        mSaveButton!!.setOnClickListener { saveData(studentList) }
    }
}
