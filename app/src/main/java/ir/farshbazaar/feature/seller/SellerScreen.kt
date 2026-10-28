package ir.farshbazaar.feature.seller

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.EmptyState
import ir.farshbazaar.core.ui.components.RugCard
import ir.farshbazaar.core.ui.theme.FarshGold
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SellerScreen(
    app: FarshApp,
    sellerId: String,
    onBack: () -> Unit,
    onRugClick: (String) -> Unit
) {
    val seller by remember(sellerId) { app.container.userRepository.observe(sellerId) }
        .collectAsState(initial = null)

    val rugs by remember(sellerId) { app.container.rugRepository.observeBySeller(sellerId) }
        .collectAsState(initial = emptyList())

    val isFollowing by app.container.interactionRepository.isFollowing(app.currentUserId, sellerId)
        .collectAsState(initial = false)

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(seller?.displayName ?: "غرفهٔ فروشنده") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                if (seller != null) {
                    Card {
                        Column(Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    seller!!.displayName,
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.weight(1f)
                                )
                                Button(
                                    onClick = {
                                        scope.launch {
                                            app.container.interactionRepository.toggleFollow(app.currentUserId, sellerId)
                                        }
                                    },
                                    colors = if (isFollowing) {
                                        ButtonDefaults.outlinedButtonColors()
                                    } else {
                                        ButtonDefaults.buttonColors(containerColor = FarshGold)
                                    }
                                ) {
                                    Icon(
                                        if (isFollowing) Icons.Default.Check else Icons.Default.PersonAdd,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(Modifier.width(4.dp))
                                    Text(if (isFollowing) "دنبال شده" else "دنبال کردن", fontSize = 12.sp)
                                }
                            }
                            Spacer(Modifier.height(8.dp))
                            Text(seller!!.bio, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(6.dp))
                            Text(
                                "نشان اعتماد دمو • عضو غرفه‌های دارای گواهی صنف",
                                fontSize = 11.sp,
                                color = FarshGold
                            )
                        }
                    }
                }
            }
            item {
                Text(
                    "آثار و فرش‌های این غرفه (${rugs.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            if (rugs.isEmpty()) {
                item { EmptyState("این فروشنده هنوز فرشی در ویترین ثبت نکرده است.") }
            } else {
                items(rugs, key = { it.id }) { rug ->
                    RugCard(rug = rug, onClick = { onRugClick(rug.id) })
                }
            }
        }
    }
}
