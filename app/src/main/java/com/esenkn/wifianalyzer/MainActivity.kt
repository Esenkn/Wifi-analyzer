package com.esenkn.wifianalyzer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esenkn.wifianalyzer.ui.theme.WifiAnalyzerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WifiAnalyzerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WifiAnalyzerScreen()
                }
            }
        }
    }
}

@Composable
fun WifiAnalyzerScreen() {
    var networks by remember {
        mutableStateOf(
            listOf(
                WifiNetwork("HomeNet-5G", "A4:2C:5E:8F:11:4D", "WPA2", "5 GHz", 36, -52, 88),
                WifiNetwork("Guest WiFi", "00:14:22:01:23:44", "WPA2", "2.4 GHz", 6, -62, 76),
                WifiNetwork("Neighbor One", "D2:5A:6B:77:08:90", "WPA3", "2.4 GHz", 11, -68, 68),
                WifiNetwork("Cafe Free", "2C:AA:8E:0C:14:FA", "Open", "2.4 GHz", 1, -74, 60),
                WifiNetwork("OfficeLink", "7E:1D:12:64:88:3C", "WPA2", "5 GHz", 149, -58, 80)
            )
        )
    }

    val primary = networks.first()
    val bestChannel = chooseBestChannel(networks)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF090F1E))
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111B30), shape = RoundedCornerShape(18.dp))
                .padding(20.dp)
        ) {
            Column {
                Text(
                    text = "WIRELESS NETWORK MONITORING",
                    color = Color(0xFFCFE0FF),
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "📶 WiFi Analyzer",
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(
                    text = "Connected network insight and nearby signal scanning.",
                    color = Color(0xFF91A4BD),
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF142039)),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Network analysis",
                    color = Color(0xFF9EB0CA),
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )
                Text(
                    text = "Scanned",
                    color = Color.White,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${networks.size} networks detected nearby. Signal quality is stable.",
                    color = Color(0xFF91A4BD)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Button(onClick = {
                        networks = refreshNetworks(networks)
                    }) {
                        Text("Refresh Scan")
                    }
                }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF142039)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "CONNECTED NETWORK",
                        color = Color(0xFF9EB0CA),
                        fontSize = 12.sp,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = primary.name,
                        color = Color.White,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )
                    Text(
                        text = "${primary.security} • ${primary.bssid} • ${primary.band}",
                        color = Color(0xFF91A4BD)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 18.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        StatusTile(label = "RSSI", value = "${primary.rssi} dBm")
                        StatusTile(label = "Signal", value = "${primary.signal}%")
                        StatusTile(label = "Band", value = primary.band)
                    }
                }
            }

            Card(
                modifier = Modifier.weight(1f),
                colors = CardDefaults.cardColors(containerColor = Color(0xFF142039)),
                shape = RoundedCornerShape(18.dp)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "CHANNEL ANALYSIS",
                        color = Color(0xFF9EB0CA),
                        fontSize = 12.sp,
                        letterSpacing = 1.5.sp
                    )
                    Text(
                        text = "Ch $bestChannel",
                        color = Color.White,
                        fontSize = 34.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(top = 6.dp, bottom = 8.dp)
                    )
                    Text(
                        text = if (bestChannel == primary.channel) {
                            "Current channel is a strong choice and should minimize interference."
                        } else {
                            "Prefer channel $bestChannel to reduce overlap and improve throughput."
                        },
                        color = Color(0xFF91A4BD)
                    )
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF142039)),
            shape = RoundedCornerShape(18.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "NEARBY NETWORKS",
                    color = Color(0xFF9EB0CA),
                    fontSize = 12.sp,
                    letterSpacing = 1.5.sp
                )

                LazyColumn(
                    modifier = Modifier.padding(top = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(networks) { network ->
                        NetworkRow(network)
                    }
                }
            }
        }
    }
}

@Composable
private fun StatusTile(label: String, value: String) {
    Column(
        modifier = Modifier
            .background(Color(0x0FFFFFFF), shape = RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            color = Color(0xFF91A4BD),
            fontSize = 11.sp,
            letterSpacing = 1.sp
        )
        Text(
            text = value,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
private fun NetworkRow(network: WifiNetwork) {
    val strength = (network.signal.coerceIn(20, 100)).toFloat() / 100f
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = network.name, color = Color.White, fontWeight = FontWeight.Bold)
            Text(
                text = network.security,
                color = Color(0xFF5FE0B1),
                fontSize = 12.sp,
                modifier = Modifier
                    .background(Color(0x1A21D49B), shape = RoundedCornerShape(999.dp))
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
        Text(
            text = "Channel ${network.channel} • ${network.band} • ${network.rssi} dBm",
            color = Color(0xFF91A4BD),
            fontSize = 12.sp
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(9.dp)
                .background(Color(0xFF293750), shape = RoundedCornerShape(999.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(strength)
                    .height(9.dp)
                    .background(
                        Color(0xFF21D49B),
                        shape = RoundedCornerShape(999.dp)
                    )
            )
        }
    }
}

private fun refreshNetworks(existing: List<WifiNetwork>): List<WifiNetwork> {
    return existing.mapIndexed { index, network ->
        val variance = (-4..4).random()
        val updatedSignal = (network.signal + if (index % 2 == 0) 2 else -2 + variance).coerceIn(30, 99)
        val updatedRssi = (network.rssi + variance).coerceIn(-90, -30)
        network.copy(rssi = updatedRssi, signal = updatedSignal)
    }
}

private fun chooseBestChannel(networks: List<WifiNetwork>): Int {
    val counts = mutableMapOf<Int, Int>()
    for (network in networks) {
        counts[network.channel] = counts.getOrDefault(network.channel, 0) + 1
    }
    return counts.entries.minByOrNull { it.value }?.key ?: 1
}
