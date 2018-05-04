package kosta.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import kosta.model.dto.Electronics;
import kosta.util.DBUtil;

public class ElectronicsDAOImpl implements ElectronicsDAO {

	@Override
	public List<Electronics> selectAll() throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Electronics> list = new ArrayList<>();
		
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("select * from Electronics");
			rs = ps.executeQuery();
			
			while(rs.next()) {
				Electronics electronics = new Electronics(rs.getString(1), rs.getString(2), rs.getInt(3),
						rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), 
						rs.getInt(9));
					
				list.add(electronics);
			}
		} finally {
			DBUtil.dbClose(con, ps, rs);
		}
		return list;
	}

	
	@Override
	public Electronics selectByModelNum(String modelNum) throws SQLException {
		Connection con= null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Electronics electronics = null;
		try {
				con = DBUtil.getConnection();
				ps = con.prepareStatement("select * from Electronics where model_num=?");
				ps.setString(1, modelNum);
				rs = ps.executeQuery();
				
				if(rs.next()) {
					electronics = new Electronics(rs.getString(1), rs.getString(2), rs.getInt(3),
							rs.getString(4), rs.getString(5), rs.getString(6), rs.getInt(7), rs.getString(8), 
							rs.getInt(9));
				}
		} catch(SQLException ex) {ex.printStackTrace();}
		
		return electronics;
	}

	@Override
	public int updateByreadNum(String modelNum) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result=0;
		
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement("update Electronics set readnum=readnum+1 where model_num=?");
			ps.setString(1, modelNum);
			result = ps.executeUpdate();
			
		} catch(Exception ex) { ex.printStackTrace(); }
		
		return result;
	}

	@Override
	public int insert(Electronics electronics) throws SQLException {
		Connection con = null;
		PreparedStatement ps = null;
		int result = 0;
		
		try {
				con = DBUtil.getConnection();
				ps = con.prepareStatement("insert into Electronics values(?,?,?,?,?,sysdate,0,?,?)");
				ps.setString(1, electronics.getModelNum());
				ps.setString(2, electronics.getModelName());
				ps.setInt(3, electronics.getPrice());
				ps.setString(4, electronics.getDescription());
				ps.setString(5, electronics.getPassword());
				ps.setString(6, electronics.getfName());
				ps.setInt(7, electronics.getfSize());
				
				result = ps.executeUpdate();
		}  finally {
			DBUtil.dbClose(con, ps);
		}
		return result;
	}

	@Override
	public int delete(String modelNum, String password) throws SQLException {
		Connection con= null;
		PreparedStatement ps = null;
		int result= 0;
		try {	
			con = DBUtil.getConnection();
			ps = con.prepareStatement("delete from Electronics where model_num=? and password=?");
			ps.setString(1, modelNum);
			ps.setString(2, password);
			result = ps.executeUpdate();
			
		} finally {
			DBUtil.dbClose(con, ps);
		}
		
		return result;
	}

	@Override
	public int update(Electronics electronics) throws SQLException {
		Connection con= null;
		PreparedStatement ps = null;
		int result= 0;
	try {	
			con = DBUtil.getConnection();
			ps = con.prepareStatement("update Electronics set model_name=?, price=?, description=?"
					+ "where model_num=? and password=?" );
			ps.setString(1, electronics.getModelName());
			ps.setInt(2, electronics.getPrice());
			ps.setString(3, electronics.getDescription());
			ps.setString(4, electronics.getModelNum());
			ps.setString(5, electronics.getPassword());
			result = ps.executeUpdate();
			
	} finally {
		DBUtil.dbClose(con, ps);
		
	}
		return result;
	}
}
