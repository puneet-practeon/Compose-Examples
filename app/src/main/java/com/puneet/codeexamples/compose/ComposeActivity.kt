package com.puneet.codeexamples.compose

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
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
                    WebviewCard(
                        data = Section(
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
                    )
                }
            }
        }
    }

}

