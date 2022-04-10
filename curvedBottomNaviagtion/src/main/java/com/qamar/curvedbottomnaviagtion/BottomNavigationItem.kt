package com.qamar.curvedbottomnaviagtion

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import androidx.core.widget.ImageViewCompat
import androidx.databinding.DataBindingUtil
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.qamar.curvedbottomnaviagtion.databinding.BottomNavigationItemBinding
import com.qamar.curvedbottomnaviagtion.enum.NavigationType


@Suppress("unused")
class BottomNavigationItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : RelativeLayout(context, attrs, defStyleAttrs) {

    companion object {
        const val EMPTY_VALUE = "empty"
    }

    private var isTextVisible = 1
    private val binding = DataBindingUtil.inflate<BottomNavigationItemBinding>(
        LayoutInflater.from(context),
        R.layout.bottom_navigation_item,
        this,
        true
    )

    var iconColor = 0
        set(value) {
            field = value
            if (allowDraw)
                ImageViewCompat.setImageTintList(
                    binding.iconIv,
                    tintColor(if (!isItemSelected) iconColor else selectedIconColor)
                )
        }
    var selectedIconColor = 0
        set(value) {
            field = value
            if (allowDraw)
                ImageViewCompat.setImageTintList(
                    binding.iconIv,
                    tintColor(if (!isItemSelected) iconColor else selectedIconColor)
                )
        }
    var circleColor = 0
        set(value) {
            field = value
            if (allowDraw)
                isItemSelected = isItemSelected
        }

    var icon = 0
        set(value) {
            field = value
            if (allowDraw)
                binding.iconIv.setImageResource(value)
        }
    var title = ""
        set(value) {
            field = value
            if (allowDraw)
                binding.titleTxt.text = value
        }

    var titleColor = 0
        set(value) {
            field = value
            if (allowDraw)
                binding.titleTxt.setTextColor(value)
        }

    private var navigationType = NavigationType.LABELED
        set(value) {
            field = value
            if (allowDraw) {
                isTextVisible = if (navigationType == NavigationType.LABELED)
                    1
                else 0
            }
        }

    var titleTextSize = 0
        set(value) {
            field = value
            if (allowDraw)
                binding.titleTxt.textSize = value.toFloat()
        }

    var badge: String? =
        EMPTY_VALUE
        set(value) {
            field = value
            if (allowDraw) {
                if (badge != null && badge == EMPTY_VALUE) {
                    binding.badgeTxt.text = ""
                    binding.badgeTxt.gone()
                } else {
                    if (badge != null && badge?.length ?: 0 >= 3) {
                        field = badge?.substring(0, 1) + ".."
                    }
                    binding.badgeTxt.text = badge
                    binding.badgeTxt.gone()
                    val scale = if (badge?.isEmpty() == true) 0.5f else 1f
                    binding.badgeTxt.scaleX = scale
                    binding.badgeTxt.scaleY = scale
                }
            }
        }

    var iconSize = 48.dp(context).toFloat()
        set(value) {
            field = value
            if (allowDraw) {
                val layoutParams = binding.iconIv.layoutParams
                layoutParams.apply {
                    gravity = Gravity.CENTER
                }
                binding.iconIv.layoutParams = layoutParams
                binding.iconIv.pivotX = iconSize / 2f
                binding.iconIv.pivotY = iconSize / 2f
            }
        }

    var selectedIconSize = 48.dp(context).toFloat()
        set(value) {
            field = value
            if (allowDraw) {
                val layoutParams = binding.fabView.layoutParams
                layoutParams.height = value.toInt()
                layoutParams.width = value.toInt()
                binding.iconIv.pivotX = iconSize / 2f
                binding.iconIv.pivotY = iconSize / 2f
            }
        }


    var badgeTextColor = 0
        set(value) {
            field = value
            if (allowDraw)
                binding.badgeTxt.setTextColor(field)
        }

    var badgeBackgroundColor = 0
        set(value) {
            field = value
            if (allowDraw) {
                val d = GradientDrawable()
                d.setColor(field)
                d.shape = GradientDrawable.OVAL
                ViewCompat.setBackground(binding.badgeTxt, d)
            }
        }

