package com.app.movilbox.ui.info

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.movilbox.domain.models.ProductModel
import com.app.movilbox.ui.product.TextData

@Composable
fun Info(navController: NavHostController, infoViewModel: InfoViewModel, id: String?) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xB701283A))
    ) {
        InitInfo(infoViewModel, id, navController)
    }
}

@Composable
private fun InitInfo(infoViewModel: InfoViewModel, id: String?, navController: NavHostController) {

    val stateProduct: ProductModel by infoViewModel.stateProduct.collectAsState()

    val ctx = LocalContext.current
    if (id != "null") {
        infoViewModel.getProductId(id!!)
    } else {
        Toast.makeText(ctx, "Error", Toast.LENGTH_LONG).show()
        navController.popBackStack()
    }
    if (stateProduct.images.isNotEmpty()) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(8.dp)
            ) {
                item {
                    TextData(info = stateProduct.title, 20.sp)
                    Spacer(modifier = Modifier.padding(8.dp))
                    AsyncImage(
                        modifier = Modifier
                            .height(200.dp)
                            .fillMaxWidth(),
                        model = ImageRequest.Builder(context = LocalContext.current)
                            .data(stateProduct.images[0]).crossfade(true).build(),
                        alignment = Alignment.Center,
                        contentDescription = stateProduct.description,
                        contentScale = ContentScale.Crop,
                    )
                    Spacer(modifier = Modifier.padding(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (stateProduct.stock > 0) {
                            TextData(
                                info = "$${stateProduct.price} - ( ${stateProduct.discountPercentage}% )",
                                15.sp
                            )
                        } else {
                            TextData(info = "No disponible", 8.sp)
                        }
                        TextData(info = "*${stateProduct.rating}", 15.sp)
                    }
                    TextData(info = "Stock: ${stateProduct.stock}", 20.sp)
                    Spacer(modifier = Modifier.padding(4.dp))
                    Text(
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Normal,
                        modifier = Modifier.padding(2.dp),
                        text = stateProduct.description,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

