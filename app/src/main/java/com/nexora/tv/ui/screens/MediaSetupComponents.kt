package com.nexora.tv.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nexora.tv.data.app.AppLanguageStore

internal val SetupViolet = Color(0xFF7C3AED)
internal val SetupVioletSoft = Color(0xFF9F67FF)
internal val SetupBlue = Color(0xFF4CC9FF)
internal val SetupPanelDark = Color(0xCC090B12)
internal val SetupPanelSoft = Color(0xAA11131C)

internal enum class SourceMode(val label: String) {
    Portal("Provider API"),
    ListUrl("M3U URL"),
    LocalFile("Local data"),
    Single("Play single stream")
}

@Composable
internal fun SourceModeSelector(
    selected: SourceMode,
    portalFocus: FocusRequester,
    onSelected: (SourceMode) -> Unit
) {
    Row(
        modifier = Modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SourceMode.values().forEach { item ->
            Button(
                onClick = { onSelected(item) },
                modifier = Modifier
                    .height(42.dp)
                    .then(if (item == SourceMode.Portal) Modifier.focusRequester(portalFocus) else Modifier)
                    .onFocusChanged { if (it.isFocused) onSelected(item) },
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selected == item) SetupViolet else Color.White.copy(alpha = 0.08f),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = item.label,
                    fontSize = 11.sp,
                    fontWeight = if (selected == item) FontWeight.Black else FontWeight.Medium,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
internal fun SetupInputField(
    fieldId: String,
    activeFieldId: String?,
    onActiveFieldChange: (String?) -> Unit,
    label: String,
    value: String,
    onChange: (String) -> Unit,
    focusRequester: FocusRequester,
    nextFocusRequester: FocusRequester? = null,
    nextFieldId: String? = null,
    secret: Boolean = false,
    singleLine: Boolean = true,
    height: Int = 66,
    modifier: Modifier = Modifier
) {
    val keyboard = LocalSoftwareKeyboardController.current
    val isActive = activeFieldId == fieldId
    val movingByKeyboard = remember { mutableStateOf(false) }

    fun activate() {
        onActiveFieldChange(fieldId)
        focusRequester.requestFocus()
        keyboard?.show()
    }

    fun moveNext() {
        if (nextFocusRequester != null) {
            movingByKeyboard.value = true
            onActiveFieldChange(nextFieldId)
            nextFocusRequester.requestFocus()
            if (nextFieldId == null) keyboard?.hide() else keyboard?.show()
        } else {
            onActiveFieldChange(null)
            keyboard?.hide()
        }
    }

    LaunchedEffect(activeFieldId) {
        if (isActive) {
            focusRequester.requestFocus()
            keyboard?.show()
        }
    }

    OutlinedTextField(
        value = value,
        onValueChange = onChange,
        label = { Text(label) },
        readOnly = !isActive,
        singleLine = singleLine,
        visualTransformation = if (secret) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(
            imeAction = if (nextFocusRequester != null) ImeAction.Next else ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onNext = { moveNext() },
            onDone = { moveNext() }
        ),
        modifier = modifier
            .width(500.dp)
            .height(height.dp)
            .focusRequester(focusRequester)
            .onFocusChanged { state ->
                if (!state.isFocused && isActive) {
                    if (movingByKeyboard.value) {
                        movingByKeyboard.value = false
                    } else {
                        onActiveFieldChange(null)
                        keyboard?.hide()
                    }
                }
            }
            .onKeyEvent { event ->
                if (event.type == KeyEventType.KeyUp && (event.key == Key.Enter || event.key == Key.NumPadEnter || event.key == Key.DirectionCenter)) {
                    if (isActive) moveNext() else activate()
                    true
                } else {
                    false
                }
            },
        shape = RoundedCornerShape(18.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = if (isActive) SetupVioletSoft else SetupViolet,
            unfocusedBorderColor = Color.White.copy(alpha = 0.15f),
            focusedLabelColor = SetupVioletSoft,
            unfocusedLabelColor = Color.White.copy(alpha = 0.50f),
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White,
            cursorColor = SetupVioletSoft,
            focusedContainerColor = Color.White.copy(alpha = if (isActive) 0.055f else 0.035f),
            unfocusedContainerColor = Color.White.copy(alpha = 0.02f)
        ),
        placeholder = {
            Text(
                AppLanguageStore.t("Press OK to edit", "Düzenlemek için OK"),
                color = Color.White.copy(alpha = 0.42f)
            )
        }
    )
}

@Composable
internal fun SetupStatusBox(text: String) {
    Box(
        modifier = Modifier
            .width(500.dp)
            .background(SetupPanelSoft, RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(14.dp)
    ) {
        Text(text, color = Color.White.copy(alpha = 0.76f), fontSize = 12.sp, lineHeight = 17.sp)
    }
}

@Composable
internal fun MediaSetupSecurityPanel() {
    Column(
        modifier = Modifier
            .width(650.dp)
            .height(594.dp)
            .background(SetupPanelDark, RoundedCornerShape(30.dp))
            .border(1.dp, Color.White.copy(alpha = 0.10f), RoundedCornerShape(30.dp))
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(AppLanguageStore.t("Security & Early Access", "Güvenlik ve Erken Erişim"), color = Color.White, fontSize = 31.sp, fontWeight = FontWeight.Black)
        Text(AppLanguageStore.t("Built as a premium TV player ecosystem.", "Premium TV oynatıcı ekosistemi olarak geliştiriliyor."), color = SetupVioletSoft, fontSize = 15.sp, fontWeight = FontWeight.Bold)

        NoticeCard(AppLanguageStore.t("Player, not a provider", "Oynatıcıdır, sağlayıcı değildir"), AppLanguageStore.t("Nexora TV does not sell channels, subscriptions, accounts, lists, or media access.", "Nexora TV kanal, abonelik, hesap, liste veya medya erişimi satmaz."))
        NoticeCard(AppLanguageStore.t("Legal use only", "Sadece yasal kullanım"), AppLanguageStore.t("Users must enter only media access they are legally authorized to use.", "Kullanıcı yalnızca yasal erişim hakkı olan medya kaynaklarını girmelidir."))
        NoticeCard(AppLanguageStore.t("No MAC-based identity", "MAC tabanlı kimlik yok"), AppLanguageStore.t("We do not use MAC as the device identity. It can expose hardware details and is not reliable across devices.", "MAC adresini cihaz kimliği olarak kullanmıyoruz. Donanım bilgisi açığa çıkarabilir ve cihazlar arasında güvenilir değildir."))
        NoticeCard(AppLanguageStore.t("Privacy-first direction", "Gizlilik öncelikli yaklaşım"), AppLanguageStore.t("The setup flow keeps access controlled by the user and avoids unnecessary device identifiers.", "Kurulum akışı erişimi kullanıcı kontrolünde tutar ve gereksiz cihaz kimliklerinden kaçınır."))
        NoticeCard(AppLanguageStore.t("Free during early access", "Erken erişimde ücretsiz"), AppLanguageStore.t("The app remains free while the full ecosystem is being completed and tested.", "Tüm ekosistem tamamlanıp test edilene kadar uygulama ücretsiz kalır."))

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            InfoPill("TV-friendly")
            InfoPill("Remote-first")
            InfoPill("early access")
        }
    }
}

@Composable
private fun NoticeCard(title: String, body: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White.copy(alpha = 0.05f), RoundedCornerShape(20.dp))
            .border(1.dp, Color.White.copy(alpha = 0.08f), RoundedCornerShape(20.dp))
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(title, color = Color.White, fontSize = 15.sp, fontWeight = FontWeight.Black)
        Text(body, color = Color.White.copy(alpha = 0.68f), fontSize = 12.sp, lineHeight = 18.sp)
    }
}

@Composable
private fun InfoPill(text: String) {
    Box(
        modifier = Modifier
            .background(SetupBlue.copy(alpha = 0.14f), RoundedCornerShape(14.dp))
            .border(1.dp, SetupBlue.copy(alpha = 0.35f), RoundedCornerShape(14.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text, color = Color.White, fontSize = 11.sp, fontWeight = FontWeight.Black)
    }
}
