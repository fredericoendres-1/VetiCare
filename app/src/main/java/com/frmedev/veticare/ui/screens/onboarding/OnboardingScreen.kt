package com.frmedev.veticare.ui.screens.onboarding

import android.content.res.Configuration
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.frmedev.veticare.R
import com.frmedev.veticare.ui.components.VetiAuthButton
import com.frmedev.veticare.ui.components.VetiPrimaryButton
import com.frmedev.veticare.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onLoginSuccess: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VetiBackground)
            .padding(vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PageIndicator(
            pageCount = pagerState.pageCount,
            currentPage = pagerState.currentPage,
            modifier = Modifier.padding(top = 16.dp)
        )

        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (page) {
                0 -> OnboardingPageContent(
                    imageRes = R.drawable.ic_launcher_foreground, // Substitua pelo cachorro
                    imageCd = stringResource(id = R.string.onboarding_cd_img_1),
                    title = stringResource(id = R.string.onboarding_title_1),
                    description = stringResource(id = R.string.onboarding_desc_1),
                    buttonText = stringResource(id = R.string.onboarding_btn_start),
                    onButtonClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(1) }
                    }
                )
                1 -> OnboardingPageContent(
                    imageRes = R.drawable.ic_launcher_foreground, // Substitua pelo celular
                    imageCd = stringResource(id = R.string.onboarding_cd_img_2),
                    title = stringResource(id = R.string.onboarding_title_2),
                    description = stringResource(id = R.string.onboarding_desc_2),
                    buttonText = stringResource(id = R.string.onboarding_btn_continue),
                    onButtonClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(2) }
                    }
                )
                2 -> AuthPageContent(
                    onGoogleClick = { /* TODO: Implementar Firebase Google Auth */ onLoginSuccess() },
                    onAppleClick = { /* TODO: Implementar Firebase Apple Auth */ onLoginSuccess() },
                    onEmailClick = { /* TODO: Navegar para tela de email */ onLoginSuccess() }
                )
            }
        }
    }
}

@Composable
private fun OnboardingPageContent(
    imageRes: Int,
    imageCd: String,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = imageRes),
            contentDescription = imageCd,
            modifier = Modifier
                .size(220.dp)
                .padding(bottom = 32.dp),
            contentScale = ContentScale.Fit
        )

        Text(
            text = title,
            color = VetiTextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = description,
            color = VetiTextSecondary,
            fontSize = 15.sp,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reutilizando nosso Design System!
        VetiPrimaryButton(
            text = buttonText,
            onClick = onButtonClick
        )
    }
}

@Composable
private fun AuthPageContent(
    onGoogleClick: () -> Unit,
    onAppleClick: () -> Unit,
    onEmailClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground), // Substitua pela prancheta
            contentDescription = stringResource(id = R.string.auth_cd_img_3),
            modifier = Modifier.size(200.dp).padding(bottom = 32.dp)
        )

        Text(
            text = stringResource(id = R.string.auth_title),
            color = VetiTextPrimary,
            fontSize = 26.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = stringResource(id = R.string.auth_desc),
            color = VetiTextSecondary,
            fontSize = 15.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.weight(1f))

        // Reutilizando nosso Design System! (Removemos a antiga função AuthButton daqui)
        VetiAuthButton(
            text = stringResource(id = R.string.auth_btn_google),
            isOutlined = false,
            onClick = onGoogleClick
        )
        Spacer(modifier = Modifier.height(12.dp))

        VetiAuthButton(
            text = stringResource(id = R.string.auth_btn_apple),
            isOutlined = false,
            onClick = onAppleClick
        )
        Spacer(modifier = Modifier.height(12.dp))

        VetiAuthButton(
            text = stringResource(id = R.string.auth_btn_email),
            isOutlined = true,
            onClick = onEmailClick
        )
    }
}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = modifier
    ) {
        repeat(pageCount) { index ->
            val isSelected = currentPage == index
            val width by animateDpAsState(
                targetValue = if (isSelected) 24.dp else 8.dp,
                label = "indicator_width"
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(width)
                    .background(
                        color = if (isSelected) VetiOlive else VetiOutline, // Usando VetiOutline do Theme
                        shape = RoundedCornerShape(50)
                    )
            )
        }
    }
}

// --- Previews ---

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    VetiCareTheme {
        OnboardingScreen(
            onLoginSuccess = {}
        )
    }
}