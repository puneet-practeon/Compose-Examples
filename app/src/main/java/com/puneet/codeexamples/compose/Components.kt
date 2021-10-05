package com.puneet.codeexamples.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.*
import com.puneet.codeexamples.R
import com.skydoves.landscapist.glide.GlideImage

data class Section<T>(
    val version: Int,
    val content: T,
    val interaction: Interaction? = null
)

data class WebviewContent(
    val title: ContentItem,
    val subtitle: ContentItem,
    val cta: ContentItem?,
    val photo: ContentItem?,
)

data class ProductInformationContent(
    val title: ContentItem,
    val infoPairs: List<InfoPair>
)

data class InfoPair(
    val key: ContentItem,
    val value: ContentItem
)

data class ContentItem(
    val type: String,
    val subType: String? = null,
    val url: String? = null,
    val label: String? = null,
    val modifiers: List<ContentModifier> = emptyList(),
    val interaction: Interaction? = null
) {
    companion object {
        const val SUBTYPE_ARROW_RIGHT = "arrow_right"
    }
}

data class Interaction(
    val onViewEvent: OnViewEvent? = null,
    val onPressEvent: OnPressEvent? = null
)

data class OnViewEvent(override val pelEvent: HashMap<String, Any>) : Event

data class OnPressEvent(
    val action: Action,
    override val pelEvent: HashMap<String, Any>
) : Event

data class Action(
    val type: String,
    val data: HashMap<String, Any>? = null
) {
    companion object {
        const val ACTION_SHOW_TOAST = "show_toast"
    }
}

interface Event {
    val pelEvent: HashMap<String, Any>
}

data class ContentModifier(
    val type: String,
    val identifier: String,
    val displayText: String,
    val size: Int? = null,
    val color: String? = null
) {
    companion object {
        const val TYPE_BOLD = "bold"
        const val TYPE_SIZE = "size"
        const val TYPE_COLOR = "color"
        const val TYPE_UNDERLINE = "underline"
        const val TYPE_STRIKETHROUGH = "strikethrough"
    }
}

