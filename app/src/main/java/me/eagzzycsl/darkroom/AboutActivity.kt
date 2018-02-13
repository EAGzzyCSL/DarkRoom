package me.eagzzycsl.darkroom

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.view.View

import me.eagzzycsl.darkroom.ui.ActivityToolbar

class AboutActivity : ActivityToolbar(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fab = findViewById<View>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener(this)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_about
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.fab -> {
                val browserIntent = Intent(Intent.ACTION_VIEW,
                        Uri.parse(getString(R.string.url_official_website)))
                startActivity(browserIntent)
            }
        }
    }
}
