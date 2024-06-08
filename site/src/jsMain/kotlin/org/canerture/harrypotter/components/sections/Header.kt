package org.canerture.harrypotter.components.sections

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.browser.dom.ElementTarget
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.foundation.layout.BoxScope
import com.varabyte.kobweb.compose.foundation.layout.Spacer
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.display
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.icons.MoonIcon
import com.varabyte.kobweb.silk.components.icons.SunIcon
import com.varabyte.kobweb.silk.components.navigation.Link
import com.varabyte.kobweb.silk.components.overlay.PopupPlacement
import com.varabyte.kobweb.silk.components.overlay.Tooltip
import com.varabyte.kobweb.silk.style.CssStyle
import com.varabyte.kobweb.silk.style.toModifier
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import org.canerture.harrypotter.components.widgets.IconButton
import org.jetbrains.compose.web.css.DisplayStyle
import org.jetbrains.compose.web.css.cssRem

val HeaderStyle = CssStyle {
    base { Modifier.fillMaxWidth().padding(3.cssRem) }
}

@Composable
private fun BoxScope.ColorModeButton() {
    var colorMode by ColorMode.currentState
    IconButton(
        modifier = Modifier.align(Alignment.CenterEnd),
        onClick = { colorMode = colorMode.opposite }
    ) {
        if (colorMode.isLight) MoonIcon() else SunIcon()
    }
    Tooltip(ElementTarget.PreviousSibling, "Toggle color mode", placement = PopupPlacement.BottomRight)
}

@Composable
fun Header() {
    val colorMode by ColorMode.currentState
    val logo = if (colorMode.isLight) "/harry-potter.png" else "/harry-potter-dark.png"
    Box(HeaderStyle.toModifier()) {
        Link(
            modifier = Modifier.align(Alignment.Center),
            path = "/character-list",
        ) {
            Image(logo, "Harry Potter Logo", Modifier.height(5.cssRem).display(DisplayStyle.Block))
        }

        Spacer()

        ColorModeButton()
    }
}