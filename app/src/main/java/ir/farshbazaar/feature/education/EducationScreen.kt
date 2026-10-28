package ir.farshbazaar.feature.education

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.DemoBanner
import ir.farshbazaar.core.ui.theme.FarshRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EducationScreen(app: FarshApp, onBack: () -> Unit) {
    val indicators by app.container.indicatorRepository.observeAll()
        .collectAsState(initial = emptyList())

    var expanded by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("آموزش — چگونه فرش را بشناسیم") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.KeyboardArrowRight, "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            DemoBanner()
            LazyColumn(
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        "هر شاخص در این صفحه توضیح داده میشود. هدف، آموزش خریدار است، نه ادعای کمال.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                }
                items(indicators, key = { it.id }) { ind ->
                    Card(
                        modifier = Modifier.fillMaxWidth().clickable {
                            expanded = if (expanded == ind.id) null else ind.id
                        }
                    ) {
                        Column(Modifier.padding(12.dp)) {
                            Row(Modifier.fillMaxWidth()) {
                                Text(
                                    ind.titleFa,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.SemiBold,
                                    color = FarshRed,
                                    modifier = Modifier.weight(1f)
                                )
                                Icon(
                                    if (expanded == ind.id) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowRight,
                                    contentDescription = null
                                )
                            }
                            if (expanded == ind.id) {
                                Spacer(Modifier.height(8.dp))
                                Text(ind.bodyFa, style = MaterialTheme.typography.bodyMedium)
                                Spacer(Modifier.height(6.dp))
                                Text(
                                    ind.titleEn,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
