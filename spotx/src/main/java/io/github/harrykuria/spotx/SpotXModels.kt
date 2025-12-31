package io.github.harrykuria.spotx

import androidx.compose.ui.graphics.Color

enum class SpotXShape {
	Circle, RoundedRect
}

data class SpotXTarget(
	val id: String,
	val title: String,
	val description: String,
	val shape: SpotXShape = SpotXShape.Circle,
	val radiusPaddingDp: Float = 12f,
	val cornerRadiusDp: Float = 12f,
	val ringColor: Color = Color.Transparent,
	val ringWidthDp: Float = 0f,
	val highlightColor: Color = Color(0xFF4F46E5),
	val overlayColor: Color = Color(0xCC000000)
) 