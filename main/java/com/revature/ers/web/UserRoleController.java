package com.revature.ers.web;

import com.revature.ers.beans.UserRole;
import com.revature.ers.middle.BusinessDelegate;

import java.util.List;

public class UserRoleController {
    public List<UserRole> getRoles(){
        return new BusinessDelegate().getRoles();
    }
}
