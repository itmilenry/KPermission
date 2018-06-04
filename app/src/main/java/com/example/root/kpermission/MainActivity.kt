package com.example.root.kpermission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import com.example.root.permission.*


class MainActivity : PermissionCompatActivity() {

    companion object {
        val REQUEST_GPS = 0
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                message.setText(R.string.title_dashboard)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perm1 -> {
                message.setText(R.string.title_perm1)
                requestGpsPermission()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_perm2 -> {
                message.setText(R.string.title_perm2)
                this.requestPermissions(
                        Manifest.permission.ACCESS_FINE_LOCATION.toString(),
                        Manifest.permission.ACCESS_COARSE_LOCATION.toString(),
                        Manifest.permission.CAMERA.toString(),
                        Manifest.permission.ACCESS_WIFI_STATE.toString(),
                        Manifest.permission.READ_CALENDAR
                )
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun requestGpsPermission() {
        Log.i( "KPERMISSION", "IN")
        ActivityCompat.requestPermissions(
                this@MainActivity,
                arrayOf( Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.CAMERA),
                REQUEST_GPS        )
    }

    /*
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var sResult = "REQUEST CODE=" + requestCode.toString() + " - "
        Log.i( "KPERMISSION", "HANDLER")
        if (requestCode == REQUEST_GPS)
        {
            for (grantResult in grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    sResult += String.format(Locale.ENGLISH, "Permission granted [%d]", requestCode)
                } else {
                    sResult += String.format(Locale.ENGLISH, "Permission denied [%d]", requestCode)
                }
            }
        }
        Toast.makeText(this, sResult, 1).show()

    }
    */

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }
}
