package com.example.inventorymanagement.client.sales.models;


import com.example.inventorymanagement.client.microservices.*;
import com.example.inventorymanagement.util.ClientCallback;
import com.example.inventorymanagement.util.exceptions.NotLoggedInException;
import com.example.inventorymanagement.util.exceptions.OutOfRoleException;
import com.example.inventorymanagement.util.exceptions.UserExistenceException;
import com.example.inventorymanagement.util.objects.User;

import java.rmi.registry.Registry;
import java.util.LinkedList;

public class ProfileManagementSalesModel {
    private ChangeUserRoleService changeUserRoleService;
    private FetchListOfUsersService fetchListOfUsersService;
    private RemoveUserService removeUserService;
    private Registry registry;
    private ClientCallback clientCallback;

    public ProfileManagementSalesModel(Registry registry, ClientCallback clientCallback) {
        this.registry = registry;
        this.clientCallback = clientCallback;
        this.changeUserRoleService = new ChangeUserRoleService();
        this.removeUserService = new RemoveUserService();
        this.fetchListOfUsersService = new FetchListOfUsersService();
    }

    public boolean changeUserRole(User user, String newRole) throws UserExistenceException, OutOfRoleException, NotLoggedInException {
        try {
            return changeUserRoleService.process(registry, clientCallback, user, newRole);
        } catch (RuntimeException e) {
            // Handle any runtime exceptions
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeUser(User user) throws UserExistenceException, OutOfRoleException, NotLoggedInException {
        try {
            return removeUserService.process(registry, clientCallback, user);
        } catch (RuntimeException e) {
            // Handle any runtime exceptions
            e.printStackTrace();
            return false;
        }
    }
    public LinkedList<User> fetchListOfUsers() throws NotLoggedInException, OutOfRoleException {
        try {
            return fetchListOfUsersService.process(clientCallback, registry);
        } catch (RuntimeException e) {
            // Handle any runtime exceptions
            e.printStackTrace();
            return null;
        }
    }
}