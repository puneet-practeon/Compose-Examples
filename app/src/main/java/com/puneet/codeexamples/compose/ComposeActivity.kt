package com.puneet.codeexamples.compose

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import coil.annotation.ExperimentalCoilApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import com.google.android.material.composethemeadapter.MdcTheme
import com.puneet.codeexamples.compose.components.HeaderWithTwoRightCtas
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalUnitApi
@InternalCoroutinesApi
@ExperimentalPagerApi
@ExperimentalMaterialApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
class ComposeActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    LazyColumn {
                        item {
                            HeaderWithTwoRightCtas(
                                modifier = Modifier,
                                section = getNavigationSection(),
                                onBackPress = { finish() },
                                onPress = { handleAction(this@ComposeActivity, action = it) }
                            )
                        }
                        item {
                            val content = getCarouselSectionData("123", false)
                            val pagerState =
                                rememberPagerState(content.section?.content?.list?.count { it.url != null }
                                    ?: 0)
                            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
                            )
                            val scope = rememberCoroutineScope()
                            FullScreenBottomSheet(
                                bottomSheetScaffoldState = bottomSheetScaffoldState,
                                component = content,
                                pagerState = pagerState,
                                scope = scope
                            )
                        }
                        item {
                            WebviewCard(component = getWebviewSectionData("asf123", false))
                        }
                        item {
                            ProductInformationCard(
                                component = getProductInfoSectionData(
                                    "53",
                                    false
                                )
                            )
                        }
                        item {
                            BannerComponent(component = getBannerSectionData("1234", false))
                        }
                        item {
                            ProductNameAndPricingComponent(
                                component = getProductNameAndPricingSectionData(
                                    "1231",
                                    false
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    companion object {
        fun getWebviewSectionData(
            id: String,
            isLazyLoaded: Boolean
        ): Component<WebviewContent> {
            return Component(
                id = id,
                type = "WEBVIEW",
                isLazyLoaded = isLazyLoaded,
                lazyLoadUrl = "something",
                section = Section(
                    version = 1,
                    content = WebviewContent(
                        title = ContentItem(
                            label = "Return Policy",
                            type = "text"
                        ),
                        subtitle = ContentItem(
                            label = "This medicine is not \$VAR$. For full details on our returns policies",
                            type = "text",
                            modifiers = listOf(
                                ContentModifier(
                                    type = "color",
                                    color = "#346ab2",
                                    displayText = "\$VAR$",
                                    identifier = "\$VAR$"
                                ),
                                ContentModifier(
                                    type = "bold",
                                    displayText = "\$VAR$",
                                    identifier = "\$VAR$"
                                ),
                                ContentModifier(
                                    type = "link",
                                    displayText = "\$VAR$",
                                    identifier = "\$VAR$",
                                    interaction = Interaction(
                                        onPressEvent = OnPressEvent(
                                            action = Action(
                                                type = "show_toast",
                                                data = Data(
                                                    extra = hashMapOf(
                                                        "message" to "Word clicked"
                                                    )
                                                )
                                            ),
                                            pelEvent = hashMapOf()
                                        ),
                                    )
                                ),
                                ContentModifier(
                                    type = "underline",
                                    displayText = "returnable",
                                    identifier = "\$VAR$"
                                )
                            )
                        ),
                        cta = ContentItem(
                            label = "\$CTA$",
                            type = "text",
                            modifiers = listOf(
                                ContentModifier(
                                    type = "bold",
                                    displayText = "\$CTA$",
                                    identifier = "\$CTA$"
                                ),
                                ContentModifier(
                                    type = "color",
                                    color = "#000000",
                                    displayText = "\$CTA$",
                                    identifier = "\$CTA$"
                                ),
                                ContentModifier(
                                    type = "link",
                                    displayText = "\$CTA$",
                                    identifier = "\$CTA$",
                                    interaction = Interaction(
                                        onPressEvent = OnPressEvent(
                                            action = Action(
                                                type = "show_toast",
                                                data = Data(
                                                    extra = hashMapOf(
                                                        "message" to "Action Performed"
                                                    )
                                                )
                                            ),
                                            pelEvent = hashMapOf(
                                                "cta_clicked" to "Read More Cta has been clicked"
                                            )
                                        )
                                    )
                                ),
                                ContentModifier(
                                    type = "underline",
                                    displayText = "Read More",
                                    identifier = "\$CTA$"
                                ),
                            ),
                        ),
                        photo = ContentItem(
                            type = "image",
//                            subType = "arrow_right"
                            url = "https://iili.io/RmEhtn.png"
                        )
                    )
                )
            )
        }
    }

    private fun getProductInfoSectionData(
        id: String,
        isLazyLoaded: Boolean
    ): Component<ProductInformationContent> {
        return Component(
            id = id,
            type = "PRODUCT_INFO",
            isLazyLoaded = isLazyLoaded,
            lazyLoadUrl = "something",
            section = Section(
                version = 1,
                content = ProductInformationContent(
                    title = ContentItem(
                        type = "text",
                        label = "\$TITLE$",
                        modifiers = listOf(
                            ContentModifier(
                                type = "size",
                                size = 22,
                                displayText = "\$TITLE$",
                                identifier = "\$TITLE$"
                            ),
                            ContentModifier(
                                type = "bold",
                                displayText = "Product Information",
                                identifier = "\$TITLE$"
                            )
                        )
                    ),
                    infoPairs = listOf(
                        InfoPair(
                            key = ContentItem(
                                type = "text",
                                label = "Name:"
                            ),
                            value = ContentItem(
                                type = "text",
                                label = "\$VAR$",
                                modifiers = listOf(
                                    ContentModifier(
                                        type = "bold",
                                        displayText = "\$VAR$",
                                        identifier = "\$VAR$"
                                    ),
                                    ContentModifier(
                                        type = "underline",
                                        displayText = "Puneet Verma",
                                        identifier = "\$VAR$"
                                    )
                                )
                            )
                        ),
                        InfoPair(
                            key = ContentItem(
                                type = "text",
                                label = "Size:"
                            ),
                            value = ContentItem(
                                type = "text",
                                label = "\$VAR$",
                                modifiers = listOf(
                                    ContentModifier(
                                        type = "bold",
                                        displayText = "Medium",
                                        identifier = "\$VAR$"
                                    )
                                )
                            )
                        ),
                        InfoPair(
                            key = ContentItem(
                                type = "text",
                                label = "Country of Origin:"
                            ),
                            value = ContentItem(
                                type = "text",
                                label = "\$VAR$",
                                modifiers = listOf(
                                    ContentModifier(
                                        type = "bold",
                                        displayText = "India",
                                        identifier = "\$VAR$"
                                    )
                                )
                            )
                        ),
                        InfoPair(
                            key = ContentItem(
                                type = "text",
                                label = "Manufacturer:"
                            ),
                            value = ContentItem(
                                type = "text",
                                label = "\$VAR$",
                                modifiers = listOf(
                                    ContentModifier(
                                        type = "bold",
                                        displayText = "Christmas Pvt. Ltd.",
                                        identifier = "\$VAR$"
                                    )
                                )
                            )
                        )
                    )
                )
            )
        )
    }

    private fun getBannerSectionData(id: String, isLazyLoaded: Boolean): Component<BannerContent> {
        return Component(
            id = id,
            type = "BANNER",
            isLazyLoaded = isLazyLoaded,
            lazyLoadUrl = "something",
            section = Section(
                version = 1,
                content = BannerContent(
                    photo = ContentItem(
                        type = "image",
                        url = "https://iili.io/RmEhtn.png",
                        imageHeight = 200.0,
                        imageWidth = 200.0
                    )
                )
            )
        )
    }

    private fun getProductNameAndPricingSectionData(
        id: String,
        isLazyLoaded: Boolean
    ): Component<ProductNameAndPricingContent> {
        return Component(
            id = id,
            type = "PRODUCT_NAME_PRICING",
            isLazyLoaded = isLazyLoaded,
            lazyLoadUrl = "something",
            section = Section(
                version = 1,
                content = ProductNameAndPricingContent(
                    title = ContentItem(
                        type = "text",
                        label = "\$TITLE$",
                        modifiers = listOf(
                            ContentModifier(
                                type = "bold",
                                displayText = "\$TITLE$",
                                identifier = "\$TITLE$"
                            ),
                            ContentModifier(
                                type = "size",
                                size = 22,
                                displayText = "Ecosprin-AV 75 Capsules",
                                identifier = "\$TITLE$"
                            )
                        )
                    ),
                    subtitle = ContentItem(
                        type = "text",
                        label = "By USV Private Limited"
                    ),
                    headerLine1 = ContentItem(
                        type = "text",
                        label = "\$PRICE$",
                        modifiers = listOf(
                            ContentModifier(
                                type = "size",
                                size = 24,
                                displayText = "\$PRICE$",
                                identifier = "\$PRICE$"
                            ),
                            ContentModifier(
                                type = "bold",
                                displayText = "₹1,410",
                                identifier = "\$PRICE$"
                            )
                        )
                    ),
                    headerLine2 = ContentItem(
                        type = "view_with_text",
                        leftIcon = "https://iili.io/5HrdR2.png",
                        label = "PRESCRIPTION REQUIRED"
                    ),
                    bodyLine1 = ContentItem(
                        type = "text",
                        label = "\$MRP_PRICE$",
                        modifiers = listOf(
                            ContentModifier(
                                type = "color",
                                color = "#000000",
                                displayText = "\$MRP_PRICE$",
                                identifier = "\$MPR_PRICE$"
                            ),
                            ContentModifier(
                                type = "strikethrough",
                                displayText = "MRP 1,990.44",
                                identifier = "\$MRP_PRICE$"
                            )
                        )
                    ),
                    bodyLine2 = ContentItem(
                        type = "text",
                        label = "Save ₹28.56"
                    ),
                    bodyLine3 = ContentItem(
                        type = "text",
                        label = "Inclusive of all taxes"
                    ),
                    cta = ContentItem(
                        type = "primary_button",
                        subType = "half_size",
                        label = "Add to Cart",
                        leftIcon = "https://iili.io/5HrMbI.png",
                        interaction = Interaction(
                            onPressEvent = OnPressEvent(
                                action = Action(
                                    type = "show_toast",
                                    data = Data(
                                        extra = hashMapOf(
                                            "drug_id" to 1
                                        )
                                    )
                                ),
                                pelEvent = hashMapOf(
                                    "Some Data Arg" to "Hell Way"
                                )
                            ),
                            onViewEvent = OnViewEvent(
                                pelEvent = hashMapOf(
                                    "Some Data Arg" to "Hell Way"
                                )
                            )
                        )
                    )
                ),
                interaction = Interaction(
                    onViewEvent = OnViewEvent(
                        pelEvent = hashMapOf(
                            "Some Data Arg" to "Value"
                        )
                    )
                )
            )
        )
    }

    private fun getCarouselSectionData(
        id: String,
        isLazyLoaded: Boolean
    ): Component<CarouselContent> {
        return Component(
            id = id,
            type = "CAROUSEL",
            isLazyLoaded = isLazyLoaded,
            lazyLoadUrl = "something",
            section = Section(
                version = 1,
                content = CarouselContent(
                    list = listOf(
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/LS5dQiuUIA4"
                        ),
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/1_CMoFsPfso"
                        ),
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/W8KTS-mhFUE"
                        ),
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/xG8IQMqMITM"
                        ),
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/XNIjmb6Ax04"
                        ),
                        ContentItem(
                            type = "image",
                            url = "https://source.unsplash.com/Wn4ulyzVoD4"
                        )
                    )
                )
            )
        )
    }

    private fun getNavigationSection(): Section<NavigationContent> {
        return Section(
            version = 1,
            content = NavigationContent(
                title = ContentItem(
                    type = "text",
                    label = "Ecosprin-AV 75 Capsules"
                ),
                cta = ContentItem(
                    type = "share_cta",
                    interaction = Interaction(
                        onPressEvent = OnPressEvent(
                            action = Action(
                                type = "share_cta",
                                data = Data(
                                    url = "Sample URL"
                                )
                            ),
                            pelEvent = hashMapOf()
                        )
                    )
                )
            )
        )
    }

}

