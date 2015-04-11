package me.tatarka.typedbundle;

import android.annotation.TargetApi;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

@TargetApi(11)
public class TypedLoaderManager {
    private LoaderManager loaderManager;

    public TypedLoaderManager(LoaderManager loaderManager) {
        this.loaderManager = loaderManager;
    }

    public void destroyLoader(Id<Loader<?>> id) {
        loaderManager.destroyLoader(id.id);
    }

    @SuppressWarnings("unchecked")
    public <T, L extends Loader<T>> L getLoader(Id<L> id) {
        return (L) loaderManager.<T>getLoader(id.id);
    }

    @SuppressWarnings("unchecked")
    public <T, L extends Loader<T>> L initLoader(Id<L> id, TypedBundle args, LoaderCallbacks<T> callback) {
        return (L) loaderManager.initLoader(id.id, args.getBundle(), new WrapLoaderCallbacks<>(callback));
    }

    @SuppressWarnings("unchecked")
    public <T, L extends Loader<T>> L restartLoader(Id<L> id, TypedBundle args, LoaderCallbacks<T> callback) {
        return (L) loaderManager.restartLoader(id.id, args.getBundle(), new WrapLoaderCallbacks<>(callback));
    }

    public LoaderManager getLoaderManager() {
        return loaderManager;
    }

    public interface LoaderCallbacks<T> {
        Loader<T> onCreateLoader(Id<Loader<T>> id, TypedBundle args);

        void onLoadFinished(Loader<T> loader, T data);

        void onLoaderReset(Loader<T> loader);
    }
    
    private static class WrapLoaderCallbacks<T> implements LoaderManager.LoaderCallbacks<T> {
        private LoaderCallbacks<T> callback;

        private WrapLoaderCallbacks(LoaderCallbacks<T> callback) {
            this.callback = callback;
        }

        @Override
        public Loader<T> onCreateLoader(int id, Bundle args) {
            return callback.onCreateLoader(new Id<Loader<T>>(id), new TypedBundle(args));
        }

        @Override
        public void onLoadFinished(Loader<T> loader, T data) {
            callback.onLoadFinished(loader, data);
        }

        @Override
        public void onLoaderReset(Loader<T> loader) {
            callback.onLoaderReset(loader);
        }
    }
}
