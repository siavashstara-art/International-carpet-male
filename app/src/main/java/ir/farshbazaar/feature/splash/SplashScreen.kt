package ir.farshbazaar.feature.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.FarshGold
import ir.farshbazaar.core.ui.theme.FarshInk
import ir.farshbazaar.core.ui.theme.FarshRed
import ir.farshbazaar.domain.Principles
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(onDone: () -> Unit) {
    LaunchedEffect(Unit) {
        delay(2000)
        onDone()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(FarshRed, FarshInk))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(24.dp)
        ) {
            Text("فرش‌بازار", fontSize = 44.sp, fontWeight = FontWeight.Bold, color = FarshGold)
            Spacer(Modifier.height(8.dp))
            Text("سامانهٔ اصالت و تالار گنجینهٔ فرش ایران و جهان", fontSize = 16.sp, color = FarshGold.copy(alpha = 0.9f))
            Spacer(Modifier.height(32.dp))
            Text(
                Principles.MOTHER_PRINCIPLE_FA,
                fontSize = 13.sp,
                lineHeight = 22.sp,
                color = FarshGold.copy(alpha = 0.85f),
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(Modifier.height(40.dp))
            CircularProgressIndicator(color = FarshGold, strokeWidth = 3.dp)
        }
    }
}
