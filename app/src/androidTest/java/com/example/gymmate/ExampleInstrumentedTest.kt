package com.example.gymmate

import android.os.Environment
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.gymmate.data.exercisedata.Exercise
import com.example.gymmate.fake_repository.FakeDailyTrackRepository
import com.example.gymmate.fake_repository.FakeExerciseRepository
import com.example.gymmate.fake_repository.FakeUserEntityRepository
import com.example.gymmate.summarypage.SummaryPageViewModel
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.time.Instant
import java.time.format.DateTimeFormatter


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    val composeTestRule = createComposeRule()
    @Test
    fun downloadImageTest() {
        composeTestRule.setContent {
            val viewModel = SummaryPageViewModel(
                FakeExerciseRepository(),
                FakeUserEntityRepository(),
                FakeDailyTrackRepository()
            )

            val context = LocalContext.current
            val exerciseList = listOf(
                Exercise(1, "Monday", "Bench Press", "push",
                    "FW", "M", "test", "", ""),
                Exercise(2, "Tuesday", "Bent-Knee Medicine Ball Hip Rotation", "push",
                    "FW", "M", "test", "", ""),
            )
            val weight = 65.3f

            val t = DateTimeFormatter.ISO_INSTANT.format(Instant.now())
            val filename = "test_exercises_${t}.jpg".replace(":", "_")
            val ok = viewModel.saveExerciseImage(context, exerciseList, weight, filename)
            assertTrue(ok)
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(path, filename)
            Log.i("ExampleInstrumentedTest", "basicDownloadTest: ${file.absolutePath}")
            assertTrue(file.exists())
        }
    }

    @Test
    fun downloadImageTestIllegalFilename() {
        composeTestRule.setContent {
            val viewModel = SummaryPageViewModel(
                FakeExerciseRepository(),
                FakeUserEntityRepository(),
                FakeDailyTrackRepository()
            )

            val context = LocalContext.current
            val exerciseList = listOf(
                Exercise(1, "Monday", "Bench Press", "push",
                    "FW", "M", "test", "", ""),
                Exercise(2, "Tuesday", "Bent-Knee Medicine Ball Hip Rotation", "push",
                    "FW", "M", "test", "", ""),
            )
            val weight = 65.3f

            val t = DateTimeFormatter.ISO_INSTANT.format(Instant.now())

            // Illegal filename, because it contains a colon(:), which will be replaced by under score
            val filename = "test_exercises_${t}.jpg"
            val ok = viewModel.saveExerciseImage(context, exerciseList, weight, filename)
            assertTrue(ok)
            val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
            val file = File(path, filename)
            Log.i("ExampleInstrumentedTest", "basicDownloadTest: ${file.absolutePath}")
            assertTrue(file.exists())
        }
    }
}