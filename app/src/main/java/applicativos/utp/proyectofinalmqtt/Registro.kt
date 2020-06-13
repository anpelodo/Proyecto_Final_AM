package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class Registro : AppCompatActivity() {

    var Nomb: EditText? = null
    var Corr: EditText? = null
    var Cont: EditText? = null
    var Tele: EditText? = null
    var Save: Button?=null
    var Peticion: RequestQueue?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        Nomb= findViewById(R.id.Nomb)
        Corr= findViewById(R.id.Corr)
        Cont= findViewById(R.id.Cont)
        Tele= findViewById(R.id.Tele)
        Save= findViewById(R.id.Guar)

    }

    override fun onResume() {
        super.onResume()

            Save!!.setOnClickListener {
            GuardarDatos()
        }

    }

    private fun GuardarDatos(){

        var dato1:String=Nomb!!.text.toString()
        var dato2:String=Corr!!.text.toString()
        var dato3:String=Cont!!.text.toString()
        var dato4:String=Tele!!.text.toString()


        Peticion= Volley.newRequestQueue(this)
        var URL="Inegresar URL"
        var conexion= StringRequest(Request.Method.GET,URL, Response.Listener { response ->
            Toast.makeText(this,"Usuario Guardado", Toast.LENGTH_SHORT).show()
        }, Response.ErrorListener {
            Toast.makeText(this,"Error En La Conexion A Internet", Toast.LENGTH_SHORT).show()
        })
        Peticion!!.add(conexion)
    }
}