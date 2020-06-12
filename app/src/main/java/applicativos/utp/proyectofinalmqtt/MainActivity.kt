package applicativos.utp.proyectofinalmqtt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import java.util.zip.Inflater

class MainActivity : AppCompatActivity() {
    private val TAG: String = "MQTT Message"

    var Nom: EditText? = null
    var Reg: Button?=null
    var Ing: Button?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Nom= findViewById(R.id.nom)
        Reg= findViewById(R.id.reg)
        Ing= findViewById(R.id.ing)
    }

    override fun onResume() {
        super.onResume()
        //Me envia al registro
        Reg!!.setOnClickListener {
                val i: Intent = Intent(this, Registro::class.java)
                startActivity(i)
        }

        Ing!!.setOnClickListener {
            val i: Intent = Intent(this,Tabbed ::class.java)
            startActivity(i)
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