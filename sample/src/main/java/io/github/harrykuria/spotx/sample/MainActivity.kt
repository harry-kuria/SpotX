package io.github.harrykuria.spotx.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import io.github.harrykuria.spotx.SpotXController
import io.github.harrykuria.spotx.SpotXOverlay
import io.github.harrykuria.spotx.SpotXShape
import io.github.harrykuria.spotx.SpotXTarget
import io.github.harrykuria.spotx.spotxAnchor
import kotlinx.coroutines.launch

private val PrimaryBackground = Color(0xFF050914)
private val CardSurface = Color(0xFF0F172A)
private val CardBorder = Color(0xFF1E293B)
private val AccentPurple = Color(0xFF7C3AED)
private val AccentBlue = Color(0xFF2563EB)
private val AccentGreen = Color(0xFF22C55E)
private val AccentPink = Color(0xFFEC4899)

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			val controller = remember { SpotXController() }
			MaterialTheme(
				colorScheme = MaterialTheme.colorScheme.copy(
					primary = AccentPurple,
					background = PrimaryBackground,
					onBackground = Color.White
				)
			) {
				Surface(
					modifier = Modifier
						.fillMaxSize()
						.background(PrimaryBackground)
				) {
					Box {
						SpotXHomeContent(controller = controller)
						SpotXOverlay(controller = controller)
					}
				}
			}
		}
	}
}

@Composable
private fun SpotXHomeContent(controller: SpotXController) {
	val listState = rememberLazyListState()
	val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)
	val stats = remember { sampleStats() }
	val modules = remember { sampleModules() }
	val activities = remember { sampleActivities() }

	LaunchedEffect(controller.currentIndex, controller.targets, listState) {
		val currentTargetId = controller.targets.getOrNull(controller.currentIndex)?.id
	val scrollIndex = when {
		currentTargetId == "home-header" -> 0
		currentTargetId == "btn.startTour" -> 1
		currentTargetId == "stats-carousel" || currentTargetId?.startsWith("stat.") == true -> 2
		currentTargetId?.startsWith("module.") == true -> 3
		currentTargetId?.startsWith("activity") == true -> 4
		else -> null
	}
		scrollIndex?.let {
			try {
				listState.animateScrollToItem(it, scrollOffset = -64)
			} catch (_: Exception) {
				/* ignore when layout isn't ready yet */
			}
		}
	}

	Box(
		modifier = Modifier
			.fillMaxSize()
			.background(PrimaryBackground)
	) {
		LazyColumn(
			state = listState,
			modifier = Modifier.fillMaxSize(),
			verticalArrangement = Arrangement.spacedBy(24.dp),
			flingBehavior = flingBehavior,
			contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp)
		) {
			item { HomeHeader(controller) }
			item { StartTourSection(controller) }
			item { StatsCarousel(stats, controller) }
			item { ModuleGrid(modules, controller) }
			item { ActivitySection(activities) }
			item { Spacer(modifier = Modifier.height(32.dp)) }
		}
	}
}

@Composable
private fun HomeHeader(controller: SpotXController) {
	val greeting = if (isSystemInDarkTheme()) "Good evening," else "Good morning,"
	Box(
		modifier = Modifier
			.padding(horizontal = 18.dp)
			.clip(RoundedCornerShape(28.dp))
			.background(
				brush = Brush.linearGradient(
					colors = listOf(Color(0xFF312E81), Color(0xFF0B1223))
				)
			)
			.padding(20.dp)
			.zIndex(1f)
			.spotxAnchor("home-header", controller)
	) {
		Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
					Text(
						text = greeting,
						style = MaterialTheme.typography.bodyMedium.copy(
							color = Color.White.copy(alpha = 0.75f)
						)
					)
					Text(
						text = "Jordan Freeman",
						style = MaterialTheme.typography.titleLarge.copy(
							color = Color.White,
							fontWeight = FontWeight.Bold
						)
					)
				}
				Icon(
					imageVector = Icons.Default.Notifications,
					contentDescription = null,
					tint = Color.White,
					modifier = Modifier
						.size(36.dp)
						.clip(CircleShape)
						.background(Color.White.copy(alpha = 0.12f))
						.padding(8.dp)
				)
			}
			Text(
				text = "Stay ahead of every transaction and connected device update",
				style = MaterialTheme.typography.bodyMedium.copy(
					color = Color.White.copy(alpha = 0.9f),
					fontSize = 14.sp
				),
				maxLines = 2,
				overflow = TextOverflow.Ellipsis
			)
			Row(
				horizontalArrangement = Arrangement.spacedBy(12.dp)
			) {
				Chip(text = "Monitored", color = AccentGreen)
				Chip(text = "Secure", color = AccentPink)
				Chip(text = "Connected", color = AccentBlue)
			}
		}
	}
}

