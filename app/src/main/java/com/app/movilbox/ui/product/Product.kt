package com.app.movilbox.ui.product

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.app.movilbox.R
import com.app.movilbox.domain.models.ProductModel
import com.app.movilbox.ui.delete.Delete
import com.app.movilbox.ui.navigation.Routes
import java.util.Locale

@Composable
fun Product(navController: NavHostController, productViewModel: ProductViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x7A00E676))
    ) {
        Main(productViewModel, navController)
    }
}

@Composable
private fun Main(productViewModel: ProductViewModel, navController: NavHostController) {
    val query: String by productViewModel.stateQuery.collectAsState()
    val filterData by productViewModel.stateFilter.collectAsState()

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        TopBar(productViewModel, query) { productViewModel.onChangedQuery(it) }
        Spacer(modifier = Modifier.padding(8.dp))
        Box {
            ListCategories(Modifier.align(Alignment.Center), productViewModel)
        }
        ListProduct(
            productViewModel,
            navController,
            query,
            filterData,
            Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun ListCategories(modifier: Modifier, productViewModel: ProductViewModel) {
    val stateCategories by productViewModel.stateCategories.collectAsState()
    productViewModel.getCategories()
    if (stateCategories.isNotEmpty()) {
        LazyRow {
            items(stateCategories) {
                Card(
                    elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxWidth(), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = it.uppercase(Locale.getDefault()),
                        fontSize = 10.sp,
                        modifier = modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(10.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    productViewModel: ProductViewModel,
    query: String,
    onQuery: (String) -> Unit
) {
    var expand by rememberSaveable { mutableStateOf(false) }
    val filterList =
        listOf("Limpiar Filtro", "Precio", "Descuento", "Categoria", "Stock", "Marca", "Rating")

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        DockedSearchBar(
            modifier = Modifier
                .width(200.dp)
                .height(56.dp)
                .padding(0.dp),
            query = query,
            onQueryChange = { onQuery(it) },
            onSearch = { productViewModel.searchProduct(query) },
            active = false,
            onActiveChange = { },
            placeholder = { Text(text = "Notebook") },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            },
            tonalElevation = 0.dp,
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onQuery("") }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close"
                        )
                    }
                }
            }
        ) {

        }
        IconButton(onClick = { /*showDialog.value = true*/ }, modifier = Modifier.size(30.dp)) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }

        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
        ) {
            IconButton(onClick = { expand = !expand }, modifier = Modifier.size(25.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter"
                )
            }
            DropdownMenu(expanded = expand, onDismissRequest = { expand = false }) {
                filterList.map {
                    DropdownMenuItem(
                        text = { Text(text = it) },
                        onClick = { productViewModel.filterProducts(it) })
                }
            }
        }
    }
}

@Composable
private fun ListProduct(
    productViewModel: ProductViewModel,
    navController: NavHostController,
    query: String,
    filterData: Boolean,
    modifier: Modifier
) {
    val stateList by productViewModel.stateList.collectAsState()
    val stateDelete by productViewModel.stateDelete.collectAsState()
    val ctx = LocalContext.current

    if (stateDelete) {
        productViewModel.onChangedDelete()
        Toast.makeText(ctx, "Producto eleminado", Toast.LENGTH_LONG).show()
    }
    if (query.isEmpty() && !filterData && !stateDelete) {
        productViewModel.getProduct()
    }

    if (stateList.isNotEmpty()) {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            fontSize = 20.sp,
            fontFamily = FontFamily.SansSerif,
            fontStyle = FontStyle.Normal,
            modifier = modifier.padding(2.dp),
            text = "Products".uppercase(),
            overflow = TextOverflow.Ellipsis
        )
        Spacer(modifier = Modifier.padding(4.dp))

        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp), modifier = Modifier.padding(1.dp)
        ) {
            items(stateList) {
                ItemProduct(it, navController, productViewModel)
            }
        }
    } else {
        Spacer(modifier = Modifier.padding(8.dp))
        TextData(info = "No existe productos".uppercase(), 25.sp)
    }
}

@Composable
private fun Deleteproducts(
    show: Boolean,
    productViewModel: ProductViewModel,
    id: Int,
    title: String,
    showDialog: (Boolean) -> Unit
) {
    if (show) {
        Delete(
            deleteProduct = { deleteProduct(productViewModel, id.toString()) },
            product = title,
            setShowDialog = {
                showDialog(it)
            })
    }
}

@Composable
private fun ItemProduct(
    data: ProductModel,
    navController: NavHostController,
    productViewModel: ProductViewModel
) {
    val showDialog: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) }

    if (showDialog.value) {
        Deleteproducts(showDialog.value, productViewModel, data.id, data.title) {
            showDialog.value = it
        }
    }

    Card(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clickable { startInfo(navController, data.id.toString()) },
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(100.dp)
                    .fillMaxWidth(),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(data.images[0]).crossfade(true)
                    .build(),
                alignment = Alignment.Center,
                contentDescription = data.description,
                contentScale = ContentScale.FillHeight,
            )
            TextData(info = data.title, 15.sp)
            TextData(info = data.brand, 10.sp)
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (data.stock > 0) {
                    TextData(info = "$ ${data.price}", 10.sp)
                } else {
                    TextData(info = "No disponible", 8.sp)
                }
                TextData(info = " * ${data.rating}", 10.sp)
            }
            Spacer(modifier = Modifier.padding(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { }, modifier = Modifier.size(25.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "update"
                    )
                }
                IconButton(onClick = { showDialog.value = true }, modifier = Modifier.size(25.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "delete"
                    )
                }
            }
        }
    }
}

@Composable
fun TextData(info: String, size: TextUnit) {
    Text(
        fontSize = size,
        fontStyle = FontStyle.Normal,
        modifier = Modifier.padding(2.dp),
        text = info, softWrap = false,
        overflow = TextOverflow.Ellipsis
    )
}

private fun deleteProduct(productViewModel: ProductViewModel, id: String) {
    productViewModel.deleteProductId(id)
}

private fun startInfo(navController: NavHostController, id: String) {
    navController.navigate(Routes.InfoApp.route + "/${id}")
}
