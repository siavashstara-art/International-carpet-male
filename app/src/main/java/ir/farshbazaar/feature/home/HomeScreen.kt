package ir.farshbazaar.feature.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.DemoBanner
import ir.farshbazaar.core.ui.components.RugPlaceholder
import ir.farshbazaar.core.ui.theme.*
import ir.farshbazaar.domain.model.Category
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    app: FarshApp,
    onRugClick: (String) -> Unit,
    onSearch: () -> Unit,
    onPrinciples: () -> Unit,
    onEducation: () -> Unit,
    onAbout: () -> Unit,
    onProfile: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf<Category?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val rugsFlow = remember(selectedCategory) {
        if (selectedCategory == null) {
            app.container.rugRepository.observeAll()
        } else {
            app.container.rugRepository.observeByCategory(selectedCategory!!)
        }
    }
    val rugs by rugsFlow.collectAsState(initial = emptyList())

    val persianFormatter = remember { NumberFormat.getNumberInstance(Locale("fa", "IR")) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "فرش‌بازار",
                            color = FarshGold,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                        Text(
                            text = "تالار ملی اصالت و صیانت از قالی ایران",
                            color = FarshGold.copy(alpha = 0.8f),
                            fontSize = 11.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSearch) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "جستجو", tint = FarshGold)
                    }
                    IconButton(onClick = onEducation) {
                        Icon(imageVector = Icons.Default.School, contentDescription = "آموزش شاخص‌ها", tint = FarshGold)
                    }
                    IconButton(onClick = onPrinciples) {
                        Icon(imageVector = Icons.Default.Security, contentDescription = "۱۴ اصل اخلاقی", tint = FarshGold)
                    }
                    IconButton(onClick = onAbout) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "درباره", tint = FarshGold)
                    }
                    IconButton(onClick = onProfile) {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "پروفایل", tint = FarshGold)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = FarshVelvet
                )
            )
        },
        containerColor = FarshInk
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(Modifier.height(4.dp))
                DemoBanner()
            }

            // Creed banner
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshRed.copy(alpha = 0.2f)),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGoldDark))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "«ما فرش را با چشم و دست می‌شناسیم، نه با عکس»",
                            color = FarshGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "اظهار نظر مشروط بر تصویر، نفی حراج فله‌ای و صیانت از حرمت هنر قالی",
                            color = FarshTextSecondary,
                            fontSize = 11.sp
                        )
                        Spacer(Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = onPrinciples,
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = FarshGold,
                                contentColor = FarshInk
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text("مرام‌نامه و ۱۴ اصل اصالت فرش", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                        }
                    }
                }
            }

            // 6 Category Filters
            item {
                Column {
                    Text(
                        text = "دسته‌بندی‌های تالار:",
                        color = FarshTextPrimary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(Modifier.height(8.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // "All" filter chip
                        FilterChip(
                            selected = selectedCategory == null,
                            onClick = { selectedCategory = null },
                            label = { Text("همه موارد", fontSize = 12.sp) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = FarshGold,
                                selectedLabelColor = FarshInk,
                                containerColor = FarshSurface,
                                labelColor = FarshTextSecondary
                            )
                        )

                        // 6 Categories
                        Category.entries.forEach { cat ->
                            FilterChip(
                                selected = selectedCategory == cat,
                                onClick = {
                                    if (cat.isActive) {
                                        selectedCategory = cat
                                    } else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                "بخش «${cat.labelFa}» در نسخهٔ فعلی نمایشی است و به‌زودی در فاز بعدی فعال می‌شود."
                                            )
                                        }
                                    }
                                },
                                label = {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(cat.labelFa, fontSize = 12.sp)
                                        if (!cat.isActive) {
                                            Spacer(Modifier.width(4.dp))
                                            Text(
                                                "(به‌زودی)",
                                                fontSize = 9.sp,
                                                color = FarshTextSecondary
                                            )
                                        }
                                    }
                                },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = FarshGold,
                                    selectedLabelColor = FarshInk,
                                    containerColor = FarshSurface,
                                    labelColor = if (cat.isActive) FarshTextPrimary else FarshTextSecondary.copy(alpha = 0.6f)
                                )
                            )
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = when (selectedCategory) {
                            null -> "کلیه آثار و خدمات ثبتی"
                            Category.HANDMADE_RUG -> "شاهکارهای قالی دستباف"
                            Category.MACHINE_RUG -> "فرش‌های ماشینی باکیفیت"
                            Category.SERVICES -> "خدمات تخصصی قالیشویی و رفوگری"
                            else -> selectedCategory!!.labelFa
                        },
                        color = FarshTextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${rugs.size} اثر دمو",
                        color = FarshGoldWarm,
                        fontSize = 12.sp
                    )
                }
            }

            items(rugs, key = { it.id }) { rug ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .clickable { onRugClick(rug.id) },
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        RugPlaceholder(
                            seed = rug.id.hashCode(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
                        Spacer(Modifier.height(12.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = rug.title,
                                color = FarshTextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(FarshGold.copy(alpha = 0.15f))
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = rug.category.labelFa,
                                    color = FarshGold,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                        Spacer(Modifier.height(6.dp))
                        Text(
                            text = "منطقه: ${rug.region} • قدمت: ${rug.era}",
                            color = FarshTextSecondary,
                            fontSize = 12.sp
                        )
                        Spacer(Modifier.height(4.dp))
                        Text(
                            text = rug.description,
                            color = FarshTextSecondary,
                            fontSize = 11.sp,
                            maxLines = 2
                        )
                        Spacer(Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = if (rug.price != null) "${persianFormatter.format(rug.price)} تومان" else "قیمت توافقی",
                                color = FarshGoldWarm,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "مشاهده شناسنامه و چک‌لیست ۱۶گانه ‹",
                                color = FarshGold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            item {
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}
