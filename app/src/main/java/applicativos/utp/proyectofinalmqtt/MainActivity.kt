package applicativos.utp.proyectofinalmqtt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    var texto: TextView ?= null
    var buttontest: Button ?= null

    private var connectionStatus = false
    private val QOS = 2
    private val broker = "tcp://broker.hivemq.com"
    private val topic = "ProyectoFinalDigitales4_1088345579"
    private lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttontest = findViewById(R.id.button)
        texto = findViewById(R.id.textView)

        connectMqtt(this)
    }

    override fun onResume() {
        super.onResume()
        //comentario

        buttontest!!.setOnClickListener {
            publishMqtt(topic, "Prueba desde el aplicativo", QOS)
        }
    }

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

                    texto!!.text = data
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