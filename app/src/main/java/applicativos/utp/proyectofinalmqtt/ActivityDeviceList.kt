package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityDeviceList : AppCompatActivity() {
    var vista: RecyclerView ?= null

    var auth = MainActivity.auth
    val cont = this

    var info = ArrayList<deviceInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        info.add(deviceInfo("BB1", "Centro", "4.814248,-75.694688", "tabla_bb1"))
        info.add(deviceInfo("BB2", "Parque Industrial", "4.814248,-75.694688", "tabla_bb2"))
        info.add(deviceInfo("BB3", "Cuba", "4.814248,-75.694688", "tabla_bb3"))

        vista = findViewById(R.id.contenedor)
        vista!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        val adapter = adaptador(info, object: ClickListener{
            val i = Intent(cont, Tabbed::class.java)

            override fun onClick(vista: View, posicion: Int) {
                Toast.makeText(applicationContext, info[posicion].id, Toast.LENGTH_SHORT).show()

                try {
                    startActivity(i)
                } catch (e: Exception) {
                    Toast.makeText(cont, "Algo salió mal", Toast.LENGTH_SHORT).show()
                    Log.d("Open.Activity.Tabbed", "Error al abrir")
                }
            }
        })
        vista!!.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        auth.signOut()
    }
}