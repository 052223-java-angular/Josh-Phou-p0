package com.revature.app.services;

import com.revature.app.daos.DepartmentDAO;
import com.revature.app.models.Department;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class DepartmentService {

    private final DepartmentDAO departmentDAO;

    // create method to retrieve departments
    public List<Department> findAll() {
        // todo implement
        return departmentDAO.findAll();
    }
}
