package com.quessr.deepfineandroid.feature.todo

import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ExperimentalWearMaterialApi
import androidx.wear.compose.material.FractionalThreshold
import androidx.wear.compose.material.rememberSwipeableState
import androidx.wear.compose.material.swipeable
import com.quessr.deepfineandroid.core.ui.theme.bgGrey
import com.quessr.deepfineandroid.core.ui.theme.checked
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    vm: TodoViewModel = hiltViewModel(),
    onGoHistory: () -> Unit
) {
    val state by vm.state.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("DEEP.FINE TODO") },
                actions = {
                    IconButton(onClick = onGoHistory) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomInputBar(
                input = state.input,
                isAddEnabled = state.isAddEnabled,
                onInputChange = { vm.handle(TodoIntent.InputChanged(it)) },
                onAdd = { vm.handle(TodoIntent.AddClicked) }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(bgGrey),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            if (state.list.isEmpty()) {
                item {
                    Box(
                        Modifier
                            .fillParentMaxSize()
                            .padding(24.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("할 일을 추가해 보세요.")
                    }
                }
            } else {
                items(
                    items = state.list,
                    key = { it.id }
                ) { todo ->
                    TodoRow(
                        content = todo.content,
                        onDelete = { vm.handle(TodoIntent.Delete(todo.id)) },
                        onComplete = { vm.handle(TodoIntent.Complete(todo.id)) }
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomInputBar(
    input: String,
    isAddEnabled: Boolean,
    onInputChange: (String) -> Unit,
    onAdd: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .navigationBarsPadding()
            .imePadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = input,
            onValueChange = onInputChange,
            placeholder = { Text("할 일을 입력해주세요.") },
            modifier = Modifier.weight(1f)
        )
        Spacer(Modifier.width(8.dp))
        Button(onClick = onAdd, enabled = isAddEnabled) {
            Text("추가")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalWearMaterialApi::class)
@Composable
private fun TodoRow(
    content: String,
    onDelete: () -> Unit,
    onComplete: () -> Unit
) {
    val swipeableState = rememberSwipeableState(initialValue = 0)
    val coroutineScope = rememberCoroutineScope()

    val actionWidth = 80.dp
    val actionWidthPx = with(LocalDensity.current) { actionWidth.toPx() }
    val anchors = mapOf(0f to 0, -actionWidthPx to 1)

    Box(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .swipeable(
                state = swipeableState,
                orientation = Orientation.Horizontal,
                anchors = anchors,
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                velocityThreshold = 1000.dp
            )
    ) {
        Row(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Red)
                .padding(start = 13.dp, end = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    onDelete()
                    coroutineScope.launch {
                        swipeableState.animateTo(0, tween(600, 0))
                    }
                }, modifier = Modifier
                    .width(actionWidth)
                    .fillMaxHeight(),
                contentPadding = PaddingValues(0.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "삭제",
                        color = Color.White
                    )
                }
            }
        }

        val offsetX = swipeableState.offset.value.coerceAtMost(0f)

        Row(
            modifier = Modifier
                .offset { IntOffset(offsetX.roundToInt(), 0) }
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onComplete) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "완료",
                    tint = checked
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(text = content)
        }
    }
}