package io.github.harrykuria.spotx

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.max

@Composable
fun SpotXOverlay(
	controller: SpotXController,
	contentPadding: PaddingValues = PaddingValues(24.dp),
	defaultOverlayColor: Color = Color(0xCC000000),
	defaultRingColor: Color = MaterialTheme.colorScheme.primary,
	defaultRingWidth: Dp = 3.dp,
	onDismissRequest: () -> Unit = { controller.stop() },
	footerContent: (@Composable () -> Unit)? = null,
) {
	if (!controller.isRunning) return
	val current = controller.targets.getOrNull(controller.currentIndex) ?: return
	val anchorBounds = controller.getAnchorBounds(current.id) ?: Rect.Zero
	val anchorCenter = controller.getAnchorCenter(current.id) ?: Offset.Zero
	val density = LocalDensity.current

	val radiusPxTarget = with(density) {
		val rp = current.radiusPaddingDp.dp.toPx()
		max(anchorBounds.width, anchorBounds.height) / 2f + rp
	}
	val animatedRadius by animateFloatAsState(targetValue = radiusPxTarget, label = "radius")

	BoxWithConstraints(
		modifier = Modifier
			.fillMaxSize()
			.background(Color.Transparent)
			.clickable { controller.next() },
	) {
		val containerHeightPx = with(density) { maxHeight.toPx() }

		Canvas(modifier = Modifier.fillMaxSize()) {
			drawIntoCanvas { canvas ->
				val paint = Paint()
				canvas.saveLayer(size.toRect(), paint)

				// Dim layer
				drawRect(color = current.overlayColor.ifTransparent(defaultOverlayColor))

				// Cutout for the target
				when (current.shape) {
					SpotXShape.Circle -> {
						drawCircle(
							color = Color.Transparent,
							radius = animatedRadius,
							center = anchorCenter,
							blendMode = BlendMode.Clear
						)
						// Ring
						drawCircle(
							color = current.ringColor.ifTransparent(defaultRingColor),
							radius = animatedRadius + with(density) { (current.ringWidthDp.dp / 2f).toPx() },
							style = Stroke(width = with(density) { current.ringWidthDp.dp.toPx() })
						)
					}
					SpotXShape.RoundedRect -> {
						val cr = with(density) { current.cornerRadiusDp.dp.toPx() }
						// Clear rounded rect
						drawRoundRect(
							color = Color.Transparent,
							topLeft = anchorBounds.topLeft,
							size = anchorBounds.size,
							cornerRadius = androidx.compose.ui.geometry.CornerRadius(cr, cr),
							blendMode = BlendMode.Clear
						)
						// Ring
						drawRoundRect(
							color = current.ringColor.ifTransparent(defaultRingColor),
							topLeft = anchorBounds.topLeft,
							size = anchorBounds.size,
							cornerRadius = androidx.compose.ui.geometry.CornerRadius(cr, cr),
							style = Stroke(width = with(density) { current.ringWidthDp.dp.toPx() })
						)
					}
				}

				canvas.restore()
			}
		}

		// Info card
		val topOrBottom = if (anchorCenter.y < containerHeightPx / 2f) Alignment.BottomCenter else Alignment.TopCenter
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(contentPadding),
			horizontalAlignment = Alignment.CenterHorizontally,
			verticalArrangement = when (topOrBottom) {
				Alignment.TopCenter -> androidx.compose.foundation.layout.Arrangement.Top
				Alignment.BottomCenter -> androidx.compose.foundation.layout.Arrangement.Bottom
				else -> androidx.compose.foundation.layout.Arrangement.Center
			}
		) {
			Surface(tonalElevation = 6.dp, shape = MaterialTheme.shapes.medium) {
				Column(modifier = Modifier.padding(16.dp)) {
					Text(
						text = current.title,
						style = MaterialTheme.typography.titleMedium,
						color = MaterialTheme.colorScheme.onSurface
					)
					Spacer(Modifier.size(8.dp))
					Text(
						text = current.description,
						style = MaterialTheme.typography.bodyMedium,
						color = MaterialTheme.colorScheme.onSurface,
						textAlign = TextAlign.Start
					)
					Spacer(Modifier.size(12.dp))
					Button(onClick = { controller.next() }) {
						Text("Next")
					}
				}
			}
			if (footerContent != null) {
				Spacer(Modifier.size(16.dp))
				footerContent()
			}
		}
	}
}

private fun Color.ifTransparent(fallback: Color): Color = if (this.alpha == 0f) fallback else this 