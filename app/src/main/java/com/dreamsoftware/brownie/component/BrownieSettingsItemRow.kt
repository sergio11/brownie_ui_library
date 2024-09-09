package com.dreamsoftware.brownie.component

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp

abstract class BrownieSettingsItemVO(
    @StringRes open val textRes: Int,
    @DrawableRes open val icon: Int,
    open val isDanger: Boolean = false
)

abstract class BrownieSettingsItemSwitchVO(
    open val isEnabled: Boolean,
    override val textRes: Int,
    override val icon: Int,
    override val isDanger: Boolean
): BrownieSettingsItemVO(textRes, icon, isDanger)

@Composable
fun BrownieSettingsItemRow(
    item: BrownieSettingsItemVO,
    onClicked: (BrownieSettingsItemVO) -> Unit = {}
) {
    with(MaterialTheme.colorScheme) {
        Column(
            modifier = Modifier
                .clickable { onClicked(item) }
                .fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp, bottom = 16.dp,
                        start = 16.dp, end = 16.dp
                    )
            ) {

                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = if (item.isDanger) error else primary
                )

                BrownieText(
                    modifier = Modifier.padding(top = 2.dp, start = 16.dp),
                    type = BrownieTextTypeEnum.LABEL_LARGE,
                    titleText = stringResource(id = item.textRes),
                    textColor = if (item.isDanger) error else primary
                )

                Spacer(modifier = Modifier.weight(1f))

                if (item is BrownieSettingsItemSwitchVO) {
                    Switch(
                        modifier = Modifier
                            .size(28.dp)
                            .padding(end = 18.dp),
                        checked = item.isEnabled,
                        onCheckedChange = { onClicked(item) },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = primary,
                            uncheckedTrackColor = primaryContainer,
                            uncheckedBorderColor = primaryContainer,
                            uncheckedThumbColor = Color.White
                        )
                    )
                }
            }
        }
    }
}