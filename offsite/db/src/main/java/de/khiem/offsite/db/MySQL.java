package de.khiem.offsite.db;

import com.github.davidmoten.rx.jdbc.Database;

public class MySQL {
	private final String url;
	private final Database db;
	
	public MySQL(String url, String uname, String pwd){
		this.url=url;
		db= Database.builder().url(url)
				.username(uname)
				.password(pwd)
				.pool(2, 10).build();
	}
	
	public Database db(){
		return db;
	}
	
}
