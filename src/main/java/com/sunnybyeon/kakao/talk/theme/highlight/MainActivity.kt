package com.sunnybyeon.kakao.talk.theme.highlight

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.sunnybyeon.kakao.talk.theme.highlight.R
import kotlinx.android.synthetic.main.main_activity.*

open class MainActivity : Activity() {

    companion object {
        private const val KAKAOTALK_SETTINGS_THEME_URI = "kakaotalk://settings/theme/"
        private const val MARKET_URI = "market://details?id="
        const val KAKAOTALK_PACKAGE_NAME = "com.kakao.talk"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        window?.apply {
            try {
                addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
                clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
                statusBarColor = resources.getColor(R.color.statusBarColor, null)
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } catch (ignored: Throwable) {
            }
        }

        apply.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(KAKAOTALK_SETTINGS_THEME_URI + packageName)
            startActivity(intent)
            finish()
        }

        market.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_URI + KAKAOTALK_PACKAGE_NAME))
            startActivity(intent)
            finish()
        }

        if (isKakaoTalkInstalled()) {
            apply.visibility = View.VISIBLE
            market.visibility = View.GONE
        } else {
            apply.visibility = View.GONE
            market.visibility = View.VISIBLE
        }
    }

    open fun isKakaoTalkInstalled(): Boolean {
        return try {
            packageManager.getPackageInfo(KAKAOTALK_PACKAGE_NAME, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }
}
