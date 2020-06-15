package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class pagina3 : Fragment() {

    companion object {
        fun newInstance() = pagina3()
    }

    private lateinit var viewModel: Pagina3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var vista3 = inflater.inflate(R.layout.pagina3_fragment, container, false)
        return  vista3
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}