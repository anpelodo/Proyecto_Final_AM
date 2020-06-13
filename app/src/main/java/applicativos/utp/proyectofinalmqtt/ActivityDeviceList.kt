package applicativos.utp.proyectofinalmqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityDeviceList : AppCompatActivity() {
    var vista: RecyclerView ?= null

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
            override fun onClick(vista: View, posicion: Int) {
                Toast.makeText(applicationContext, info?.get(posicion).id, Toast.LENGTH_SHORT).show()
            }
        })
        vista!!.adapter = adapter

    }

    override fun onResume() {
        super.onResume()


    }
}