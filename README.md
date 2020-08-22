# daypicker
Simple Android View for picking multiple day in a week

## Installation
Project level `build.gradle`
```gradle
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' } // << Add this line
    }
}
```

Module level `build.gradle`
```gradle
dependencies {
    ...
    implementation "com.github.rido-ramadan:daypicker:0.1.1" // << Add this line
    ...
}
```

## Features

*   Easily customizable

    ```xml
    <com.edgardrake.libs.views.daypicker.WeeklyDayPicker
        android:id="@+id/weeklyDayPicker"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="center_horizontal"
        android:layout_marginTop="16dp"
        app:locale="id"
        app:dayFormat="shorthand"
        app:days="all"
        app:radius="32dp"
        app:mode="showAll"
        app:iconBackground="@drawable/selector_circle_blue"
        app:captionColor="@color/caption_color_blue" />
    ```

*   Easy Extract Data from `WeeklyDayPicker`

    ```kotlin
    // activeDays = [Day.SUNDAY, Day.SATURDAY]
    val activeDays: Collection<Day> = weeklyDayPicker.selectedDays
    
    // shortnames = ["Sun", "Sat"]
    val shortnames: Collection<String> = weeklyDayPicker.selectedDaysName

    // fullnames = ["Sonntag", "Samstag"]
    weeklyDayPicker.locale = Locale("de")
    val fullnames: Collection<String> = weeklyDayPicker.selectedDaysFullName
    ```

*   Support Localization

    ![i18n](./docs/assets/i18n.png)

*   Demo
    
    <img src="./docs/assets/demo.gif" alt="demo" width="300">
