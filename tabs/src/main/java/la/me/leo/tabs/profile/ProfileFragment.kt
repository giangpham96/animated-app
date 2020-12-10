package la.me.leo.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.tabs.databinding.FragmentProfileBinding

internal class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }
}