@Composable
private fun Chip(text: String, color: Color) {
	Box(
		modifier = Modifier
			.clip(RoundedCornerShape(10.dp))
			.background(color.copy(alpha = 0.15f))
			.padding(vertical = 4.dp, horizontal = 10.dp)
	) {
		Text(
			text = text,
			style = MaterialTheme.typography.labelLarge.copy(
				color = color,
				fontWeight = FontWeight.SemiBold
			)
		)
	}
}

@Composable
private fun StatsCarousel(stats: List<HomeStat>, controller: SpotXController) {
	LazyRow(
		modifier = Modifier
			.padding(horizontal = 16.dp)
			.fillMaxWidth()
			.spotxAnchor("stats-carousel", controller),
		horizontalArrangement = Arrangement.spacedBy(12.dp)
	) {
		items(stats) { stat ->
			StatCard(stat = stat, controller = controller)
		}
	}
}

@Composable
private fun StatCard(stat: HomeStat, controller: SpotXController, modifier: Modifier = Modifier) {
	Column(
		modifier = modifier
			.width(152.dp)
			.background(CardSurface, shape = RoundedCornerShape(20.dp))
			.padding(16.dp)
			.border(
				width = 1.dp,
				color = CardBorder,
				shape = RoundedCornerShape(20.dp)
			)
			.spotxAnchor(stat.id, controller)
	) {
		Box(
			modifier = Modifier
				.size(32.dp)
				.clip(CircleShape)
				.background(stat.accent.copy(alpha = 0.15f)),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = stat.badge,
				style = MaterialTheme.typography.bodySmall.copy(
					color = stat.accent,
					fontWeight = FontWeight.Bold
				)
			)
		}
		Spacer(modifier = Modifier.height(8.dp))
		Text(
			text = stat.value,
			style = MaterialTheme.typography.headlineSmall.copy(
				color = Color.White,
				fontWeight = FontWeight.Bold
			)
		)
		Text(
			text = stat.title,
			style = MaterialTheme.typography.bodySmall.copy(
				color = Color.White.copy(alpha = 0.6f)
			)
		)
		Text(
			text = stat.subtitle,
			style = MaterialTheme.typography.labelSmall.copy(
				color = Color.White.copy(alpha = 0.6f)
			)
		)
	}
}

@Composable
private fun ModuleGrid(modules: List<HomeModule>, controller: SpotXController) {
	Column(modifier = Modifier.padding(horizontal = 16.dp)) {
		Row(
			modifier = Modifier.fillMaxWidth(),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.CenterVertically
		) {
			Text(
				text = "Modules",
				style = MaterialTheme.typography.titleMedium.copy(
					fontWeight = FontWeight.Bold,
					color = Color.White
				)
			)
			Text(
				text = "See all",
				style = MaterialTheme.typography.bodySmall.copy(
					color = AccentBlue,
					fontWeight = FontWeight.Medium
				),
				modifier = Modifier.clickable { }
			)
		}
		Spacer(modifier = Modifier.height(12.dp))
		modules.chunked(2).forEach { row ->
			Row(
				horizontalArrangement = Arrangement.spacedBy(12.dp),
				modifier = Modifier.fillMaxWidth()
			) {
				row.forEach { module ->
					ModuleCard(module = module, controller = controller, modifier = Modifier.weight(1f))
				}
				if (row.size < 2) {
					Spacer(modifier = Modifier.width(0.dp))
				}
			}
			Spacer(modifier = Modifier.height(12.dp))
		}
	}
}

