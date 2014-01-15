package com.lister.sports.implementation.player;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lister.sports.exception.SportsException;
import com.lister.sports.interfaces.player.PlayerDao;
import com.lister.sports.interfaces.utility.UtilityDao;
import com.lister.sports.model.Game;
import com.lister.sports.model.Player;
import com.lister.sports.model.Team;

/**
 * @author sai_pranav
 *
 */

@Repository
public class PlayerDaoImpl implements PlayerDao {
	
	@Autowired
	UtilityDao utilityDao;

	public int addPlayer(Player player) {
		Session session = utilityDao.getSession();
		try {
			session.save(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}
	
	public int modifyPlayer(Player player) {
		Session session = utilityDao.getSession();
		try{
			session.update(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}

	public int modifyPlayerAddGame(Player player, Game game) {
		Session session = utilityDao.getSession();
		try{
			player.getGames().add(game);
			session.update(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}

	public int modifyPlayerDeleteGame(Player player, Game game) {
		Session session = utilityDao.getSession();
		try{
			player.getGames().remove(game);
			session.update(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}
	
	public void modifyPlayerReloadGames(Player player, List<Game> games){
		Session session = utilityDao.getSession();
		try{
			player.setGames(games);
			session.update(player);
		}catch(HibernateException e){
			throw e;
		}
	}

	public int deletePlayer(Player player) {
		Session session = utilityDao.getSession();
		try{
			session.delete(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}
	
	/*public int modifyPlayerFromAdmin(int employeeId, String employeeName, String employeeEmail, String games) throws SportsException {
		You need to rollback the transaction if something fails so all done in same method.
		Session session = utilityDao.getSession();
		Player player;
		try{
			player = utilityDao.getPlayer(employeeId);
			String[] gamesArray = null;
			if(games.contains(",")){
				gamesArray  = games.split(",");
			}else{
				gamesArray = new String[1];
				gamesArray[0] = games;
			}
			if(player==null){
				player = new Player();
				player.setEmployeeId(employeeId);
			}
			player.setEmployeeName(employeeName);
			player.setEmployeeEmail(employeeEmail);
			List<Game> playerGames = new ArrayList<Game>(gamesArray.length);
			for(String tempGame: gamesArray){
				Game game = utilityDao.getGame(tempGame.split("-")[0], tempGame.split("-")[1]);
				if(game==null){
					throw new SportsException("No such game:"+tempGame);
				}
				playerGames.add(game);
			}
			player.setGames(playerGames);
			session.save(player);
		}catch(HibernateException e){
			throw e;
		}
		return player.getEmployeeId();
	}*/

	public List<Player> getPlayers() {
		Session session = utilityDao.getSession();
		List<Player> playersList = session.createCriteria(Player.class).list();
		return playersList;
	}

	public List<Player> getPlayersByGame(Game game) {
		Session session = utilityDao.getSession();
		List<Player> playersList = getPlayers();
		for(int i=0;i<playersList.size();i++){
			boolean checkGameFlag = true;
			List<Game> playerGames = playersList.get(i).getGames();
			for(Game checkGame: playerGames){
				if(checkGame.getId() == game.getId()){
					checkGameFlag = false;
				}
			}
			if(checkGameFlag==true){
				playersList.remove(i);
				i--;
			}
		}
		return playersList;
	}

	public List<Player> getPlayersByTeam(Team team) {
		List<Player> players = team.getPlayers();
		players.size();
		return players;
	}

}