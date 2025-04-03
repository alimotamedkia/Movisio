package ir.anishehparsi.movisio

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orhanobut.hawk.Hawk
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import ir.anishehparsi.movisio.destinations.UserUiDestination

@Destination
@Composable
fun AccountUi(modifier: Modifier = Modifier, navigator: DestinationsNavigator) {
    val context = LocalContext.current
    var accountName by rememberSaveable  { mutableStateOf("") }
    var accountAge by rememberSaveable  { mutableStateOf("") }
    var isNameError by remember { mutableStateOf(false) }
    var isAgeError by remember { mutableStateOf(false) }

    Hawk.put("name", accountName)
    Hawk.put("age", accountAge)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier.height(64.dp))
        Text(
            text = stringResource(R.string.account_title),
            fontSize = 60.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily(Font(R.font.bagel_fat_one))
        )
        Spacer(modifier.height(16.dp))

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            label = { Text(text = "Your name") },
            value = accountName,
            onValueChange = { accountName = it },
            isError = isNameError
        )
        if (isNameError) {
            Text(
                text = "Name cannot be empty!",
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(start = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))


        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            label = { Text(text = "Your Age") },
            value = accountAge,
            onValueChange = { newValue ->
                if (newValue.all { it.isDigit() }) {
                    accountAge = newValue
                    isAgeError = newValue.isEmpty()
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = isAgeError
        )
        if (isAgeError) {
            Text(
                text = "Age cannot be empty!",
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(start = 32.dp)
            )
        }

        Spacer(modifier = Modifier.height(48.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            onClick = {
                isNameError = accountName.isBlank()
                isAgeError = accountAge.isBlank()

                if (!isNameError && !isAgeError) {
                    navigator.navigate(
                        UserUiDestination()
                    )
                    Toast.makeText(context, "Your data is saved 😊", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                }
            }
        ) {
            Text(text = "Save 💾")
        }
    }
}