@Composable
private fun ModuleCard(module: HomeModule, controller: SpotXController, modifier: Modifier = Modifier) {
	Card(
		modifier = modifier
			.height(156.dp)
			.spotxAnchor(module.anchorId, controller),
		shape = RoundedCornerShape(20.dp),
		colors = CardDefaults.cardColors(
			containerColor = CardSurface
		)
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp),
			verticalArrangement = Arrangement.SpaceBetween
		) {
			Box(
				modifier = Modifier
					.size(36.dp)
					.clip(CircleShape)
					.background(module.accent.copy(alpha = 0.2f)),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = module.badge,
					style = MaterialTheme.typography.bodyMedium.copy(
						color = module.accent,
						fontWeight = FontWeight.Bold
					)
				)
			}
			Column(
				verticalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text(
					text = module.title,
					style = MaterialTheme.typography.titleMedium.copy(
						fontWeight = FontWeight.Bold,
						color = Color.White
					)
				)
				Text(
					text = module.description,
					style = MaterialTheme.typography.bodySmall.copy(
						color = Color.White.copy(alpha = 0.65f)
					),
					maxLines = 2,
					overflow = TextOverflow.Ellipsis
				)
			}
		}
	}
}

@Composable
private fun ActivitySection(activities: List<ActivityEvent>) {
	Column(modifier = Modifier.padding(horizontal = 16.dp)) {
		Text(
			text = "Recent activity",
			style = MaterialTheme.typography.titleMedium.copy(
				fontWeight = FontWeight.Bold,
				color = Color.White
			)
		)
		Spacer(modifier = Modifier.height(12.dp))
		activities.forEachIndexed { index, activity ->
			ActivityCard(activity)
			if (index < activities.lastIndex) {
				HorizontalDivider(
					modifier = Modifier.padding(vertical = 12.dp),
					color = Color.White.copy(alpha = 0.08f)
				)
			}
		}
	}
}

@Composable
private fun ActivityCard(activity: ActivityEvent) {
	Row(
		modifier = Modifier
			.fillMaxWidth()
			.height(68.dp)
			.clip(RoundedCornerShape(18.dp))
			.background(CardSurface)
			.padding(12.dp),
		horizontalArrangement = Arrangement.SpaceBetween,
		verticalAlignment = Alignment.CenterVertically
	) {
		Box(
			modifier = Modifier
				.size(40.dp)
				.clip(CircleShape)
				.background(activity.accent.copy(alpha = 0.25f)),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = activity.badge,
				style = MaterialTheme.typography.bodyMedium.copy(
					color = activity.accent,
					fontWeight = FontWeight.Bold
				)
			)
		}
		Column(
			modifier = Modifier.weight(1f),
			verticalArrangement = Arrangement.Center
		) {
			Text(
				text = activity.title,
				style = MaterialTheme.typography.bodyLarge.copy(
					color = Color.White,
					fontWeight = FontWeight.SemiBold
				)
			)
			Text(
				text = activity.subtitle,
				style = MaterialTheme.typography.bodySmall.copy(
					color = Color.White.copy(alpha = 0.65f)
				),
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
		}
		Text(
			text = activity.timestamp,
			style = MaterialTheme.typography.labelSmall.copy(
				color = Color.White.copy(alpha = 0.45f),
				fontWeight = FontWeight.Medium
			)
		)
	}
}

