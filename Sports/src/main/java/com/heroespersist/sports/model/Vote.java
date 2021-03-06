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
package com.heroespersist.sports.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;

/**
 * @author Sai Pranav
 *
 */
@Entity
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Table(name="vote")
public class Vote {

	@Id
	@GenericGenerator(name="vote_seq_gen" , strategy="increment")
	@GeneratedValue(generator="vote_seq_gen")
	@Column(name = "id")
	private int id;
	
	@Column(name = "ip_address")
	private String ipAddress;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinTable(name="vote_artifact",joinColumns={@JoinColumn(name="vote_id")},inverseJoinColumns={@JoinColumn(name="artifact_id")})
	private List<Artifact> artifacts = new ArrayList<Artifact>();
	
	public Vote(){}
	
	public Vote(String ipAddress){
		this.ipAddress = ipAddress;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public List<Artifact> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<Artifact> artifacts) {
		this.artifacts = artifacts;
	}
	
}
