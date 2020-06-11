package applicativos.utp.proyectofinalmqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MQTT Message"
    var buttontest: Button ?= null
    val Reg= "123"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttontest = findViewById(R.id.button)
    }

    override fun onResume() {
        super.onResume()
        //comentario

        buttontest!!.setOnClickListener {
            Log.i(TAG, "Prueba de los logs")
        }
    }
}