package me.eagzzycsl.darkroom

import android.animation.ArgbEvaluator
import android.content.Intent
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_guide.*
import me.eagzzycsl.darkroom.guide.*
import me.eagzzycsl.darkroom.ui.GuidePagerAdapter
import me.eagzzycsl.darkroom.guide.GuideFragmentIntro
import me.eagzzycsl.darkroom.utils.MyConfig

class GuideActivity : AppCompatActivity(), View.OnClickListener {
    private val evaluator = ArgbEvaluator()


    class GuideEventListener(private val buttonNext: Button, private val buttonSkip: Button, private val buttonDone: Button) {
        fun toggleShowDone(showDone: Boolean) {
            buttonNext.visibility = if (showDone) View.GONE else View.VISIBLE
            buttonSkip.visibility = if (showDone) View.GONE else View.VISIBLE
            buttonDone.visibility = if (showDone) View.VISIBLE else View.GONE
        }
    }

    class GuidePage(val fragment: GuideFragment, val bgColor: Int) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_guide)
        val guideTab = findViewById<TabLayout>(R.id.guide_tab)
        val guideViewPager = findViewById<ViewPager>(R.id.guide_pager)
        val buttonNext = findViewById<Button>(R.id.button_next)
        val buttonSkip = findViewById<Button>(R.id.button_skip)
        val buttonDone = findViewById<Button>(R.id.button_done)
        val constraintGuide = findViewById<View>(R.id.constraint_guide)
        val guideEventListener = GuideEventListener(
                buttonNext,
                buttonSkip,
                buttonDone
        )
        val guidePageArray = arrayOf<GuidePage>(
                GuidePage(
                        GuideFragmentIntro(guideEventListener),
                        Color.rgb(25, 118, 210)
                ),
                GuidePage(
                        GuideFragmentAccessibility(guideEventListener),
                        Color.rgb(0, 151, 167)
                ),
                GuidePage(
                        GuideFragmentBattery(guideEventListener),
                        Color.rgb(0, 121, 107)
                ),
                GuidePage(
                        GuideFragmentWarning(guideEventListener),
                        Color.rgb(245, 124, 0)
                ),
                GuidePage(
                        GuideFragmentDone(guideEventListener),
                        Color.rgb(56, 142, 60)
                )
        )

        guideViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if (position < guidePageArray.size - 1) {
                    constraintGuide.setBackgroundColor(
                            calcEvaluateColor(positionOffset, position, guidePageArray)
                    )
                }

            }

            override fun onPageSelected(position: Int) {
                if (position == guidePageArray.size - 1) {
                    guideEventListener.toggleShowDone(true)

                } else {
                    guideEventListener.toggleShowDone(false)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        guideTab.setupWithViewPager(guideViewPager)
        guideViewPager.adapter = GuidePagerAdapter(
                supportFragmentManager,
                guidePageArray.map { it -> it.fragment }.toTypedArray()
        )
        buttonNext.setOnClickListener(this)
        buttonSkip.setOnClickListener(this)
        buttonDone.setOnClickListener(this)
    }

    fun calcEvaluateColor(positionOffset: Float, position: Int, guidePages: Array<GuidePage>): Int {
        return evaluator.evaluate(
                positionOffset,
                guidePages[position].bgColor,
                guidePages[position + 1].bgColor) as Int
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_skip -> {
                guide_pager.setCurrentItem(guide_pager.adapter.count, false)
            }
            R.id.button_next -> {
                guide_pager.setCurrentItem(guide_pager.currentItem + 1, true)
            }
            R.id.button_done -> {
                MyConfig.doNotShowGuideActivity(this)
                startActivity(Intent(
                        this,
                        MainActivity::class.java
                ))
                finish()
            }
        }
    }
}
