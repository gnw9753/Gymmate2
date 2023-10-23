package com.example.gymmate.summarypage

import com.example.gymmate.data.exercisedata.ExerciseDay
import com.example.gymmate.data.userdata.UserInstance
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.ChronoUnit
import java.util.stream.Collectors
import java.util.stream.Stream


class CalendarDataSource {

    val today: LocalDate
        get() {
            return LocalDate.now()
        }

    fun getData(startDate: LocalDate = today, lastSelectedDate: LocalDate): CalendarUiModel {
        val firstDayOfWeek = startDate.with(DayOfWeek.MONDAY)
        val endDayOfWeek = firstDayOfWeek.plusDays(15)
        val visibleDates = getDatesBetween(firstDayOfWeek, endDayOfWeek)
        return toUiModel(visibleDates, lastSelectedDate)
    }

    private fun getDatesBetween(startDate: LocalDate, endDate: LocalDate): List<LocalDate> {
        val numOfDays = ChronoUnit.DAYS.between(startDate, endDate)
        return Stream.iterate(startDate) { date ->
            date.plusDays(/* daysToAdd = */ 1)
        }
            .limit(numOfDays)
            .collect(Collectors.toList())
    }

    private fun toUiModel(
        dateList: List<LocalDate>,
        lastSelectedDate: LocalDate
    ): CalendarUiModel {
        var exerciseDayList= UserInstance.currentUser?.exerciseSchedule

        //combine
        return CalendarUiModel(
            selectedDate = toItemUiModel(lastSelectedDate, true,false),
            visibleDates = dateList.map {
                var hasExercise:Boolean = false
                if (exerciseDayList != null) {
                    for(item in exerciseDayList)
                    {
                        if(it.dayOfWeek.value==toIndexofWeek(item.day))
                        {
                            if(item.exerciseList!=null && item.exerciseList.size>0) {

                                hasExercise = true
                                break
                            }
                        }
                    }
                }
                toItemUiModel(it, it.isEqual(lastSelectedDate),hasExercise)
            },
        )
    }

    fun toIndexofWeek(day:String):Int
    {
        var index=0
        when(day)
        {
            "Monday"->index= 1
            "Tuesday"->index=2
            "Wednesday"->index=3
            "Thursday"->index=4
            "Friday"->index=5
            "Saturday"->index=6
            "Sunday"->index=7
        }
        return index
    }

    private fun toItemUiModel(date: LocalDate, isSelectedDate: Boolean,hasExercise:Boolean) = CalendarUiModel.Date(
        isSelected = isSelectedDate,
        isToday = date.isEqual(today),
        date = date,
        hasExercise = hasExercise
    )
}