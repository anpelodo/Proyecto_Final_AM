package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class ActivityDeviceList : AppCompatActivity() {
    var vista: RecyclerView ?= null

    var auth = MainActivity.auth
    val cont = this

    var info = ArrayList<deviceInfo>()

    var peticion: RequestQueue?= null
    val dirIP = MainActivity.dirIP

    companion object {
        var infoDevice: deviceInfo ?= null

        var dataJson: dataJSON ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        val list:devicesJSON = MainActivity.devicesJson!!

        when (list.ndata) {
            -1 -> Toast.makeText(this, "Error con la base de datos", Toast.LENGTH_SHORT).show()
            0 -> Toast.makeText(this,"No hay elementos para mostrar", Toast.LENGTH_SHORT).show()
        }

        //Se carga el ArrayList para el Recycler View
        for (i in (0..list.ndata-1)) {
            list.data?.get(i)?.let { info.add(it) }
        }

        vista = findViewById(R.id.contenedor)
        vista!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = adaptador(info, object: ClickListener{
            val i = Intent(cont, Tabbed::class.java)

            override fun onClick(vista: View, posicion: Int) {
                Toast.makeText(applicationContext, info[posicion].id, Toast.LENGTH_SHORT).show()

                //Compartimos la variable
                infoDevice = info[posicion]

                val tabla = info[posicion].tabla
                val url = "http://$dirIP/finalDigitales4/getData.php?tabla=$tabla"

                loadData(url)
            }
        })
        vista!!.adapter = adapter
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut()
    }

    //Método para cargar los datos desde la base de datos
    private fun loadData(URL: String){
        peticion = Volley.newRequestQueue(this)

        val conexion = StringRequest(Request.Method.GET, URL,
            Response.Listener { response ->
                Log.d("data Get Request", "JSON data Recibed: $response")
                val gson = Gson()

                dataJson = gson.fromJson(response, dataJSON::class.java)
                openTabbed()
            }, Response.ErrorListener {
                Log.d("data Get Request", "Failed")
            }
        )

        //Enviamos la petición
        peticion!!.add(conexion)
    }

    //Método para abrir el tabbed View
    private fun openTabbed() {
        val i = Intent(this, Tabbed::class.java)

        try {
            startActivity(i)
        } catch (e: Exception) {
            Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
            Log.d("Open.Activity.registro", "Error al abrir")
        }
    }
}