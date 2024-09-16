package com.roundfeather.paven.core.update.config;

import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithName;

import java.util.List;

@ConfigMapping(prefix = "userInfo")
public interface UserInfoConfig {

    @WithName("aud")
    List<String> aud();

    @WithName("iss")
    String iss();
}
