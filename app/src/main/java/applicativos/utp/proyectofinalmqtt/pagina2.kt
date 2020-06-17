package applicativos.utp.proyectofinalmqtt

import android.content.res.ColorStateList
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class pagina2 : Fragment() {
    private lateinit var setTime: Switch
    private lateinit var dataRT: Switch
    private lateinit var sendCmd: Button

    private lateinit var timeTxt: EditText
    private lateinit var dataTxt: TextView

    companion object {
        fun newInstance() = pagina2()
    }

    private lateinit var viewModel: Pagina2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val vista2= inflater.inflate(R.layout.pagina2_fragment, container, false)
        setTime=vista2.findViewById(R.id.switch1)
        dataRT=vista2.findViewById(R.id.switch2)
        timeTxt=vista2.findViewById(R.id.dato)
        sendCmd=vista2.findViewById(R.id.enviar)
        dataTxt=vista2.findViewById(R.id.visor)

        return vista2
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        setTime.setOnClickListener {
            stateButton()
        }

        dataRT.setOnClickListener {
            stateButton()
        }

       sendCmd.setOnClickListener {

        }
    }

    private fun stateButton(){
        timeTxt.visibility = if (setTime.isChecked) {
            TextView.VISIBLE
        } else {
            TextView.INVISIBLE
        }
        sendCmd.isEnabled = !(!setTime.isChecked && !dataRT.isChecked)
    }
}