@ExperimentalUnitApi
@Composable
fun ProductInformationCard(data: Section<ProductInformationContent>) {
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            RenderTextComponent(data.content.title)
            Spacer(modifier = Modifier.height(16.dp))
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val constraints = this
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.widthIn(max = constraints.maxWidth / 2)
                            .padding(end = 12.dp)
                    ) {
                        data.content.infoPairs.forEachIndexed { _, pair ->
                            Row {
                                RenderTextComponent(pair.key)
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        data.content.infoPairs.forEachIndexed { _, pair ->
                            Row {
                                RenderTextComponent(pair.value)
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun WebviewCard(data: Section<WebviewContent>) {
    Card {
        ConstraintLayout(Modifier.apply {
            if (data.content.cta == null && data.interaction != null) {
                clickable { /* Handle the click action */ }
            }
        }.padding(16.dp)) {
            val (image, title, subtitle, cta) = createRefs()

            data.content.title.let { model ->
                RenderTextComponent(model, Modifier.constrainAs(title) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(parent.top)

                    width = Dimension.preferredWrapContent
                })
            }
            Spacer(Modifier.size(12.dp))
            data.content.subtitle.let { model ->
                RenderTextComponent(model, Modifier.constrainAs(subtitle) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(title.bottom, 12.dp)

                    width = Dimension.preferredWrapContent
                })
            }
            data.content.cta?.let { model ->
                RenderTextComponent(model, Modifier.constrainAs(cta) {
                    linkTo(
                        start = parent.start,
                        end = image.start,
                        endMargin = 16.dp,
                        bias = 0f
                    )
                    top.linkTo(subtitle.bottom)

                    width = Dimension.preferredWrapContent
                })
            }
            data.content.photo?.let {
                it.subType?.let { type ->
                    if (type == ContentItem.SUBTYPE_ARROW_RIGHT) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_chevron_right_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.constrainAs(image) {
                                end.linkTo(parent.end, 16.dp)
                                linkTo(
                                    top = title.top,
                                    bottom = title.bottom
                                )
                            }
                        )
                    }
                }
                it.url?.let { url ->
                    GlideImage(
                        imageModel = url,
                        modifier = Modifier
                            .size(80.dp)
                            .constrainAs(image) {
                                end.linkTo(parent.end, 16.dp)
                                centerVerticallyTo(parent)
                            }
                    )
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun RenderTextComponent(model: ContentItem?, modifier: Modifier = Modifier) {
    model?.let {
        MyTextComponent(
            model = it,
            isRichText = it.isRichText(),
            tapAction = it.interaction,
            modifier = modifier
        )
    }
}

@ExperimentalUnitApi
fun processText(contentItem: ContentItem?): AnnotatedString {
    return buildAnnotatedString {
        contentItem?.run {
            if (modifiers.isNotEmpty()) {
                var sourceText = label!!
                val textUnderStyling = modifiers.last().displayText
                sourceText = sourceText.replace(
                    Regex.fromLiteral(modifiers[0].identifier),
                    textUnderStyling
                )
                val textList = sourceText.split(textUnderStyling)
                if (textList.size == 1) {
                    withStyle(style = processStyle(modifiers)) {
                        append(textUnderStyling)
                    }
                } else {
                    append(textList[0])
                    withStyle(style = processStyle(modifiers)) {
                        append(textUnderStyling)
                    }
                    append(textList[1])
                }
            } else {
                append(label!!)
            }
        }
    }
}

@ExperimentalUnitApi
fun processStyle(modifiers: List<ContentModifier>): SpanStyle {
    return SpanStyle(
        fontSize = modifiers.getModifier(ContentModifier.TYPE_SIZE)?.size?.let {
            TextUnit(
                value = it.toFloat(),
                type = TextUnitType.Sp
            )
        } ?: TextUnit.Unspecified,
        color = modifiers.getModifier(ContentModifier.TYPE_COLOR)?.color?.let {
            Color(android.graphics.Color.parseColor(it))
        } ?: Color.Unspecified,
        textDecoration = modifiers.getModifier(ContentModifier.TYPE_UNDERLINE)?.let {
            TextDecoration.Underline
        } ?: modifiers.getModifier(ContentModifier.TYPE_STRIKETHROUGH)?.let {
            TextDecoration.LineThrough
        },
        fontWeight = modifiers.getModifier(ContentModifier.TYPE_BOLD)?.let {
            FontWeight.Bold
        }
    )
}

@ExperimentalUnitApi
@Composable
fun MyTextComponent(
    model: ContentItem,
    isRichText: Boolean = false,
    tapAction: Interaction? = null,
    modifier: Modifier
) {
    val context = LocalContext.current
    if (isRichText) {
        Text(
            text = processText(model),
            modifier = tapAction?.onPressEvent?.action?.let {
                Modifier.clickable {
                    handleAction(context, it)
                }.then(modifier)
            } ?: modifier
        )
    } else {
        Text(
            text = model.modifiers.last().displayText,
            modifier = tapAction?.onPressEvent?.action?.let {
                Modifier.clickable {
                    handleAction(context, it)
                }.then(modifier)
            } ?: modifier,
            fontSize = model.getModifier(ContentModifier.TYPE_SIZE)?.size?.let {
                TextUnit(it.toFloat(), TextUnitType.Sp)
            } ?: TextUnit.Unspecified,
            fontWeight = model.getModifier(ContentModifier.TYPE_BOLD)?.let {
                FontWeight.Bold
            },
            color = model.getModifier(ContentModifier.TYPE_COLOR)?.color?.let {
                Color(android.graphics.Color.parseColor(it))
            } ?: Color.Unspecified,
            textDecoration = model.getModifier(ContentModifier.TYPE_UNDERLINE)?.let {
                TextDecoration.Underline
            }
        )
    }
}

fun handleAction(context: Context, action: Action?) {
    action?.let {
        if (it.type == Action.ACTION_SHOW_TOAST) {
            Toast.makeText(context, it.data?.get("message") as CharSequence, Toast.LENGTH_SHORT)
                .show()
        }
    }
}

fun ContentItem.isRichText(): Boolean {
    return !(label?.startsWith("$") == true && label.endsWith("$"))
}

fun ContentItem.getModifier(type: String): ContentModifier? {
    return modifiers.find { it.type == type }
}

fun List<ContentModifier>.getModifier(type: String): ContentModifier? {
    return find { it.type == type }
}
