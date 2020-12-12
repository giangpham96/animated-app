package la.me.leo.tabs.delivery

import android.view.LayoutInflater
import android.view.ViewGroup
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.tabs.databinding.FragmentDeliveryBinding

internal class DeliveryFragment : BaseFragment<FragmentDeliveryBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveryBinding {
        return FragmentDeliveryBinding.inflate(inflater, container, false)
    }
}
