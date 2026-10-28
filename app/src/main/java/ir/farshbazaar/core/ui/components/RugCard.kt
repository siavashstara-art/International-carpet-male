package ir.farshbazaar.core.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ir.farshbazaar.core.ui.theme.*
import ir.farshbazaar.domain.model.Rug
import java.text.NumberFormat
import java.util.Locale

@Composable
fun RugCard(
    rug: Rug,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val persianFormatter = remember { NumberFormat.getNumberInstance(Locale("fa", "IR")) }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = FarshSurface),
        border = CardDefaults.outlinedCardBorder().copy(brush = androidx.compose.ui.graphics.SolidColor(FarshBorder))
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            RugPlaceholder(
                seed = rug.id.hashCode(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            )
            Spacer(Modifier.height(10.dp))
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
                Text(
                    text = rug.category.labelFa,
                    color = FarshGold,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(Modifier.height(4.dp))
            Text(
                text = "مکتب ${rug.region} • ${rug.era}",
                color = FarshTextSecondary,
                fontSize = 11.sp
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = if (rug.price != null) "${persianFormatter.format(rug.price)} تومان" else "قیمت توافقی",
                color = FarshGoldWarm,
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
