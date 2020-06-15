package applicativos.utp.proyectofinalmqtt.ui.main

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import applicativos.utp.proyectofinalmqtt.MapsFragment
import applicativos.utp.proyectofinalmqtt.R
import applicativos.utp.proyectofinalmqtt.pagina2

private val TAB_TITLES = arrayOf(
    R.string.tab_text_1,
    R.string.tab_text_2 ,
    R.string.tab_text_3

)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        //return PlaceholderFragment.newInstance(position + 1)
        when(position + 1 ) {
            1-> {

                return  PlaceholderFragment()
            }
            2-> {
                return pagina2()
            }
            3-> {
                return MapsFragment()
            }
            else-> {
                return PlaceholderFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 2 total pages.
        return 3
    }
}