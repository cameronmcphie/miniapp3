package edu.fullerton.ecs.cpsc411.miniapp3

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import kotlinx.android.synthetic.main.fragment_file_transfer_speed.*
import android.support.v4.app.FragmentManager

class FileTransferSpeed : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_transfer_speed)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

       val fragment = CalculatorFragment()
        fragmentTransaction.add(R.id.main_activity, fragment)
        fragmentTransaction.commit()

        fileValue.onChange()
        speedValue.onChange()

    }

    private fun calcTransferSpeed(): Double {
        val sizeString = fileValue.text.toString()
        val speedString = speedValue.text.toString()

        return if (speedString.trim { it <= ' ' }.isEmpty() || sizeString.trim { it <= ' ' }.isEmpty()) {
            0.0
        } else {
            var sizeDouble: Double = sizeString.toDouble()
            var speedDouble: Double = speedString.toDouble()

            // Size in bits
            sizeDouble *= Math.pow(2.0, 20.0) * 8.0

            // Speed in bits per sec
            speedDouble *= Math.pow(10.0, 6.0)

            return sizeDouble / speedDouble
        }
    }

    private fun EditText.onChange() {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val transferTime = calcTransferSpeed()
                val oneDecPlace = DecimalFormat("#.#")
                transferSpeedValue.text = oneDecPlace.format(transferTime)
            }
        })
    }
}