#MonthView

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-MonthView-brightgreen.svg?style=flat)](https://android-arsenal.com/details/1/1492)

MonthView is an android library to display month calendar within the app. 

<img src="https://raw.githubusercontent.com/genestream/MonthView/gh-pages/demo/month_view.gif" width=300 />

## How to use this library

you just add the following to build.gradle.

```
repositories {
    maven { url 'http://genestream.github.com/MonthView/repository' }
}

dependencies {
    compile 'jp.co.genestream:monthview:0.9.+'
}
```
**This information will change. We will use jcenter or maven central**

## Usage

First, you should call `setUp`.And you can set OnMonthChangeListener and OnDayClickListener.

```
        final MonthViewPager monthViewPager = (MonthViewPager) findViewById(R.id.month_view);
        // you should setup calendar instance.
        monthViewPager.setup(calendar, Calendar.MONDAY);
        
        // you can set OnMonthChangeListener.
        monthViewPager.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onChange(Calendar calendar) {
                ...
            }
        });
        
        // you can set OnDayClickListener.
        monthViewPager.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(Calendar calendar) {
                ...
            }
        });
```

## Lisence

```
Copyright 2015 Genestream

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
