package ir.farshbazaar.feature.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBack: () -> Unit,
    onAbout: () -> Unit,
    onPrinciples: () -> Unit,
    onEducation: () -> Unit
) {
    var selectedLanguage by remember { mutableStateOf("فارسی (پیش‌فرض)") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "تنظیمات سامانه",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "بازگشت",
                            tint = FarshGold
                        )
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
                Spacer(Modifier.height(6.dp))
                Text(
                    "زبان و نگارش برنامه",
                    color = FarshGoldWarm,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = FarshGold)
                            Spacer(Modifier.width(10.dp))
                            Text("زبان سامانه (RTL)", color = FarshTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        }
                        Spacer(Modifier.height(12.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf("فارسی (پیش‌فرض)", "English (فاز ۲)").forEach { lang ->
                                val isSelected = selectedLanguage == lang
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { selectedLanguage = lang },
                                    label = { Text(lang, fontSize = 11.sp) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = FarshGold,
                                        selectedLabelColor = FarshInk,
                                        containerColor = FarshSurfaceElevated,
                                        labelColor = FarshTextSecondary
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    "اصول و دانش‌نامهٔ فرشبازار",
                    color = FarshGoldWarm,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            item {
                SettingsNavigationItem(
                    icon = Icons.Default.Security,
                    title = "اصول ۱۴گانهٔ اخلاقی فرشبازار",
                    subtitle = "نفی نگاه سمساری، اظهار نظر مشروط بر تصویر و صداقت در ارزیابی",
                    onClick = onPrinciples
                )
            }

            item {
                SettingsNavigationItem(
                    icon = Icons.Default.School,
                    title = "آموزش — چگونه فرش را بشناسیم",
                    subtitle = "توضیح ۱۶ شاخص بازرسی حضوری (رج، چله، پود، رنگ طبیعی)",
                    onClick = onEducation
                )
            }

            item {
                SettingsNavigationItem(
                    icon = Icons.Default.Info,
                    title = "دربارهٔ فرشبازار و یادبود پدر",
                    subtitle = "به یاد مرحوم حاج حسین آقای علی‌میری و عهد بنیانگذار",
                    onClick = onAbout
                )
            }

            item {
                Spacer(Modifier.height(16.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "فرش‌بازار (نسخهٔ دمو v1)\nطراحی‌شده با شأن و وقار هنر اصیل قالی دستباف ایران",
                        color = FarshTextMuted,
                        fontSize = 11.sp,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 18.sp
                    )
                }
                Spacer(Modifier.height(20.dp))
            }
        }
    }
}

@Composable
private fun SettingsNavigationItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FarshSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(FarshGold.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(imageVector = icon, contentDescription = null, tint = FarshGold, modifier = Modifier.size(22.dp))
            }
            Spacer(Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = FarshTextPrimary, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(Modifier.height(2.dp))
                Text(text = subtitle, color = FarshTextSecondary, fontSize = 11.sp)
            }
            Icon(imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft, contentDescription = null, tint = FarshGold)
        }
    }
}
