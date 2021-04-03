package com.ossovita.contactappmvvm.db;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.ContactsContract;

import androidx.lifecycle.LiveData;
import androidx.loader.content.AsyncTaskLoader;

import com.ossovita.contactappmvvm.model.Contact;

import java.util.List;

public class ContactRepository {
    private ContactDao contactDao;
    private LiveData<List<Contact>> allContacts;

    public ContactRepository(Application application) {
        ContactDatabase database = ContactDatabase.getInstance(application);
        contactDao = database.contactDao();
        allContacts=contactDao.getAllContacts();
    }

    public void insert(Contact contact){new InsertContactAsyncTask(contactDao).execute(contact);}
    public void update(Contact contact){new UpdateContactAsyncTask(contactDao).execute(contact);}
    public void delete(Contact contact){new DeleteContactAsyncTask(contactDao).execute(contact);}
    public void deleteAllContacts(){new DeleteAllContactAsyncTask(contactDao).execute();}
    public LiveData<List<Contact>> getAllContacts(){return allContacts;}


    private static class InsertContactAsyncTask extends AsyncTask<Contact,Void,Void> {
        private ContactDao contactDao;

        public InsertContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        //parametreden gelen Contact objesini database'e yazıyoruz
        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.insert(contacts[0]);
            return null;
        }
    }

    private static class UpdateContactAsyncTask extends AsyncTask<Contact,Void,Void> {
        private ContactDao contactDao;

        public UpdateContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }


        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.update(contacts[0]);
            return null;
        }
    }

    private static class DeleteContactAsyncTask extends AsyncTask<Contact,Void,Void> {
        private ContactDao contactDao;

        public DeleteContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Contact... contacts) {
            contactDao.delete(contacts[0]);
            return null;
        }
    }

    private static class DeleteAllContactAsyncTask extends AsyncTask<Void,Void,Void> {
        ContactDao contactDao;

        public DeleteAllContactAsyncTask(ContactDao contactDao) {
            this.contactDao = contactDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            contactDao.deleteAllContacts();
            return null;
        }
    }


}
