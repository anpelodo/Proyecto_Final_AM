package applicativos.utp.proyectofinalmqtt

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_device_list.view.*
import kotlin.random.Random


class adaptador(lista: ArrayList<deviceInfo>, var clickListener: ClickListener): RecyclerView.Adapter<adaptador.ViewHolder>() {
    var lista: ArrayList<deviceInfo> ?= null
    var viewHolder:ViewHolder ?= null

    init {
        this.lista = lista
    }

    class ViewHolder (view: View, listener: ClickListener): RecyclerView.ViewHolder(view), View.OnClickListener {
        var vista = view
        var id: TextView ?= null
        var name: TextView ?= null
        var icon: ImageView ?= null
        var listener: ClickListener ?= null
        init {
            id = vista.cardTextViewID
            name = vista.cardTextViewName
            icon = vista.cardImageViewIcon

            this.listener = listener

            vista.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            this.listener?.onClick(v!!, adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adaptador.ViewHolder {
        val mostrar = LayoutInflater.from(parent.context).inflate(R.layout.card_device_list, parent, false)

        viewHolder = ViewHolder(mostrar, clickListener)

        return viewHolder!!
    }

    override fun getItemCount(): Int {
        //retornamos el tamaño de la lista
        return lista!!.size
    }

    override fun onBindViewHolder(holder: adaptador.ViewHolder, position: Int) {
        val item = lista?.get(position)
        val listRandom = List(3) {Random.nextInt(20,255)}

        val color = Color.argb(255, listRandom[0], listRandom[1], listRandom[2])

        holder.id?.text = item?.id
        holder.name?.text = item?.nombre
        holder.icon?.setBackgroundColor(color)
    }
}