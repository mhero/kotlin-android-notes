package com.mac.fire.common

import android.app.Activity
import android.content.Intent
import com.mac.fire.login.LoginActivity
import com.mac.fire.notedetail.NoteDetailActivity
import com.mac.fire.notelist.NoteListActivity

internal fun startListFeature(activity: Activity?) {
    activity?.startActivity(
            Intent(
                    activity,
                    NoteListActivity::class.java
            )
    ).also { activity?.finish() }
}

internal fun startNoteDetailFeatureWithExtras(
        activity: Activity?,
        noteId: String,
        isPrivate: Boolean
) {
    val intent = Intent(activity, NoteDetailActivity::class.java)
    intent.putExtra(STRING_EXTRA_NOTE_ID, noteId)
    intent.putExtra(BOOLEAN_EXTRA_IS_PRIVATE, isPrivate)
    activity?.startActivity(intent)
}

internal fun startLoginFeature(activity: Activity?) {
    val intent = Intent(activity, LoginActivity::class.java)
    activity?.startActivity(intent)
            .also { activity?.finish() }
}

