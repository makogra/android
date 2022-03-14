package com.mako.roomwordsample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

class WordRepository {

    private WordDao mWordDao;
    private LiveData<List<Word>> mAllWords;

    // In order to unit test the WordRepository, you have to remove Application dependency
    // See the BasicSample in the android-architecture-components repository at
    // https://github.com/googlesamples
    WordRepository(Application application){
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAlphabetizedWords();
    }

    LiveData<List<Word>> getAllWords(){
        return mAllWords;
    }

    void insert(Word word){
        WordRoomDatabase.databaseWriteExecutor.execute(() -> {
            mWordDao.insert(word);
        });
    }
}
