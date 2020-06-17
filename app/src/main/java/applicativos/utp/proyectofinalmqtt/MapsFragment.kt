package applicativos.utp.proyectofinalmqtt

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : Fragment() {

    private val device = ActivityDeviceList.infoDevice

    private val callback = OnMapReadyCallback { googleMap ->

        val id = device!!.id
        val nombre = device.nombre
        val ubicacionS = device.ubicacion

        val lat = ubicacionS.substringBefore(',').toDouble()
        val lng = ubicacionS.substringAfter(',').toDouble()

        val tag = "id: $id\nnombre: $nombre"

        val colombia = LatLng(lat,lng)
        googleMap.addMarker(MarkerOptions().position(colombia).title(tag))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(colombia))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(colombia,15f))
    }

    override fun onCreateView(

        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}