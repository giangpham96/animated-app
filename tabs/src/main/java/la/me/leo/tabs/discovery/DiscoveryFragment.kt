package la.me.leo.tabs.discovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.data.discovery.DiscoveryRepository
import la.me.leo.tabs.databinding.FragmentDiscoveryBinding
import la.me.leo.tabs.discovery.adapter.FlexyAdapter

internal class DiscoveryFragment : BaseFragment<FragmentDiscoveryBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDiscoveryBinding {
        return FragmentDiscoveryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvDiscovery.apply {
            adapter = FlexyAdapter(lifecycle)
                .also { it.items = DiscoveryRepository.provideDiscoveryData().sections }
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
        }
    }
}
