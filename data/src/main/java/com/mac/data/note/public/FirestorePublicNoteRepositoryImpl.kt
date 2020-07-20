package com.mac.data.note.public

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.mac.data.awaitTaskCompletable
import com.mac.data.awaitTaskResult
import com.mac.data.datamodels.FirebaseNote
import com.mac.data.toFirebaseNote
import com.mac.data.toNote
import com.mac.domain.domainmodel.Note
import com.mac.domain.domainmodel.Result
import com.mac.domain.repository.IPublicNoteRepository


const val COLLECTION_PUBLIC = "public_notes"
object FirestoreRemoteNoteImpl : IPublicNoteRepository {
    override suspend fun getNotes(): Result<Exception, List<Note>> {
        val firestore = FirebaseFirestore.getInstance()

        var reference = firestore.collection(COLLECTION_PUBLIC)

        return try {
            val task = awaitTaskResult(reference.get())

            return resultToNoteList(task)
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override suspend fun getNote(id: String): Result<Exception, Note?> {
        val firestore = FirebaseFirestore.getInstance()

        var reference = firestore.collection(COLLECTION_PUBLIC)
                .document(id)

        return try {
            val task = awaitTaskResult(reference.get())

            Result.build {
                task.toObject(FirebaseNote::class.java)?.toNote
            }
        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override suspend fun deleteNote(note: Note): Result<Exception, Unit> {
        val firestore = FirebaseFirestore.getInstance()

        return try {
            awaitTaskCompletable(firestore.collection(COLLECTION_PUBLIC)
                    .document(note.creationDate)
                    .delete()
            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    override suspend fun updateNote(note: Note): Result<Exception, Unit> {
        val firestore = FirebaseFirestore.getInstance()

        return try {
            awaitTaskCompletable(firestore.collection(COLLECTION_PUBLIC)
                    .document(note.creationDate)
                    .set(note.toFirebaseNote)
            )

            Result.build { Unit }

        } catch (exception: Exception) {
            Result.build { throw exception }
        }
    }

    private fun resultToNoteList(result: QuerySnapshot?): Result<Exception, List<Note>> {
        val noteList = mutableListOf<Note>()

        result?.forEach { documentSnapshop ->
            noteList.add(documentSnapshop.toObject(FirebaseNote::class.java).toNote)
        }

        return Result.build {
            noteList
        }
    }
}