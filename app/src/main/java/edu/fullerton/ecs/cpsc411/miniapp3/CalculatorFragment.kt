package edu.fullerton.ecs.cpsc411.miniapp3

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import kotlinx.android.synthetic.main.fragment_file_transfer_speed.*
import kotlinx.android.synthetic.main.fragment_file_transfer_speed.view.*
import java.text.DecimalFormat


class CalculatorFragment : Fragment() {

//    private var savedSpeed = 0
//    private var savedSize = 0
//    private var savedTime = 0.0
//    private val savedSpeedKey = "savedSpeed"


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view  = inflater.inflate(R.layout.fragment_file_transfer_speed, container, false)
//        if (savedInstanceState != null) {
//            //var sv = savedInstanceState.getInt(savedSpeedKey).toString()
//            view.fileValue.setText(savedInstanceState.getInt(savedSpeedKey))
//        }

        view.fileValue.onChange(view)
        view.speedValue.onChange(view)

        retainInstance = true

        return view
    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        val savedSpeedString = fileValue.text.toString()
//        val savedSpeed = savedSpeedString.toInt()
//        outState.putInt(savedSpeedKey, savedSpeed)
//        super.onSaveInstanceState(outState)
//    }

    private fun calcTransferSpeed(v: View): Double {
        val sizeString = v.fileValue.text.toString()
        val speedString = v.speedValue.text.toString()


        return if (speedString.trim { it <= ' ' }.isEmpty() || sizeString.trim { it <= ' ' }.isEmpty() || speedString == "∞") {
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

    private fun EditText.onChange(v: View) {
        this.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val transferTime = calcTransferSpeed(v)
                val oneDecPlace = DecimalFormat("#.#")
                val transferSpeedValue = v.transferSpeedValue
                transferSpeedValue.text = oneDecPlace.format(transferTime)
            }
        })
    }


}