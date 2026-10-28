package ir.farshbazaar.feature.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.theme.*
import kotlinx.coroutines.launch

data class StubItem(
    val title: String,
    val description: String,
    val icon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    app: FarshApp,
    onBack: () -> Unit,
    onSettings: () -> Unit
) {
    val user by app.container.userRepository.observe(app.currentUserId)
        .collectAsState(initial = null)

    val totalXp by app.container.interactionRepository.observeTotalXp(app.currentUserId)
        .collectAsState(initial = 12)

    val likedRugs by app.container.interactionRepository.observeLikedRugs(app.currentUserId)
        .collectAsState(initial = emptyList())

    val followingList by app.container.interactionRepository.observeFollowing(app.currentUserId)
        .collectAsState(initial = emptyList())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val stubs = listOf(
        StubItem("سفارش‌ها و فاکتورهای من", "مشاهده تاریخچه معاملات قطعی و پیش‌فاکتورها", Icons.Default.ReceiptLong),
        StubItem("کیف پول ریالی و ارزی", "مدیریت حساب سپرده تضمین معاملات تجاری", Icons.Default.AccountBalanceWallet),
        StubItem("قراردادهای شاهکار و استعلام اصالت", "ثبت قراردادهای انحصاری بر بستر امن صنفی", Icons.Default.Description),
        StubItem("پیام‌های بازرگانی و گفتگوی تجار", "ارتباط مستقیم با بازرگانان بازار مظفریه و نقش‌جهان", Icons.Default.ChatBubbleOutline)
    )

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "پروفایل کاربری و امتیاز تجربه (XP)",
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
                actions = {
                    IconButton(onClick = onSettings) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "تنظیمات", tint = FarshGold)
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
                // User card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurfaceElevated),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGold))
                ) {
                    Column(modifier = Modifier.padding(18.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(54.dp)
                                    .clip(RoundedCornerShape(27.dp))
                                    .background(FarshGold.copy(alpha = 0.15f))
                                    .border(1.dp, FarshGold, RoundedCornerShape(27.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Person,
                                    contentDescription = null,
                                    tint = FarshGold,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                            Spacer(Modifier.width(14.dp))
                            Column {
                                Text(
                                    text = user?.displayName ?: "حاج احمد تهرانی (کاربر نمونه)",
                                    color = FarshTextPrimary,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    text = user?.bio ?: "علاقه‌مند به هنر فرش دستباف",
                                    color = FarshTextSecondary,
                                    fontSize = 12.sp
                                )
                            }
                        }

                        Spacer(Modifier.height(14.dp))
                        HorizontalDivider(color = FarshBorder, thickness = 1.dp)
                        Spacer(Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("امتیاز XP", fontSize = 11.sp, color = FarshTextSecondary)
                                Text("$totalXp", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FarshGold)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("آثار پسندیده‌شده", fontSize = 11.sp, color = FarshTextSecondary)
                                Text("${likedRugs.size}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FarshTextPrimary)
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("غرفه‌های دنبال‌شده", fontSize = 11.sp, color = FarshTextSecondary)
                                Text("${followingList.size}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = FarshTextPrimary)
                            }
                        }
                    }
                }
            }

            // XP Engine & Rules Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurface),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.Diamond, contentDescription = null, tint = FarshGold)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "سامانهٔ امتیازدهی تجربه (XpEngine v1)",
                                color = FarshTextPrimary,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "• ثبت اثر در تالار: ۱۰ امتیاز پایه\n• دریافت پسند از علاقه‌مندان: ۲ امتیاز پایه\n• ضریب هم‌افزایی (Multiplier): ۱ برابر (نسخه دمو v1)\n• دریافت نظرات و دنبال‌کنندگان: در فاز ۲ فعال می‌شود.",
                            color = FarshTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 22.sp
                        )
                    }
                }
            }

            // Stub List (سفارش‌ها، کیف پول، قراردادها، پیام‌ها)
            item {
                Text(
                    text = "سایر امکانات و پرونده‌های بازرگانی:",
                    color = FarshTextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            items(stubs) { stub ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "قابلیت «${stub.title}» در نسخهٔ دمو v1 فعال نیست و در فاز بعدی راه‌اندازی می‌شود."
                            )
                        }
                    },
                    colors = CardDefaults.cardColors(containerColor = FarshSurface)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .background(FarshGold.copy(alpha = 0.12f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(imageVector = stub.icon, contentDescription = null, tint = FarshGold)
                        }
                        Spacer(Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(stub.title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = FarshTextPrimary)
                            Spacer(Modifier.height(2.dp))
                            Text(stub.description, fontSize = 11.sp, color = FarshTextSecondary)
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(FarshRed.copy(alpha = 0.12f))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text("به‌زودی", fontSize = 10.sp, color = FarshRed, fontWeight = FontWeight.Bold)
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
