package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    var Correo: EditText? = null
    var Contra: EditText? = null
    var Reg: Button?=null
    var Ing: Button?=null

    companion object {
        lateinit var auth: FirebaseAuth

        var email: String = ""
        var pswd: String = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Correo= findViewById(R.id.nom)
        Contra= findViewById(R.id.con)
        Reg= findViewById(R.id.reg)
        Ing= findViewById(R.id.ing)

        //Se inicializa FireBase Auth
        auth = FirebaseAuth.getInstance()
    }

    override fun onResume() {
        super.onResume()
        //Me envia al registro
        Reg!!.setOnClickListener {
            val i = Intent(this, Registro::class.java)

            email = Correo!!.text.toString()
            pswd = Contra!!.text.toString()

            try{
                startActivity(i)
            } catch (e: Exception) {
                Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
                Log.d("Open.Activity.registro", "Error al abrir")
            }
        }

        Ing!!.setOnClickListener {
            email = Correo!!.text.toString()
            pswd = Contra!!.text.toString()

            if (email.isEmpty() || pswd.length < 8) {
                Toast.makeText(this, "Ingrese una contraseña o correo válidos", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, pswd)
                .addOnSuccessListener {
                    //Abriendo el actívity de lista de dispositivos
                    val i = Intent(this, ActivityDeviceList::class.java)

                    try {
                        Correo!!.setText("")
                        Contra!!.setText("")
                        startActivity(i)
                    } catch (e: Exception) {
                        Toast.makeText(this, "Algo salió mal", Toast.LENGTH_SHORT).show()
                        Log.d("Open.Activity.registro", "Error al abrir")
                    }
                }.addOnFailureListener {
                    Toast.makeText(this, "Usuario no registrado", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        var nuevomenu:MenuInflater=menuInflater
        nuevomenu.inflate(R.menu.menuone, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {        val nuevomenu: MenuInflater = menuInflater

        return  when (item.itemId){
            R.id.item1 -> {
                Toast.makeText(this,"Selecciono la opcion 1", Toast.LENGTH_LONG).show()
                true
            }

            R.id.item2 -> {
                Toast.makeText(this,"Selecciono la opcion 2", Toast.LENGTH_LONG).show()
                true
            }
        else -> super.onOptionsItemSelected(item)
        }
    }
}