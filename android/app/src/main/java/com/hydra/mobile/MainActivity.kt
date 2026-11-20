package com.hydra.mobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF090B10)
private val Panel = Color(0xFF141923)
private val Accent = Color(0xFF55A6FF)
private val Muted = Color(0xFF9AA8BA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { HydraApp() }
    }
}

@Composable
private fun HydraApp() {
    var selected by remember { mutableStateOf("Home") }
    var notifications by remember { mutableStateOf(true) }

    MaterialTheme(
        colorScheme = darkColorScheme(
            primary = Accent,
            background = Bg,
            surface = Panel,
            onBackground = Color.White,
            onSurface = Color.White
        )
    ) {
        Scaffold(containerColor = Bg, bottomBar = {
            NavigationBar(containerColor = Panel) {
                listOf("Home", "Tools", "Settings").forEach { item ->
                    NavigationBarItem(
                        selected = selected == item,
                        onClick = { selected = item },
                        icon = { Text(when (item) { "Home" -> "⌂"; "Tools" -> "◇"; else -> "⚙" }, fontSize = 20.sp) },
                        label = { Text(item) }
                    )
                }
            }
        }) { padding ->
            Column(
                Modifier.fillMaxSize().padding(padding).verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 22.dp),
                verticalArrangement = Arrangement.spacedBy(18.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("HYDRA", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Black, letterSpacing = 3.sp)
                        Text("MOBILE COMPANION", color = Muted, fontSize = 11.sp, letterSpacing = 2.sp)
                    }
                    Box(Modifier.size(42.dp).background(Accent.copy(alpha = .15f), RoundedCornerShape(14.dp)), Alignment.Center) {
                        Text("H", color = Accent, fontWeight = FontWeight.Bold, fontSize = 22.sp)
                    }
                }
                when (selected) {
                    "Home" -> {
                        CardBlock {
                            Text("Welcome back", color = Muted, fontSize = 13.sp)
                            Text("Your Hydra dashboard", color = Color.White, fontSize = 21.sp, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(8.dp))
                            Text("A mobile companion for information, preferences, and permitted community tools.", color = Muted, lineHeight = 20.sp)
                        }
                        Text("QUICK ACCESS", color = Muted, fontSize = 11.sp, letterSpacing = 2.sp)
                        ToolCard("Game information", "View general Among Us reference notes", "Open") { selected = "Tools" }
                        ToolCard("Session notes", "Keep personal notes for private games", "Open") { selected = "Tools" }
                    }
                    "Tools" -> {
                        Text("TOOLS", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        ToolCard("Game information", "A place for game-version and reference notes.", "Available") {}
                        ToolCard("Session notes", "Personal notes for organizing private sessions.", "Coming soon") {}
                        ToolCard("Community guidelines", "Keep play fair and use only features permitted by the host.", "Read") {}
                    }
                    else -> {
                        Text("SETTINGS", color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        CardBlock {
                            Text("Notifications", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text("Control optional app notifications.", color = Muted, fontSize = 13.sp)
                            Switch(checked = notifications, onCheckedChange = { notifications = it })
                        }
                        CardBlock {
                            Text("About Hydra Mobile", color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text("Version 1.0.0 · Native Android companion", color = Muted, fontSize = 13.sp)
                        }
                    }
                }
                Text("HYDRA MOBILE · ANDROID", color = Color(0xFF596575), fontSize = 10.sp, letterSpacing = 1.5.sp)
            }
        }
    }
}

@Composable
private fun CardBlock(content: @Composable ColumnScope.() -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Column(Modifier.fillMaxWidth().padding(18.dp), verticalArrangement = Arrangement.spacedBy(8.dp), content = content)
    }
}

@Composable
private fun ToolCard(title: String, description: String, action: String, onClick: () -> Unit) {
    Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
        Row(Modifier.fillMaxWidth().padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(title, color = Color.White, fontWeight = FontWeight.SemiBold)
                Text(description, color = Muted, fontSize = 12.sp, lineHeight = 17.sp)
            }
            TextButton(onClick = onClick) { Text(action, color = Accent) }
        }
    }
}
