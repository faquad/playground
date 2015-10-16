package de.khiem.offsite.eventLog.impl;

import de.khiem.offsite.eventLog.Sequence;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import de.khiem.offsite.eventLog.SequenceGenerator;

public class LocalEventSequence implements SequenceGenerator{
	final Map<String, AtomicLong> localDB;
	
	public LocalEventSequence() {
		localDB = new  ConcurrentHashMap();
	}
	
	@Override
	public Sequence next(String type) {
		AtomicLong l = getOrCreate(type);
		return new Sequence(type, l.incrementAndGet());
	}

	@Override
	public Sequence current(String type) {
		AtomicLong l = getOrCreate(type);
                return new Sequence(type, l.get());
	}

	private AtomicLong getOrCreate(String type){
		String k = type.toLowerCase();
		AtomicLong l = localDB.get(k);
		if (l==null)
			localDB.put(k, l=new AtomicLong(0l));
		return l;
	}
	
}
