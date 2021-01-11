package la.me.leo.venue_detail_page

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.venue_detail_page.databinding.FragmentVenueDetailPageBinding

class VenueDetailPageFragment : BaseFragment<FragmentVenueDetailPageBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentVenueDetailPageBinding {
        return FragmentVenueDetailPageBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
    }

    private fun setupToolbar() = with(binding) {
        venueCollapsingImageWidget.bind(scrollView)
        venueCollapsingImageWidget.setImage(
            "https://master-wolt-venue-images-cdn.wolt.com/s/EH4m0ITlNUYO70tkLJ_MYCokGXuL7erC_Mek-YFsxUk/5887ab623b9677116df257f5/ab9940bfcfb066805d6dadad7f5085c9-frontpage.jpg",
            "jsIsj5TcP;hlPcplFSyqTtKHh4uD"
        )
        venueCollapsingImageWidget.setLeftIcon(R.drawable.ic_m_back) { }
        venueCollapsingImageWidget.setRightIcon2(R.drawable.ic_m_search2) { }
        venueCollapsingImageWidget.setRightIcon1(R.drawable.ic_m_more_vertical) { }
        venueCollapsingImageWidget.title = "Naughty BRGR / Sello"
        venueCollapsingImageWidget.bigTitle = binding.tvName
    }
}
