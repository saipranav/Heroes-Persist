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
package com.heroespersist.sports.implementation.game;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.heroespersist.sports.exception.SportsException;
import com.heroespersist.sports.interfaces.game.GameDao;
import com.heroespersist.sports.interfaces.game.GameService;
import com.heroespersist.sports.interfaces.utility.UtilityDao;
import com.heroespersist.sports.model.Game;
import com.heroespersist.sports.model.Player;
import com.heroespersist.sports.webservice.game.GameForm;

/**
 * @author Sai Pranav
 *
 */

@Component
@Transactional
public class GameServiceImpl implements GameService {

	@Autowired
	GameDao gameDao;
	
	@Autowired
	UtilityDao utilityDao;
	
	/**
	 * Add a game if it does not exists.
	 */
	public int addGame(GameForm gameForm){
		if(utilityDao.getGame(gameForm.getName(), gameForm.getCategory()) != null){
			throw new SportsException("This Game Already Exists");
		}
		Game game = new Game(gameForm.getName(),gameForm.getCategory(),gameForm.getMinNoPlayers(),gameForm.getMaxNoPlayers());
		return gameDao.addGame(game);
	}

	/**
	 * Modify game if it exists.
	 * Cannot modify Name and Category, delete and add new game.
	 */
	public int modifyGame(int id, GameForm gameForm){
		Game game = utilityDao.getGame(id);
		if(game == null){
			throw new SportsException("No such Game");
		}
		if(game.getName().equalsIgnoreCase(gameForm.getName()) && game.getCategory().equalsIgnoreCase(gameForm.getCategory())){
			game.setMinNoPlayers(gameForm.getMinNoPlayers());
			game.setMaxNoPlayers(gameForm.getMaxNoPlayers());
			return gameDao.modifyGame(game);
		}
		else{
			throw new SportsException("You cannot modify Name, Category");
		}
	}

	/**
	 * Delete game if exists.
	 */
	public int deleteGame(int id){
		Game game = utilityDao.getGame(id);
		if(game == null){
			throw new SportsException("No such Game");
		}
		return gameDao.deleteGame(game);
	}

	public List<Game> getGames(){
		return gameDao.getGames();
	}
	
	public List<Game> getGamesByPlayer(int employeeId){
		Player player = utilityDao.getPlayer(employeeId);
		if(player!=null){
			return gameDao.getGamesByPlayer(player);
		}
		else{
			throw new SportsException("Player does not exist");
		}
	}
	
}
