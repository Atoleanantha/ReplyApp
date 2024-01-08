package com.example.reply.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.reply.data.Email
import com.example.reply.data.MailboxType
import com.example.reply.ui.theme.ReplyTheme
import com.example.reply.ui.utils.ReplyContentType
import com.example.reply.ui.utils.ReplyNavigationType

@Composable
fun ReplyApp(
    windowSize: WindowWidthSizeClass,
    modifier:Modifier=Modifier,
){
    val contentType: ReplyContentType
    val navigationType: ReplyNavigationType
    val viewModel:ReplyViewModel = viewModel()
    val replyUiState=viewModel.uiState.collectAsState().value

    when (windowSize) {
        WindowWidthSizeClass.Compact -> {
            contentType=ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }

        WindowWidthSizeClass.Medium -> {
            contentType=ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.NAVIGATION_RAIL
        }

        WindowWidthSizeClass.Expanded -> {
            contentType=ReplyContentType.LIST_AND_DETAIL
            navigationType = ReplyNavigationType.PERMANENT_NAVIGATION_DRAWER
        }

        else -> {
            contentType=ReplyContentType.LIST_ONLY
            navigationType = ReplyNavigationType.BOTTOM_NAVIGATION
        }
    }

            ReplyHomeScreen(
                navigationType=navigationType,
                replyUiState = replyUiState,
                modifier=modifier,
                onTabPressed = { mailboxType :MailboxType->
                    viewModel.updateCurrentMailbox(mailboxType=mailboxType)
                    viewModel.resetHomeScreenStates()
                },
                onEmailCardPressed = { email: Email ->
                    viewModel.updateDetailsScreenStates(
                        email = email
                    )
                },
                onDetailScreenBackPressed = {
                    viewModel.resetHomeScreenStates()
                },)


}

@Preview(showBackground = true)
@Composable
fun ReplyAppPreview() {
    ReplyTheme {
        Surface {
            ReplyApp(
                windowSize = WindowWidthSizeClass.Compact,
            )
        }
    }
}