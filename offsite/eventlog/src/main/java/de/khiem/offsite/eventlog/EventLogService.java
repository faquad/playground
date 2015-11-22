package de.khiem.offsite.eventlog;

import java.util.List;

import rx.Observable;

public interface EventLogService {
	Observable<Event> list(String type, long offset, int size);
	Observable<Long>  insert(String type, Event event);
	Observable<Long> insert(String type, List<Event> events);
        Observable<Long> last(String type);
}
