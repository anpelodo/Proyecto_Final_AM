package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.UserProfileChangeRequest

class Registro : AppCompatActivity() {

    var Nomb: EditText? = null
    var Corr: EditText? = null
    var Cont: EditText? = null
    var Save: Button?=null

    var name: String ?= null
    var email: String ?= null
    var pswd: String ?= null

    var auth = MainActivity.auth
    var userFirebase: FirebaseUser ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro2)

        Nomb= findViewById(R.id.Nomb)
        Corr= findViewById(R.id.Corr)
        Cont= findViewById(R.id.Cont)
        Save= findViewById(R.id.Guar)

        // adquirimos los valores de correo y contraseña si existen.
        Corr!!.setText(MainActivity.email)
        Cont!!.setText(MainActivity.pswd)

        userFirebase = auth.currentUser
    }

    override fun onResume() {
        super.onResume()

        Save!!.setOnClickListener {
            if(checkParam()) {
                RegistrarUsuario()
            }
        }

    }

    private fun checkParam(): Boolean {
        val msg: String

        name = Nomb!!.text.toString()
        email = Corr!!.text.toString()
        pswd = Cont!!.text.toString()

        return if (name!!.isEmpty() ||
            email!!.isEmpty() ||
            pswd!!.length < 8) {

            msg = if (pswd!!.length < 8) {
                "La contraseña debe tener más de 8 caracteres"
            } else {
                "Todos los campos deben llenarsen"
            }

            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

            false
        } else true
    }

    private fun RegistrarUsuario(){
        auth.createUserWithEmailAndPassword(email!!, pswd!!)
            .addOnSuccessListener {
                val profileUpdate = UserProfileChangeRequest.Builder()
                    .setDisplayName(name)
                    .build()

                userFirebase?.updateProfile(profileUpdate)

                Toast.makeText(this, "Usuario Creado, inicie sesion", Toast.LENGTH_SHORT).show()

                val i = Intent(this, MainActivity::class.java)

                try {
                    startActivity(i)
                } catch (e: Exception) {
                    Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
                    Log.d("Open.MainActivity", "Error al abrir")
                }
            }.addOnFailureListener {
                Toast.makeText(this, "No se pudo registrar\nIntente nuevamente", Toast.LENGTH_SHORT).show()
            }
    }
}