    var badgeFont: Typeface? = null
        set(value) {
            field = value
            if (allowDraw && field != null)
                binding.badgeTxt.typeface = value
        }

     var titleFont: Typeface? = null
        set(value) {
            field = value
            if (allowDraw && field != null)
                binding.titleTxt.typeface = value
        }

    var rippleColor = 0
        set(value) {
            field = value
            if (allowDraw) {
                isItemSelected = isItemSelected
            }
        }

    var isFromLeft = false
    var duration = 0L
    private var progress = 0f
        set(value) {
            field = value
            binding.fl.y = (1f - progress) * 18f.dp(context) - 3f.dp(context)
            ImageViewCompat.setImageTintList(
                binding.iconIv,
                tintColor(if (progress == 1f) selectedIconColor else iconColor)
            )
            val scale = (1f - progress) * (-0.2f) + 1f
            binding.iconIv.scaleX = scale
            binding.iconIv.scaleY = scale

            val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL

            ViewCompat.setBackground(binding.fabView, d)
            ViewCompat.setElevation(
                binding.fabView, if (progress > 0.7f) (progress * 4f).dp(context) else 0f
            )

            val m = 24.dp(context)
            binding.fabView.x =
                (1f - progress) * (if (isFromLeft) -m else m) + ((measuredWidth - 48f.dp(context)) / 2f)
            binding.fabView.y = (1f - progress) * measuredHeight + 6.dp(context)
        }

    var isItemSelected = false
        set(value) {
            field = value
            if (isTextVisible == 1) binding.titleTxt.gone()
            val d = GradientDrawable()
            d.setColor(circleColor)
            d.shape = GradientDrawable.OVAL
            if (isItemSelected) {
                {
                    binding.fl.setBackgroundColor(Color.TRANSPARENT)
                }.withDelay(200)
            }
        }

    var onClickListener: () -> Unit = {}
        set(value) {
            field = value
            binding.fl.setOnClickListener {
                onClickListener()
            }
        }

    private var allowDraw = false

    init {
        allowDraw = true//todo need Delete
        draw()
    }

    private fun draw() {
        if (!allowDraw)
            return
        icon = icon
        badge = badge
        title = title
        titleColor = titleColor
        titleTextSize = titleTextSize
        titleFont = titleFont
        iconSize = iconSize
        selectedIconSize = selectedIconSize
        badgeTextColor = badgeTextColor
        badgeBackgroundColor = badgeBackgroundColor
        badgeFont = badgeFont
        rippleColor = rippleColor
        onClickListener = onClickListener
        if (isTextVisible == 1)
            binding.titleTxt.visible()
        else binding.titleTxt.gone()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        progress = progress
    }

    fun unselectedItem(isAnimate: Boolean = true) {
        if (isItemSelected)
            animateProgress(false, isAnimate)
        isItemSelected = false
        binding.titleTxt.visible()
        binding.iconIv.setMargins(0, 2, 0, 0)
    }

    fun selectedItem(isAnimate: Boolean = true) {
        if (!isItemSelected){
            animateProgress(true, isAnimate)
        }
        isItemSelected = true
        binding.titleTxt.gone()
        val layoutParams = binding.fabView.layoutParams as FrameLayout.LayoutParams
        layoutParams.height = selectedIconSize.toInt()
        layoutParams.width = selectedIconSize.toInt()
        binding.fabView.layoutParams = layoutParams
        val iconTvParams = binding.iconIv.layoutParams as LinearLayout.LayoutParams
        iconTvParams.gravity = Gravity.CENTER
        binding.iconIv.layoutParams = iconTvParams
        binding.iconIv.setMargins(0, 12, 0, 0)
    }

    private fun animateProgress(enableCell: Boolean, isAnimate: Boolean = true) {
        val d = if (enableCell) duration else 250
        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            startDelay = if (enableCell) d / 4 else 0L
            duration = if (isAnimate) d else 1L
            interpolator = FastOutSlowInInterpolator()
            addUpdateListener {
                val f = it.animatedFraction
                Log.e("qmrFraction", "$f")
                progress = if (enableCell)
                    f
                else
                    1f - f
            }
            start()
        }
    }
}