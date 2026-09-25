package com.example.api;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class TaskController {

	// In memory, so the list starts empty after every restart.
	private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
	private final AtomicLong nextId = new AtomicLong(1);

	@GetMapping("/tasks")
	public List<Task> list() {
		return tasks.values().stream().sorted((a, b) -> Long.compare(a.id(), b.id())).toList();
	}

	@GetMapping("/tasks/{id}")
	public Task get(@PathVariable long id) {
		Task task = tasks.get(id);
		if (task == null) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found");
		}
		return task;
	}

	@PostMapping("/tasks")
	@ResponseStatus(HttpStatus.CREATED)
	public Task create(@Valid @RequestBody NewTask body) {
		Task task = new Task(nextId.getAndIncrement(), body.title(), false);
		tasks.put(task.id(), task);
		return task;
	}

	// Shows what the JVM got from the container: useful when tuning memory.
	@GetMapping("/runtime")
	public Map<String, Object> runtime() {
		Runtime rt = Runtime.getRuntime();
		return Map.of(
				"java", Runtime.version().toString(),
				"processors", rt.availableProcessors(),
				"maxHeapMb", rt.maxMemory() / (1024 * 1024));
	}
}
