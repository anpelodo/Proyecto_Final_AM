package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.graphics.blue
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.pagina1_fragment.*

class pagina1 : Fragment() {


    var grafica1: GraphView?=null
    var grafica2: GraphView?=null
    var grafica3: GraphView?=null
    var grafica4: GraphView?=null

    companion object {
        fun newInstance() = pagina1()
    }

    private lateinit var viewModel: Pagina1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val vista1= inflater.inflate(R.layout.pagina1_fragment, container, false)
        grafica1=vista1.findViewById(R.id.graph1)
        grafica2=vista1.findViewById(R.id.graph2)
        grafica3=vista1.findViewById(R.id.graph3)
        grafica4=vista1.findViewById(R.id.graph4)

        return  vista1
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Pagina1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onResume() {
        super.onResume()

        ParametrosGrafica1()
        ParametrosGrafica2()
        ParametrosGrafica3()
        ParametrosGrafica4()





    }

    private fun ParametrosGrafica1(){
        grafica1!!.viewport.setMaxX(9.0)
        grafica1!!.viewport.setMinX(0.0)
        grafica1!!.viewport.setMaxY(60.0)
        grafica1!!.viewport.setMinY(0.0)
        grafica1!!.viewport.setScalableY(true)
        grafica1!!.gridLabelRenderer.horizontalAxisTitle="Valores X"
        grafica1!!.gridLabelRenderer.verticalAxisTitle = "Valores Y"


        val Datos = LineGraphSeries(
            arrayOf(
                DataPoint(1.0, 1.1),
                DataPoint(2.0, 2.2),
                DataPoint(3.0, 3.3),
                DataPoint(4.0, 4.4),
                DataPoint(5.0, 1.0),
                DataPoint(6.0, 2.0),
                DataPoint(7.0, 4.4),
                DataPoint(8.0, 2.2),
                DataPoint(9.0,2.0)
            )
        )

        grafica1!!.addSeries(Datos)

    }

    private fun ParametrosGrafica2(){
        grafica2!!.viewport.setMaxX(9.0)
        grafica2!!.viewport.setMinX(0.0)
        grafica2!!.viewport.setMaxY(60.0)
        grafica2!!.viewport.setMinY(0.0)
        grafica2!!.viewport.setScalableY(true)
        grafica2!!.gridLabelRenderer.horizontalAxisTitle="Valores X"
        grafica2!!.gridLabelRenderer.verticalAxisTitle = "Valores Y"
        val Datos = LineGraphSeries(
            arrayOf(
                DataPoint(1.0, 1.1),
                DataPoint(2.0, 2.2),
                DataPoint(3.0, 3.3),
                DataPoint(4.0, 4.4),
                DataPoint(5.0, 1.0),
                DataPoint(6.0, 2.0),
                DataPoint(7.0, 4.4),
                DataPoint(8.0, 2.2),
                DataPoint(9.0,2.0)
            )
        )

        grafica2!!.addSeries(Datos)

    }
    private fun ParametrosGrafica3(){
        grafica3!!.viewport.setMaxX(9.0)
        grafica3!!.viewport.setMinX(0.0)
        grafica3!!.viewport.setMaxY(60.0)
        grafica3!!.viewport.setMinY(0.0)
        grafica3!!.viewport.setScalableY(true)
        grafica3!!.gridLabelRenderer.horizontalAxisTitle="Valores X"
        grafica3!!.gridLabelRenderer.verticalAxisTitle = "Valores Y"
        val Datos = LineGraphSeries(
            arrayOf(
                DataPoint(1.0, 1.1),
                DataPoint(2.0, 2.2),
                DataPoint(3.0, 3.3),
                DataPoint(4.0, 4.4),
                DataPoint(5.0, 1.0),
                DataPoint(6.0, 2.0),
                DataPoint(7.0, 4.4),
                DataPoint(8.0, 2.2),
                DataPoint(9.0,2.0)
            )
        )

        grafica3!!.addSeries(Datos)

    }
    private fun ParametrosGrafica4(){
        grafica4!!.viewport.setMaxX(9.0)
        grafica4!!.viewport.setMinX(0.0)
        grafica4!!.viewport.setMaxY(60.0)
        grafica4!!.viewport.setMinY(0.0)
        grafica4!!.viewport.setScalableY(true)
        grafica4!!.gridLabelRenderer.horizontalAxisTitle="Valores X"
        grafica4!!.gridLabelRenderer.verticalAxisTitle = "Valores Y"
        val Datos = LineGraphSeries(
            arrayOf(
                DataPoint(1.0, 1.1),
                DataPoint(2.0, 2.2),
                DataPoint(3.0, 3.3),
                DataPoint(4.0, 4.4),
                DataPoint(5.0, 1.0),
                DataPoint(6.0, 2.0),
                DataPoint(7.0, 4.4),
                DataPoint(8.0, 2.2),
                DataPoint(9.0,2.0)
            )
        )

        grafica4!!.addSeries(Datos)

    }



}