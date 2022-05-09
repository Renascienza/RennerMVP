package com.renascienza.rennermvp.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.renascienza.rennermvp.R
import com.renascienza.rennermvp.ui.theme.RennerMVPTheme

@Composable
fun AboutScreen(){

    val uriHandler = LocalUriHandler.current
    val whatsappString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = RennerMVPTheme.colors.onBackground
            )
        ) {
            append("(whatsapp) ")
        }

        pushStringAnnotation(
            tag = "whatsapp",
            annotation = "https://wa.me/5521965712142?text=Ol%C3%A1%2C%20Cl%C3%A1udio.%20Peguei%20seu%20contato%20no%20desafio%20Renner"
        )
        withStyle(
            style = SpanStyle(
                color = RennerMVPTheme.colors.secondary,
                fontWeight = FontWeight.Bold
            ),
        ) {
            append("+55 21 965712142")
        }
        pop()
    }
    val mailtoString = buildAnnotatedString {

        pushStringAnnotation(
            tag = "mailto",
            annotation = "mailto://claudio.marcelo.silva@gmail.com"
        )
        withStyle(
            style = SpanStyle(
                color = RennerMVPTheme.colors.secondary,
                fontWeight = FontWeight.Bold
            ),
        ) {
            append("claudio.marcelo.silva@gmail.com")
        }
        pop()
    }
    val githubString = buildAnnotatedString {

        withStyle(
            style = SpanStyle(
                color = RennerMVPTheme.colors.onBackground
            )
        ) {
            append("Esse Demo é uma resposta ao desafio Android Renner.\n\n")
            append("A aplicação foi escrita inteiramente em Kotlin + Jetpack Compose.\n\n")
            append("Contém navegação, tela inicial, coleção de produtos, detalhe de produtos e sistema de favoritos.\n\n")
            append("O código fonte pode ser encontrado ")
        }

        pushStringAnnotation(tag = "github", annotation = "https://google.com/policy")
        withStyle(
            style = SpanStyle(
                color = RennerMVPTheme.colors.secondary,
                fontWeight = FontWeight.Bold
            ),
        ) {
            append("no Github")
        }
        pop()
    }

    Column(Modifier.fillMaxSize()) {
        Row(modifier = Modifier
            .padding(top = 32.dp, start = 8.dp, end = 8.dp)
            .fillMaxWidth()){
            Image(
                painter = painterResource(R.drawable.me),
                contentDescription = "Claudio M. SIlva",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(128.dp)
                    .clip(CircleShape)
                    .border(2.dp, RennerMVPTheme.colors.onBackground, CircleShape)
            )
            Column(Modifier.padding(8.dp)) {
                Text(
                    text = "Cláudio Marcelo Silva",
                    color = RennerMVPTheme.colors.onBackground,
                    style = RennerMVPTheme.typography.h5,
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = "Desenvolvedor sênior",
                    color = RennerMVPTheme.colors.onBackground,
                    style = RennerMVPTheme.typography.subtitle1,
                    modifier = Modifier.padding(top = 8.dp, start = 16.dp, bottom = 8.dp)
                )

                ClickableText(
                    text = whatsappString,
                    style = RennerMVPTheme.typography.body2,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ){ offset ->
                    whatsappString.getStringAnnotations(tag = "whatsapp", start = offset, end = offset).firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
                }
                ClickableText(
                    text = mailtoString,
                    style = RennerMVPTheme.typography.body2,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                ){ offset ->
                    mailtoString.getStringAnnotations(tag = "mailto", start = offset, end = offset).firstOrNull()?.let {
                        uriHandler.openUri(it.item)
                    }
                }
                SelectionContainer {
                    Text(
                        text = "Rua Doutor Leal 258 - Engenho de Dentro -",
                        color = RennerMVPTheme.colors.onBackground,
                        style = RennerMVPTheme.typography.body2,
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
                SelectionContainer {
                    Text(
                        text = "Rio de Janeiro/RJ - CEP 207-30380",
                        color = RennerMVPTheme.colors.onBackground,
                        style = RennerMVPTheme.typography.body2,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .fillMaxWidth(),
                        textAlign = TextAlign.End
                    )
                }
            }
        }

        ClickableText(
            modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
            text = githubString,
            style = RennerMVPTheme.typography.body2
        ){ offset ->
            githubString.getStringAnnotations(tag = "github", start = offset, end = offset).firstOrNull()?.let {
                uriHandler.openUri(it.item)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_XL, showSystemUi = true)
@Composable
fun AboutScreenPreview(){
    RennerMVPTheme {
        AboutScreen()
    }
}