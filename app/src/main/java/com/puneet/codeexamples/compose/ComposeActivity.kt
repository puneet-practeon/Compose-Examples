package com.puneet.codeexamples.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.ui.unit.ExperimentalUnitApi
import com.google.android.material.composethemeadapter.MdcTheme
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalUnitApi
@InternalCoroutinesApi
class ComposeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MdcTheme {
                Surface {
                    LazyColumn {
                        item {
                            WebviewCard(data = getWebviewSectionData())
                        }
                        item {
                            ProductInformationCard(data = getProductInfoSectionData())
                        }
                    }
                }
            }
        }
    }

    private fun getWebviewSectionData(): Section<WebviewContent> {
        return Section(
            version = 1,
            content = WebviewContent(
                title = ContentItem(
                    label = "\$VAR$",
                    type = "text",
                    modifiers = listOf(
                        ContentModifier(
                            type = "bold",
                            displayText = "\$VAR$",
                            identifier = "\$VAR$"
                        ),
                        ContentModifier(
                            type = "size",
                            size = 24,
                            displayText = "\$VAR$",
                            identifier = "\$VAR$"
                        ),
                        ContentModifier(
                            type = "color",
                            color = "#000000",
                            displayText = "Return Policy",
                            identifier = "\$VAR$"
                        )
                    )
                ),
                subtitle = ContentItem(
                    label = "This medicine is not \$TITLE$. For full details on our returns policies",
                    type = "text",
                    modifiers = listOf(
                        ContentModifier(
                            type = "underline",
                            displayText = "\$TITLE$",
                            identifier = "\$TITLE$"
                        ),
                        ContentModifier(
                            type = "bold",
                            displayText = "\$TITLE$",
                            identifier = "\$TITLE$"
                        ),
                        ContentModifier(
                            type = "color",
                            color = "#346ab2",
                            displayText = "returnable",
                            identifier = "\$TITLE$"
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
                            type = "underline",
                            displayText = "Read More",
                            identifier = "\$CTA$"
                        )
                    ),
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
                    ),
                ),
                photo = ContentItem(
                    type = "image",
                    subType = "arrow_right"
//                                url = "https://iili.io/RmEhtn.png"
                )
            )
        )
    }

    private fun getProductInfoSectionData(): Section<ProductInformationContent> {
        return Section(
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
    }

}

