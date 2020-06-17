package applicativos.utp.proyectofinalmqtt

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import applicativos.utp.proyectofinalmqtt.JsonClases.dataJSON
import applicativos.utp.proyectofinalmqtt.JsonClases.mqttJSON
import com.google.gson.Gson
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import kotlin.Exception

class pagina2 : Fragment() {
    private lateinit var setTime: Switch
    private lateinit var dataRT: Switch
    private lateinit var sendCmd: Button

    private lateinit var timeTxt: EditText
    private lateinit var dataTxt: TextView

    private lateinit var viewModel: Pagina2ViewModel

    //MQTT
    private var connectionStatus = false
    private val QOS = 2
    private val broker = "tcp://broker.hivemq.com"
    private val topic = "ProyectoFinalDigitales4_1088345579"
    private lateinit var mqttAndroidClient: MqttAndroidClient
    private var subj : String ?= null

    private var dataRecibed: mqttJSON ?= null
    //private var dataToSend: mqttJSON ?= null

    private var auth = MainActivity.auth

    private var uid = auth.uid

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

        connectMqtt(Tabbed.tabContext)

        subj = ActivityDeviceList.infoDevice!!.id

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
           val gson = Gson()

           try {
               val dataToSend = mqttJSON()
               dataToSend.subjectID = subj!!
               dataToSend.fromID = uid!!

               if (setTime.isChecked) {
                   if (timeTxt.text.isNotEmpty()) {
                       dataToSend.accion["setTiempo"] = timeTxt.text.toString().toInt()
                   }
               }

               dataToSend.accion["dataStream"] = dataRT.isChecked

               val envio = gson.toJson(dataToSend)

               publishMqtt(topic, envio, 2)
           } catch (e: Exception) {
               Log.d("MQTT send", "Error")
           }
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

    private fun putData() {
        val a1:String = dataRecibed!!.data["A1"]!!.toString()
        val a2:String = dataRecibed!!.data["A2"]!!.toString()
        val s1:Boolean = dataRecibed!!.data["S1"] as Boolean
        val s2:Boolean = dataRecibed!!.data["S2"] as Boolean

        var txt = "A1 = $a1\n"
        txt += "A2 = $a2\n"
        txt += "S1 = $s1\n"
        txt += "S2 = $s2\n"

        dataTxt.text = txt
    }
//-------------------------------------------------------------------------------------------
//----------------------------------MQTT Metods----------------------------------------------
//-------------------------------------------------------------------------------------------
    fun connectMqtt (context: Context) {
        mqttAndroidClient = MqttAndroidClient(context.applicationContext, broker, "clientID123456789")

        try {
            val token = mqttAndroidClient.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    connectionStatus = true
                    Log.i("MQTT Connection", "Success")

                    //Apenas conecta, se subscribe
                    subscribeMqtt(topic, QOS)
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    connectionStatus = false
                    Log.i("MQTT Connection", "Failure")

                    if (exception != null) {
                        exception.printStackTrace()
                    }
                }
            }
        } catch (e: MqttException){
            e.printStackTrace()
        }
    }

    fun subscribeMqtt (Topic: String, qos: Int) {
        try{
            mqttAndroidClient.subscribe(Topic, qos, null, object:
                IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("MQTT Subscribe", "Success")

                    //Apenas se subscribe al tópico, inicia en AsynTask la recepción de mensajes
                    receiveMessagesMqtt()
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.i("MQTT Subscribe", "Failure")
                    //todo Entregar la función de callback para la subscripción incorrecta
                }
            })
        } catch (e: MqttException){
            Log.i("MQTT Subscribe Catch", "Failure")
        }
    }

    fun unSubscribeMqtt (Topic: String) {
        try {
            val unsubToken = mqttAndroidClient.unsubscribe(Topic)
            unsubToken.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("MQTT unSubscribe", "Success")
                    //todo Entregar la función de callback para la dessubscripción correcta
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    Log.i("MQTT unSubscribe", "Failure")
                    //todo Entregar la función de callback para la dessubscripción incorrecta
                }
            }
        } catch (e: MqttException) {
            Log.i("MQTT unSubscribe catch", "Failure")
        }
    }

    fun receiveMessagesMqtt () {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                //connectionStatus = false
                //todo Entregar la función de callback si se pierde la conexión
            }

            override fun messageArrived(Topic: String?, message: MqttMessage?) {
                try {
                    val data = String(message!!.payload, charset("UTF-8"))
                    val qos = message.qos

                    val gson = Gson()
                    val dataJson = gson.fromJson(data, mqttJSON::class.java)

                    val subjectID = dataJson.subjectID

                    //Revisamos que el dato sea para éste dispositivo
                    if (subjectID == uid) {
                        dataRecibed = dataJson
                        Toast.makeText(context,"Dato recibido",Toast.LENGTH_SHORT).show()
                        Log.i("MQTT Message Recibed", "Topic: $Topic, payload: $data, qos: $qos")
                        putData()
                    }

                } catch (e: Exception) {
                    Log.i("MQTT Recibed Error", "catch error")
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Aknowledgement on delivery complete
            }
        })
    }

    fun publishMqtt (Topic: String, data: String, qos: Int)  {
        val encodedPayload : ByteArray

        try {
            encodedPayload = data.toByteArray(charset("UTF-8"))
            val message = MqttMessage(encodedPayload)
            message.qos = qos
            message.isRetained = false
            mqttAndroidClient.publish(Topic, message)

        } catch (e: Exception) {
            // Give Callback on error here
        } catch (e: MqttException) {
            // Give Callback on error here
        }
    }
}
