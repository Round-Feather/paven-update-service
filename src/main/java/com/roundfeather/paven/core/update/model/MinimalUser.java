package com.roundfeather.paven.core.update.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.roundfeather.persistence.utils.datastore.annotation.DatastoreEntity;
import com.roundfeather.persistence.utils.datastore.annotation.DatastoreKey;
import com.roundfeather.persistence.utils.datastore.annotation.KeyType;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@DatastoreEntity("users")
public class MinimalUser {
    @Getter(onMethod_ = {@JsonGetter("https://paven.com/userKey")})
    @DatastoreKey(keyType = KeyType.LONG)
    private Long userKey;
    @JsonIgnore
    private String deletedUserId;
    @Getter(onMethod_ = {@JsonGetter("https://paven.com/userId")})
    private String userId;
    @Getter(onMethod_ = {@JsonGetter("https://paven.com/email")})
    private String email;
    private String iss;
    private List<String> aud;

    @JsonGetter("https://paven.com/externalUserId")
    public String getExternalUserId() {
        return null;
    }
    public String getSub() {
        return "email|65a556812cb289d5ac669814";
    }
    public Long getIat() {
        return System.currentTimeMillis();
    }
    public Long getExp() {
        return System.currentTimeMillis() + 6000000L;
    }
    public String getScope() {
        return "openid profile email offline_access";
    }
    public String getAzp() {
        return "0kWUYiGPlYaYUlq6azl3W7zUdoHCUZUj";
    }
}
