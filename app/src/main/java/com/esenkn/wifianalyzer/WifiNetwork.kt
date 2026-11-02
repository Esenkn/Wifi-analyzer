package com.esenkn.wifianalyzer

data class WifiNetwork(
    val name: String,
    val bssid: String,
    val security: String,
    val band: String,
    val channel: Int,
    val rssi: Int,
    val signal: Int
)
