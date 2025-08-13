package io.github.harrykuria.spotx

import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.unit.toSize

fun Modifier.spotxAnchor(id: String, controller: SpotXController): Modifier =
	this.then(
		Modifier.onGloballyPositioned { coords ->
			val pos = coords.positionInRoot()
			val size = coords.size.toSize()
			controller.registerAnchor(id, Rect(offset = pos, size = size))
		}
	) 