package ir.farshbazaar.feature.rugdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.DemoBanner
import ir.farshbazaar.core.ui.components.RugPlaceholder
import ir.farshbazaar.core.ui.theme.FarshGold
import ir.farshbazaar.core.ui.theme.FarshRed
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RugDetailScreen(
    app: FarshApp,
    rugId: String,
    onBack: () -> Unit,
    onSellerClick: (String) -> Unit,
    onInspection: () -> Unit,
    onPrinciples: () -> Unit
) {
    val rug by remember(rugId) { app.container.rugRepository.observe(rugId) }
        .collectAsState(initial = null)

    val indicators by app.container.rugRepository.observeIndicators()
        .collectAsState(initial = emptyList())

    val checklist by app.container.rugRepository.observeChecklist(rugId)
        .collectAsState(initial = emptySet())

    val sellerFlow = remember(rug?.sellerId) {
        rug?.let { app.container.userRepository.observe(it.sellerId) } ?: flowOf(null)
    }
    val seller by sellerFlow.collectAsState(initial = null)

    val isLiked by app.container.interactionRepository.isLiked(app.currentUserId, rugId)
        .collectAsState(initial = false)

    val likeCount by app.container.interactionRepository.observeLikeCount(rugId)
        .collectAsState(initial = 0)

    val comments by app.container.interactionRepository.observeComments(rugId)
        .collectAsState(initial = emptyList())

    var newCommentText by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()
    val persianFormatter = remember { NumberFormat.getNumberInstance(Locale("fa", "IR")) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(rug?.title ?: "شناسنامه مشروط اثر") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "بازگشت")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        scope.launch {
                            app.container.interactionRepository.toggleLike(app.currentUserId, rugId)
                        }
                    }) {
                        Icon(
                            imageVector = if (isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = "پسندیدن",
                            tint = if (isLiked) FarshRed else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        if (rug == null) {
            Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = FarshRed)
            }
            return@Scaffold
        }

        val r = rug!!
        LazyColumn(
            modifier = Modifier.padding(padding).fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { DemoBanner() }

            item {
                RugPlaceholder(
                    seed = r.id.hashCode(),
                    modifier = Modifier.fillMaxWidth().height(220.dp)
                )
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(r.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "${r.category.labelFa} • مکتب ${r.region} • ${r.era}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .background(FarshRed.copy(alpha = 0.12f))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Icon(Icons.Default.Favorite, contentDescription = null, tint = FarshRed, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("$likeCount", color = FarshRed, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                    }
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    if (r.price != null) "${persianFormatter.format(r.price)} تومان" else "قیمت توافقی",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = FarshRed
                )
            }

            item {
                Text(r.description, style = MaterialTheme.typography.bodyLarge, lineHeight = 24.sp)
            }

            item { HorizontalDivider() }

            // Principle 1 & 14 Banner: Conditional Opinion
            item {
                Card(colors = CardDefaults.cardColors(containerColor = FarshGold.copy(alpha = 0.12f))) {
                    Column(Modifier.padding(14.dp)) {
                        Text("اظهار نظر مشروط بر تصویر (اصل ۱ و ۱۴)", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text(
                            "این اطلاعات بر اساس تصاویر و اظهارات فروشنده ثبت شده است. کارشناس مستقل، فرش را از نزدیک لمس نکرده و بافت پشت را نسنجیده است. این صفحه یک راهنمای اولیه است، نه گواهی قطعی اصالت.",
                            style = MaterialTheme.typography.bodyMedium,
                            lineHeight = 22.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        TextButton(onClick = onPrinciples) {
                            Text("مطالعهٔ مرام‌نامه و ۱۴ اصل اصالت فرش", color = FarshRed, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            // Checklist section (16 indicators)
            item {
                Text("پیش از خرید، این شاخص‌ها را حضوری بسنجید:", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(4.dp))
                Text(
                    "بر اساس اصل ۱۱ (فهرست باز شاخص‌های بازرسی حضوری). موارد بررسی‌شده را علامت بزنید:",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f)
                )
            }

            items(indicators, key = { it.id }) { ind ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (checklist.contains(ind.id)) FarshGold.copy(alpha = 0.1f) else MaterialTheme.colorScheme.surface
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = checklist.contains(ind.id),
                            onCheckedChange = { checked ->
                                scope.launch {
                                    app.container.rugRepository.setChecklist(rugId, ind.id, checked)
                                }
                            }
                        )
                        Spacer(Modifier.width(8.dp))
                        Column(Modifier.weight(1f)) {
                            Text(ind.titleFa, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(2.dp))
                            Text(
                                ind.bodyFa,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                            )
                        }
                    }
                }
            }

            item { HorizontalDivider() }

            // Seller section
            item {
                Text("غرفه و مشخصات فروشنده", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(6.dp))
                if (seller != null) {
                    Card(onClick = { onSellerClick(seller!!.id) }, modifier = Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(14.dp)) {
                            Text(seller!!.displayName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Spacer(Modifier.height(4.dp))
                            Text(seller!!.bio, style = MaterialTheme.typography.bodyMedium)
                            Spacer(Modifier.height(6.dp))
                            Text("مشاهده غرفه و سایر آثار این فروشنده ‹", color = FarshGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            item {
                Button(
                    onClick = onInspection,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("درخواست بررسی حضوری کارشناس خبره (نمایشی)")
                }
            }

            // Comments & Expert Reviews
            item {
                Text("نظرات و دیدگاه‌های کارشناسی (${comments.size})", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Spacer(Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = newCommentText,
                        onValueChange = { newCommentText = it },
                        modifier = Modifier.weight(1f),
                        placeholder = { Text("دیدگاه یا پرسش درباره این اثر...", fontSize = 12.sp) },
                        maxLines = 3
                    )
                    Spacer(Modifier.width(8.dp))
                    IconButton(
                        onClick = {
                            if (newCommentText.isNotBlank()) {
                                scope.launch {
                                    app.container.interactionRepository.addComment(
                                        rugId = rugId,
                                        authorId = app.currentUserId,
                                        text = newCommentText
                                    )
                                    newCommentText = ""
                                }
                            }
                        }
                    ) {
                        Icon(Icons.Default.Send, contentDescription = "ارسال نظر", tint = FarshGold)
                    }
                }
            }

            items(comments, key = { it.id }) { c ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(10.dp)) {
                        Text(c.authorName, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = FarshGold)
                        Spacer(Modifier.height(4.dp))
                        Text(c.text, fontSize = 13.sp)
                    }
                }
            }

            item {
                Text(
                    "⚠️ هشدار: این صفحه یک نسخهٔ نمایشی است. خرید، پرداخت و انتقال وجه در نسخهٔ اول وجود ندارد.",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
                )
                Spacer(Modifier.height(16.dp))
            }
        }
    }
}
