package io.jatoms;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import one.microstream.storage.embedded.types.EmbeddedStorage;
import one.microstream.storage.embedded.types.EmbeddedStorageManager;

/**
 * Minimal example to confuse Microstream?
 * First a ArrayList ist saved, then a ImmutableList
 */
public class ListExperiment {
    public static void main(String[] args) {
        final EmbeddedStorageManager storageManager = EmbeddedStorage.start();

        Data data = (Data)storageManager.root();
        if(data == null) {
            // Create a root instance
            data = new Data();
            storageManager.setRoot(data);
            storageManager.storeRoot();
        }
        // Would have expected to see here always 1, but if "List.of()" is used, then a strange object appears in the list after being loaded
        System.out.println(data.issues.size());
       
        data.update(List.of(new Issue()));
        storageManager.store(data);
        System.exit(0);
    }

    public static class Data {
        public List<Issue> issues = new ArrayList<>();

        public Data() {
            issues.add(new Issue());
        }
    
        public void update(List<Issue> issues) {
            this.issues = issues;
        }
    }

    public static class Issue {
        public String name = "Test";
        public LocalDateTime timestamp = LocalDateTime.now();
    }
}
