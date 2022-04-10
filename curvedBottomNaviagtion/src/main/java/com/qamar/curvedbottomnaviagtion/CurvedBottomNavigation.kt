@file:Suppress("unused")


package com.qamar.curvedbottomnaviagtion

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.util.AttributeSet
import android.util.LayoutDirection
import android.util.Log
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.res.ResourcesCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.navigation.NavController
import com.qamar.curvedbottomnaviagtion.enum.NavigationType
import kotlin.math.abs

internal typealias IBottomNavigationListener = (model: CurvedBottomNavigation.Model) -> Unit

@Suppress("MemberVisibilityCanBePrivate")
class CurvedBottomNavigation : FrameLayout {

    var models = ArrayList<Model>()
    var cells = ArrayList<BottomNavigationItem>()
        private set
    private var callListenerWhenIsSelected = false

    private var selectedId = -1

    private var onClickedListener: IBottomNavigationListener = {}
    private var onShowListener: IBottomNavigationListener = {}
    private var onReselectListener: IBottomNavigationListener = {}

    private lateinit var navController: NavController
    private var isAnimating = false

    var iconColor = Color.parseColor("#757575")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var heightBottomNaviagtion = 86.dp(context)
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var navigationType = NavigationType.LABELED
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var titleColor = Color.parseColor("#ffffff")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var titleTextSize = 12
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var iconSize = 48f
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var selectedIconSize = 48f
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var selectedIconColor = Color.parseColor("#2196f3")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var backgroundBottomColor = Color.parseColor("#ffffff")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var curveRadius = 72f
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var fabColor = Color.parseColor("#ffffff")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    private var shadowColor = -0x454546
    var badgeTextColor = Color.parseColor("#ffffff")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var badgeBackgroundColor = Color.parseColor("#ff0000")
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var badgeFontType: Typeface? = null
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var titleFontType: Typeface? = null
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    var hasAnimation: Boolean = true
        set(value) {
            field = value
            updateAllIfAllowDraw()
        }
    private var rippleColor = Color.parseColor("#757575")

    private var allowDraw = false

    @Suppress("PrivatePropertyName")
    private lateinit var ll_cells: LinearLayout
    private lateinit var curvedView: CurvedView


