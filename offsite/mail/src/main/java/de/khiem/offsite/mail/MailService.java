package de.khiem.offsite.mail;

import java.util.List;

public interface MailService {
	void send(List<Mail> mails);
}
