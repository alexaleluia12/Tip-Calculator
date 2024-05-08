package org.hyperskill.calculator.tip

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val billInput = findViewById<EditText>(R.id.edit_text)
        val percentInput = findViewById<SeekBar>(R.id.seek_bar)
        // out put views
        val billOutput = findViewById<TextView>(R.id.bill_value_tv)
        val tipPercentOutput = findViewById<TextView>(R.id.tip_percent_tv)
        val tipTotalOutput = findViewById<TextView>(R.id.tip_amount_tv)
        var lastBillInput = 0.0

        billInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(input: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val bill = input.toString().toDoubleOrNull() ?: 0.0
                lastBillInput = bill
                if (bill > 0.0) {
                    billOutput.text = getString(R.string.bill_value, bill)
                    tipPercentOutput.text = getString(R.string.tip, percentInput.progress)
                    val totalTip = calculateTip(percentInput.progress, bill)
                    tipTotalOutput.text = getString(R.string.tip_amount, totalTip)
                }  else {
                    billOutput.text = ""
                    tipPercentOutput.text = ""
                    tipTotalOutput.text = ""
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        percentInput.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, currentProgress: Int, p2: Boolean) {
                if (currentProgress >= 0 && lastBillInput > 0.0) {
                    tipPercentOutput.text = getString(R.string.tip, currentProgress)
                    val totalTip = calculateTip(currentProgress, lastBillInput)
                    tipTotalOutput.text = getString(R.string.tip_amount, totalTip)
                }
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }
}

fun calculateTip(percent: Int, value: Double) = percent/100.0 * value