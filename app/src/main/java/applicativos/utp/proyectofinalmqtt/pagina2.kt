package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class pagina2 : Fragment() {
    internal lateinit var Boton1: CheckBox
    var B1=0
    internal lateinit var Boton2: CheckBox
    var B2=0

    var Time: EditText? = null
    var Obtener: Button? = null
    var Visor: TextView? = null

    companion object {
        fun newInstance() = pagina2()
    }

    private lateinit var viewModel: Pagina2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista2= inflater.inflate(R.layout.pagina2_fragment, container, false)
        Boton1=vista2.findViewById(R.id.checkBox1)
        Boton2=vista2.findViewById(R.id.checkBox2)
        Time=vista2.findViewById(R.id.dato)
        Obtener=vista2.findViewById(R.id.enviar)
        Visor=vista2.findViewById(R.id.visor)

        return vista2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        Boton1!!.setOnClickListener {
            if (Boton1.isChecked){ B1=1;
                Toast.makeText(context,"Acepto1",Toast.LENGTH_SHORT).show()}
            else{ B1=0;
                Toast.makeText(context,"Nego1",Toast.LENGTH_SHORT).show()}
        }
        Boton2!!.setOnClickListener {
            if (Boton2.isChecked){ B2=1;
                Toast.makeText(context,"Acepto2",Toast.LENGTH_SHORT).show()}
            else{ B2=0;
                Toast.makeText(context,"Nego2",Toast.LENGTH_SHORT).show()}
        }
       Obtener!!.setOnClickListener {
            Toast.makeText(context,"Obteniendo Datos",Toast.LENGTH_SHORT).show()
        }

    }

}