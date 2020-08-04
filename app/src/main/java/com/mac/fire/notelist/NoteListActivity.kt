package com.mac.fire.notelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mac.fire.R
import com.mac.fire.common.attachFragment
import com.mac.fire.notelist.buildlogic.NoteListInjector

private const val VIEW = "NOTE_LIST"


class NoteListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_list)
        //A container basically just builds things and sets the feature in motion
        val view = this.supportFragmentManager.findFragmentByTag(VIEW)
                ?: NoteListView.newInstance()

        attachFragment(supportFragmentManager, R.id.root_activity_list, view, VIEW)

        NoteListInjector(application)
                .buildNoteListLogic(view as NoteListView)

    }
}
