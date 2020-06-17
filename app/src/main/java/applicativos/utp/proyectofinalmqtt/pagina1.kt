package applicativos.utp.proyectofinalmqtt

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.jjoe64.graphview.GraphView
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries

class pagina1 : Fragment() {
    var grafica1: GraphView?=null
    var grafica2: GraphView?=null
    var grafica3: GraphView?=null
    var grafica4: GraphView?=null

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
        //var A1:LineGraphSeries<DataPoint>?=null
        //obtenemos los datos de la base de datos
        val valores = ActivityDeviceList.dataJson!!
        var sizeValores = valores.ndata

        if (sizeValores == -1){
            Toast.makeText(Tabbed.tabContext, "Error con la base de datos", Toast.LENGTH_SHORT).show()
        } else if (sizeValores == 0) {
            Toast.makeText(Tabbed.tabContext, "No hay elementos para mostrar", Toast.LENGTH_SHORT).show()
        } else if(sizeValores > 0) {
            sizeValores --

            val an1: MutableList<DataPoint> = arrayListOf()
            val an2: MutableList<DataPoint> = arrayListOf()
            val s1: MutableList<DataPoint> = arrayListOf()
            val s2: MutableList<DataPoint> = arrayListOf()

            for (i in 0..sizeValores) {
                val x = i.toDouble()
                val y1 = valores.data!![i].a1.toDouble()
                val y2 = valores.data!![i].a2.toDouble()
                val y3 = if (valores.data!![i].s1) 1.0 else 0.0
                val y4 = if (valores.data!![i].s2) 1.0 else 0.0

                an1.add(DataPoint(x,y1))
                an2.add(DataPoint(x,y2))
                s1.add(DataPoint(x,y3))
                s2.add(DataPoint(x,y4))
            }

            val A1 = LineGraphSeries(an1.toTypedArray())
            val A2 = LineGraphSeries(an2.toTypedArray())
            val S1 = LineGraphSeries(s1.toTypedArray())
            val S2 = LineGraphSeries(s2.toTypedArray())

            /*val a1 = LineGraphSeries(
                arrayOf(
                    DataPoint(1.0, 1.1),
                    DataPoint(2.0, 2.2),
                    DataPoint(3.0, 3.3),
                    DataPoint(4.0, 4.4),
                    DataPoint(5.0, 1.0),
                    DataPoint(6.0, 2.0),
                    DataPoint(7.0, 4.4),
                    DataPoint(8.0, 2.2),
                    DataPoint(9.0, 2.0)
                )
            )*/

            parametrosGrafica(A1, grafica1!!, "Valores X", "A1[V]")
            parametrosGrafica(A2, grafica2!!, "Valores X", "A2[V]")
            parametrosGrafica(S1, grafica3!!, "Valores X", "S1[bool]")
            parametrosGrafica(S2, grafica4!!, "Valores X", "S2[bool]")
        }
    }

    private fun parametrosGrafica(datos: LineGraphSeries<DataPoint>, graph: GraphView, xTitle: String, yTitle: String) {
        graph.gridLabelRenderer.horizontalAxisTitle=xTitle
        graph.gridLabelRenderer.verticalAxisTitle=yTitle
        graph.addSeries(datos)
    }
}