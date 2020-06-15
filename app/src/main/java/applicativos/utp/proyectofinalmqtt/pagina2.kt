package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class pagina2 : Fragment() {

    companion object {
        fun newInstance() = pagina2()
    }

    private lateinit var viewModel: Pagina2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista2= inflater.inflate(R.layout.pagina2_fragment, container, false)
        return vista2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}