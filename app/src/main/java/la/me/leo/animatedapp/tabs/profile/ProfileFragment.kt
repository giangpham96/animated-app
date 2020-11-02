package la.me.leo.animatedapp.tabs.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import la.me.leo.animatedapp.databinding.FragmentProfileBinding
import la.me.leo.core.base.ui.BaseFragment

internal class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProfileBinding {
        return FragmentProfileBinding.inflate(inflater, container, false)
    }
}
