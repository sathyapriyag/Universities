package com.learn.universities


import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.learn.universities.model.repository.APIService
import com.learn.universities.model.repository.Repository
import com.learn.universities.ui.theme.UniversitiesTheme
import com.learn.universities.viewModel.ListViewModel
import com.learn.universities.viewModel.ListViewModelFactory
import kotlinx.coroutines.launch


class UniversitiesList : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val retrofitService = APIService.getInstance()
        val repository = Repository(retrofitService)
        setContent {

            UniversitiesTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                )
                {
                    val viewModelFactory = ListViewModelFactory(repository)
                    val viewModel: ListViewModel by viewModels { viewModelFactory }
                    Welcome(viewModel)

                }
            }
        }
    }
}


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun Welcome(viewModel: ListViewModel) {

        Log.i("Welcome","welcome")

        var text by remember { mutableStateOf("United States") }

        val scope = rememberCoroutineScope()
        val context= LocalContext.current

        LaunchedEffect(Unit) {
            scope.launch {
                viewModel.getPosts()

            }
        }


        Column (modifier=Modifier.padding(start = 5.dp)){
           // var countryName by rememberSaveable { mutableStateOf("") }

            TitleText()
            /*Text(
                text = "Universities in text",
                color = MaterialTheme.colorScheme.primary, modifier = Modifier
                    .padding(10.dp), fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center
            )*/
            Spacer(modifier = Modifier.height(2.dp))

            Row( modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically)
            {

                val countryName = remember { mutableStateOf(TextFieldValue("")) }
                Surface( modifier = Modifier.weight(1f)) {

                    TextField(countryName )
                }

              /*  TextField(value = countryName,
                    onValueChange = { countryName = it },

                   *//* onValueChange = {
                        // Avoid recomposition while typing
                        if (it != countryName) {
                            countryName = it
                        }
                    },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text, imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Perform an action when the "Done" button is pressed
                            // You can update your ViewModel or trigger some other action here
                        }
                    ),*//*
                    placeholder = { Text(text = "Enter country name") },
                    textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
                    singleLine = true,modifier = Modifier
                        .padding(6.dp)
                        .weight(1f))
*/

                Button(modifier = Modifier.padding(6.dp),
                    onClick = { //call view model

                        if(countryName.value.text.isNotEmpty()) {


                             var text = countryName.value.text.substring(0, 1).uppercase() + countryName.value.text.substring(1)

                            scope.launch { viewModel.getPosts(text.trim()) }


                        }else{
                            Toast.makeText(context,"Enter Country Name and then continue..!",Toast.LENGTH_LONG).show()
                        }
                    }) {
                    Text(text = "Get List")
                }

            }

            Spacer(modifier = Modifier.height(5.dp))
           UpdateLazy(viewModel)

            }


        }

@Composable
fun TitleText() {

    Text(
        text = "Get Universities around the world",
        color = MaterialTheme.colorScheme.primary, modifier = Modifier
            .padding(10.dp).fillMaxWidth(), fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.headlineLarge, textAlign = TextAlign.Center
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextField(
    textFieldValue: MutableState<TextFieldValue>,
) {
    TextField(
        value = textFieldValue.value,
        onValueChange = { textFieldValue.value = it },placeholder = { Text(text = "Enter country name") },
        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp),
        singleLine = true,modifier = Modifier.padding(6.dp))

}
     @Composable
    fun UpdateLazy(
         viewModel: ListViewModel

     ) {
         val universities by viewModel.universities.collectAsStateWithLifecycle()

         Log.i("Update lazy",universities.size.toString())
         if(universities.isNotEmpty()) {
             Log.i("UpdateLazy column",universities.toString())


             LazyColumn(modifier = Modifier.padding(6.dp)) {

                items(universities) { data ->
                     Text(text = data.name, modifier = Modifier.padding(2.dp))
                     Divider(color = Color.Gray, thickness = 1.dp)
                 }
             }
         }
         else{
             Text(
                 text = "No Universities found!",
                 color = MaterialTheme.colorScheme.primary, modifier = Modifier
                     .padding(20.dp), fontWeight = FontWeight.Bold,
                 style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center
             )
         }
     }



  /*  @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        UniversitiesTheme {
            Surface {

                val viewModel= ListViewModel(repository)

                Welcome(viewModel)
            }
        }
    }
*/