    constructor(context: Context) : super(context) {
        initializeViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setAttributeFromXml(context, attrs)
        initializeViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setAttributeFromXml(context, attrs)
        initializeViews()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setAttributeFromXml(context: Context, attrs: AttributeSet) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CurvedBottomNavigationView, 0, 0
        )
        try {
            a.apply {
                titleColor = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_titleColor,
                    titleColor
                )
                titleTextSize = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_titleTextSize,
                    titleTextSize
                )
                iconColor = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_iconColor,
                    iconColor
                )
                selectedIconColor = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_selectedIconColor,
                    selectedIconColor
                )
                backgroundBottomColor = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_background,
                    backgroundBottomColor
                )
                heightBottomNaviagtion = getDimensionPixelSize(
                    R.styleable.CurvedBottomNavigationView_cbn_height,
                    heightBottomNaviagtion
                )
                iconSize = getDimensionPixelSize(
                    R.styleable.CurvedBottomNavigationView_cbn_icon_size,
                    iconSize.toInt()
                ).toFloat()
                selectedIconSize = getDimensionPixelSize(
                    R.styleable.CurvedBottomNavigationView_cbn_selected_icon_size,
                    selectedIconSize.toInt()
                ).toFloat()
                curveRadius = getDimensionPixelSize(
                    R.styleable.CurvedBottomNavigationView_cbn_curve_radius,
                    curveRadius.toInt()
                ).toFloat()
                fabColor =
                    getColor(R.styleable.CurvedBottomNavigationView_cbn_fabColor, fabColor)
                badgeTextColor =
                    getColor(
                        R.styleable.CurvedBottomNavigationView_cbn_badgeTextColor,
                        badgeTextColor
                    )
                badgeBackgroundColor = getColor(
                    R.styleable.CurvedBottomNavigationView_cbn_badgeBackgroundColor,
                    badgeBackgroundColor
                )
                rippleColor =
                    getColor(R.styleable.CurvedBottomNavigationView_cbn_rippleColor, rippleColor)
                shadowColor =
                    getColor(R.styleable.CurvedBottomNavigationView_cbn_shadowColor, shadowColor)

                if (hasValue(R.styleable.CurvedBottomNavigationView_cbn_badgeFont)) {
                    val fontId = getResourceId(R.styleable.CurvedBottomNavigationView_cbn_badgeFont, -1)
                    val typeface = ResourcesCompat.getFont(context, fontId)
                    badgeFontType = typeface
                }
                if (hasValue(R.styleable.CurvedBottomNavigationView_cbn_titleFont)) {
                    val fontId = getResourceId(R.styleable.CurvedBottomNavigationView_cbn_titleFont, -1)
                    val typeface = ResourcesCompat.getFont(context, fontId)
                    if (typeface != null)
                        titleFontType = typeface
                }
                hasAnimation =
                    getBoolean(
                        R.styleable.CurvedBottomNavigationView_cbn_hasAnimation,
                        hasAnimation
                    )
            }
        } finally {
            a.recycle()
        }
    }

    private fun initializeViews() {
        ll_cells = LinearLayout(context)
        ll_cells.apply {
            val params = LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightBottomNaviagtion)
            params.gravity =Gravity.CENTER
            layoutParams = params
            orientation = LinearLayout.HORIZONTAL
            clipChildren = false
            clipToPadding = false
        }

        curvedView = CurvedView(context)
        curvedView.curveX = curveRadius
        Log.e("qmrCurve","${curveRadius}")
        curvedView.apply {
            curveWidth = curveRadius
            layoutParams =
                LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, heightBottomNaviagtion)
            color = backgroundBottomColor
            curveX = curveRadius
            shadowColor = this@CurvedBottomNavigation.shadowColor
        }

        addView(curvedView)
        addView(ll_cells)
        allowDraw = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (selectedId == -1) {
            curvedView.curveX =
                if (Build.VERSION.SDK_INT >= 17 && layoutDirection == LayoutDirection.RTL) measuredWidth + 255f.dp(
                    context
                ) else (-255f).dp(context)
        }
        if (selectedId != -1) {
            show(selectedId, false)
        }
    }

    init {

    }

    fun add(model: Model) {
        val cell = BottomNavigationItem(context)
        cell.apply {
            val params = LinearLayout.LayoutParams(0, heightBottomNaviagtion, 1f)
            layoutParams = params
            icon = model.icon
            title = model.title
            badge = model.count
            titleTextSize = this@CurvedBottomNavigation.titleTextSize
            titleColor = this@CurvedBottomNavigation.titleColor
            iconColor = this@CurvedBottomNavigation.iconColor
            selectedIconColor = this@CurvedBottomNavigation.selectedIconColor
            circleColor = this@CurvedBottomNavigation.fabColor
            badgeTextColor = this@CurvedBottomNavigation.badgeTextColor
            badgeBackgroundColor = this@CurvedBottomNavigation.badgeBackgroundColor
            badgeFont = this@CurvedBottomNavigation.badgeFontType
            titleFontType = this@CurvedBottomNavigation.titleFontType
            selectedIconSize = this@CurvedBottomNavigation.selectedIconSize
            iconSize = this@CurvedBottomNavigation.iconSize
            rippleColor = this@CurvedBottomNavigation.rippleColor
            onClickListener = {
                if (isShowing(model.id)) // added for https://github.com/shetmobile/MeowBottomNavigation/issues/39
                    onReselectListener(model)

                if (!cell.isItemSelected && !isAnimating) {
                    show(model.id, hasAnimation)
                    onClickedListener(model)
                } else {
                    if (callListenerWhenIsSelected)
                        onClickedListener(model)
                }
            }
            unselectedItem(hasAnimation)
            ll_cells.addView(this)
        }
        cells.add(cell)
        models.add(model)
    }

    private fun updateAllIfAllowDraw() {
        if (!allowDraw)
            return

        cells.forEach {
            it.titleColor = titleColor
            it.titleTextSize = titleTextSize
            it.iconColor = iconColor
            it.selectedIconColor = selectedIconColor
            it.circleColor = fabColor
            it.badgeTextColor = badgeTextColor
            it.badgeBackgroundColor = badgeBackgroundColor
            it.badgeFont = badgeFontType
            it.titleFont = titleFontType
            it.iconSize = iconSize
            it.selectedIconSize = selectedIconSize
        }
        curvedView.color = backgroundBottomColor
        curvedView.curveWidth = curveRadius
        val layoutParams = curvedView.layoutParams
        layoutParams.height = heightBottomNaviagtion
        curvedView.layoutParams = layoutParams
    }

    private fun anim(cell: BottomNavigationItem, id: Int, enableAnimation: Boolean = true) {
        isAnimating = true

        val pos = getModelPosition(id)
        val nowPos = getModelPosition(selectedId)

        val nPos = if (nowPos < 0) 0 else nowPos
        val dif = abs(pos - nPos)
        val d = (dif) * 100L + 150L

        val animDuration = if (enableAnimation && hasAnimation) d else 1L
        val animInterpolator = FastOutSlowInInterpolator()

        val anim = ValueAnimator.ofFloat(0f, 1f)
        anim.apply {
            duration = animDuration
            interpolator = animInterpolator
            val beforeX = curvedView.curveX
            addUpdateListener {
                val f = it.animatedFraction
                val newX = cell.x + (cell.measuredWidth / 2)
                if (newX > beforeX)
                    curvedView.curveX = f * (newX - beforeX) + beforeX
                else
                    curvedView.curveX = beforeX - f * (beforeX - newX)
                if (f == 1f)
                    isAnimating = false
            }
            start()
        }

        if (abs(pos - nowPos) > 1) {
            val progressAnim = ValueAnimator.ofFloat(0f, 1f)
            progressAnim.apply {
                duration = animDuration
                interpolator = animInterpolator
                addUpdateListener {
                    val f = it.animatedFraction
                    curvedView.progress = f * 2f
                }
                start()
            }
        }

        cell.isFromLeft = pos > nowPos
        cells.forEach {
            it.duration = d
        }
    }

    fun show(id: Int, enableAnimation: Boolean = true) {

        for (i in models.indices) {
            val model = models[i]
            val cell = cells[i]
            if (model.id == id) {
                anim(cell, id, enableAnimation)
                cell.selectedItem(enableAnimation)
                onShowListener(model)
            } else {
                cell.unselectedItem(hasAnimation)
            }
        }
        selectedId = id
    }

    fun setupNavController(navController: NavController) {
        this.navController = navController
        show(navController.currentDestination?.id!!)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            show(destination.id, true)
        }
    }


    fun isShowing(id: Int): Boolean {
        return selectedId == id
    }

    fun getModelById(id: Int): Model? {
        models.forEach {
            if (it.id == id)
                return it
        }
        return null
    }

    fun getCellById(id: Int): BottomNavigationItem? {
        return cells[getModelPosition(id)]
    }

    fun getModelPosition(id: Int): Int {
        for (i in models.indices) {
            val item = models[i]
            if (item.id == id)
                return i
        }
        return -1
    }

    fun setCount(id: Int, count: String) {
        val model = getModelById(id) ?: return
        val pos = getModelPosition(id)
        model.count = count
        cells[pos].badge = count
    }

    fun clearCount(id: Int) {
        val model = getModelById(id) ?: return
        val pos = getModelPosition(id)
        model.count = BottomNavigationItem.EMPTY_VALUE
        cells[pos].badge = BottomNavigationItem.EMPTY_VALUE
    }

    fun clearAllCounts() {
        models.forEach {
            clearCount(it.id)
        }
    }

    fun setOnShowListener(listener: IBottomNavigationListener) {
        onShowListener = listener
    }

    fun setOnClickMenuListener(listener: IBottomNavigationListener) {
        onClickedListener = listener
    }

    fun setOnReselectListener(listener: IBottomNavigationListener) {
        onReselectListener = listener
    }

    class Model(var id: Int, var title: String, var icon: Int) {

        var count: String = BottomNavigationItem.EMPTY_VALUE

    }
}