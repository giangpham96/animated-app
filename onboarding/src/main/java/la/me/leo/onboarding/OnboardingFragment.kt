package la.me.leo.onboarding

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import la.me.leo.core.base.navigation.ToTabs
import la.me.leo.core.base.ui.BaseFragment
import la.me.leo.core_animation.animator.cancelAnimatorOnDestroy
import la.me.leo.core_animation.animator.constructAnimator
import la.me.leo.onboarding.databinding.FragmentOnboardingBinding

class OnboardingFragment : BaseFragment<FragmentOnboardingBinding>() {

    override fun createView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOnboardingBinding {
        return FragmentOnboardingBinding.inflate(inflater, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEventListener()
        prepareUiForButtonAnimation()
        val buttonAnimator = createButtonAnimation()
        cancelAnimatorOnDestroy(buttonAnimator, lifecycle)
        setupLottieAnimation(buttonAnimator)
        binding.lottieAnimationView.playAnimation()
    }

    private fun setupEventListener() {
        binding.tvDoneButton.setOnClickListener {
            navigator.navigateTo(ToTabs)
        }
    }

    private fun setupLottieAnimation(buttonAnimation: Animator) {
        val lottieView = binding.lottieAnimationView
        lottieView.setAnimation(R.raw.onboarding)
        lottieView.setMaxFrame(972)
        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator?) = buttonAnimation.start()

            override fun onAnimationRepeat(animation: Animator?) = lottieView.setMinFrame(228)

        })
    }

    private fun prepareUiForButtonAnimation() = with(binding) {
        val u2 = requireContext().resources.getDimensionPixelSize(R.dimen.u2)
        tvDoneButton.isClickable = false
        tvTitle.translationY = u2.toFloat()
        tvDescription.translationY = u2.toFloat()
        vDivider.translationY = u2.toFloat()
        tvDoneButton.translationY = u2.toFloat()
        tvTitle.alpha = 0f
        tvDescription.alpha = 0f
        vDivider.alpha = 0f
        tvDoneButton.alpha = 0f
    }

    private fun createButtonAnimation(): AnimatorSet = with(binding) {
        val u2 = requireContext().resources.getDimensionPixelSize(R.dimen.u2)

        val animations = listOf(
            constructAnimator(300, DecelerateInterpolator(), startDelay = 0,
                onUpdate = { tvTitle.translationY = u2 - it * u2 }
            ),
            constructAnimator(300, DecelerateInterpolator(), startDelay = 50,
                onUpdate = {
                    tvDescription.translationY = u2 - it * u2
                    vDivider.translationY = u2 - it * u2
                }
            ),
            constructAnimator(300, OvershootInterpolator(), startDelay = 100,
                onUpdate = { tvDoneButton.translationY = u2 - it * u2 }
            ),
            constructAnimator(300, LinearInterpolator(), startDelay = 0,
                onUpdate = { tvTitle.alpha = it }
            ),
            constructAnimator(300, LinearInterpolator(), startDelay = 50,
                onUpdate = {
                    tvDescription.alpha = it
                    vDivider.alpha = it
                }
            ),
            constructAnimator(300, LinearInterpolator(), startDelay = 100,
                onUpdate = { tvDoneButton.alpha = it },
                onEnd = { tvDoneButton.isClickable = true }
            )
        )

        val buttonAnimation = AnimatorSet()
        buttonAnimation.playTogether(animations)
        buttonAnimation.startDelay = 2180
        return buttonAnimation
    }

}
