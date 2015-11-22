package de.khiem.offsite.eventlog;

public interface SequenceGenerator {
	Sequence next(String type);
	Sequence current(String type);
}
