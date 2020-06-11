package applicativos.utp.proyectofinalmqtt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.IMqttActionListener
import org.eclipse.paho.client.mqttv3.IMqttToken
import org.eclipse.paho.client.mqttv3.MqttException
import java.security.AccessControlContext

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MQTT Message"
    var buttontest: Button ?= null

    val Reg= "123"
    val broker = "broker.hivemq.com"

    private lateinit var mqttAndroidClient: MqttAndroidClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttontest = findViewById(R.id.button)

    }

    override fun onResume() {
        super.onResume()
        //comentario

        buttontest!!.setOnClickListener {
            Log.i(TAG, "Prueba de los logs")
        }
    }

    private fun connect (context: Context) {
        mqttAndroidClient = MqttAndroidClient(context.applicationContext, broker, "clientID123456789")

        try {
            val token = mqttAndroidClient.connect()
            token.actionCallback = object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("MQTT Connection", "Success")

                    //connectionStatus = true
                    //todo crea la variable para decir que se generó la conección
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    //connectionStatus = false
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

    private fun subscribe (topic: String, qos: Int) {
        try{
            mqttAndroidClient.subscribe(topic, qos, null, object:
                IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    Log.i("MQTT Subscribe", "Success")
                    //todo Entregar la función de callback para la subscripción correcta
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

    private fun unSubscribe(topic: String) {
        try {
            val unsubToken = mqttAndroidClient.unsubscribe(topic)
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


}