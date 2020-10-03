package com.mersiades.awcdata.services;

import com.mersiades.awcdata.models.User;

public interface UserService extends CrudService<User, Long> {
    User findByDiscourseID(String discourseID);
}
