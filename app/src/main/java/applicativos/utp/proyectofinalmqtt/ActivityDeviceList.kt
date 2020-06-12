package applicativos.utp.proyectofinalmqtt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActivityDeviceList : AppCompatActivity() {
    var vista: RecyclerView ?= null

    var info = ArrayList<deviceInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_list)

        vista = findViewById(R.id.contenedor)
        vista!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
    }

    override fun onResume() {
        super.onResume()

        info.add(deviceInfo("BB1", "Centro", "4.814248,-75.694688", "tabla_bb1"))
        info.add(deviceInfo("BB2", "Parque Industrial", "4.814248,-75.694688", "tabla_bb2"))
        info.add(deviceInfo("BB3", "Cuba", "4.814248,-75.694688", "tabla_bb3"))

        val adapter = adaptador(info)
        vista!!.adapter = adapter
    }
}