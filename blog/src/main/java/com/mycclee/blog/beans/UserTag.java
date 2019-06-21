package com.mycclee.blog.beans;

import java.util.Objects;

public class UserTag {
    private User user;
    private String tags;

    public UserTag() {
    }

    public UserTag(User user, String tags) {
        this.user = user;
        this.tags = tags;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserTag userTag = (UserTag) o;
        return Objects.equals(user, userTag.user) &&
                Objects.equals(tags, userTag.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, tags);
    }

    @Override
    public String toString() {
        return "UserTag{" +
                "user=" + user +
                ", tags='" + tags + '\'' +
                '}';
    }
}
