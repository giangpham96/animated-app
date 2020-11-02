package la.me.leo.animatedapp.tabs.discovery

import android.view.LayoutInflater
import android.view.ViewGroup
import la.me.leo.animatedapp.databinding.FragmentDiscoveryBinding
import la.me.leo.core.base.ui.BaseFragment

internal class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDiscoveryBinding {
        return FragmentDiscoveryBinding.inflate(inflater, container, false)
    }
}
