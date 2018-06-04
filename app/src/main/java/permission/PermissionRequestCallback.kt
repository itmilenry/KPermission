package com.example.root.permission

interface PermissionRequestCallback {

    fun onPermissionGranted(permissions: List<String>)
    fun onPermissionDenied(permissions: List<String>)
    fun onPermissionSummary(permissionsNames: List<String>, permissionsValues: IntArray)

}