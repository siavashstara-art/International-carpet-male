package com.example.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CarpetInquiry
import com.example.model.CarpetItem
import com.example.ui.theme.FarshBorder
import com.example.ui.theme.FarshCardSurface
import com.example.ui.theme.FarshGold
import com.example.ui.theme.FarshGoldContainer
import com.example.ui.theme.FarshGoldWarm
import com.example.ui.theme.FarshMadderRuby
import com.example.ui.theme.FarshObsidian
import com.example.ui.theme.FarshTextPrimary
import com.example.ui.theme.FarshTextSecondary
import com.example.ui.theme.FarshVelvetSurface
import com.example.ui.theme.VazirmatnFontFamily

@Composable
fun DirectChatDialog(
    carpet: CarpetItem,
    inquiries: List<CarpetInquiry>,
    onSendMessage: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var inputText by remember { mutableStateOf("") }
    val relevantInquiries = inquiries.filter { it.carpetId == carpet.id }

    val quickQuestions = listOf(
        "امکان کارشناسی و بازدید حضوری در گالری فراهم است؟",
        "استعلام رسمی شناسنامه و بیمه حمل و نقل بین‌المللی",
        "امکان سفارش بافت جفت با همین رج‌شمار و نقشه؟",
        "نحوه تسویه مستقیم و گارانتی مرجوعی فرش"
    )

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .height(580.dp)
                .clip(RoundedCornerShape(20.dp))
                .border(1.dp, FarshGold.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
                .background(FarshObsidian)
                .testTag("direct_chat_dialog"),
            color = FarshObsidian
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
            ) {
                // Top Header: Seller / Master Weaver Identity
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "بستن گفتگو",
                            tint = FarshTextSecondary
                        )
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(horizontalAlignment = Alignment.End) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = carpet.weaverMaster,
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = FarshTextPrimary
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.Verified,
                                    contentDescription = "معتبر",
                                    tint = FarshGold,
                                    modifier = Modifier.size(14.dp)
                                )
                            }
                            Text(
                                text = "${carpet.merchantLocation} • آنلاین",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 10.sp,
                                color = FarshGoldWarm
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        // Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(FarshGoldContainer)
                                .border(1.dp, FarshGold, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = carpet.weaverMaster.take(1),
                                fontFamily = VazirmatnFontFamily,
                                fontWeight = FontWeight.Bold,
                                color = FarshGoldWarm,
                                fontSize = 16.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Pinned Carpet Reference Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                        .background(FarshCardSurface)
                        .border(1.dp, FarshBorder, RoundedCornerShape(10.dp))
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(6.dp))
                    ) {
                        Image(
                            painter = painterResource(id = carpet.imageResId),
                            contentDescription = carpet.titleFa,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = carpet.titleFa,
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = FarshTextPrimary,
                            maxLines = 1
                        )
                        Text(
                            text = "${carpet.originCityFa} • ${carpet.knotDensityRaj} رج • ${carpet.dimensions}",
                            fontFamily = VazirmatnFontFamily,
                            fontSize = 10.sp,
                            color = FarshTextSecondary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = FarshBorder, thickness = 1.dp)

                // Message Thread
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (relevantInquiries.isEmpty()) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 20.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "گفتگوی اختصاصی و امن با کارگاه بافنده برقرار است.\nسؤال یا پیشنهاد خود را مطرح فرمایید.",
                                    fontFamily = VazirmatnFontFamily,
                                    fontSize = 12.sp,
                                    color = FarshTextSecondary,
                                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    } else {
                        items(relevantInquiries) { msg ->
                            val isUser = msg.isFromUser
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = if (isUser) Arrangement.Start else Arrangement.End
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.82f)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 12.dp,
                                                topEnd = 12.dp,
                                                bottomStart = if (isUser) 2.dp else 12.dp,
                                                bottomEnd = if (isUser) 12.dp else 2.dp
                                            )
                                        )
                                        .background(if (isUser) FarshMadderRuby.copy(alpha = 0.85f) else FarshVelvetSurface)
                                        .border(
                                            0.5.dp,
                                            if (isUser) FarshGold.copy(alpha = 0.4f) else FarshBorder,
                                            RoundedCornerShape(12.dp)
                                        )
                                        .padding(10.dp)
                                ) {
                                    Column {
                                        Text(
                                            text = msg.senderName,
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 10.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = FarshGoldWarm
                                        )
                                        Spacer(modifier = Modifier.height(2.dp))
                                        Text(
                                            text = msg.messageText,
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 12.sp,
                                            color = Color.White,
                                            lineHeight = 18.sp
                                        )
                                        Spacer(modifier = Modifier.height(4.dp))
                                        Text(
                                            text = msg.timestampFormatted,
                                            fontFamily = VazirmatnFontFamily,
                                            fontSize = 9.sp,
                                            color = FarshTextSecondary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                // Quick Question Pills
                LazyColumn(
                    modifier = Modifier.height(68.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(quickQuestions) { q ->
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(6.dp))
                                .background(FarshCardSurface)
                                .border(0.5.dp, FarshBorder, RoundedCornerShape(6.dp))
                                .clickable {
                                    onSendMessage(q)
                                }
                                .padding(horizontal = 8.dp, vertical = 5.dp)
                        ) {
                            Text(
                                text = "💬 $q",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 10.sp,
                                color = FarshTextPrimary,
                                maxLines = 1
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Input Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        placeholder = {
                            Text(
                                text = "پیام شما به استادکار...",
                                fontFamily = VazirmatnFontFamily,
                                fontSize = 12.sp,
                                color = FarshTextSecondary
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = FarshGold,
                            unfocusedBorderColor = FarshBorder,
                            focusedTextColor = FarshTextPrimary,
                            unfocusedTextColor = FarshTextPrimary,
                            focusedContainerColor = FarshCardSurface,
                            unfocusedContainerColor = FarshCardSurface
                        ),
                        shape = RoundedCornerShape(10.dp),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (inputText.isNotBlank()) {
                                onSendMessage(inputText)
                                inputText = ""
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(FarshGold)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "ارسال پیام",
                            tint = FarshObsidian
                        )
                    }
                }
            }
        }
    }
}
