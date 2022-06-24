package com.dcardprocessing.util;

import java.net.InetAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import com.dcardprocessing.bean.License;

public class JDBCConnection {

	public static License getLicense() {
		License license = null;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dcardprocessing", "root", "root");
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from license");
			while (rs.next()) {
				license = new License();
				license.setStartDate(rs.getDate(5));
				license.setEndDate(rs.getDate(3));
				license.setCount(rs.getInt(2));
				license.setTotalIP(rs.getInt(6));
				license.setIpAddress(rs.getString(4));
			}
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return license;
	}

	public static boolean updateLicense() {

		License license = getLicense();

		try {
			StringTokenizer st1 = new StringTokenizer(license.getIpAddress(), " | ");
			Set<String> ipAddressSet = new HashSet<String>();
			StringTokenizer st2 = new StringTokenizer(license.getIpAddress(), " | ");
			while (st2.hasMoreTokens()) {
				ipAddressSet.add(st2.nextToken());
			}
			InetAddress IP = InetAddress.getLocalHost();
			if (st1.countTokens() <= license.getTotalIP() && ipAddressSet.contains(IP.getHostAddress())) {
				return false;
			} else {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dcardprocessing", "root",
						"root");
				String sql = "update license set ip_address=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				if (null == license.getIpAddress() || license.getIpAddress().isEmpty()) {
					stmt.setString(1, IP.getHostAddress() + "|");
					stmt.executeUpdate();
					con.close();
					return false;
				} else {
					
					if (ipAddressSet.contains(IP.getHostAddress())) {
						return false;
					}
					else if (!ipAddressSet.contains(IP.getHostAddress()) && st1.countTokens() < license.getTotalIP()) {
						String ipAddress = IP.getHostAddress() + "|" + license.getIpAddress();
						stmt.setString(1, ipAddress);
						stmt.executeUpdate();
						return false;
					} 
					
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}

		return true;
	}

	public static boolean updateSingleLicense() {

		License license = getLicense();
		
		try {
			InetAddress IP = InetAddress.getLocalHost();
			if(license.getCount()>1 && (license.getIpAddress().equalsIgnoreCase(IP.getHostAddress()))) {
				return false;
			}else if(license.getCount()>1 && (!license.getIpAddress().equalsIgnoreCase(IP.getHostAddress()))) {
				return true;
			}{
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/dcardprocessing", "root",
						"root");
				String sql = "update license set count=? , ip_address=?";
				PreparedStatement stmt = con.prepareStatement(sql);
				if (null == license.getIpAddress() || license.getIpAddress().isEmpty()) {
					stmt.setInt(1, license.getCount() + 1);
					stmt.setString(2, IP.getHostAddress());
					stmt.executeUpdate();
					con.close();
					return false;
				} 
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}

		return true;
	}

}
