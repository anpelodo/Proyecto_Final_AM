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
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

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

    //MQTT Metods
    private fun connectMqtt (context: Context) {
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

    private fun subscribeMqtt (Topic: String, qos: Int) {
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

    private fun unSubscribeMqtt (Topic: String) {
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

    private fun receiveMessagesMqtt () {
        mqttAndroidClient.setCallback(object : MqttCallback {
            override fun connectionLost(cause: Throwable?) {
                //connectionStatus = false
                //todo Entregar la función de callback si se pierde la conexión
            }

            override fun messageArrived(Topic: String?, message: MqttMessage?) {
                try {
                    val data = String(message!!.payload, charset("UTF-8"))
                    val qos = message.qos

                    dataTxt.text = data
                    Log.i("MQTT Message Recibed", "Topic: $Topic, payload: $data, qos: $qos")
                } catch (e: Exception) {
                    Log.i("MQTT Recibed Error", "catch error")
                }
            }

            override fun deliveryComplete(token: IMqttDeliveryToken?) {
                // Aknowledgement on delivery complete
            }
        })
    }

    private fun publishMqtt (Topic: String, data: String, qos: Int)  {
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