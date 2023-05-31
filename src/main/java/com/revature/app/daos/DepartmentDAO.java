package com.revature.app.daos;

import com.revature.app.models.Department;

import java.util.List;
import java.util.Optional;

public class DepartmentDAO implements ICrudDAO<Department> {

    @Override
    public void save(Department department) {
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public void update(Department department) {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Optional<Department> findById(String id) {
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Department> findAll() {
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

}
