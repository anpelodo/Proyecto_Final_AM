package applicativos.utp.proyectofinalmqtt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_device_list.view.*


class adaptador(lista: ArrayList<deviceInfo>): RecyclerView.Adapter<adaptador.ViewHolder>() {
    var lista: ArrayList<deviceInfo> ?= null
    var viewHolder:ViewHolder ?= null

    init {
        this.lista = lista
    }

    class ViewHolder (view: View): RecyclerView.ViewHolder(view) {
        var vista = view
        var id: TextView ?= null
        var name: TextView ?= null

        init {
            id = vista.cardTextViewID
            name = vista.cardTextViewName
        }

        fun llenarDatos(data: deviceInfo) {
            id = itemView.findViewById(R.id.cardTextViewID)
            name = itemView.findViewById(R.id.cardTextViewName)

            id.text = data.id
            name.text = data.nombre
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptador.ViewHolder {
        val mostrar = LayoutInflater.from(parent.context).inflate(R.layout.activity_device_list, parent, false)

        viewHolder = ViewHolder(mostrar)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        //retornamos el tamaño de la lista
        return lista!!.size
    }

    override fun onBindViewHolder(holder: adaptador.ViewHolder, position: Int) {
        //llena los datos
        holder.llenarDatos(lista!![position])
    }
}