@Composable
private fun StartTourSection(controller: SpotXController) {
	Column(modifier = Modifier.padding(horizontal = 16.dp)) {
		HorizontalDivider(color = Color.White.copy(alpha = 0.12f))
		Spacer(modifier = Modifier.height(12.dp))
		Text(
			text = "Explore the onboarding tour",
			style = MaterialTheme.typography.titleMedium.copy(
				color = Color.White,
				fontWeight = FontWeight.Bold
			)
		)
		Text(
			text = "SpotX highlights each section so your users instantly understand how everything works.",
			style = MaterialTheme.typography.bodyMedium.copy(
				color = Color.White.copy(alpha = 0.7f)
			)
		)
		Spacer(modifier = Modifier.height(12.dp))
		Button(
			onClick = {
				controller.start(
					targets = listOf(
						SpotXTarget(
							id = "home-header",
							title = "Welcome",
							description = "This is your control tower — overview cards and quick actions",
							shape = SpotXShape.RoundedRect
						),
						SpotXTarget(
							id = "stats-carousel",
							title = "Your stats",
							description = "Swipe through KPIs at a glance",
							shape = SpotXShape.RoundedRect
						),
						SpotXTarget(
							id = "stat.employees",
							title = "Employee stats",
							description = "Track headcount and onboarding velocity",
							shape = SpotXShape.RoundedRect,
							ringColor = AccentGreen
						),
						SpotXTarget(
							id = "module.pos",
							title = "Retail Banking",
							description = "Drive transactions and loyalty programs",
							shape = SpotXShape.RoundedRect,
							ringColor = AccentBlue
						),
						SpotXTarget(
							id = "module.wallets",
							title = "Digital Wallets",
							description = "Connect and secure tap-to-pay devices",
							shape = SpotXShape.RoundedRect,
							ringColor = AccentPink
						),
						SpotXTarget(
							id = "module.accounts",
							title = "Corporate Accounts",
							description = "Manage treasury and corporate onboarding",
							shape = SpotXShape.RoundedRect,
							ringColor = AccentPurple
						),
						SpotXTarget(
							id = "module.wealth",
							title = "Wealth & Custody",
							description = "Protect high-value zones and privileges",
							shape = SpotXShape.RoundedRect,
							ringColor = AccentGreen
						),
						SpotXTarget(
							id = "btn.startTour",
							title = "Relaunch tour",
							description = "Trigger SpotX from within your own flow",
							shape = SpotXShape.RoundedRect
						)
					)
				)
			},
			modifier = Modifier
				.fillMaxWidth()
				.spotxAnchor("btn.startTour", controller)
		) {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.spacedBy(4.dp)
			) {
				Text("Start guided tour")
				Text("->", fontWeight = FontWeight.Bold)
			}
		}
	}
}

private fun sampleStats(): List<HomeStat> {
	return listOf(
		HomeStat(
			id = "stat.employees",
			title = "Employees",
			value = "1,240",
			subtitle = "+42 today",
			accent = AccentGreen,
			badge = "EMP"
		),
		HomeStat(
			id = "stat.wallets",
			title = "Wallets",
			value = "893",
			subtitle = "+12 new",
			accent = AccentBlue,
			badge = "WAL"
		),
		HomeStat(
			id = "stat.transactions",
			title = "Transactions",
			value = "18.2K",
			subtitle = "₦18.4M settled",
			accent = AccentPurple,
			badge = "TRN"
		)
	)
}

	private fun sampleModules(): List<HomeModule> {
	return listOf(
		HomeModule(
			anchorId = "module.pos",
			title = "Retail Banking",
			description = "Run tills, cash drops, and approvals.",
			accent = AccentBlue,
			badge = "RTB"
		),
		HomeModule(
			anchorId = "module.accounts",
			title = "Corporate Accounts",
			description = "Onboard treasury clients and consortiums.",
			accent = AccentPurple,
			badge = "COR"
		),
		HomeModule(
			anchorId = "module.wallets",
			title = "Digital Wallets",
			description = "Monitor tap-to-pay devices and fraud signals.",
			accent = AccentPink,
			badge = "DWL"
		),
		HomeModule(
			anchorId = "module.wealth",
			title = "Wealth & Custody",
			description = "Protect high-value vaults & privileges.",
			accent = AccentGreen,
			badge = "WEL"
		)
	)
}

private fun sampleActivities(): List<ActivityEvent> {
	return listOf(
		ActivityEvent(
			title = "New merchant registered",
			subtitle = "Kavtech Suites · POS #12",
			timestamp = "09:42 AM",
			accent = AccentPurple,
			badge = "M"
		),
		ActivityEvent(
			title = "IoT device firmware updated",
			subtitle = "Zone A · Gate 3",
			timestamp = "08:06 AM",
			accent = AccentPink,
			badge = "FW"
		),
		ActivityEvent(
			title = "15 new wallet top-ups",
			subtitle = "₦430K total",
			timestamp = "07:10 AM",
			accent = AccentBlue,
			badge = "W"
		)
	)
}

private data class HomeStat(
	val id: String,
	val title: String,
	val value: String,
	val subtitle: String,
	val accent: Color,
	val badge: String
)

private data class HomeModule(
	val anchorId: String,
	val title: String,
	val description: String,
	val accent: Color,
	val badge: String
)

private data class ActivityEvent(
	val title: String,
	val subtitle: String,
	val timestamp: String,
	val accent: Color,
	val badge: String
)