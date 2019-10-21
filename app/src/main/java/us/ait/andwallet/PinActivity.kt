package us.ait.andwallet

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.poovam.pinedittextfield.PinField
import kotlinx.android.synthetic.main.activity_pin.*


class PinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin)

        val linePinField = lineField
        linePinField.onTextCompleteListener = object : PinField.OnTextCompleteListener {
            override fun onTextComplete(enteredText: String) {
                if (enteredText == getString(R.string.pin_code)) {
                    var intentDetails = Intent()
                    intentDetails.setClass(this@PinActivity,
                        MainActivity::class.java)
                    startActivityForResult(intentDetails, MainActivity.REQUEST_DETAILS)
                }
            }
        }



    }
}