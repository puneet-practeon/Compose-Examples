package com.puneet.codeexamples.compose

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.annotation.ExperimentalCoilApi
import com.bumptech.glide.Glide
import com.google.accompanist.pager.*
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer
import com.puneet.codeexamples.R
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class Component<T>(
    val id: String,
    val type: String,
    val isLazyLoaded: Boolean,
    val lazyLoadUrl: String?,
    val section: Section<T>?
)

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

data class BannerContent(
    val photo: ContentItem
)

data class ProductNameAndPricingContent(
    val title: ContentItem,
    val subtitle: ContentItem,
    val headerLine1: ContentItem,
    val headerLine2: ContentItem? = null,
    val bodyLine1: ContentItem? = null,
    val bodyLine2: ContentItem? = null,
    val bodyLine3: ContentItem,
    val cta: ContentItem
)

data class CarouselContent(
    val list: List<ContentItem>
)

data class NavigationContent(
    val title: ContentItem?,
    val cta: ContentItem?
)

data class ContentItem(
    val type: String,
    val subType: String? = null,
    val url: String? = null,
    val label: String? = null,
    val imageWidth: Double? = null,
    val imageHeight: Double? = null,
    val leftIcon: String? = null,
    val modifiers: List<ContentModifier> = emptyList(),
    val interaction: Interaction? = null
) {
    companion object {
        const val SUBTYPE_ARROW_RIGHT = "arrow_right"
        const val TYPE_IMAGE = "image"
        const val TYPE_CARD_BANNER = "card_banner"
        const val TYPE_FULL_BANNER = "full_banner"
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
    val data: Data? = null
) {
    companion object {
        const val ACTION_SHOW_TOAST = "show_toast"
    }
}

data class Data(
    val url: String? = null,
    val extra: HashMap<String, Any>? = null
)

interface Event {
    val pelEvent: HashMap<String, Any>
}

data class ContentModifier(
    val type: String,
    val identifier: String,
    val displayText: String,
    val size: Int? = null,
    val color: String? = null,
    val interaction: Interaction? = null
) {
    companion object {
        const val TYPE_BOLD = "bold"
        const val TYPE_SIZE = "size"
        const val TYPE_COLOR = "color"
        const val TYPE_UNDERLINE = "underline"
        const val TYPE_STRIKETHROUGH = "strikethrough"
        const val TYPE_LINK = "link"
    }
}

// MARK - 05/10/21 Components

@Composable
fun PdpNavigationHeader(modifier: Modifier, onBackPress: () -> Unit) {
    TopAppBar(
        modifier = modifier
    ) {
        Row {
            IconButton(
                onClick = onBackPress
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back button"
                )
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun ProductInformationCard(component: Component<ProductInformationContent>) {
    val data = component.section
    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            RenderTextComponent(data?.content?.title)
            Spacer(modifier = Modifier.height(16.dp))
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val constraints = this
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.widthIn(max = constraints.maxWidth / 2)
                            .padding(end = 12.dp)
                    ) {
                        data?.content?.infoPairs?.forEachIndexed { _, pair ->
                            Row {
                                RenderTextComponent(pair.key)
                            }
                        }
                    }
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        data?.content?.infoPairs?.forEachIndexed { _, pair ->
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
fun WebviewCard(component: Component<WebviewContent>) {
    val data = component.section
    val placeholderModifier = Modifier.placeholder(
        visible = component.isLazyLoaded,
        highlight = PlaceholderHighlight.shimmer()
    )
    Card {
        ConstraintLayout(Modifier.padding(16.dp)) {
            val (image, title, subtitle, cta) = createRefs()

            data?.content?.title.let { model ->
                RenderTextComponent(
                    model,
                    Modifier.then(placeholderModifier).constrainAs(title) {
                        linkTo(
                            start = parent.start,
                            end = image.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                        top.linkTo(parent.top)

                        width = Dimension.preferredWrapContent
                    },
                    textStyle = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = TextUnit(24f, TextUnitType.Sp),
                        color = Color.Black
                    )
                )
            }
            Spacer(Modifier.size(12.dp))
            data?.content?.subtitle.let { model ->
                RenderTextComponent(
                    model,
                    Modifier.then(placeholderModifier).constrainAs(subtitle) {
                        linkTo(
                            start = parent.start,
                            end = image.start,
                            endMargin = 16.dp,
                            bias = 0f
                        )
                        top.linkTo(title.bottom, 12.dp)

                        width = Dimension.preferredWrapContent
                    },
                )
            }
            data?.content?.cta?.let { model ->
                RenderTextComponent(model, Modifier.then(placeholderModifier).constrainAs(cta) {
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
            data?.content?.photo?.let {
                it.subType?.let { type ->
                    if (type == ContentItem.SUBTYPE_ARROW_RIGHT) {
                        Image(
                            painter = painterResource(R.drawable.ic_baseline_chevron_right_24),
                            contentDescription = null,
                            colorFilter = ColorFilter.tint(Color.Black),
                            modifier = Modifier.then(placeholderModifier).constrainAs(image) {
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
                            .then(placeholderModifier)
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

@Composable
fun BannerComponent(component: Component<BannerContent>) {
    val data = component.section
    data?.content?.photo?.run {
        if (type == ContentItem.TYPE_CARD_BANNER)
            Card {
                Box {
                    val cardWidth = LocalView.current.width.toFloat()
                    val widthModifier = imageWidth?.let { w ->
                        if (w > cardWidth)
                            Modifier.fillMaxWidth()
                        else
                            Modifier.width(w.dp)
                    } ?: Modifier
                    val heightModifier = imageHeight?.let { h ->
                        Modifier.height(h.dp)
                    } ?: Modifier
                    url?.let { url ->
                        GlideImage(
                            imageModel = url,
                            modifier = Modifier.then(widthModifier).then(heightModifier)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Fit
                        )
                    }
                }
            }
    }
}

@ExperimentalUnitApi
@Composable
fun ProductNameAndPricingComponent(component: Component<ProductNameAndPricingContent>) {
    val data = component.section ?: return
    val context = LocalContext.current
    Card {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Prescription Tag
            data.content.headerLine2?.let {
                Row(
                    modifier = Modifier.background(
                        color = Color(android.graphics.Color.parseColor("#FFF8E7"))
                    )
                        .padding(horizontal = 12.dp, vertical = 4.dp)
                ) {
                    it.leftIcon?.let { url ->
                        GlideImage(
                            imageModel = url,
                            modifier = Modifier.size(12.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    if (it.modifiers.isNotEmpty()) {
                        RenderTextComponent(it)
                    } else {
                        it.label?.let { label ->
                            Text(
                                text = label,
                                fontWeight = FontWeight.Bold,
                                fontSize = TextUnit(14f, TextUnitType.Sp)
                            )
                        }
                    }
                }
            }
            RenderTextComponent(data.content.title)
            RenderTextComponent(data.content.subtitle)
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(0.5f)
                ) {
                    RenderTextComponent(data.content.headerLine1)
                    Row {
                        data.content.bodyLine1?.let { item ->
                            if (item.modifiers.isNotEmpty()) {
                                RenderTextComponent(item)
                            } else {
                                item.label?.let { label ->
                                    Text(
                                        text = label,
                                        textDecoration = TextDecoration.LineThrough
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        data.content.bodyLine2?.let { item ->
                            if (item.modifiers.isNotEmpty()) {
                                RenderTextComponent(item)
                            } else {
                                item.label?.let { label ->
                                    Text(
                                        text = label,
                                        color = Color(android.graphics.Color.parseColor("#02A401"))
                                    )
                                }
                            }
                        }
                    }
                    RenderTextComponent(data.content.bodyLine3)
                }
                Column(
                    modifier = Modifier
                        .padding(horizontal = 24.dp)
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(
                            color = Color(
                                android.graphics.Color.parseColor("#199FD9")
                            )
                        )
                        .clickable {
                            handleAction(
                                context,
                                data.content.cta.interaction?.onPressEvent?.action
                            )
                        }
                        .padding(horizontal = 16.dp, vertical = 20.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                    ) {
                        data.content.cta.leftIcon?.let { url ->
                            GlideImage(
                                imageModel = url,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        data.content.cta.label?.let { label ->
                            Text(
                                text = label,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@ExperimentalPagerApi
@Composable
fun CarouselComponent(
    component: Component<CarouselContent>,
    pagerState: PagerState,
    onImageTap: () -> Unit = {}
) {
    val data = component.section
    val scaffold = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(initialValue = BottomSheetValue.Collapsed)
    )
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffold,
        sheetContent = {
            Scaffold(
                topBar = {
                    TopAppBar {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    scaffold.bottomSheetState.collapse()
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Rounded.ArrowBack,
                                contentDescription = null
                            )
                        }
                    }
                }
            ) {
                Column {
                    HorizontalPager(
                        state = pagerState,
                    ) { position ->
                        data?.content?.list?.get(position)?.url?.let { url ->
                            GlideImage(
                                imageModel = url,
                                modifier = Modifier.height(200.dp).clickable { onImageTap.invoke() }
                            )
                        }

                    }
                    HorizontalPagerIndicator(
                        pagerState = pagerState,
                        modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
                    )
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        Column {
            HorizontalPager(
                state = pagerState,
            ) { position ->
                data?.content?.list?.get(position)?.url?.let { url ->
                    GlideImage(
                        imageModel = url,
                        modifier = Modifier.height(200.dp).clickable {
                            scope.launch {
                                scaffold.bottomSheetState.expand()
                            }
                        }
                    )
                }

            }
            HorizontalPagerIndicator(
                pagerState = pagerState,
                modifier = Modifier.padding(top = 16.dp).align(Alignment.CenterHorizontally)
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalPagerApi
@Composable
fun FullScreenBottomSheet(
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    component: Component<CarouselContent>,
    pagerState: PagerState,
    scope: CoroutineScope
) {
    BottomSheetScaffold(
        scaffoldState = bottomSheetScaffoldState,
        topBar = null,
        sheetContent = {
            Box(
                Modifier.fillMaxWidth()
            ) {
                FullScreenCarouselComponent(component, pagerState) {
                    scope.launch {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        },
        sheetPeekHeight = 0.dp
    ) {
        CarouselComponent(
            component = component,
            pagerState = pagerState
        ) {
            scope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        }
    }
}

@ExperimentalPagerApi
@Composable
fun FullScreenCarouselComponent(
    component: Component<CarouselContent>,
    pagerState: PagerState,
    onBackPress: () -> Unit
) {
    val data = component.section
    Column {
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = onBackPress) {
                    Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = null)
                }
            })
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.height(200.dp)
        ) { position ->
            data?.content?.list?.get(position)?.url?.let { url ->
                GlideImage(imageModel = url)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        FullScreenCarouselIndicator(
            pagerState = pagerState,
            data = data?.content?.list?.filter { it.url != null } ?: emptyList(),
            activeColor = Color(android.graphics.Color.parseColor("#199FD9")),
            inactiveColor = Color(android.graphics.Color.parseColor("#F0F0F5"))
        )
    }
}

@ExperimentalPagerApi
@Composable
fun FullScreenCarouselIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = LocalContentColor.current.copy(alpha = LocalContentAlpha.current),
    inactiveColor: Color = Color.Gray,
    indicatorWidth: Dp = 80.dp,
    indicatorHeight: Dp = indicatorWidth,
    spacing: Dp = 8.dp,
    indicatorShape: Shape = RoundedCornerShape(12.dp),
    data: List<ContentItem> = emptyList()
) {
    val scrollingState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    Box(
        modifier = modifier,
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(spacing),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val indicatorModifier = Modifier
                .size(width = indicatorWidth, height = indicatorHeight)
                .background(color = Color.White, shape = indicatorShape)
                .padding(4.dp)

            LaunchedEffect(pagerState) {
                snapshotFlow { pagerState.currentPage }.collect {
                    scrollingState.animateScrollToItem(it)
                }
            }

            LazyRow(
                state = scrollingState,
                horizontalArrangement = Arrangement.spacedBy(spacing),
                contentPadding = PaddingValues(
                    horizontal = spacing
                )
            ) {
                items(pagerState.pageCount) { position ->
                    Box(
                        modifier = Modifier
                            .border(
                                border = BorderStroke(
                                    width = 1.dp,
                                    color = if (pagerState.currentPage == position) activeColor else inactiveColor
                                ),
                                shape = indicatorShape
                            )
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(position)
                                }
                            }
                    ) {
                        GlideImage(
                            imageModel = data[position].url!!,
                            modifier = indicatorModifier
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalUnitApi
@Composable
fun RenderTextComponent(
    model: ContentItem?,
    modifier: Modifier = Modifier,
    textStyle: TextStyle? = null
) {
    model?.let {
        MyTextComponent(
            model = it,
            isRichText = it.isRichText(),
            tapAction = it.interaction,
            modifier = modifier,
            textStyle = textStyle
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
                    pushStringAnnotation(
                        tag = "actionable_text",
                        annotation = "action"
                    )
                    withStyle(style = processStyle(modifiers)) {
                        append(textUnderStyling)
                    }
                    pop()
                } else {
                    append(textList[0])
                    pushStringAnnotation(
                        tag = "actionable_text",
                        annotation = "action"
                    )
                    withStyle(style = processStyle(modifiers)) {
                        append(textUnderStyling)
                    }
                    pop()
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
    modifier: Modifier,
    textStyle: TextStyle? = null
) {
    val context = LocalContext.current
    val style = if (!isRichText) createTextStyle(model, textStyle) else null
    val labelText = if (model.modifiers.isNotEmpty()) {
        model.modifiers.last().displayText
    } else model.label!!

    if (model.modifiers.any { it.type == ContentModifier.TYPE_LINK }) {
        val linkModifier = model.getModifier(ContentModifier.TYPE_LINK) ?: return
        val annotatedText = if (isRichText)
            processText(model)
        else
            buildAnnotatedString {
                pushStringAnnotation(tag = "actionable_text", annotation = "action")
                withStyle(style!!.toSpanStyle()) { append(labelText) }
                pop()
            }
        ClickableText(
            text = annotatedText,
            onClick = { offset ->
                annotatedText.getStringAnnotations(
                    tag = "actionable_text",
                    start = offset,
                    end = offset
                ).firstOrNull()?.let {
                    linkModifier.interaction?.onPressEvent?.action?.let {
                        handleAction(context, it)
                    }
                }
            },
            modifier = modifier
        )
    } else {
        val annotatedText = if (isRichText)
            processText(model)
        else
            buildAnnotatedString { append(labelText) }
        Text(
            text = annotatedText,
            modifier = tapAction?.onPressEvent?.action?.let {
                Modifier.clickable {
                    handleAction(context, it)
                }.then(modifier)
            } ?: modifier,
            style = style ?: LocalTextStyle.current
        )
    }
}

@ExperimentalUnitApi
fun createTextStyle(model: ContentItem, baseStyle: TextStyle?): TextStyle {
    return baseStyle?.let { style ->
        style.copy(
            fontSize = model.getModifier(ContentModifier.TYPE_SIZE)?.size?.let {
                TextUnit(it.toFloat(), TextUnitType.Sp)
            } ?: style.fontSize,
            fontWeight = model.getModifier(ContentModifier.TYPE_BOLD)?.let {
                FontWeight.Bold
            } ?: style.fontWeight,
            color = model.getModifier(ContentModifier.TYPE_COLOR)?.color?.let {
                Color(android.graphics.Color.parseColor(it))
            } ?: style.color,
            textDecoration = model.getModifier(ContentModifier.TYPE_UNDERLINE)?.let {
                TextDecoration.Underline
            } ?: model.getModifier(ContentModifier.TYPE_STRIKETHROUGH)?.let {
                TextDecoration.LineThrough
            } ?: style.textDecoration
        )
    } ?: TextStyle(
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
        } ?: model.getModifier(ContentModifier.TYPE_STRIKETHROUGH)?.let {
            TextDecoration.LineThrough
        }
    )
}

fun handleAction(context: Context, action: Action?) {
    action?.let { a ->
        if (a.type == Action.ACTION_SHOW_TOAST) {

            (a.data?.extra?.get("message") as CharSequence?)?.let {
                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
            }
            (a.data?.extra?.get("drug_id") as Int?)?.let {
                Toast.makeText(context, "Drug ID $it", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

fun ContentItem.isRichText(): Boolean {
    return !(label?.startsWith("$") == true && label.endsWith("$")) && label?.contains("\$") == true
}

fun ContentItem.getModifier(type: String): ContentModifier? {
    return modifiers.find { it.type == type }
}

fun List<ContentModifier>.getModifier(type: String): ContentModifier? {
    return find { it.type == type }
}
