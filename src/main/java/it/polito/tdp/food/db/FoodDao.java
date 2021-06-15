package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.food.model.Arco;
import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	
	public void loadAllVertici(Map <Integer, Food> idMap,Integer porzioni){
		String sql = "SELECT f.food_code, f.display_name "
				+ "FROM food as f, portion as p "
				+ "WHERE f.food_code=p.food_code "
				+ "GROUP BY p.food_code, f.display_name "
				+ "HAVING COUNT(p.portion_id)<=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			st.setInt(1, porzioni);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				if(!idMap.containsKey(res.getInt("food_code"))) {
					Food f= new Food(res.getInt("food_code"), res.getString("display_name"));
					idMap.put(res.getInt("food_code"), f);
					System.out.println(f);
				}//if
				
			}//while
			
			conn.close();
			return  ;

		} catch (SQLException e) {
			e.printStackTrace();
			return  ;
		}

	}
	
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	
	public List<Arco> listArchi(Map <Integer, Food> idMap){ //idMAP
		String sql = "SELECT f1.food_code as f1, f2.food_code as f2, COUNT(DISTINCT f1.condiment_code) as Ncomuni, AVG(c.condiment_calories) as peso "
				+ "FROM food_condiment as f1, food_condiment as f2, condiment as c "
				+ "WHERE f1.food_code>f2.food_code and f1.condiment_code=f2.condiment_code and c.condiment_code=f1.condiment_code "
				+ "GROUP BY f1.food_code , f2.food_code " ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Arco> list = new ArrayList<Arco>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
					Food cibo1 = idMap.get(res.getInt("f1"));
					Food cibo2 = idMap.get(res.getInt("f2"));
					
					if(cibo1!=null & cibo2!=null & res.getInt("Ncomuni")>0) {
						Arco a = new Arco (cibo1,cibo2,res.getDouble("peso"));
						list.add(a);
						System.out.println(a);
					}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	
}
