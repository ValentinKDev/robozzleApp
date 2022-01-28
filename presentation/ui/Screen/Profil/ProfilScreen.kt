package com.mobilegame.robozzle.presentation.ui.Screen.Profil

//@InternalCoroutinesApi
//@Composable
//fun ProfilScreen(navController: NavController, userVM: UserViewModel) {
//    infoLog("", "launch profil Screen")
//
////    val noUser: Boolean by userVM.noUser.collectAsState(true)
//    val noUser: Boolean = userVM.noUser.value
//
//    infoLog("noUser", noUser.toString())
//    if (noUser) {
//        infoLog("navigate", "register/login")
////        navController.navigate(Screens.RegisterLoginScreen.route)
//        RegisterLoginScreen(userVM = userVM)
//    }
//    else {
//        infoLog("navigate", "user infos")
//        navController.navigate(Screens.UserInfoScreen.route)
//    }
//
////    val selectedTab by userVM.profilVM.tabSeclected.collectAsState()
////    val selectedTab by userVM.profilVM.tabSeclected.observeAsState(initial = 1)
//}


//@InternalCoroutinesApi
//@Composable
//fun DisplayProfilTabsHeader(userVM: UserViewModel) {
//    Row(
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(70.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .weight(1.0f)
//                .background(if (userVM.profilVM.IsRegisterTabSelected()) Color.Gray else Color.Transparent)
////                    .height(70.dp)
//                .clickable {
//                    userVM.profilVM.SelectLoginTab()
//                }
//        ) {
//            Text(text = "Login ${userVM.profilVM.tabSeclected.value}", Modifier.align(Alignment.Center))
//        }
//
//        Box(
//            modifier = Modifier
//                .weight(1.0f)
//                .background(if (userVM.profilVM.IsLoginTabSelected()) Color.Gray else Color.Transparent)
//                .clickable {
//                    userVM.profilVM.SelectRegisterTab()
//                }
//        ) {
//            Text(text = "Register", Modifier.align(Alignment.Center))
//        }
//    }
//}
//
