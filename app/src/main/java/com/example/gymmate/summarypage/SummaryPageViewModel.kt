package com.example.gymmate.summarypage

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.graphics.createBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymmate.R
import com.example.gymmate.data.CSVReader
import com.example.gymmate.data.GenerateWorkout
import com.example.gymmate.data.dailytrack.DailyTrack
import com.example.gymmate.data.dailytrack.DailyTrackRepository
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.data.exercisedata.ExerciseRepository
import com.example.gymmate.data.userdata.UserEntityRepository
import com.example.gymmate.data.userdata.UserInstance
import com.example.gymmate.data.userdata.UserInstance.currentUser
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.OutputStream
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class Recipe (
    var recipe: String = "",
    var calories: Int = 0
)

@RequiresApi(Build.VERSION_CODES.O)
class SummaryPageViewModel(
    val exerciseRepository: ExerciseRepository,
    val userEntityRepository: UserEntityRepository,
    val dailyTrackRepository: DailyTrackRepository
) : ViewModel() {
    val workouts = arrayOf("yoga", "Pilates", "Push-up", "Sit-up", "Aerobics", "Rope skipping")
    var workoutItemSelected by mutableStateOf(workouts[0])
    var pickedTime: LocalTime by mutableStateOf(LocalTime.NOON)
    val formattedTime: String by derivedStateOf {
        DateTimeFormatter
            .ofPattern("hh:mm a")
            .format(pickedTime)
    }

    var workoutDateSelected by mutableStateOf(GenerateWorkout.dayString[0])

    var name by mutableStateOf(currentUser!!.name)
    var email by mutableStateOf(currentUser!!.email)
    var weight by mutableFloatStateOf(currentUser!!.weight)
    var height by mutableFloatStateOf(currentUser!!.height)

    fun addExercise() {
        val currentUser = UserInstance.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                val user =
                    userEntityRepository.getUserByEmail(currentUser.email).firstOrNull()!!
                val exercise = Exercise(
                    user.id,
                    workoutDateSelected,
                    "",
                    workoutItemSelected,
                    "", "", "", "", ""
                )
                exerciseRepository.insertExercise(exercise)
                for (exerciseDay in currentUser.exercise_schedule) {
                    if (exerciseDay.day.equals(workoutDateSelected, ignoreCase = true)) {
                        exerciseDay.exerciseList += listOf(exercise)
                    }
                }
            }

        } else {
            Log.e("SummaryPageViewModel", "User is null")
        }

    }

    fun changeUserInfo(): Boolean {
        val currentUser = UserInstance.currentUser
        if (currentUser != null) {
            viewModelScope.launch {
                val user =
                    userEntityRepository.getUserByEmail(currentUser.email).firstOrNull()!!
                user.name = name
                user.email = email
                user.weight = weight
                user.height = height
                userEntityRepository.updateUser(user)

                currentUser.name = name
                currentUser.email = email
                currentUser.weight = weight
                currentUser.height = height
            }
            viewModelScope.launch {
                val dt = dailyTrackRepository.getTodayTrack()!!
                dt.weight = weight
                dailyTrackRepository.update(dt)
            }
            return true

        } else {
            Log.e("SummaryPageViewModel", "User is null")
            return false
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun setDownload(context: Context) {
        viewModelScope.launch {
            val user = userEntityRepository.getUserByEmail(currentUser!!.email).firstOrNull()!!
            val list = exerciseRepository.getAllExerciseById(user.id).firstOrNull()!!

            if (saveExerciseImage(context, list, user.weight,
                    "${user.name}_exercises.jpg")) {
                Toast.makeText(context, "save image ok", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "save image fail", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    fun saveExerciseImage(
        context: Context,
        list: List<Exercise>,
        weight: Float,
        filename: String = "exercises.jpg"
    ): Boolean {
        // calculate the size of the bitmap
        val width = 2500
        val height = 5000

        val bitmap = createBitmap(width, height)

        // paint the background
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.WHITE
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)

        paint.color = Color.BLACK
        paint.textSize = 75f

        val xPos = 100f
        var yPos = 100f

        val mapDayExercise = mutableMapOf<String, MutableList<Exercise>>()
        for (exercise in list) {
            if (mapDayExercise.containsKey(exercise.day)) {
                mapDayExercise[exercise.day]!!.add(exercise)
            } else {
                mapDayExercise[exercise.day] = mutableListOf(exercise)
            }
        }

        val bounds = Rect()
        var maxSizeText = ""
        for (exercise in list) {
            val exerciseText = "${exercise.muscleGroup}: ${exercise.exerciseName}"
            if (exerciseText.length > maxSizeText.length) {
                maxSizeText = exerciseText
            }
        }
        paint.getTextBounds(maxSizeText, 0, maxSizeText.length, bounds)

        for (entry in mapDayExercise) {
            val day = entry.key
            canvas.drawText(day, xPos, yPos, paint)
            paint.getTextBounds(day, 0, entry.key.length, bounds)
            yPos += bounds.height() + 10

            for (exercise in entry.value) {
                val exerciseText = "${exercise.muscleGroup}: ${exercise.exerciseName}"
                canvas.drawText(exerciseText, xPos, yPos, paint)
                paint.getTextBounds(exerciseText, 0, exerciseText.length, bounds)
                yPos += bounds.height() + 10
            }

            val split = "============================"
            canvas.drawText(split, xPos, yPos, paint)
            yPos += bounds.height() + 10
        }

        // draw the weight
        canvas.drawText("weight", xPos, yPos, paint)
        yPos += bounds.height() + 10

        canvas.drawText(
            weight.toString() + "kg",
            xPos, yPos,
            paint
        )

        bitmap.height = yPos.toInt() + 70
//        bitmap.width = bounds.width() + 20
//        canvas.drawBitmap(bitmap, Rect(0, 0, bitmap.width, bitmap.height),
//            Rect(0, 0, bounds.width() + 20,  yPos.toInt() + 70), paint)

        try {
            return saveQUp(bitmap, context, filename, 80)
        } catch (ex: Exception) {
            ex.printStackTrace()
        }

        return false
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Throws(Exception::class)
    fun saveQUp(image: Bitmap, context: Context, fileName: String?, quality: Int): Boolean {
        // 文件夹路径
        val imageSaveFilePath = Environment.DIRECTORY_PICTURES
//        imageSaveFilePath.mkdirs()

        // 文件名字
        val contentValues = ContentValues()
        contentValues.put(MediaStore.MediaColumns.TITLE, fileName)
        contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
        contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        contentValues.put(MediaStore.MediaColumns.DATE_TAKEN, fileName)
        //该媒体项在存储设备中的相对路径，该媒体项将在其中保留
        contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, imageSaveFilePath)

        var uri: Uri? = null
        var outputStream: OutputStream? = null
        val localContentResolver = context.contentResolver
        try {
            uri = localContentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            outputStream = localContentResolver.openOutputStream(uri!!)
            // Bitmap图片保存
            // 1、宽高比例压缩
            // 2、压缩参数
            image.compress(Bitmap.CompressFormat.JPEG, quality, outputStream!!)
            outputStream.flush()
            outputStream.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            if (uri != null) {
                localContentResolver.delete(uri, null, null)
            }
        } finally {
            image.recycle()
            try {
                outputStream?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return false
    }

    private fun getRandomRecipe(context: Context): Recipe {
        val inputStream = context.resources.openRawResource(R.raw.recipes)
        val csvReader = CSVReader(inputStream)
        val list = csvReader.read()
        // randomly choose a row
        val rowIndex = (0..list.size-1).random()
        val row = list[rowIndex]
        val recipe = row[1]
        val calories = row[2].trim().toInt()
        return Recipe(recipe, calories)
    }

    suspend fun getTodayRecipe(context: Context): DailyTrack {
        var track = dailyTrackRepository.getTodayTrack()
        if (track == null) {
            val recipe = getRandomRecipe(context)
            dailyTrackRepository.insertDailyTrack(weight, recipe)
            val dailyAllTracks = dailyTrackRepository.getDailyAllTracks()
            Log.i("SummaryPageViewModel", "getTodayRecipe: $dailyAllTracks")
            track = dailyTrackRepository.getTodayTrack()
        }
        return track!!
    }

    suspend fun getAllDailyTracks(): List<DailyTrack> {
        return dailyTrackRepository.getDailyAllTracks()
    }
}