package io.github.harrykuria.spotx

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect

/**
 * Controls the SpotX onboarding tour sequence.
 */
class SpotXController {
	private val anchorBoundsById: MutableMap<String, Rect> = mutableStateMapOf()
	private val anchorCenterById: MutableMap<String, Offset> = mutableStateMapOf()

	var targets: List<SpotXTarget> by mutableStateOf(emptyList())
		private set

	var currentIndex: Int by mutableStateOf(-1)
		private set

	val isRunning: Boolean
		get() = currentIndex in targets.indices

	fun registerAnchor(id: String, boundsInRoot: Rect) {
		anchorBoundsById[id] = boundsInRoot
		anchorCenterById[id] = boundsInRoot.center
	}

	fun getAnchorBounds(id: String): Rect? = anchorBoundsById[id]
	fun getAnchorCenter(id: String): Offset? = anchorCenterById[id]

	fun start(targets: List<SpotXTarget>, startIndex: Int = 0) {
		this.targets = targets
		this.currentIndex = if (targets.isNotEmpty()) startIndex.coerceIn(0, targets.lastIndex) else -1
	}

	fun next() {
		if (!isRunning) return
		currentIndex++
		if (currentIndex !in targets.indices) stop()
	}

	fun previous() {
		if (!isRunning) return
		currentIndex--
		if (currentIndex !in targets.indices) stop()
	}

	fun stop() {
		currentIndex = -1
	}
} 