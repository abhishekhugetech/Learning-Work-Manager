package com.dictionary.learningworkmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            val data = Data.Builder()
                .putString("message" , "Hello EveryOne")
                .build()

            val constraint = Constraints.Builder()
                .setRequiresCharging(true)
                .build()

            val oneTimeWorkRequest =
                OneTimeWorkRequest.Builder(MyFirstWork::class.java)
                    .setInputData(data)
                    .setConstraints(constraint)
                    .build()

            val workManager = WorkManager.getInstance()

            workManager.enqueue(oneTimeWorkRequest)

            val workId = oneTimeWorkRequest.id

            workManager.getWorkInfoByIdLiveData(workId).observe(this,object : Observer<WorkInfo>{
                override fun onChanged(t: WorkInfo?) {
                    // Now we can use the Work Info object get any kind of data
                    val statusName = t!!.state.name
                    status.append("$statusName \n ")
                    if (t.state.isFinished){
                        val dataObject = t.outputData
                        val message = dataObject.getString("message")
                        status.append("$message \n \n")
                    }
                }
            })
        }


    }
}
