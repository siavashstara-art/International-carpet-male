package ir.farshbazaar.feature.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.EmptyState
import ir.farshbazaar.core.ui.components.RugCard
import kotlinx.coroutines.flow.flowOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    app: FarshApp,
    onBack: () -> Unit,
    onRugClick: (String) -> Unit
) {
    var query by remember { mutableStateOf("") }

    val resultsFlow = remember(query) {
        if (query.length < 2) flowOf(emptyList())
        else app.container.rugRepository.search(query)
    }
    val results by resultsFlow.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("جستجو") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "بازگشت")
                    }
                }
            )
        }
    ) { padding ->
        Column(Modifier.padding(padding).fillMaxSize()) {
            OutlinedTextField(
                value = query,
                onValueChange = { query = it },
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                label = { Text("نام، منطقه، یا توضیحات") },
                singleLine = true
            )
            if (query.length < 2) {
                EmptyState("حداقل دو حرف وارد کنید.")
            } else if (results.isEmpty()) {
                EmptyState("نتیجهای یافت نشد.")
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(results, key = { it.id }) { rug ->
                        RugCard(rug = rug, onClick = { onRugClick(rug.id) })
                    }
                }
            }
        }
    }
}
