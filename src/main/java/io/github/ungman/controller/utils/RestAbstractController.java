package io.github.ungman.controller.utils;

import io.github.ungman.domain.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RestAbstractController<T> {

    @GetMapping(produces = "application/json")
    public List<T> getList();

    @RequestMapping("{id}")
    @GetMapping(produces = "application/json")
    public T getOne(@PathVariable Long id);

    @PostMapping(produces = "application/json", consumes = "application/json")
    public T create(@RequestBody T obj);

    @PutMapping(produces = "application/json", consumes = "application/json")
    public T update(@RequestBody T obj);

    @DeleteMapping(produces = "application/json", consumes = "application/json")
    public void delete(@RequestBody T obj);
}
