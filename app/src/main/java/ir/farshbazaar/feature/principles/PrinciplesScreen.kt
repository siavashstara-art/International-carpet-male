package ir.farshbazaar.feature.principles

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.farshbazaar.core.ui.theme.FarshGold
import ir.farshbazaar.core.ui.theme.FarshRed
import ir.farshbazaar.domain.Principles

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrinciplesScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("اصول ۱۴گانهٔ فرش‌بازار") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowRight, "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(colors = CardDefaults.cardColors(containerColor = FarshRed.copy(alpha = 0.12f))) {
                    Text(
                        Principles.FOUNDER_QUOTE_FA,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = FarshRed
                    )
                }
            }
            items(Principles.ALL) { p ->
                Card {
                    Column(Modifier.padding(16.dp)) {
                        Text(
                            "${Principles.ALL.indexOf(p) + 1}. ${p.titleFa}",
                            fontWeight = FontWeight.Bold,
                            color = FarshGold
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(p.bodyFa)
                        Spacer(Modifier.height(4.dp))
                        Text(p.titleEn, style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}
