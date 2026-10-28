package ir.farshbazaar.feature.inspection

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.FarshApp
import ir.farshbazaar.core.ui.components.DemoBanner
import ir.farshbazaar.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspectionRequestScreen(
    app: FarshApp? = null,
    onBack: () -> Unit,
    rugId: String
) {
    var applicantName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var cityLocation by remember { mutableStateOf("تهران") }
    var inspectionPurpose by remember { mutableStateOf("صدور شناسنامه اصالت و ثبت در گنجینه") }
    var additionalNotes by remember { mutableStateOf("") }
    var isSubmitted by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "درخواست کارشناسی و بازرسی فنی (نمایشی)",
                        color = FarshGold,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
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
        if (isSubmitted) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = FarshSurfaceElevated),
                    border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshGold))
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = FarshGold,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "درخواست کارشناسی در حافظه دستگاه ذخیره شد",
                            color = FarshTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "کد رهگیری آزمایشی: FB-INSP-${System.currentTimeMillis().toString().takeLast(6)}\n(این قابلیت به صورت ذخیرهٔ محلی و نسخهٔ دمو v1 پیاده‌سازی شده است و نیاز به سرور ندارد).",
                            color = FarshTextSecondary,
                            fontSize = 12.sp,
                            lineHeight = 22.sp,
                            textAlign = TextAlign.Center
                        )
                        Spacer(Modifier.height(24.dp))
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth().height(46.dp)
                        ) {
                            Text("بازگشت به صفحه اثر", color = FarshInk, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        } else {
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

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(FarshVelvet)
                            .border(1.dp, FarshBorderGold, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.FactCheck,
                                contentDescription = null,
                                tint = FarshGold,
                                modifier = Modifier.size(28.dp)
                            )
                            Spacer(Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "ارزیابی حضوری توسط هیئت کارشناسان خبره",
                                    color = FarshGold,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "سنجش دقیق رج‌شمار در ۷ سانتی‌متر، سلامت چله و اصالت رنگ طبیعی",
                                    color = FarshTextSecondary,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                item {
                    Text("نام و نام خانوادگی درخواست‌دهنده", color = FarshTextPrimary, fontSize = 13.sp)
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = applicantName,
                        onValueChange = { applicantName = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("مثال: حاج احمد تهرانی", color = FarshTextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Text("شماره تلفن همراه جهت تماس", color = FarshTextPrimary, fontSize = 13.sp)
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("۰۹۱۲۰۰۰۰۰۰۰", color = FarshTextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Text("شهر محل نگهداری یا استقرار قالی", color = FarshTextPrimary, fontSize = 13.sp)
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = cityLocation,
                        onValueChange = { cityLocation = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Text("هدف از کارشناسی حضوری", color = FarshTextPrimary, fontSize = 13.sp)
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = inspectionPurpose,
                        onValueChange = { inspectionPurpose = it },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Text("توضیحات تکمیلی یا پرسش‌های کارشناسی", color = FarshTextPrimary, fontSize = 13.sp)
                    Spacer(Modifier.height(4.dp))
                    OutlinedTextField(
                        value = additionalNotes,
                        onValueChange = { additionalNotes = it },
                        modifier = Modifier.fillMaxWidth().height(90.dp),
                        placeholder = { Text("مثال: بررسی سابقه مرمت، تست رنگرزی روناس یا سنجش تقارن ترنج...", color = FarshTextMuted) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                }

                item {
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = {
                            app?.submitInspection(
                                rugId = rugId,
                                applicantName = applicantName.ifBlank { "کاربر نمونه" },
                                applicantPhone = phoneNumber.ifBlank { "۰۹۱۲۰۰۰۰۰۰۰" },
                                city = cityLocation,
                                notes = additionalNotes
                            )
                            isSubmitted = true
                        },
                        modifier = Modifier.fillMaxWidth().height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FarshGold),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = FarshInk)
                            Spacer(Modifier.width(8.dp))
                            Text(
                                "ثبت محلی درخواست کارشناسی (دمو)",
                                color = FarshInk,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                }
            }
        }
    }
}
