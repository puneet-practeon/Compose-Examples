package com.puneet.codeexamples.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalUnitApi
@InternalCoroutinesApi
@ExperimentalPagerApi
class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isFullScreen by remember { mutableStateOf(false) }
            var currentIndex by remember { mutableStateOf(0) }
            MdcTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    if (isFullScreen) {
                        FullScreenCarouselComponent(
                            component = getCarouselSectionData("123", false),
                            currentIndex
                        ) { index ->
                            isFullScreen = false
                            currentIndex = index
                        }
                    } else {
                        LazyColumn {
//                            items(getComponents().size) {
//
//                            }
                            item {
                                CarouselComponent(
                                    component = getCarouselSectionData("123", false),
                                    currentIndex
                                ) { index ->
                                    isFullScreen = true
                                    currentIndex = index
                                }
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
    }

    private fun getWebviewSectionData(
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
                                            data = hashMapOf(
                                                "message" to "Word clicked"
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
                                            data = hashMapOf(
                                                "message" to "Action Performed"
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
                        subType = "arrow_right"
//                                url = "https://iili.io/RmEhtn.png"
                    )
                )
            )
        )
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
                                    data = hashMapOf(
                                        "drug_id" to 1
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

}

