package com.puneet.codeexamples.compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Share
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.puneet.codeexamples.compose.*

@ExperimentalMaterialApi
@ExperimentalUnitApi
@Composable
fun HeaderWithTwoRightCtas(
    modifier: Modifier,
    section: Section<NavigationContent>,
    onBackPress: () -> Unit,
    onPress: (Action?) -> Unit
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = Color.Blue,
        contentColor = Color.White,
        elevation = 0.dp
    ) {
        IconButton(
            onClick = onBackPress
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "back button"
            )
        }
        section.content.title?.let {
            MyTextComponent(
                it, false,
                it.interaction,
                modifier.padding(16.dp).weight(1f),
                TextStyle(fontSize = 24.sp, fontWeight = FontWeight.W700)
            )
        }
        Spacer(modifier = modifier)
        section.content.cta?.let {
            if (it.type == "share_cta") {
                IconButton(
                    onClick = { onPress.invoke(it.interaction?.onPressEvent?.action) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share button"
                    )
                }
            }
        }
        IconButton(
            onClick = {
                onPress.invoke(
                    Action(type = "on_cart_clicked")
                )
            }
        ) {
            BadgeBox(
                badgeContent = {
                    Text("2")
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.ShoppingCart,
                    contentDescription = "Cart button"
                )
            }
        }
    }
}