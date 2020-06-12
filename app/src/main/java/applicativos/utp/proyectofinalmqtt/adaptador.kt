package applicativos.utp.proyectofinalmqtt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class adaptador(var lista: ArrayList<deviceInfo>): RecyclerView.Adapter<adaptador.ViewHolder>() {
    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        fun llenarDatos(data: deviceInfo) {
            val id: TextView = itemView.findViewById(R.id.cardTextViewID)
            val name: TextView = itemView.findViewById(R.id.cardTextViewName)

            id.text = data.id
            name.text = data.nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val mostrar = LayoutInflater.from(parent.context).inflate(R.layout.activity_device_list, parent, false)

        return ViewHolder(mostrar)
    }

    override fun getItemCount(): Int {
        //retornamos el tamaño de la lista
        return lista.size
    }

    override fun onBindViewHolder(holder: adaptador.ViewHolder, position: Int) {
        //llena los datos
        holder.llenarDatos(lista[position])
    }
}