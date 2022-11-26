package com.storn.dynamicbattery

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.storn.dynamicbattery.databinding.ActivityBigBatteryBinding
import com.storn.view.battery.BatteryOrientation
import java.lang.ref.WeakReference

class BigBatteryActivity : AppCompatActivity() {

    private lateinit var handler: MyHandler

    private lateinit var binding: ActivityBigBatteryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBigBatteryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        binding.btStartAnim.setOnClickListener {
            handler.removeMessages(ANIM.RESET_ANIM.type)
            val msg = Message.obtain()
            msg.what = ANIM.START_ANIM.type
            handler.sendMessage(msg)
        }
        binding.btResetAnim.setOnClickListener {
            handler.removeMessages(ANIM.START_ANIM.type)
            val msg = Message.obtain()
            msg.what = ANIM.RESET_ANIM.type
            handler.sendMessage(msg)
        }

        handler = MyHandler()
        handler.init(this)
    }

    /**
     * 开始电量动画
     */
    private fun startAnim() {
        var power = binding.bigBatteryView.currentPower
        ++power
        binding.bigBatteryView.currentPower = power
        if (power < 100) {
            val msg = Message.obtain()
            msg.what = ANIM.START_ANIM.type
            handler.sendMessageDelayed(msg, 10)
        }
    }

    /**
     * 重置电量动画
     */
    private fun resetAnim() {
        var power = binding.bigBatteryView.currentPower
        --power
        binding.bigBatteryView.currentPower = power
        if (power > 0) {
            val msg = Message.obtain()
            msg.what = ANIM.RESET_ANIM.type
            handler.sendMessageDelayed(msg, 10)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_up -> {
                binding.bigBatteryView.setOrientation(BatteryOrientation.TO_UP)
            }
            R.id.menu_down -> {
                binding.bigBatteryView.setOrientation(BatteryOrientation.TO_DOWN)
            }
            R.id.menu_left -> {
                binding.bigBatteryView.setOrientation(BatteryOrientation.TO_LEFT)
            }
            R.id.menu_right -> {
                binding.bigBatteryView.setOrientation(BatteryOrientation.TO_RIGHT)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class MyHandler : Handler(Looper.getMainLooper()) {

        private var mActivity: BigBatteryActivity? = null

        fun init(activity: BigBatteryActivity) {
            val weakReference = WeakReference(activity)
            mActivity = weakReference.get()
        }

        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                ANIM.START_ANIM.type -> {
                    mActivity?.startAnim()
                }
                ANIM.RESET_ANIM.type -> {
                    mActivity?.resetAnim()
                }
            }
        }
    }
}