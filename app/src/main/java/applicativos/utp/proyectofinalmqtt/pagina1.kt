package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast

class pagina1 : Fragment() {
    var boton: Button?=null

    companion object {
        fun newInstance() = pagina1()
    }

    private lateinit var viewModel: Pagina1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val vista1= inflater.inflate(R.layout.pagina1_fragment, container, false)
        boton=vista1.findViewById(R.id.prueba)
        return  vista1
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()
        boton!!.setOnClickListener {
            Toast.makeText(context,"Boton presionado",Toast.LENGTH_SHORT).show()
        }
    }

}