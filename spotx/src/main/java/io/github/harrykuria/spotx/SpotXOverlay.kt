package io.github.harrykuria.spotx

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
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
	defaultRingColor: Color = Color.Transparent,
	defaultRingWidth: Dp = 0.dp,
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
			val overlayPath = Path().apply {
				addRect(Rect(Offset.Zero, size))
			}
			val targetPath = Path().apply {
				when (current.shape) {
					SpotXShape.Circle -> {
						val circleRect = Rect(
							left = anchorCenter.x - animatedRadius,
							top = anchorCenter.y - animatedRadius,
							right = anchorCenter.x + animatedRadius,
							bottom = anchorCenter.y + animatedRadius
						)
						addOval(circleRect)
					}
					SpotXShape.RoundedRect -> {
						val corner = CornerRadius(
							x = with(density) { current.cornerRadiusDp.dp.toPx() },
							y = with(density) { current.cornerRadiusDp.dp.toPx() }
						)
						addRoundRect(androidx.compose.ui.geometry.RoundRect(anchorBounds, corner))
					}
				}
			}

			val spotlightPath = Path.combine(PathOperation.Difference, overlayPath, targetPath)
			val overlayColor = current.overlayColor.ifTransparent(defaultOverlayColor)
			drawPath(
				path = spotlightPath,
				color = overlayColor
			)

			val ringColor = current.ringColor.ifTransparent(defaultRingColor)
			val ringWidthPx = with(density) { current.ringWidthDp.dp.toPx() }

			if (ringWidthPx > 0f) {
				when (current.shape) {
					SpotXShape.Circle -> {
						drawCircle(
							color = ringColor,
							center = anchorCenter,
							radius = animatedRadius + ringWidthPx / 2f,
							style = Stroke(width = ringWidthPx, cap = StrokeCap.Round)
						)
					}
					SpotXShape.RoundedRect -> {
						val corner = CornerRadius(
							x = with(density) { current.cornerRadiusDp.dp.toPx() },
							y = with(density) { current.cornerRadiusDp.dp.toPx() }
						)
						drawRoundRect(
							color = ringColor,
							topLeft = anchorBounds.topLeft,
							size = anchorBounds.size,
							cornerRadius = corner,
							style = Stroke(width = ringWidthPx, cap = StrokeCap.Round)
						)
					}
				}
			}
		}

		// Info card positioned near the bottom or top edge instead of filling the screen
		val popoverAlignment = if (anchorCenter.y < containerHeightPx / 2f) Alignment.BottomCenter else Alignment.TopCenter
		Box(
			modifier = Modifier
				.fillMaxSize()
				.padding(contentPadding)
				.padding(bottom = 64.dp) // Added bottom padding to avoid navigation bars
		) {
			Surface(
				modifier = Modifier
					.align(popoverAlignment)
					.widthIn(max = 320.dp),
				tonalElevation = 0.dp, // Flat background to match React Card
				shadowElevation = 8.dp, // Standard shadow instead of tonal tint
				shape = MaterialTheme.shapes.medium
			) {
				Column(
					modifier = Modifier
						.padding(16.dp)
						.widthIn(max = 320.dp)
				) {
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
					
					Spacer(Modifier.size(16.dp))
					
					if (footerContent != null) {
						footerContent()
					} else {
						// Default footer if no custom footer provided
						val isLastStep = controller.currentIndex == controller.targets.lastIndex
						Row(
							modifier = Modifier.fillMaxWidth(),
							horizontalArrangement = Arrangement.End
						) {
							Button(
								onClick = {
									if (isLastStep) onDismissRequest() else controller.next()
								}
							) {
								Text(if (isLastStep) "Finish" else "Next")
							}
						}
					}
				}
			}
		}
	}
}

private fun Color.ifTransparent(fallback: Color): Color = if (this.alpha == 0f) fallback else this
