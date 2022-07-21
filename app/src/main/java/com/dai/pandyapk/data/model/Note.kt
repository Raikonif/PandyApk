package com.dai.pandyapk.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.Exclude
import java.util.*

data class Note (
    val title: String? = "",
    val description: String? = "",
    val imgUrl: String? = "",
    @Exclude @JvmField
    var favorite: Boolean? = false,
    val createdAt: Timestamp? = Timestamp.now(),
//    val id: String? = UUID.randomUUID().toString()
)
//
//"https://firebasestorage.googleapis.com/v0/b/pandyapk-31408.appspot.com/images/notes/EoZS5i1grRSCFamSqX6KuSEU4Kt2/Ovarian Mucinous Cystadenoma. Frozen section. HE 400X.jpg?alt=media&token=69bfa149-008d-4084-87dd-ce865a3f3e5f"
//gs://pandyapk-31408.appspot.com/images/notes/EoZS5i1grRSCFamSqX6KuSEU4Kt2/Ovarian Mucinous Cystadenoma. Frozen section. HE 400X.jpg
//gs://pandyapk-31408.appspot.com/images/notes/EoZS5i1grRSCFamSqX6KuSEU4Kt2/Ovarian Mucinous Cystadenoma. Frozen section. HE 400X.jpg/