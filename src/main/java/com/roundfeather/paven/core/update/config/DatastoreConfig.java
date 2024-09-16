package com.roundfeather.paven.core.update.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

@ConfigMapping(prefix = "datastore")
public interface DatastoreConfig {

    @WithName("namespace")
    String namespace();
}
