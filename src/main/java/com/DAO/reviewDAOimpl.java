package com.DAO;

import com.DAO.Interface.reviewDAO;
import com.Util.JdbcConnector;
import com.model.Address;
import com.model.Review;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

public class reviewDAOimpl implements reviewDAO {

    private Review mapRow(ResultSet rs) throws SQLException {
        Review rv = new Review();
        rv.setRid(rs.getString("RID"));
        rv.setProductId(rs.getString("PID"));
        rv.setUserId(rs.getString("UserID"));
        rv.setRating(rs.getInt("Rating"));
        rv.setComment(rs.getString("Comment"));
        rv.setCreatedAt(rs.getTimestamp("CreatedAt").toLocalDateTime());
        return rv;
    }

    @Override
    public Review findById(String rid) {
        String sql = "SELECT * FROM review WHERE RID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rid);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public List<Review> findByProductId(String pid) {
        String sql = "SELECT * FROM review WHERE PID = ?";
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, pid);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public List<Review> findByUserId(String userID) {
        String sql = "SELECT * FROM review WHERE UserID = ?";
        List<Review> reviews = new ArrayList<>();
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
        ) {

            ps.setString(1, userID);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    reviews.add(mapRow(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return reviews;
    }

    @Override
    public boolean insert(Review review) {
        String sql = "INSERT INTO review (RID, UserID, PID, Rating, Comment, CreatedAt) " +
                "VALUES(?,?,?,?,?,?)";
        try (Connection conn = JdbcConnector.connect();
        PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, review.getRid());
            ps.setString(2, review.getUserId());
            ps.setString(3, review.getProductId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComment());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(review.getCreatedAt()));

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean update(Review review) {
        String sql = "UPDATE review SET RID = ?, UserID = ?, PID = ?, " +
                "Rating = ?, Comment = ?, CreatedAt = ? " +
                "WHERE RID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, review.getRid());
            ps.setString(2, review.getUserId());
            ps.setString(3, review.getProductId());
            ps.setInt(4, review.getRating());
            ps.setString(5, review.getComment());
            ps.setTimestamp(6, java.sql.Timestamp.valueOf(review.getCreatedAt()));
            ps.setString(7, review.getRid());

            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean delete(String rid) {
        String sql = "DELETE FROM review WHERE RID = ?";
        try (Connection conn = JdbcConnector.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, rid);
            int affected = ps.executeUpdate();
            return affected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Map<String, Double> getAverageRatingsForProducts(List<String> pids) {
        Map<String,Double> result=new HashMap<>();

        if (pids == null || pids.isEmpty()) {
            return result;
        }

        String placeholders = String.join(",", Collections.nCopies(pids.size(), "?"));//create the corresponding number of pid placeholders

        String sql="SELECT PID, AVG(Rating) as AvgRating "+
                "FROM review "+
                "WHERE PID IN (" + placeholders + ") " +
                "GROUP BY PID";
        try(Connection conn=JdbcConnector.connect();
        PreparedStatement ps= conn.prepareStatement(sql)) {
            for(int i=0;i<pids.size();i++){
                ps.setString(i+1,pids.get(i));
            }
            try(ResultSet rs= ps.executeQuery()){
                while(rs.next()){
                    String pid=rs.getString("PID");
                    double avgRating=rs.getDouble("AvgRating");
                    result.put(pid,avgRating);
                }
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
