/*	Heroes Persist
    Product which helps in organizing, broadcasting, celebrating events
    Copyright (C) 2014  Sai Pranav
    Email: rsaipranav92@gmail.com

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package com.lister.sports.implementation.feedback;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lister.sports.interfaces.feedback.FeedbackDao;
import com.lister.sports.interfaces.utility.UtilityDao;
import com.lister.sports.model.Feedback;

/**
 * @author Sai Pranav
 *
 */

@Repository
public class FeedbackDaoImpl implements FeedbackDao{

	@Autowired
	UtilityDao utilityDao;
	
	public int recordFeedback(Feedback feedback) {
		Session session = utilityDao.getSession();
		try {
			session.save(feedback);
		}catch(HibernateException e){
			e.printStackTrace();
			throw e;
		}
		return feedback.getId();
	}

}
