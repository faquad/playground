package de.khiem.offsite.eventLog.impl;

import java.util.List;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

import de.khiem.offsite.eventLog.Event;
import de.khiem.offsite.eventLog.EventLogService;
import de.khiem.offsite.eventLog.Sequence;
import de.khiem.offsite.eventLog.SequenceGenerator;
import rx.Observable;

public class LocalEventLogService implements EventLogService{
	final SequenceGenerator sequence;
        final Table<String, Long, Event> db;
        
	public LocalEventLogService() {
		sequence=new LocalEventSequence();
		db = HashBasedTable.create();
	}
	
	@Override
	public Observable<Event> list(final String type, final long offset, final int size) {
		return Observable.<Event>create(s -> {
			long n = offset;
			int _s=0;
                        System.out.println("getting " +type + "," + n);
			Event e = db.get(type, n);
			while (!s.isUnsubscribed() && _s<size  &&  e!=null){
				s.onNext(e);
                                _s++;
                                n++;
                                e = db.get(type, n);
			}
                        s.onCompleted();
		});	
	}

	@Override
	public Observable<Long> insert(final String type, final Event event) {
		Observable<Sequence> row = Observable.just(sequence.next(type));//  <Long>create(f -> { sequence.current(type);}).subscribeOn(Schedulers.io());
		return row.flatMap((Sequence t) -> {
                    db.put(type, t.getCurrent(), event);
                    System.out.println("table put:" + type + ",id:" + t);
                    return Observable.just(t.getCurrent());
                });	
	}

	@Override
	public Observable<Long> insert(String type, List<Event> events) {
            return Observable.create(s -> {
                for (Event e: events){
                    Long ins = insert(type, e).toBlocking().single();
                    s.onNext(ins);
                }
                s.onCompleted();
            });	
	}

    @Override
    public Observable<Long> last(String type) {
        return Observable.just(sequence.current(type).getCurrent());
    }
        
        
}
