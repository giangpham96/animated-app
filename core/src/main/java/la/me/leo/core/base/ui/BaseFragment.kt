package la.me.leo.core.base.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<B : ViewBinding> : Fragment() {

    protected val binding: B
        get() = _binding!!
    private var _binding: B? = null

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = createView(inflater, container)
        return binding.root
    }

    abstract fun createView(inflater: LayoutInflater, container: ViewGroup?): B

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
