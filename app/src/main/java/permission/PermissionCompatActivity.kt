package com.example.root.permission

import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import java.util.concurrent.atomic.AtomicInteger
import java.util.concurrent.ConcurrentHashMap
import android.util.Log
import com.example.root.kpermission.R
import kotlinx.android.synthetic.main.activity_main.*

abstract class PermissionCompatActivity: AppCompatActivity() {

    private val latestPermissionRequest = AtomicInteger()
    private val permissionRequests = ConcurrentHashMap< Int, List<String>>()
    private val permissionCallbacks = ConcurrentHashMap<  List<String>, PermissionRequestCallback>()



    private val defaultPermissionRequestCallback = object : PermissionRequestCallback{

        override fun onPermissionGranted(permissions: List<String>) {
            val sResult : String = String.format( "Permission granted [$permissions]")
            Log.i("ENRICO", sResult)
        }

        override fun onPermissionDenied(permissions: List<String>) {
            val sResult : String = String.format( "Permission denied [$permissions]")
            Log.i("ENRICO", sResult)
        }

        override fun onPermissionSummary(permissionsNames: List<String>, permissionsValues: IntArray) {

            val nCont : Int = permissionsValues.size
            var sResult = ""

            for(i in 0..(nCont-1) )
            {
                sResult += String.format(
                        permissionsNames[i].replace("android.permission.", "") + " = " +
                        when (permissionsValues[i]) {
                            -1 -> "DENIED"
                            else -> "GRANTED"
                        }
                        + "\n")
            }
            message.text = sResult
            Log.i("ENRICO", sResult)

        }
    }


    fun requestPermissions(
            vararg permissions: String,
            callback: PermissionRequestCallback = defaultPermissionRequestCallback
    )
    {
        val id = latestPermissionRequest.incrementAndGet()
        val items = mutableListOf<String>()
        items.addAll(permissions)
        permissionRequests[id] = items
        permissionCallbacks[items] = callback
        ActivityCompat.requestPermissions(this, permissions, id)
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<String>,
            grantResults: IntArray     )
    {
        val items = permissionRequests[requestCode]
        items?.let {
            val callback = permissionCallbacks[items]
            callback?.let {
                callback.onPermissionSummary(items, grantResults)

                /*var success = true
                for (x in 0..grantResults.lastIndex) {
                    val result = grantResults[x]
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        success = false
                        break
                    }
                }
                if (success) {
                    callback.onPermissionGranted(items)
                } else {
                    callback.onPermissionDenied(items)
                }*/

            }
        }
    }

}