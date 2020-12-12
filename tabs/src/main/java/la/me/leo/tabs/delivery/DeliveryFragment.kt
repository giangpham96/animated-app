package la.me.leo.tabs.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.data.delivery.DeliveryRepository
import la.me.leo.tabs.databinding.FragmentDeliveryBinding
import la.me.leo.tabs.delivery.adapter.VenueLargeCardAdapter

internal class DeliveryFragment : BaseFragment<FragmentDeliveryBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDeliveryBinding {
        return FragmentDeliveryBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.apply {
            adapter = VenueLargeCardAdapter()
                .also { it.items = DeliveryRepository(requireContext()).provideDeliveryData().items }
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        }
    }
}
