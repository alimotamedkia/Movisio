package ir.anishehparsi.movisio

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.orhanobut.hawk.Hawk
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.anishehparsi.movisio.destinations.AccountUiDestination

@Destination
@Composable
fun UserUi(modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val user_name = Hawk.get("name", "unknown")
    val user_age = Hawk.get("age", "")

    Column {
        if (user_age == "") {
            Text(
                modifier = Modifier
                    .padding(top = 32.dp, start = 8.dp, end = 8.dp),
                text = stringResource(R.string.account_enter_data),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
                color = Color.Red,
                textAlign = TextAlign.Center,
                lineHeight = 2.5.em
            )

        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp)
            ) {
                Spacer(modifier.height(32.dp))
                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    text = "Hi dear $user_name,",
                    fontWeight = FontWeight.Medium
                )

                Text(
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    text = "Considering your age, which is $user_age:",
                    fontWeight = FontWeight.Medium
                )
                Text(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    text = calculateAgeRate(user_age),
                    fontWeight = FontWeight.Bold
                )
            }

        }
        Spacer(modifier.weight(1f))

        ExtendedFloatingActionButton(
            modifier = Modifier
                .padding(8.dp),
            onClick = {
                navigator.navigate(AccountUiDestination)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "Register account button"
                )
            },
            text = { Text(text = "Account") },
        )

    }
}

@Composable
fun calculateAgeRate(user_age: String): String {

    val age = user_age.toInt()
    return when {
        age <= 13 -> stringResource(R.string.under13)
        age <= 17 -> stringResource(R.string.under17)
        else -> stringResource(R.string.age_popular)
    }
}
