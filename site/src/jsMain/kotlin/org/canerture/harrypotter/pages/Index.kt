package org.canerture.harrypotter.pages

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.varabyte.kobweb.compose.foundation.layout.Column
import com.varabyte.kobweb.compose.ui.Alignment
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.border
import com.varabyte.kobweb.compose.ui.modifiers.borderRadius
import com.varabyte.kobweb.compose.ui.modifiers.fillMaxWidth
import com.varabyte.kobweb.compose.ui.modifiers.height
import com.varabyte.kobweb.compose.ui.modifiers.marginBlock
import com.varabyte.kobweb.compose.ui.modifiers.marginInline
import com.varabyte.kobweb.compose.ui.modifiers.padding
import com.varabyte.kobweb.compose.ui.thenIf
import com.varabyte.kobweb.core.Page
import com.varabyte.kobweb.silk.components.graphics.Image
import com.varabyte.kobweb.silk.components.layout.SimpleGrid
import com.varabyte.kobweb.silk.style.breakpoint.Breakpoint
import com.varabyte.kobweb.silk.style.breakpoint.ResponsiveValues
import com.varabyte.kobweb.silk.theme.breakpoint.rememberBreakpoint
import com.varabyte.kobweb.silk.theme.colors.ColorMode
import kotlinx.browser.window
import org.canerture.harrypotter.components.layouts.PageLayout
import org.canerture.harrypotter.data.getCharacters
import org.canerture.harrypotter.data.model.Character
import org.canerture.harrypotter.toSitePalette
import org.jetbrains.compose.web.css.LineStyle
import org.jetbrains.compose.web.css.cssRem
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.dom.H4
import org.jetbrains.compose.web.dom.Text

@Page("/")
@Composable
fun IndexPage() {
    PageLayout("Character List") {

        var state by remember { mutableStateOf<CharacterListState>(CharacterListState.Loading) }

        LaunchedEffect(Unit) {
            state = CharacterListState.Loading
            getCharacters().onSuccess {
                state = CharacterListState.Success(it)
            }.onFailure {
                state = CharacterListState.Error(it.message.orEmpty())
            }
        }

        when (val currentState = state) {
            is CharacterListState.Loading -> Unit

            is CharacterListState.Success -> {
                SimpleGrid(
                    numColumns = ResponsiveValues(1, 2, 4, 4, 5),
                    modifier = Modifier.fillMaxWidth().padding(2.cssRem)
                ) {
                    currentState.characters.forEach { character ->
                        CharacterItem(character = character)
                    }
                }
            }

            is CharacterListState.Error -> window.alert("Error: ${currentState.message}")
        }
    }
}

@Composable
private fun CharacterItem(character: Character) {
    val breakpoint = rememberBreakpoint()
    val colorPalette = ColorMode.current.toSitePalette()
    Column(
        modifier = Modifier
            .borderRadius(r = 2.cssRem)
            .border(
                width = 2.px,
                color = colorPalette.brand.primary,
                style = LineStyle.Solid
            )
            .thenIf(breakpoint >= Breakpoint.MD, Modifier.marginInline(end = 2.5.cssRem))
            .marginBlock(end = 2.5.cssRem),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(340.px)
                .borderRadius(topLeft = 2.cssRem, topRight = 2.cssRem),
            src = character.imageUrl.orEmpty(),
            alt = character.name.orEmpty(),
            height = 340
        )
        H4 {
            Text(character.name.orEmpty())
        }
    }
}