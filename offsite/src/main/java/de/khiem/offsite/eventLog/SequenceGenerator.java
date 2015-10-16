package de.khiem.offsite.eventLog;

public interface SequenceGenerator {
	Sequence next(String type);
	Sequence current(String type);
}
