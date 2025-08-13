package io.github.harrykuria.spotx.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.github.harrykuria.spotx.SpotXController
import io.github.harrykuria.spotx.SpotXOverlay
import io.github.harrykuria.spotx.SpotXShape
import io.github.harrykuria.spotx.SpotXTarget
import io.github.harrykuria.spotx.spotxAnchor

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MaterialTheme {
				val controller = remember { SpotXController() }
				Surface(Modifier.fillMaxSize()) {
					Column(
						modifier = Modifier.fillMaxSize().padding(24.dp),
						verticalArrangement = Arrangement.Center
					) {
						Button(
							modifier = Modifier.spotxAnchor("btn.primary", controller),
							onClick = {}
						) { Text("Primary Action") }
						Spacer(Modifier.size(16.dp))
						Button(
							modifier = Modifier.spotxAnchor("btn.secondary", controller),
							onClick = {}
						) { Text("Secondary Action") }
						Spacer(Modifier.size(24.dp))
						Button(onClick = {
							controller.start(
								targets = listOf(
									SpotXTarget(
										id = "btn.primary",
										title = "Primary Button",
										description = "Tap to perform the main action.",
										ringColor = Color(0xFF22C55E)
									),
									SpotXTarget(
										id = "btn.secondary",
										title = "Secondary Button",
										description = "Use this for alternative options.",
										shape = SpotXShape.RoundedRect,
										ringColor = Color(0xFF3B82F6)
									)
								)
							)
						}) { Text("Start Tour") }
					}
					SpotXOverlay(controller = controller)
				}
			}
		}
	}
} 