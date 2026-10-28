package ir.farshbazaar.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.FarshBorderGold
import ir.farshbazaar.core.ui.theme.FarshGold
import ir.farshbazaar.core.ui.theme.FarshTextSecondary
import ir.farshbazaar.core.ui.theme.FarshVelvet

@Composable
fun DemoBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(FarshVelvet)
            .border(1.dp, FarshBorderGold, RoundedCornerShape(10.dp))
            .padding(12.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = FarshGold,
                modifier = Modifier.size(20.dp)
            )
            Spacer(Modifier.width(10.dp))
            Text(
                text = "نسخهٔ پیش‌نمایش تخصصی فرشبازار • صیانت از اصالت فرش دستباف بر پایهٔ ۱۴ اصل کارشناسی",
                color = FarshTextSecondary,
                fontSize = 11.sp,
                lineHeight = 16.sp
            )
        }
    }
}
